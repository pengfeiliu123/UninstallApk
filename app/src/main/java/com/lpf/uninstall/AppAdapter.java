package com.lpf.uninstall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupengfei on 17/1/17.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    public List<AppInfo> datas = new ArrayList<AppInfo>();
    public List<AppInfo> checkedDatas = new ArrayList<AppInfo>();
    private Context mContext;
    private List<Integer> checkPositionlist;

    public AppAdapter(Context context, List<AppInfo> datas) {
        this.datas = datas;
        this.mContext = context;
        checkPositionlist = new ArrayList<>();
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_layout, null);
        AppViewHolder holder = new AppViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        AppInfo appInfo = datas.get(position);
        holder.appName.setText(appInfo.getAppName());
        holder.pgkName.setText(appInfo.getPkgName());
        holder.versionCode.setText(appInfo.getVerionCode());
        holder.appIcon.setImageDrawable(appInfo.getAppIcon());
        holder.appCheck.setTag(new Integer(position));

        //checkbox  复用问题
        if (checkPositionlist != null) {
            holder.appCheck.setChecked((checkPositionlist.contains(new Integer(position)) ? true : false));
        } else {
            holder.appCheck.setChecked(false);
        }

        // click event just write here
        holder.appItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.appCheck.isChecked() ? false : true;
                holder.appCheck.setChecked(isChecked);
            }
        });

        holder.appCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeClickStated(isChecked, position, holder);
            }
        });
    }

    private void changeClickStated(boolean isChecked, int position, AppViewHolder holder) {
        AppInfo appInfo = datas.get(position);
        if (isChecked) {
            if (!checkPositionlist.contains(holder.appCheck.getTag())) {
                checkedDatas.add(appInfo);
                checkPositionlist.add(new Integer(position));
            }
        } else {
            if (checkPositionlist.contains(holder.appCheck.getTag())) {
                checkedDatas.remove(appInfo);
                checkPositionlist.remove(new Integer(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        View appItemView;
        public TextView appName;
        public TextView pgkName;
        public ImageView appIcon;
        public CheckBox appCheck;
        public TextView versionCode;

        public AppViewHolder(View itemView) {
            super(itemView);
            appItemView = itemView;
            appName = (TextView) itemView.findViewById(R.id.appName);
            pgkName = (TextView) itemView.findViewById(R.id.pgkName);
            versionCode = (TextView) itemView.findViewById(R.id.versionCode);
            appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
            appCheck = (CheckBox) itemView.findViewById(R.id.appCheck);
        }
    }

    public List<AppInfo> getDatas() {
        return checkedDatas;
    }

    // clear all selected datas
    public void clearSelectedDatas() {
        if (checkedDatas != null) {
            checkedDatas.clear();
        }
        if (checkPositionlist != null) {
            checkPositionlist.clear();
        }
    }

    public void unSelectedData(AppInfo appInfo) {
        if (checkedDatas != null) {
            checkedDatas.remove(appInfo);
        }
        if (checkPositionlist != null) {
            checkPositionlist.remove(appInfo);
        }
    }

    // select all datas
    public void selectedAllDatas() {
        checkedDatas.addAll(datas);
        for (int i = 0; i < datas.size(); i++) {
            checkPositionlist.add(i);
        }
    }

    //add data
    public void addData(int position, AppInfo newApp) {
        datas.add(position, newApp);             // add
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }
}
