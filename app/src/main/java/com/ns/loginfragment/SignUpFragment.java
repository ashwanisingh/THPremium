package com.ns.loginfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.load.HttpException;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.ns.activity.SignInAndUpActivity;
import com.ns.alerts.Alerts;
import com.ns.sharedpreference.THPPreferences;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.utils.ValidationUtil;
import com.ns.view.CustomProgressBar;
import com.ns.view.CustomTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SignUpFragment extends BaseFragmentTHP {

    public static SignUpFragment getInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    private EditText emailOrMobile_Et;
    private CustomProgressBar progressBar;

    private CustomTextView tc_Txt;
    private CustomTextView faq_Txt;
    private CustomTextView signUp_Txt;

    private ImageButton googleBtn;
    private ImageButton tweeterBtn;
    private ImageButton facebookBtn;

    private boolean isUserEnteredEmail;
    private boolean isUserEnteredMobile;

    private SignInAndUpActivity mActivity;

    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;

    //Tag for the logs optional
    private static final String TAG = "GoogleSignIn";

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    GoogleSignInAccount alreadyloggedAccount;

    // For facebook Login
    private CallbackManager callbackManager;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_signup;
    }

    private void enableButton(boolean isEnable) {
        if(isEnable) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        signUp_Txt.setEnabled(isEnable);
        googleBtn.setEnabled(isEnable);
        tweeterBtn.setEnabled(isEnable);
        facebookBtn.setEnabled(isEnable);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SignInAndUpActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailOrMobile_Et = view.findViewById(R.id.emailOrMobile_Et);
        tc_Txt = view.findViewById(R.id.tc_Txt);
        faq_Txt = view.findViewById(R.id.faq_Txt);
        signUp_Txt = view.findViewById(R.id.signUp_Txt);
        progressBar = view.findViewById(R.id.progressBar);

        googleBtn = view.findViewById(R.id.googleBtn);
        tweeterBtn = view.findViewById(R.id.tweeterBtn);
        facebookBtn = view.findViewById(R.id.facebookBtn);

        // Terms and Conditions Click Listener
        ResUtil.doClickSpanForString(getActivity(), "By signing up, you agree to our  ",
                "Terms and Conditions",
                tc_Txt, R.color.blueColor_1, new TextSpanCallback() {
                    @Override
                    public void onTextSpanClick() {
                        TCFragment fragment = TCFragment.getInstance(THPConstants.TnC_URL);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout,
                                fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
                    }
                });

        signUp_Txt.setOnClickListener(v->{

            String emailOrMobile = emailOrMobile_Et.getText().toString();
            String mobileStr = "";
            String emailStr = "";

            isUserEnteredMobile = false;
            isUserEnteredEmail = false;

            if(ValidationUtil.isValidMobile(emailOrMobile)) {
                isUserEnteredMobile = true;
                isUserEnteredEmail = false;
                mobileStr = emailOrMobile;
            }

            if(ValidationUtil.isValidEmail(emailOrMobile)) {
                isUserEnteredEmail = true;
                isUserEnteredMobile = false;
                emailStr = emailOrMobile;
            }

            if(!isUserEnteredMobile && !isUserEnteredEmail) {
                Alerts.showAlertDialogNoBtnWithCancelable(getActivity(), "", "\nPlease enter valid Email or Mobile \n");
                return;
            }

            signUp_Txt.setEnabled(false);

            progressBar.setVisibility(View.VISIBLE);

            String mobile = mobileStr;
            String email = emailStr;

            // Hide SoftKeyboard
            CommonUtil.hideKeyboard(getView());

            ApiManager.userVerification(new RequestCallback<Boolean>() {
                @Override
                public void onNext(Boolean bool) {
                    if(getActivity() == null && getView() == null) {
                        return;
                    }

                    if(!bool) {
                        if(isUserEnteredMobile) {
                            Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Mobile already exist");
                        }
                        else {
                            Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Email already exist");
                        }
                    }
                    else {
                         // Opening OTP Verification Screen
                        OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(THPConstants.FROM_SignUpFragment,
                                isUserEnteredEmail, email, mobile);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                                FragmentUtil.FRAGMENT_ANIMATION, false);

                    }

                }

                @Override
                public void onError(Throwable t, String str) {
                    if(getActivity() != null && getView() != null) {
                        progressBar.setVisibility(View.GONE);
                        signUp_Txt.setEnabled(true);
                        Alerts.showErrorDailog(getChildFragmentManager(), null, t.getLocalizedMessage());
                    }
                }

                @Override
                public void onComplete(String str) {
                    progressBar.setVisibility(View.GONE);
                    signUp_Txt.setEnabled(true);
                }
            }, emailStr, mobileStr, BuildConfig.SITEID);



        });
        // Google Sign in click listener
        mAuth = FirebaseAuth.getInstance();

        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        googleBtn.setOnClickListener(v->{
            if (alreadyloggedAccount != null) {
                Alerts.showToast(getActivity(), "User Already Logged In with Gmail");
            }else{
                signIn();
            }
        });

        // Facebook Sign in click listener
        facebookBtn.setOnClickListener(v->{
            //  if(AccessToken.getCurrentAccessToken()!=null){
            //      Alerts.showToast(getActivity(), "User Already Logged In with Facebook");
            //  }else{
            Fblogin();
            //    }
        });

    }

    /*Google Sign In methods starts here*/
    @Override
    public void onStart() {
        super.onStart();
        //To check user already Logged in
        alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        getGmailLoginDetails(acct);
    }

    private void getGmailLoginDetails(GoogleSignInAccount acct) {
        String provider="Google";
        String socialId=acct.getId();
        String userEmail=acct.getEmail();
        String userName=acct.getDisplayName();

        ApiManager.socialLogin(ResUtil.getDeviceId(getActivity()), BuildConfig.ORIGIN_URL, provider, socialId, userEmail, userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userId -> {
                            if (getActivity() == null && getView() == null) {
                                return;
                            }

                            if (TextUtils.isEmpty(userId)) {
                                if (isUserEnteredMobile) {
                                    Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "User Mobile number not found.");
                                } else {
                                    Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "User email not found.");
                                }
                            } else {
                                // Making server request to get User Info
                                ApiManager.getUserInfo(getActivity(), BuildConfig.SITEID, ResUtil.getDeviceId(getActivity()), userId)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(bool->{

                                            if(bool) {
                                                // TODO, process for user sign - In
                                                Alerts.showToast(getActivity(), "Successfully Logged In with Gmail.");
                                                IntentUtil.openContentListingActivity(getActivity(), "");
                                            }
                                            else {
                                                Alerts.showErrorDailog(getChildFragmentManager(), "Sorry",
                                                        "We are fetching some technical problem.\n Please try later.");
                                            }

                                        }, throwable -> {
                                            enableButton(true);
                                            if (throwable instanceof HttpException || throwable instanceof ConnectException
                                                    || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                                                Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                            }
                                            else {
                                                Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                                            }
                                        }, () ->{
                                            enableButton(true);
                                        });


                            }
                        }, throwable -> {
                            if (getActivity() != null && getView() != null) {
                                enableButton(true);
                                if (throwable instanceof HttpException || throwable instanceof ConnectException
                                        || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                                    Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                }
                                else {
                                    Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                                }
                            }
                        },
                        () -> {

                        });

    }

    /*Google Sign In methods ends here*/


    /*Facebook Sign In methods starts here*/
    private void Fblogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                        } else {
                                            getFaceBookLoginDetails(json);
                                        }
                                    }

                                }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Alerts.showToast(getActivity(), "Facebook Login Cancelled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Alerts.showToast(getActivity(), "Error Occurred while Sign In");
                    }
                });
    }

    private void getFaceBookLoginDetails(JSONObject json) {
        String provider;
        String userEmail;
        String socialId;
        String userName;
        try {
            provider="Facebook";
            socialId=json.getString("id");
            userName=json.getString("name");
            if(json.has("email")){
                if(json.getString("email")==null || json.get("email").equals("")){
                    Alerts.showAlertDialogOKBtn(getActivity(), "Sorry !", " You didn't find your primary contact details, please make it is visible to create the account from social Login");
                }else{
                    userEmail=json.getString("email");
                    ApiManager.socialLogin(ResUtil.getDeviceId(getActivity()), BuildConfig.ORIGIN_URL, provider, socialId, userEmail, userName)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(userId -> {
                                        if (getActivity() == null && getView() == null) {
                                            return;
                                        }

                                        if (TextUtils.isEmpty(userId)) {
                                            if (isUserEnteredMobile) {
                                                Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "User Mobile number not found.");
                                            } else {
                                                Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "User email not found.");
                                            }
                                        } else {
                                            // Making server request to get User Info
                                            ApiManager.getUserInfo(getActivity(), BuildConfig.SITEID, ResUtil.getDeviceId(getActivity()), userId)
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(bool->{

                                                        if(bool) {
                                                            // TODO, process for user sign - In
                                                            Alerts.showToast(getActivity(), "Successfully Logged In with Facebook.");
                                                            IntentUtil.openContentListingActivity(getActivity(), "");
                                                        }
                                                        else {
                                                            Alerts.showErrorDailog(getChildFragmentManager(), "Sorry",
                                                                    "We are fetching some technical problem.\n Please try later.");
                                                        }

                                                    }, throwable -> {
                                                        enableButton(true);
                                                        if (throwable instanceof HttpException || throwable instanceof ConnectException
                                                                || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                                                            Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                                        }
                                                        else {
                                                            Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                                                        }
                                                    }, () ->{
                                                        enableButton(true);
                                                    });


                                        }
                                    }, throwable -> {
                                        if (getActivity() != null && getView() != null) {
                                            enableButton(true);
                                            if (throwable instanceof HttpException || throwable instanceof ConnectException
                                                    || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                                                Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                            }
                                            else {
                                                Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                                            }
                                        }
                                    },
                                    () -> {

                                    });
                }
            }else{
                Alerts.showAlertDialogOKBtn(getActivity(), "Sorry !", " We didn't find your primary contact details, please make it is visible to create the account from social Login");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Alerts.showToast(getActivity(), "Error while getting the values");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        /*For Facebook callback*/
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
