package com.ns.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.HttpException;
import com.netoperation.model.PersonaliseDetails;
import com.netoperation.model.PersonaliseModel;
import com.netoperation.model.PrefListModel;
import com.netoperation.model.UserProfile;
import com.netoperation.net.ApiManager;
import com.ns.adapter.PersonaliseAdapter;
import com.ns.alerts.Alerts;
import com.ns.callbacks.THPPersonaliseItemClickListener;
import com.ns.personalisefragment.AuthorsFragment;
import com.ns.personalisefragment.CitiesFragment;
import com.ns.personalisefragment.TopicsFragment;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.IntentUtil;
import com.ns.utils.ResUtil;
import com.ns.view.CustomTextView;
import com.ns.view.ViewPagerScroller;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class THPPersonaliseActivity extends BaseAcitivityTHP implements THPPersonaliseItemClickListener {
    private ViewPager viewPager;
    private TextView mErrorText;
    private ProgressBar mProgressBar;

    ImageButton backBtn, forwardArrow, backArrow;
    private PersonaliseAdapter personaliseAdapter;
    protected final CompositeDisposable mDisposable = new CompositeDisposable();
    ArrayList<String> topics = new ArrayList<>();
    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();

    ArrayList<String> topicsAlreadySelected = new ArrayList<>();
    ArrayList<String> citiesAlreadySelected = new ArrayList<>();
    ArrayList<String> authorsAlreadySelected = new ArrayList<>();

    private CustomTextView tv_savepref, tv_items;

    private UserProfile mUserProfile;

    private PrefListModel mPrefListModel;

    @Override
    public int layoutRes() {
        return R.layout.activity_thp_personalise;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backBtn = findViewById(R.id.backBtn);
        tv_savepref = findViewById(R.id.tv_savepref);
        tv_items = findViewById(R.id.tv_items);
        forwardArrow = findViewById(R.id.forwardArrow);
        backArrow = findViewById(R.id.backArrow);
        viewPager = findViewById(R.id.personalise_viewPager);

        viewPager.setOffscreenPageLimit(2);


        // TODO, Show ProgressBar
        mProgressBar = findViewById(R.id.section_progress);
        mErrorText = findViewById(R.id.error_text);
        // This is smooth scroll of ViewPager
        smoothPagerScroll();

        tv_savepref.setOnClickListener(v->{
            topics.addAll(topicsAlreadySelected);
            cities.addAll(citiesAlreadySelected);
            authors.addAll(authorsAlreadySelected);
            if((topics!=null && topics.size()>0) || (cities!=null && cities.size()>0) ||
                    (authors!=null && authors.size()>0)) {
                saveUserPersonalise();
            }else{
                Alerts.showErrorDailog(getSupportFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.select_preference));
            }

        });

        backBtn.setOnClickListener(v->{
            IntentUtil.openContentListingActivity(THPPersonaliseActivity.this, "Personalise");
        });

        getCurrentItemPosition();
        forwardArrow.setOnClickListener(v->{
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            getCurrentItemPosition();
        });

        backArrow.setOnClickListener(v->{
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            getCurrentItemPosition();
        });

        onPageScrolling();
    }

    private void getCurrentItemPosition() {
        int selectedPosition=0;
        selectedPosition=viewPager.getCurrentItem();
        if(selectedPosition==0){
            backArrow.setImageResource(R.drawable.ic_left_arrow_disable);
            forwardArrow.setImageResource(R.drawable.ic_right_arror_enable);
            forwardArrow.setEnabled(true);
            backArrow.setEnabled(false);
        }else if(selectedPosition==1){
            backArrow.setImageResource(R.drawable.ic_left_arrow_enable);
            forwardArrow.setImageResource(R.drawable.ic_right_arror_enable);
            forwardArrow.setEnabled(true);
            backArrow.setEnabled(true);
        }else if(selectedPosition==2){
            forwardArrow.setEnabled(false);
            backArrow.setEnabled(true);
            forwardArrow.setImageResource(R.drawable.ic_right_arrow_disable);
            backArrow.setImageResource(R.drawable.ic_left_arrow_enable);
        }
    }

    private void onPageScrolling() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                getCurrentItemPosition();
                String fragName =personaliseAdapter.getPageTitle(i).toString();
                switch (i){
                    case 0:
                        tv_items.setText(fragName);
                        break;
                    case 1:
                        tv_items.setText(fragName);
                        break;
                    case 2:
                        tv_items.setText(fragName);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        loadUserProfile();
    }

    /**
     * Loads User Profile
     */
    private void loadUserProfile() {
        mDisposable.add(ApiManager.getUserProfile(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                   mUserProfile = userProfile;
                    getUserSavedPersonalise(mUserProfile.getUserId());
                 //    getUserSavedPersonalise("489"); // For Testing
                }));
    }



    private void getUserSavedPersonalise(String userId) {
        mDisposable.add(
                ApiManager.getPersonalise(userId, BuildConfig.SITEID, ResUtil.getDeviceId(this))
                .map(prefListModel ->{
                    mPrefListModel = prefListModel;
                    getAllPersonalise();
                    return "";
                })
                .subscribe(value->{

                })
        );

    }


    private void getAllPersonalise() {
             mDisposable.add(
                ApiManager.getAllPreferences()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {
                            List<Fragment> mPersonaliseFragments = new ArrayList<>();
                            PersonaliseDetails topicsFromServer = value.getTopics();
                            PersonaliseDetails citiesFromServer = value.getCities();
                            PersonaliseDetails authorsFromServer = value.getAuthors();

                            if(mPrefListModel != null) {
                                PersonaliseDetails topicsFromUser = mPrefListModel.getTopics();
                                PersonaliseDetails citiesFromUser = mPrefListModel.getCities();
                                PersonaliseDetails authorsFromUser = mPrefListModel.getAuthors();

                                ArrayList<PersonaliseModel> topicSelected = topicsFromUser.getValues();
                                if(topicSelected != null) {
                                    ArrayList<PersonaliseModel> topicDataServer = topicsFromServer.getValues();
                                    for(PersonaliseModel topicD : topicSelected) {
                                        int index = topicsFromServer.getValues().indexOf(topicD);
                                        if (index != -1) {
                                            topicDataServer.get(index).setSelected(true);
                                            topicsAlreadySelected.add(topicD.getValue());
                                        }
                                    }
                                }

                                ArrayList<PersonaliseModel> citiesSelected = citiesFromUser.getValues();
                                if(citiesSelected != null) {
                                    ArrayList<PersonaliseModel> citiesDataServer = citiesFromServer.getValues();
                                    for(PersonaliseModel cityD : citiesSelected) {
                                        int index = citiesFromServer.getValues().indexOf(cityD);
                                        if (index != -1) {
                                            citiesDataServer.get(index).setSelected(true);
                                            citiesAlreadySelected.add(cityD.getValue());
                                        }
                                    }
                                }

                                ArrayList<PersonaliseModel> authorsSelected = authorsFromUser.getValues();
                                if(authorsSelected != null) {
                                    ArrayList<PersonaliseModel> authorsDataServer = authorsFromServer.getValues();
                                    for(PersonaliseModel authorD : authorsSelected) {
                                        int index = authorsFromServer.getValues().indexOf(authorD);
                                        if (index != -1) {
                                            authorsDataServer.get(index).setSelected(true);
                                            authorsAlreadySelected.add(authorD.getValue());
                                        }
                                    }
                                }
                            }
                            mPersonaliseFragments.add(TopicsFragment.getInstance(topicsFromServer, topicsFromServer.getName()));
                            mPersonaliseFragments.add(CitiesFragment.getInstance(citiesFromServer, citiesFromServer.getName()));
                            mPersonaliseFragments.add(AuthorsFragment.getInstance(authorsFromServer, authorsFromServer.getName()));

                            String topic=value.getTopics().getName();
                            String cities=value.getCities().getName();
                            String authors=value.getAuthors().getName();

                            personaliseAdapter = new PersonaliseAdapter(getSupportFragmentManager(), mPersonaliseFragments, topic, cities, authors);
                            viewPager.setAdapter(personaliseAdapter);

                            tv_items.setText(topic);


                        }, throwable -> {
                            if (throwable instanceof HttpException || throwable instanceof ConnectException
                                    || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                                Alerts.showErrorDailog(getSupportFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                            }
                            mProgressBar.setVisibility(View.GONE);
                            mErrorText.setVisibility(View.VISIBLE);

                        }, () -> {
                            mProgressBar.setVisibility(View.GONE);
                            mErrorText.setVisibility(View.VISIBLE);
                        })
        );
    }



    private void smoothPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, new ViewPagerScroller(THPPersonaliseActivity.this,
                    new LinearInterpolator(), 250));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        mDisposable.clear();
    }

    @Override
    public void personaliseItemClick(PersonaliseModel model, String from) {
        Alerts.showToast(this, model.getTitle());
        if (from != null) {
            if (from.equalsIgnoreCase("Topics")) {
                topics.add(model.getValue());
            } else if (from.equalsIgnoreCase("Cities")) {
                cities.add(model.getValue());
            } else if (from.equalsIgnoreCase("Authors")) {
                authors.add(model.getValue());
            }
        }
    }

    /**
     * Loads User Profile Data from local Database
     */
    private void saveUserPersonalise() {
        tv_savepref.setEnabled(false);
        final ProgressDialog progress = Alerts.showProgressDialog(THPPersonaliseActivity.this);
        mDisposable.add(ApiManager.getUserProfile(this)
                .map(userProfile -> {
                    if (userProfile == null) {
                        return "";
                    }

                    // Now we are sending Personlise data to server
                    ApiManager.setPersonalise(userProfile.getUserId(), BuildConfig.SITEID, ResUtil.getDeviceId(THPPersonaliseActivity.this),
                            topics, cities, authors)
                            .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bool->{
                        Alerts.showToast(THPPersonaliseActivity.this, "Your personalise is updated successfully.");
                        IntentUtil.openContentListingActivity(THPPersonaliseActivity.this, "Personalise");
                    }, throwable -> {
                        if (throwable instanceof HttpException || throwable instanceof ConnectException
                                || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                            Alerts.showErrorDailog(getSupportFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                        }
                        tv_savepref.setEnabled(true);
                        progress.dismiss();
                    }, ()->{
                        tv_savepref.setEnabled(true);
                        progress.dismiss();
                    })
                    ;

                    return "";
                })
                .subscribe(v -> {
                        },
                        t ->
                                Log.i("", "" + t)
                ));
    }
}
