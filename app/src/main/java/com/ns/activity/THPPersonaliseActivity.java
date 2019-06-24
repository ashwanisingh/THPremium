package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.netoperation.model.PersonaliseModel;
import com.netoperation.model.PrefListModel;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.adapter.AppTabPagerAdapter;
import com.ns.adapter.PersonaliseAdapter;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.alerts.Alerts;
import com.ns.callbacks.THPPersonaliseItemClickListener;
import com.ns.personalisefragment.AuthorsFragment;
import com.ns.personalisefragment.CitiesFragment;
import com.ns.personalisefragment.TopicsFragment;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.view.ViewPagerScroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class THPPersonaliseActivity extends BaseAcitivityTHP implements THPPersonaliseItemClickListener {

    private ViewPager viewPager;
    private PersonaliseAdapter personaliseAdapter;
    protected final CompositeDisposable mDisposable = new CompositeDisposable();
    ArrayList<String> topics = new ArrayList<>();
    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();

    @Override
    public int layoutRes() {
        return R.layout.activity_thp_personalise;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = findViewById(R.id.personalise_viewPager);
        viewPager.setOffscreenPageLimit(2);

        // TODO, Show ProgressBar
        // This is smooth scroll of ViewPager
        smoothPagerScroll();

        loadData();
    }


    private void loadData() {
             mDisposable.add(
                ApiManager.getPrefList(NetConstants.USER_ID,
                BuildConfig.SITEID, NetConstants.ITEM_SIZE, NetConstants.RECO_ALL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {

                            List<Fragment> mPersonaliseFragments = new ArrayList<>();

                            mPersonaliseFragments.add(TopicsFragment.getInstance(value.getTopicsModels(), "Topics"));
                            mPersonaliseFragments.add(CitiesFragment.getInstance(value.getCitiesModels(), "Cities"));
                            mPersonaliseFragments.add(AuthorsFragment.getInstance(value.getAuthorsModels(), "Authors"));

                            personaliseAdapter = new PersonaliseAdapter(getSupportFragmentManager(), mPersonaliseFragments);
                            viewPager.setAdapter(personaliseAdapter);

                        }, throwable -> {
                                // TODO, Error Handling,
                                // TODO, Hide ProgressBar
                                // TODO, Show Error Alert Dialog
                        }, () -> {
                                // TODO, Hide ProgressBar

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
        Alerts.showToast(this, model.getName());
        JSONObject mPrefObject=new JSONObject();
        if(from!=null) {
            if (from.equalsIgnoreCase("Topics")) {
                topics.add(model.getName());
            } else if (from.equalsIgnoreCase("Cities")) {
                cities.add(model.getName());
            }else{
                authors.add(model.getName());
            }
        }
        try {
            mPrefObject.put("author", authors);
            mPrefObject.put("city", cities);
            mPrefObject.put("topics", topics);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
