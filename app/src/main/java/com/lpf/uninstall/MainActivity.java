package com.lpf.uninstall;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import com.android.pm.IPackageStatsObserver;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
    RecyclerView recyclerView;
    AppAdapter appAdapter;
    LinearLayout btnDelete;
    CheckBox btnCheckAll;
    AppInfo mAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAds();
    }

    private void initViews() {

        recyclerView = (RecyclerView) findViewById(R.id.content_rv);
        btnDelete = (LinearLayout) findViewById(R.id.deleteBtn);
        btnCheckAll = (CheckBox) findViewById(R.id.checkAll);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        appAdapter = new AppAdapter(this, appList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(appAdapter);
        appAdapter.notifyDataSetChanged();

        btnDelete.setOnClickListener(this);
        btnCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appAdapter.selectedAllDatas();
                    appAdapter.notifyDataSetChanged();
                } else {
                    appAdapter.clearSelectedDatas();
                    appAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    private void initAds() {

//        AdView mAdView1 = (AdView)findViewById(R.id.adView1);
//        AdRequest adRequest2 = new AdRequest.Builder().build();
//        mAdView1.loadAd(adRequest);

//        AdView mAdView2 = (AdView)findViewById(R.id.adView2);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView2.loadAd(adRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAppList();
        appAdapter.notifyDataSetChanged();
        appAdapter.clearSelectedDatas();                // clear all selected datas
    }

    //uninstall apks
    private void uninstallApks(List<AppInfo> apks) {
        for (AppInfo appInfo : apks) {
            mAppInfo = appInfo;
            Uri uri = Uri.parse("package:" + appInfo.pkgName);
            Intent intent = new Intent(Intent.ACTION_DELETE, uri);
            startActivity(intent);
        }
    }

    // install apk
//    private void installApks(String fileName){
//        Intent intent = new Intent();
//        intent.setAction(intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.parse("file://"+fileName),"application/vnd.android.package-archive");
//        startActivity(intent);
//    }

    public void getAppList() {
        if (appList != null && appList.size() > 0) {
            appList.clear();
        }
        PackageManager pm = getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            if (packageInfo.packageName.equals("com.lpf.uninstall")) {
                continue;
            }
            boolean flag = false;
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                flag = true;
            } else if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                flag = true;
            }
            if (flag) {

                AppInfo appInfo = new AppInfo();
                appInfo.pkgName = packageInfo.packageName;
                appInfo.appName = packageInfo.applicationInfo.loadLabel(pm).toString();
                appInfo.appIcon = packageInfo.applicationInfo.loadIcon(pm);
                appInfo.appIntent = pm.getLaunchIntentForPackage(packageInfo.packageName);
                appInfo.verionCode = packageInfo.versionName;
                appInfo.time = packageInfo.lastUpdateTime;
                appList.add(appInfo);
            }
        }

        Collections.sort(appList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteBtn:
                uninstallMethod();
                break;

        }
    }

    private void uninstallMethod() {
        if (appAdapter.getDatas() != null && appAdapter.getDatas().size() > 0) {
            uninstallApks(appAdapter.getDatas());
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_choose, Toast.LENGTH_SHORT).show();
        }
    }

    private BigDecimal parseApkSize(int size) {
        BigDecimal bd = new BigDecimal((double) size / (1024 * 1024));
        BigDecimal setScale = bd.setScale(2, BigDecimal.ROUND_DOWN);
        return setScale;
    }
}
