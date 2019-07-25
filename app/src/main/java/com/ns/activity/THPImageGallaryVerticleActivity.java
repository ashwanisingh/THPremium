package com.ns.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ns.adapter.GalleryVerticleAdapter;
import com.ns.model.ImageGallaryUrl;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class THPImageGallaryVerticleActivity extends BaseAcitivityTHP implements GalleryVerticleAdapter.OnCheckPermisssion{
    private final String TAG = "THPImageGallaryActivity";
    private TextView mErrorText;
    private ProgressBar mProgressBar;
    private LinearLayout mProgressContainer;
    private ArrayList<ImageGallaryUrl> mImageUrlList;

    private RecyclerView mRecyclerView;

    private GalleryVerticleAdapter galleryAdapter;

    // Permission
    public static final int MY_PERMISSIONS_REQUEST = 123;
    Context mContext = this;
    private String[] permissionList = null;

    private List<String> mImageListToFolder ;


    @Override
    public int layoutRes() {
        return R.layout.activity_image_gallary_verticle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageUrlList = getIntent().getParcelableArrayListExtra("ImageUrl");

        mProgressBar = (ProgressBar) findViewById(R.id.section_progress);


        // Check Permission
        int permission = ContextCompat.checkSelfPermission(THPImageGallaryVerticleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission == 0) {
            mImageListToFolder = CommonUtil.getFolderImageList();
        } else {
            mImageListToFolder = new ArrayList<>();
        }

        mErrorText = (TextView) findViewById(R.id.error_text);
        mProgressContainer = (LinearLayout) findViewById(R.id.progress_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (mImageUrlList != null && mImageUrlList.size() > 0) {
            mProgressContainer.setVisibility(View.GONE);
            galleryAdapter = new GalleryVerticleAdapter(this, mImageUrlList, getIntent().getStringExtra("title"), mImageListToFolder);
            galleryAdapter.setOnChecPermission(this);
            mRecyclerView.setAdapter(galleryAdapter);

            setToolbarTitle(1, galleryAdapter.getItemCount());

        } else {
            mProgressBar.setVisibility(View.GONE);
            mErrorText.setVisibility(View.VISIBLE);
        }


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = ((LinearLayoutManager)mRecyclerView.getLayoutManager());


                int fvp = layoutManager.findFirstVisibleItemPosition();
                int fcvip = layoutManager.findFirstCompletelyVisibleItemPosition();
                int lvip = layoutManager.findLastVisibleItemPosition();
                int lcvip =layoutManager.findLastCompletelyVisibleItemPosition();



                setToolbarTitle((fcvip+1), galleryAdapter.getItemCount());

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setToolbarTitle(int selectedPosition, int total) {
        getToolbar().setToolbarTitle("Photo "+selectedPosition+" of "+total);
    }


    /**
     * Check Permission for Download button click
     * @interface
     */
    @Override
    public void onItemCheckPermission() {
        boolean result = checkPermission();
        if (result) {
            galleryAdapter.setStoragePermission(true);
        } else {
            galleryAdapter.setStoragePermission(false);
        }
    }

    /**
     * Gets user WRITE OR READ EXTERNAL STORAGE.
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean checkPermission() {

        this.permissionList = new String[]{
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
        };

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.KITKAT) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!isPermissionsAccepted()) {
                    ActivityCompat.requestPermissions((Activity) mContext, permissionList, MY_PERMISSIONS_REQUEST);
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext, permissionList, MY_PERMISSIONS_REQUEST);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private boolean isPermissionsAccepted() {
        boolean isAllPermissionsAccepted = true;
        for (int i = 0; i < this.permissionList.length; ++i) {
            isAllPermissionsAccepted = isAllPermissionsAccepted && ContextCompat.checkSelfPermission(this, this.permissionList[i]) == 0;
        }
        return isAllPermissionsAccepted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    //code for deny
                    finish();
                }
                break;
        }
    }


}
