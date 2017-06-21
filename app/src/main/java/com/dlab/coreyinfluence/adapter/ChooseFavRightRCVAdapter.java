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
 * Created by Corey on 2017/6/20.
 */

public class ChooseFavRightRCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 记录被选中的item的位置
    private int chosenPosition = -1;
    // 记录item是否被选中还是被取消
    private int isItemChosen = -1;

    private Context mContext;
    private List<String> listName;
    private List<String> listImgUrls;

    public ChooseFavRightRCVAdapter(Context context,List<String> listName, List<String> listImgUrls) {
        this.mContext = context;
        this.listName = listName;
        this.listImgUrls = listImgUrls;
    }

    public void update(Context context,List<String> listName, List<String> listImgUrls) {
        this.mContext = context;
        this.listName = listName;
        this.listImgUrls = listImgUrls;
        this.notifyDataSetChanged();
    }

    // 定义接口，布尔值是判断选中还是取消选中
    public interface OnChooseFavRightItemClickListener {
        void onItemClick(View view, int position, int isItemChosen);
    }

    // 声明接口变量
    private OnChooseFavRightItemClickListener mOnItemClickListener = null;

    // set方法，引用到这个adapter的时候通过这个方法来调用item的点击事件
    public void setOnChooseFavRightItemClickListener(OnChooseFavRightItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        // 每一个item的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_choose_fav_right, parent, false);

        holder = new MyHolder(view);

        //将创建的View注册点击事件
//        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyHolder holder_choose_fav_item = (MyHolder) holder;
        holder_choose_fav_item.tv_choose_fav_item_name.setText(listName.get(position));

        // 如果position是打了标记的位置，那就让小图标显示出来，表示已经选中
        if (position == chosenPosition) {
            Log.i("ChooseRightAd---", "position = " + position);
            holder_choose_fav_item.tv_choose_fav_item_vote.setBackgroundResource(R.drawable.bg_choose_fav_right_selected);
            holder_choose_fav_item.tv_choose_fav_item_vote.setText("已关注");
            holder_choose_fav_item.tv_choose_fav_item_vote.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder_choose_fav_item.tv_choose_fav_item_vote.setBackgroundResource(R.drawable.bg_choose_fav_right_normal);
            holder_choose_fav_item.tv_choose_fav_item_vote.setText("关注");
            holder_choose_fav_item.tv_choose_fav_item_vote.setTextColor(mContext.getResources().getColor(R.color.text_yellow));
        }

        Glide.with(mContext).load(listImgUrls.get(position)).into(holder_choose_fav_item.civ_choose_fav_item_headimg);

        if (mOnItemClickListener != null) {
            // 为item设置监听器
            holder_choose_fav_item.tv_choose_fav_item_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = holder_choose_fav_item.getLayoutPosition();
                    Log.i("DialogRCVAdapter---", "index = " + index);

                    if (chosenPosition < 0) {
                        // 没有item被选中，记录当前被选中的位置
                        chosenPosition = index;
                        /**
                         * 传给fragment来弹toast用的位置
                         * 因为取消点击chosenPosition为-1，没有办法记录取消的是哪个item
                         * 后期不用显示哪个被取消则可以拿掉这个变量
                         **/
                        isItemChosen = index;
                    } else {
                        // 已经有item被选中，需要先清掉原先的状态
                        if (chosenPosition == index) {
                            // 再次点击
                            chosenPosition = -1;
                            isItemChosen = index;
                        } else {
                            // 重新选择另外一个item
                            chosenPosition = index;
                            isItemChosen = index;
                        }
                    }
                    notifyDataSetChanged();

                    // 回调定义的点击事件接口
                    mOnItemClickListener.onItemClick(holder_choose_fav_item.tv_choose_fav_item_vote, chosenPosition, isItemChosen);

                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return null == listImgUrls ? 0 : listImgUrls.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        // 头像
        private CircleImageView civ_choose_fav_item_headimg;
        // 姓名、票数、关注按钮
        private TextView tv_choose_fav_item_name, tv_choose_fav_item_count, tv_choose_fav_item_vote;

        //参数：当前item中显示的view对象
        public MyHolder(View itemView) {
            super(itemView);
            civ_choose_fav_item_headimg = (CircleImageView) itemView.findViewById(R.id.civ_choose_fav_item_headimg);
            tv_choose_fav_item_name = (TextView) itemView.findViewById(R.id.tv_choose_fav_item_name);
            tv_choose_fav_item_count = (TextView) itemView.findViewById(R.id.tv_choose_fav_item_count);
            tv_choose_fav_item_vote = (TextView) itemView.findViewById(R.id.tv_choose_fav_item_vote);
        }
    }

}
