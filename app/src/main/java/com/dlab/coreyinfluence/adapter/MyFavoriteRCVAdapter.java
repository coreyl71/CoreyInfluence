package com.dlab.coreyinfluence.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.utils.CircleImageView;

import java.util.List;

/**
 * Created by Corey on 2017/6/13.
 */

public class MyFavoriteRCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> mDataName;
    private List<String> mDataCount;
    private List<String> listImgUrls;

    public MyFavoriteRCVAdapter(Context context, List<String> dataName, List<String> dataCount, List<String> listImgUrls) {
        this.mContext = context;
        this.mDataName = dataName;
        this.mDataCount = dataCount;
        this.listImgUrls = listImgUrls;
    }

    // 定义接口
    public interface OnFavoriteVoteClickListener {
        void onItemVoteClick(View view, int position);
    }

    // 声明接口变量
    private OnFavoriteVoteClickListener mOnItemVoteClickListener = null;

    // set方法，引用到这个adapter的时候通过这个方法来调用item的点击事件
    public void setOnItemVoteClickListener(OnFavoriteVoteClickListener listener) {
        this.mOnItemVoteClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        // 每一个item的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_favorite_mine, parent, false);
        holder = new MyFavorHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyFavorHolder holder_favorite_item = (MyFavorHolder) holder;
        holder_favorite_item.tv_favorite_item_name.setText(mDataName.get(position));
        holder_favorite_item.tv_favorite_item_count.setText(mDataCount.get(position));
        Glide.with(mContext).load(listImgUrls.get(position)).into(holder_favorite_item.civ_favorite_item_headimg);

        if (mOnItemVoteClickListener != null) {
            // 为拉票按钮设置监听器
            holder_favorite_item.tv_favorite_item_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = holder_favorite_item.getLayoutPosition();
                    Log.i("MyFavoriteRCVAdapter---", "拉票---index = " + index);

                    // 回调定义的点击事件接口
                    mOnItemVoteClickListener.onItemVoteClick(holder_favorite_item.tv_favorite_item_vote, index);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return null == listImgUrls ? 0 : listImgUrls.size();
    }

    class MyFavorHolder extends RecyclerView.ViewHolder {

        // 关注的人的头像
        private CircleImageView civ_favorite_item_headimg;
        // 关注的人的姓名、得票数、拉票按钮
        private TextView tv_favorite_item_name, tv_favorite_item_count, tv_favorite_item_vote;


        //参数：当前item中显示的view对象
        public MyFavorHolder(View itemView) {
            super(itemView);

            civ_favorite_item_headimg = (CircleImageView) itemView.findViewById(R.id.civ_favorite_item_headimg);
            tv_favorite_item_name = (TextView) itemView.findViewById(R.id.tv_favorite_item_name);
            tv_favorite_item_count = (TextView) itemView.findViewById(R.id.tv_favorite_item_count);

            tv_favorite_item_vote = (TextView) itemView.findViewById(R.id.tv_favorite_item_vote);
        }
    }

}
