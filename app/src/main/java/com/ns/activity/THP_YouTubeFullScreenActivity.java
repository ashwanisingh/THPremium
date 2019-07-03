package com.ns.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ns.thpremium.R;

/**
 * Created by ashwani on 16/10/16.
 */

public class THP_YouTubeFullScreenActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnFullscreenListener, YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayerView playerView;
    private String mVideoId;

    private int mVideoViewLayoutWidth = 0;
    private int mVideoViewLayoutHeight = 0;
    private boolean mAutoRotation = true;


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thp_activity_youttubefullscreen);

        playerView = (YouTubePlayerView) findViewById(R.id.youTubePlayer);
        playerView.setVisibility(View.VISIBLE);
        mVideoId = getIntent().getStringExtra("videoId");
        playerView.initialize("AIzaSyBENdg9kEoEsb8WrOWIUk7cS0HPgU0BztQ", this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);
        if (!wasRestored) {
            player.cueVideo(mVideoId);
        }
        // player.setFullscreen(true);
        // player.setShowFullscreenButton(false);
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            Toast.makeText(this, "There was an error initializing the YouTubePlayer " + errorReason.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            playerView.initialize("AIzaSyBENdg9kEoEsb8WrOWIUk7cS0HPgU0BztQ", this);
        }
    }




    private Activity activity;


    public void setFullscreen(boolean fullscreen, int screenOrientation) {
        if (activity == null) {
            activity = this;
        }

        if (fullscreen) {
            if (mVideoViewLayoutWidth == 0 && mVideoViewLayoutHeight == 0) {
                ViewGroup.LayoutParams params = playerView.getLayoutParams();
                mVideoViewLayoutWidth = params.width;
                mVideoViewLayoutHeight = params.height;
            }
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.setRequestedOrientation(screenOrientation);
        } else {
            ViewGroup.LayoutParams params = playerView.getLayoutParams();
            params.width = mVideoViewLayoutWidth;
            params.height = mVideoViewLayoutHeight;
            playerView.setLayoutParams(params);

            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.setRequestedOrientation(screenOrientation);
        }
        onScaleChange(fullscreen);
    }

    public void onScaleChange(boolean isFullscreen) {
        if (activity == null) {
            activity = this;
        }

        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = playerView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = playerView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(layoutParams);
        }

    }




    @Override
    public void onFullscreen(boolean b) {

    }
}
