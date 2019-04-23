package com.netoperation.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.netoperation.util.NetConstants.EVENT_CHANGE_ACCOUNT_STATUS;
import static com.netoperation.util.NetConstants.EVENT_FORGOT_PASSWORD;
import static com.netoperation.util.NetConstants.EVENT_SIGNUP;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public interface RetentionDef {

    @Retention(SOURCE)
    @StringDef({EVENT_SIGNUP, EVENT_FORGOT_PASSWORD, EVENT_CHANGE_ACCOUNT_STATUS})
    @interface userVerificationMode {}
}
