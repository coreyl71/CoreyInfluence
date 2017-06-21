package com.dlab.coreyinfluence.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.utils.CircleImageView;

import java.util.List;

/**
 * Created by Corey on 2017/6/19.
 */

public class ChooseFavBottomRCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> listImgUrls;

    public ChooseFavBottomRCVAdapter(Context context, List<String> listImgUrls) {
        this.mContext = context;
        this.listImgUrls = listImgUrls;
    }

    public void update(Context context, List<String> listImgUrls) {
        this.mContext = context;
        this.listImgUrls = listImgUrls;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        // 每一个item的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rcv_has_attention, parent, false);

        holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyHolder holder_vote_item = (MyHolder) holder;

        Glide.with(mContext).load(listImgUrls.get(position)).into(holder_vote_item.civ_has_attention_item);

    }

    @Override
    public int getItemCount() {
        return null == listImgUrls ? 0 : listImgUrls.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private CircleImageView civ_has_attention_item;
        private ImageView iv_delete_attention_item;


        //参数：当前item中显示的view对象
        public MyHolder(View itemView) {
            super(itemView);
            civ_has_attention_item = (CircleImageView) itemView.findViewById(R.id.civ_has_attention_item);
            iv_delete_attention_item = (ImageView) itemView.findViewById(R.id.iv_delete_attention_item);
        }
    }

}
