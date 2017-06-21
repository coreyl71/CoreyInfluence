package com.dlab.coreyinfluence.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.adapter.RankListVPAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class RankListFragment extends Fragment {

    // 定义FragmentTabHost对象
    private TabLayout tl_rank_classify;
    private ViewPager vp_rank_list;

    // 定义一个布局
    private LayoutInflater layoutInflater;

    // Tab选项卡的文字集合
    private ArrayList<String> titleList;

    private RankListVPAdapter rankListVPAdapter;

    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = {StarRankListFrag.class, OStarRankListFrag.class};

    // Tab选项卡的文字
    private String mTextviewArray[] = {"明星影响力", "网红影响力"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rank_list,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 找控件
        initViews(view);

        // 初始化标签
        initTab();

    }

    private void initTab() {
        titleList = new ArrayList<>();
        titleList.add("明星");
        titleList.add("网红");

        rankListVPAdapter = new RankListVPAdapter(getChildFragmentManager(), titleList);
        vp_rank_list.setAdapter(rankListVPAdapter);

        // 将ViewPager绑定到TabLayout
        tl_rank_classify.setupWithViewPager(vp_rank_list);

        // 设置TabLayout下面线的长度，数字就是左右的padding
        setIndicator(getContext(), tl_rank_classify, 70, 70);

    }

    private void initViews(View view) {

        // 实例化布局对象
        layoutInflater = LayoutInflater.from(getContext());

        // 实例化TabHost对象，得到TabHost
        tl_rank_classify = (TabLayout) view.findViewById(R.id.tl_rank_classify);
        vp_rank_list = (ViewPager) view.findViewById(R.id.vp_rank_list);

        // 设置TabLayout标签的颜色
        tl_rank_classify.setTabTextColors(Color.parseColor("#77FFFFFF"), Color.parseColor("#FFFFFF"));
        tl_rank_classify.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
//        tl_rank_classify.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) (getDisplayMetrics(context).density * leftDip);
        int right = (int) (getDisplayMetrics(context).density * rightDip);

        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public static float getPXfromDP(float value, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

}
