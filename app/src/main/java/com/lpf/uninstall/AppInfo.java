package com.lpf.uninstall;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by liupengfei on 17/1/17.
 */

public class AppInfo implements Comparable {
    public String pkgName;
    public String appName;
    public Drawable appIcon;
    public Intent appIntent;
    public String verionCode;
    public String memorySize;
    public boolean checked = false;

    public long time;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getAppIntent() {
        return appIntent;
    }

    public void setAppIntent(Intent appIntent) {
        this.appIntent = appIntent;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getVerionCode() {
        return verionCode;
    }

    public void setVerionCode(String verionCode) {
        this.verionCode = verionCode;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 0;
        }
        return (((AppInfo)o).getTime()).compareTo(this.getTime());
    }
}
