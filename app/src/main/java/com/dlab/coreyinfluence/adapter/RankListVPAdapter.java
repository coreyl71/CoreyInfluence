package com.dlab.coreyinfluence.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.dlab.coreyinfluence.fragment.OStarRankListFrag;
import com.dlab.coreyinfluence.fragment.StarRankListFrag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corey on 2017/6/1.
 */

public class RankListVPAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> datas;
    private List<String> titleList;

    // 适配器构造方法
    public RankListVPAdapter(FragmentManager fm, List<String> titleList) {
        super(fm);
        this.titleList = titleList;
        init();
    }

    // 页面显示的初始化
    private void init() {
        // 用datas集合存放fragment
        datas = new ArrayList<>();

        for (int i = 0; i < titleList.size(); i++) {

            if (i == 0) {
                // 明星排行榜标签下的fragment
                datas.add(new StarRankListFrag());
            } else {
                // 网红排行榜标签下的fragment
                datas.add(new OStarRankListFrag());
            }

        }
        // 刷新适配器
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return null == titleList ? 0 : titleList.size();
    }

    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}
