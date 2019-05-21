package com.ns.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ns.callbacks.ToolbarClickListener;
import com.ns.model.ToolbarCallModel;
import com.ns.thpremium.R;


/**
 * Created by ashwanisingh on 21/09/18.
 */

public class CustomToolbar extends Toolbar {

    private ToolbarClickListener mToolbarClickListener;
    private TextView mTitleTextView;
    private ImageView mBackImageView;
    private ImageView mShareImageView;
    private ImageView mCreateBookMarkImageView;
    private ImageView mRemoveBookMarkedImageView;
    private ImageView mTextSizeImageView;
    private ImageView mCommentImageView;
    private ImageView mTTSPlayImageView;
    private ImageView mTTSPauseImageView;
    private ProgressBar mTtsProgress;

    private View mView;
    private String mTitle;
    private ToolbarCallModel mToolbarCallModel;

    public CustomToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        mView = LayoutInflater.from(context).inflate(R.layout.toolbar_for_detail, this, true);

        if(mView == null) {
            return;
        }

        mTitleTextView =  findViewById(R.id.title);
        mBackImageView = (ImageView) findViewById(R.id.back);
        mShareImageView = findViewById(R.id.shareIC);
        mCreateBookMarkImageView = findViewById(R.id.bookmarkIC);
        mRemoveBookMarkedImageView = findViewById(R.id.bookmarkedIC);
        mTextSizeImageView = findViewById(R.id.fontSizeIC);
        mCommentImageView = findViewById(R.id.commentIC);
        mTTSPlayImageView = findViewById(R.id.ttsPlayIC);
        mTTSPauseImageView = findViewById(R.id.ttsPauseIC);
        mTtsProgress = findViewById(R.id.ttsProgress);

        if(mBackImageView != null) {
            mBackImageView.setOnClickListener(v -> {
                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onBackClickListener();
                }
            });
        }



        if(mShareImageView != null) {
            mShareImageView.setOnClickListener(v->{
                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onShareClickListener(mToolbarCallModel);
                }
            });
        }

        if(mCreateBookMarkImageView != null) {

            // CLick Listener
            mCreateBookMarkImageView.setOnClickListener(v->{
                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onCreateBookmarkClickListener(mToolbarCallModel);
                }
            });
        }

        if(mRemoveBookMarkedImageView != null) {
            // CLick Listener
            mRemoveBookMarkedImageView.setOnClickListener(v->{
                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onRemoveBookmarkClickListener(mToolbarCallModel);
                }
            });
        }

        if(mTextSizeImageView != null) {
            mTextSizeImageView.setOnClickListener(v->{
                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onFontSizeClickListener(mToolbarCallModel);
                }
            });
        }

        if(mCommentImageView != null) {
            mCommentImageView.setOnClickListener(v->{
                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onCommentClickListener(mToolbarCallModel);
                }
            });
        }

        if(mTTSPlayImageView != null) {
            mTTSPlayImageView.setOnClickListener(v->{

                mTtsProgress.setVisibility(VISIBLE);
                mTTSPlayImageView.setVisibility(GONE);

                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onTTSPlayClickListener(mToolbarCallModel);
                }


//                mTTSPauseImageView.setVisibility(VISIBLE);
            });
        }

        if(mTTSPauseImageView != null) {
            mTTSPauseImageView.setOnClickListener(v->{
                mTtsProgress.setVisibility(VISIBLE);
                mTTSPauseImageView.setVisibility(GONE);

                if (mToolbarClickListener != null) {
                    mToolbarClickListener.onTTSStopClickListener(mToolbarCallModel);
                }



//                mTTSPlayImageView.setVisibility(VISIBLE);
            });
        }


        setToolbarTitle(mTitle);
    }

    public void setIsBookmarked(boolean isBookmarked) {
        View view = null;
        if(mCreateBookMarkImageView != null) {
            if(isBookmarked) {
                mCreateBookMarkImageView.setVisibility(GONE);
            } else {
                mCreateBookMarkImageView.setVisibility(VISIBLE);
                view = mCreateBookMarkImageView;
            }
        }
        if(mRemoveBookMarkedImageView != null) {
            if(isBookmarked) {
                mRemoveBookMarkedImageView.setVisibility(VISIBLE);
                view = mRemoveBookMarkedImageView;
            } else {
                mRemoveBookMarkedImageView.setVisibility(GONE);
            }
        }

    }


    public void setToolbarTextSize(int size) {
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public void setTitle(int resId) {

    }


    public void setToolbarTitle(CharSequence title) {
        if(mTitleTextView != null) {
            mTitleTextView.setText(title);
            mTitleTextView.setVisibility(VISIBLE);
        }
    }




    public void setToolbarTitle(int resId) {
        mTitleTextView.setText(resId);
    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        if(icon == null) {
            mBackImageView.setImageDrawable(icon);
            mBackImageView.setVisibility(GONE);
        } else {
            mBackImageView.setImageDrawable(icon);
            mBackImageView.setVisibility(VISIBLE);
        }
    }

    public TextView getTitleView() {
        return mTitleTextView;
    }

    public ImageView getBackView() {
        return mBackImageView;
    }


    public void hideBackBtn() {
        mBackImageView.setVisibility(GONE);
    }

    public void showBackBtn() {
        mBackImageView.setVisibility(VISIBLE);
    }



    public void hideShareView() {
        if(mShareImageView != null) {
            mShareImageView.setVisibility(GONE);
        }
    }

    public void showTTSPlayView(int isLanguageSupportTTS) {
        if(isLanguageSupportTTS == 0) {
            if(mTTSPlayImageView != null) {
                mTTSPlayImageView.setVisibility(GONE);
                mTTSPauseImageView.setVisibility(GONE);
            }
        } else {
            if (mTTSPauseImageView != null) {
                mTTSPauseImageView.setVisibility(GONE);
                mTTSPlayImageView.setVisibility(VISIBLE);
                mTtsProgress.setVisibility(GONE);
            }
        }
    }

    public void showTTSPauseView(int isLanguageSupportTTS) {
        if(isLanguageSupportTTS == 0) {
            if(mTTSPlayImageView != null) {
                mTTSPlayImageView.setVisibility(GONE);
                mTTSPauseImageView.setVisibility(GONE);
            }
        } else {
            if (mTTSPauseImageView != null) {
                mTTSPauseImageView.setVisibility(VISIBLE);
                mTTSPlayImageView.setVisibility(GONE);
                mTtsProgress.setVisibility(GONE);
            }
        }
    }

    public void setToolbarMenuListener(ToolbarClickListener toolbarClickListener) {
        mToolbarClickListener = toolbarClickListener;
    }

    public ToolbarCallModel getToolbarCallModel() {
        return mToolbarCallModel;
    }

    public void setToolbarCallModel(ToolbarCallModel toolbarCallModel) {
        this.mToolbarCallModel = toolbarCallModel;
    }
}
