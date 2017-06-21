package com.dlab.coreyinfluence.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corey on 2017/6/1.
 */

public class StarRankRCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    // 布局类型，0是头部，1是普通列表
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private Context mContext;
    private List<String> mDataName;
    private List<String> mDataCount;
    private List<String> listImgUrls;

    public void update(ArrayList<String> listName, ArrayList<String> listCount, ArrayList<String> listImgUrls) {
        this.mDataName = listName;
        this.mDataCount = listCount;
        this.listImgUrls = listImgUrls;
        this.notifyDataSetChanged();
    }

    public StarRankRCVAdapter(Context context, List<String> dataName, List<String> dataCount, List<String> listImgUrls) {
        this.mContext = context;
        this.mDataName = dataName;
        this.mDataCount = dataCount;
        this.listImgUrls = listImgUrls;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        if (viewType == TYPE_HEADER) {
//            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //渲染header布局
            View header = LayoutInflater.from(mContext).inflate(R.layout.viewholder_ranklist_star_header, parent, false);
            //设置banner的高度为手机屏幕的四分之一
//            mBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));
            holder = new StarRankHeadViewHolder(header);
        } else {
            // 第四名开始的列表布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_ranklist_star_normal, parent, false);
            // 动态设置view高度和view的向下间距bottomMargin
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Point size = new Point();
            windowManager.getDefaultDisplay().getSize(size);
            int screenHeight = size.y;
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight * 13 / 120);
            layoutParams.bottomMargin = 1;
            view.setLayoutParams(layoutParams);
            holder = new StarRankNormalViewHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                StarRankHeadViewHolder holder0 = (StarRankHeadViewHolder) holder;
                // 规则说明被点击
                holder0.tv_rule_vote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "---规则说明---", Toast.LENGTH_LONG).show();
                    }
                });

                // 设置一二三名的头像、姓名和得票数
                holder0.tv_rank_name_first.setText(mDataName.get(0));
                holder0.tv_rank_name_second.setText(mDataName.get(1));
                holder0.tv_rank_name_third.setText(mDataName.get(2));

                holder0.tv_rank_count_first.setText(mDataCount.get(0));
                holder0.tv_rank_count_second.setText(mDataCount.get(1));
                holder0.tv_rank_count_third.setText(mDataCount.get(2));

                Glide.with(mContext).load(listImgUrls.get(0)).into(holder0.civ_rank_headimg_first);
                Glide.with(mContext).load(listImgUrls.get(1)).into(holder0.civ_rank_headimg_second);
                Glide.with(mContext).load(listImgUrls.get(2)).into(holder0.civ_rank_headimg_third);
                break;
            case TYPE_NORMAL:
                StarRankNormalViewHolder holder2 = (StarRankNormalViewHolder) holder;
                Log.i("StarRankRCVAdapter---", "mDataName.size = " + mDataName.size());
                Log.i("StarRankRCVAdapter---", "position = " + position);
                holder2.tv_rank_no.setText("0" + String.valueOf(position + 3) + ".");
                holder2.tv_rank_name.setText(mDataName.get(position + 2));
                holder2.tv_rank_count.setText(mDataCount.get(position + 2));
                Glide.with(mContext).load(listImgUrls.get(position + 2)).into(holder2.civ_rank_headimg);
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (null == mDataName) {
            count = 0;
        } else {
            if (mDataName.size() < 2) {
                count = 0;
            } else {
                count = mDataName.size() - 2;
            }
        }
        return count;
    }

    /**
     * 第四名开始的列表布局
     */
    class StarRankNormalViewHolder extends RecyclerView.ViewHolder {

        // 序号、名字、排名
        private TextView tv_rank_no, tv_rank_name, tv_rank_count;
        // 头像
        private CircleImageView civ_rank_headimg;

        public StarRankNormalViewHolder(View itemView) {
            super(itemView);
            tv_rank_no = (TextView) itemView.findViewById(R.id.tv_rank_no);
            tv_rank_name = (TextView) itemView.findViewById(R.id.tv_rank_name);
            tv_rank_count = (TextView) itemView.findViewById(R.id.tv_rank_count);
            civ_rank_headimg = (CircleImageView) itemView.findViewById(R.id.civ_rank_headimg);
        }
    }

    /**
     * 头部标题布局，里面还有一个规则说明的按钮
     */
    class StarRankHeadViewHolder extends RecyclerView.ViewHolder {

        // 规则说明按钮
        private TextView tv_rule_vote;
        private SpannableString mspStr;
        // 姓名
        private TextView tv_rank_name_first, tv_rank_name_second, tv_rank_name_third;
        // 得票数
        private TextView tv_rank_count_first, tv_rank_count_second, tv_rank_count_third;
        // 头像
        private CircleImageView civ_rank_headimg_first, civ_rank_headimg_second, civ_rank_headimg_third;

        public StarRankHeadViewHolder(View itemView) {
            super(itemView);
            tv_rule_vote = (TextView) itemView.findViewById(R.id.tv_rule_vote);
            mspStr = new SpannableString("规则说明");

            tv_rank_name_first = (TextView) itemView.findViewById(R.id.tv_rank_name_first);
            tv_rank_name_second = (TextView) itemView.findViewById(R.id.tv_rank_name_second);
            tv_rank_name_third = (TextView) itemView.findViewById(R.id.tv_rank_name_third);

            tv_rank_count_first = (TextView) itemView.findViewById(R.id.tv_rank_count_first);
            tv_rank_count_second = (TextView) itemView.findViewById(R.id.tv_rank_count_second);
            tv_rank_count_third = (TextView) itemView.findViewById(R.id.tv_rank_count_third);

            civ_rank_headimg_first = (CircleImageView) itemView.findViewById(R.id.civ_rank_headimg_first);
            civ_rank_headimg_second = (CircleImageView) itemView.findViewById(R.id.civ_rank_headimg_second);
            civ_rank_headimg_third = (CircleImageView) itemView.findViewById(R.id.civ_rank_headimg_third);

        }
    }

}
