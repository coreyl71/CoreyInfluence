package com.dlab.coreyinfluence.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corey on 2017/5/26.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 布局类型，0是头部banner，1是最新资讯列表
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ANNOUNCEMENT = 1;
    public static final int TYPE_NORMAL = 2;
    //public static final int TYPE_FOOTVIEW = 3;

    //上拉加载更多
    //public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    //public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    //private int load_more_status=0;

    private Context mContext;
    private List<String> mDataTitle;
    private List<String> mDataTime;
    private List<String> listImgUrls;
    private List<String> bannerImgUrls;

    public void update(ArrayList<String> listTitle, ArrayList<String> listTime, ArrayList<String> listImgUrls) {
        this.mDataTitle = listTitle;
        this.mDataTime = listTime;
        this.listImgUrls = listImgUrls;
        this.notifyDataSetChanged();
    }

    public HomeAdapter(Context context, List<String> dataTitle, List<String> dataTime, List<String> listImgUrls, List<String> bannerImgUrls) {
        this.mContext = context;
        this.mDataTitle = dataTitle;
        this.mDataTime = dataTime;
        this.listImgUrls = listImgUrls;
        this.bannerImgUrls = bannerImgUrls;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_ANNOUNCEMENT;
        } /*else if (position == mDataTitle.size() + 3) {
            // 上拉加载的footview
            return TYPE_FOOTVIEW;
        } */else {
            return TYPE_NORMAL;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        if (viewType == TYPE_HEADER) {
//            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //渲染header布局
            View header = LayoutInflater.from(mContext).inflate(R.layout.viewholder_home_header_vp, parent, false);
            //设置banner的高度为手机屏幕的四分之一
//            mBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));
            holder = new MyHeadViewHolder(header);
        } else if (viewType == TYPE_ANNOUNCEMENT) {
            // 公告布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_home_announcement, parent, false);
            holder = new AnnouncementViewHolder(view);
        } /*else if (viewType == TYPE_FOOTVIEW) {
            // 公告布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_home_footview, parent, false);
            holder = new FooterViewHolder(view);
        }*/ else {
            // 最新资讯列表布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_home_item, parent, false);
            // 动态设置view高度和view的向下间距bottomMargin
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Point size = new Point();
            windowManager.getDefaultDisplay().getSize(size);
            int screenHeight = size.y;
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight * 17 / 120);
            layoutParams.bottomMargin = 1;
            view.setLayoutParams(layoutParams);
            holder = new MyNewsListViewHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                MyHeadViewHolder holder0 = (MyHeadViewHolder) holder;
                //设置banner样式
                holder0.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                holder0.mBanner.setImageLoader(new GlideImageLoader());
                //设置banner动画效果
                holder0.mBanner.setBannerAnimation(Transformer.Accordion);
                //设置自动轮播，默认为true
                holder0.mBanner.isAutoPlay(true);
                //设置轮播时间
                holder0.mBanner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                holder0.mBanner.setIndicatorGravity(BannerConfig.CENTER);
                //设置图片集合
                holder0.mBanner.setImages(bannerImgUrls);
                //banner设置方法全部调用完毕时最后调用
                holder0.mBanner.start();
                break;
            case TYPE_ANNOUNCEMENT:
                AnnouncementViewHolder holder1 = (AnnouncementViewHolder) holder;
                holder1.tv_announcement_content.setText("公告：红人苏小妍即将上市，2017年10月10日");
                break;
            case TYPE_NORMAL:
                MyNewsListViewHolder holder2 = (MyNewsListViewHolder) holder;
                Log.i("HomeAdapter---", "mDataTitle.size = " + mDataTitle.size());
                Log.i("HomeAdapter---", "mDataTitle.position = " + (position-2));
                holder2.tv_home_item_title.setText(mDataTitle.get(position-2));
                holder2.tv_home_item_date.setText(mDataTime.get(position-2));
                Log.i("HomeAdapter---", "listImgUrls = " + listImgUrls.get(position - 2));
                Glide.with(mContext).load(listImgUrls.get(position - 2)).into(holder2.iv_home_item);
                break;
            /*case TYPE_FOOTVIEW:
                FooterViewHolder holder3 = (FooterViewHolder) holder;
                switch (load_more_status) {
                    case PULLUP_LOAD_MORE:
                        holder3.tv_footview_loading.setText("上拉加载更多");
                        break;
                    case LOADING_MORE:
                        holder3.tv_footview_loading.setText("正在加载中...");
                        break;
                }

                break;*/
        }

    }

    @Override
    public int getItemCount() {
        return null == mDataTitle ? 2 : mDataTitle.size() + 2;
    }



    /**
     * 上拉加载布局
     */
    /*class FooterViewHolder extends XRecyclerView.ViewHolder {

        private TextView tv_footview_loading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tv_footview_loading = (TextView) itemView.findViewById(R.id.tv_footview_loading);
        }
    }*/

    /**
     * 最新资讯列表布局
     */
    class MyNewsListViewHolder extends RecyclerView.ViewHolder {

//        private TextView mTvItem;
        // 新闻图片
        private ImageView iv_home_item;
        // 新闻标题
        private TextView tv_home_item_title, tv_home_item_date;

        public MyNewsListViewHolder(View itemView) {
            super(itemView);
//            mTvItem = (TextView) itemView.findViewById(R.id.tv_item);
            iv_home_item = (ImageView) itemView.findViewById(R.id.iv_home_item);
            tv_home_item_title = (TextView) itemView.findViewById(R.id.tv_home_item_title);
            tv_home_item_date = (TextView) itemView.findViewById(R.id.tv_home_item_date);
        }
    }

    /**
     * 公告布局
     */
    class AnnouncementViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_announcement_content;

        public AnnouncementViewHolder(View itemView) {
            super(itemView);
            tv_announcement_content = (TextView) itemView.findViewById(R.id.tv_announcement_content);
        }
    }

    /**
     * 头部banner布局
     */
    class MyHeadViewHolder extends RecyclerView.ViewHolder {

        private Banner mBanner;

        public MyHeadViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.banner_home);
        }
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     *  //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    /*public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }*/

}
