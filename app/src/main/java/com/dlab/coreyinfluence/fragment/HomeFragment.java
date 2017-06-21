package com.dlab.coreyinfluence.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.adapter.HomeAdapter;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;

/**
 * 首页Fragment
 */

public class HomeFragment extends Fragment {

    //缓存Fragment view
    private View rootView;

    // RecyclerView
    private RecyclerView rcv_home_frag;
    private PullToRefreshScrollView ptr_home_frag;

    /**
     * 数据源集合，在获取数据源的时候再去实例化，不然每次重走生命周期，size越来越多，小圆点个数每次都翻倍
     */
    // banner的图片数据源
    private ArrayList<String> bannerImgUrls;
    // 最新资讯列表的图片数据源
    private ArrayList<String> listImgUrls;
    // 最新资讯列表的标题
    private ArrayList<String> listTitle;
    // 最新资讯列表的时间
    private ArrayList<String> listTime;

    private LinearLayoutManager mLayoutManager;
    // RecyclerView的适配器
    private HomeAdapter mRcvAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
//        View v = inflater.inflate(R.layout.fragment_home,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        // 图片填充到状态栏
        //StatusBarColor.setTranslucent(getActivity());

        // 找控件
        initView(view);
        // 获取数据源
        getData();
        // 设置RecyclerView的适配器
        setAdapter();
        // 设置滑动监听
        setScrollListener();
        // 初始化PTR的上拉下拉属性
        initPTR();


    }

    private void setScrollListener() {
        /*rcv_home_frag.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rcv_home_frag.refreshComplete();
                    }
                },3000);
            }

            @Override
            public void onLoadMore() {
//                mHandler.sendEmptyMessageDelayed(LOADMORE_COMPLETE, 2000);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getNewsListData();
                        rcv_home_frag.loadMoreComplete();
                    }
                },3000);

            }
        });*/

        // 设置可以同时上拉和下拉
        ptr_home_frag.setMode(PullToRefreshBase.Mode.BOTH);

        // 设置上拉加载和下拉刷新的监听
        ptr_home_frag.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getNewsListData();
                // 下拉刷新
                ptr_home_frag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr_home_frag.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 上拉加载
                ptr_home_frag.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getNewsListData();
                        ptr_home_frag.onRefreshComplete();
                    }
                }, 1000);
            }
        });

    }

    private void setAdapter() {
        mRcvAdapter = new HomeAdapter(getContext(), listTitle, listTime, listImgUrls, bannerImgUrls);
        rcv_home_frag.setAdapter(mRcvAdapter);
    }

    /*@Override
    public void onStart() {
        //开始轮播
        mBanner.startAutoPlay();
        super.onStart();
    }

    @Override
    public void onStop() {
        //结束轮播
        mBanner.stopAutoPlay();
        super.onStop();
    }*/

    private void getData() {

        // banner的图片数据源
        bannerImgUrls = new ArrayList<>();
        // 最新资讯列表的图片数据源
        listImgUrls = new ArrayList<>();
        // 最新资讯列表的标题
        listTitle = new ArrayList<>();
        // 最新资讯列表的时间
        listTime = new ArrayList<>();

        // banner的图片资源
        bannerImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495882053004&di=689c40c238a8009fa8d86d4b44dddaa0&imgtype=0&src=http%3A%2F%2Fi2.3conline.com%2Fimages%2Fpiclib%2F200911%2F04%2Fbatch%2F1%2F45758%2F12573163207965bi5jugeyd.jpg");
        bannerImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495882096842&di=7ffe82aa5fb05d1ff0ce008f9118d355&imgtype=0&src=http%3A%2F%2Fpic2.52pk.com%2Ffiles%2F140117%2F3716262_120802_1_lit.jpg");
        bannerImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495882139295&di=bde8536c1d14ca108c00a0e7b1db6f76&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F57%2F33%2F94A58PICmE2.jpg");

        // 最新资讯的图片资源
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496461314&di=480f470b3a651764ec5dc60de4bf1d31&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F11%2F37%2F22%2F85558PIC2bt.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866658353&di=2f9633f1d2736918097b52ed93e621b1&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F57%2F35%2F32k58PIC7BC.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866731019&di=3866853e6f97411d63f9b472bf86e733&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F57%2F34%2F15E58PICAjB.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866731019&di=3866853e6f97411d63f9b472bf86e733&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F57%2F34%2F15E58PICAjB.jpg");

        //最新资讯中的标题和时间
        listTitle.add("影响力盛典群星荟萃，8月24日不见不散，共享盛典");
        listTitle.add("群星云集举杯同庆，影响力晚宴诚邀莅临，招募中");
        listTitle.add("奇思妙想各出新才，影响力App火热上线内测");
        listTitle.add("相聚悉尼，影响力发布会宣布将在悉尼剧院召开");
        listTitle.add("相聚悉尼，影响力发布会宣布将在悉尼剧院召开");
        listTime.add("2017-05-19   11:33");
        listTime.add("2017-05-01   21:17");
        listTime.add("2017-04-28   17:37");
        listTime.add("2017-04-22   21:00");
        listTime.add("2017-04-22   21:00");
        Log.i("HomeFragment---", "bannerlist.size = " + bannerImgUrls.size());
//        Log.i("HomeFragment---", "listTitle.size = " + listTitle.size());

    }

    private void getNewsListData() {

        // 最新资讯的图片资源
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496461314&di=480f470b3a651764ec5dc60de4bf1d31&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F11%2F37%2F22%2F85558PIC2bt.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866658353&di=2f9633f1d2736918097b52ed93e621b1&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F57%2F35%2F32k58PIC7BC.jpg");
        listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866731019&di=3866853e6f97411d63f9b472bf86e733&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F57%2F34%2F15E58PICAjB.jpg");

        //最新资讯中的标题和时间
        listTitle.add("影响力盛典群星荟萃，8月24日不见不散，共享盛典");
        listTitle.add("群星云集举杯同庆，影响力晚宴诚邀莅临，招募中");
        listTitle.add("奇思妙想各出新才，影响力App火热上线内测");
        listTitle.add("相聚悉尼，影响力发布会宣布将在悉尼剧院召开");
        listTime.add("2017-05-19   11:33");
        listTime.add("2017-05-01   21:17");
        listTime.add("2017-04-28   17:37");
        listTime.add("2017-04-22   21:00");

        Log.i("HomeFrag---", "listTitle.size = " + listTitle.size());
        mRcvAdapter.update(listTitle, listTime, listImgUrls);

    }

    private void initView(View view) {

        // PullToRefreshScrollView
        ptr_home_frag = (PullToRefreshScrollView) view.findViewById(R.id.ptr_home_frag);

        // RecyclerView
        rcv_home_frag = (RecyclerView) view.findViewById(R.id.rcv_home_frag);

        mLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcv_home_frag.setLayoutManager(mLayoutManager);

        // 当不是瀑布流时，设置这个可以避免重复的增删造成而外的浪费资源，因为要计算item的尺寸，重新绘制
        rcv_home_frag.setHasFixedSize(true);
        // 设置是否允许嵌套滑动
        rcv_home_frag.setNestedScrollingEnabled(false);

    }

    private void initPTR() {

        ILoadingLayout startLabels = ptr_home_frag.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开立即刷新");// 下拉达到一定距离时，显示的提示

        ILoadingLayout endLabels = ptr_home_frag.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示

        // 设置最近刷新时间
        String updateTime = DateUtils.formatDateTime(
                getContext(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        ptr_home_frag.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新：" + updateTime);
    }

    /*private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    *//*String[] urls = getResources().getStringArray(R.array.url);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);*//*
                    //把新的图片地址加载到Banner
//                    mBanner.update(arrayList);
                    mRcvAdapter.notifyDataSetChanged();
                    ptr_home_frag.onRefreshComplete();
//                    rcv_home_frag.refreshComplete();
                    //下拉刷新控件隐藏
                    //srl_home_refresh.setRefreshing(false);
                    break;
                case LOADMORE_COMPLETE:
                    getNewsListData();
//                    rcv_home_frag.loadMoreComplete();
                    ptr_home_frag.onRefreshComplete();
                    break;
            }
        }
    };*/

}
