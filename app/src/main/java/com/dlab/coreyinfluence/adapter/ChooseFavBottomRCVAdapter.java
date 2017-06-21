package com.dlab.coreyinfluence.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    // 定义接口，布尔值是判断选中还是取消选中
    public interface OnChooseFavBottomItemClickListener {
        void onItemClick(View view, int position);
    }

    // 声明接口变量
    private OnChooseFavBottomItemClickListener mOnItemClickListener = null;

    // set方法，引用到这个adapter的时候通过这个方法来调用item的点击事件
    public void setOnChooseFavBottomItemClickListener(OnChooseFavBottomItemClickListener listener) {
        this.mOnItemClickListener = listener;
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

        final MyHolder holder_choose_fav_item_bottom = (MyHolder) holder;

        Glide.with(mContext).load(listImgUrls.get(position)).into(holder_choose_fav_item_bottom.civ_has_attention_item);

        if (mOnItemClickListener != null) {
            // 为item设置监听器
            holder_choose_fav_item_bottom.iv_delete_attention_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = holder_choose_fav_item_bottom.getLayoutPosition();
                    Log.i("DialogRCVAdapter---", "index = " + index);


                    notifyDataSetChanged();

                    // 回调定义的点击事件接口
                    mOnItemClickListener.onItemClick(holder_choose_fav_item_bottom.iv_delete_attention_item, index);

                }

            });
        }

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
