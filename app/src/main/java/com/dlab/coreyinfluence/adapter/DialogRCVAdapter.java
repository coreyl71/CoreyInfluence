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
 * Created by Corey on 2017/6/7.
 */

public class DialogRCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 记录被选中的item的位置
    private int chosenPosition = -1;
    // 记录item是否被选中还是被取消
    private int isItemChosen = -1;

    private Context mContext;
    private List<String> mDataName;
    private List<String> listImgUrls;

    public DialogRCVAdapter(Context context, List<String> dataName, List<String> listImgUrls) {
        this.mContext = context;
        this.mDataName = dataName;
        this.listImgUrls = listImgUrls;
    }


    // 定义接口，布尔值是判断选中还是取消选中
    public interface OnItemClickListener {
        void onItemClick(View view, int position, int isItemChosen);
    }

    // 声明接口变量
    private OnItemClickListener mOnItemClickListener = null;

    // set方法，引用到这个adapter的时候通过这个方法来调用item的点击事件
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        // 每一个item的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_vote_list, parent, false);

        holder = new MyHolder(view);

        //将创建的View注册点击事件
//        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final MyHolder holder_vote_item = (MyHolder) holder;
        holder_vote_item.tv_item_vote_list.setText(mDataName.get(position));

        // 如果position是打了标记的位置，那就让小图标显示出来，表示已经选中
        if (position == chosenPosition) {
            Log.i("DialogRCVAdapter---", "position = " + position);
            holder_vote_item.iv_item_chosen_vote_list.setVisibility(View.VISIBLE);
        } else {
            holder_vote_item.iv_item_chosen_vote_list.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(listImgUrls.get(position)).into(holder_vote_item.civ_item_vote_list);


        if (mOnItemClickListener != null) {
            // 为item设置监听器
            holder_vote_item.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = holder_vote_item.getLayoutPosition();
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
                    mOnItemClickListener.onItemClick(holder_vote_item.itemView, chosenPosition, isItemChosen);

                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return null == listImgUrls ? 0 : listImgUrls.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private CircleImageView civ_item_vote_list;
        private ImageView iv_item_chosen_vote_list;
        private TextView tv_item_vote_list;


        //参数：当前item中显示的view对象
        public MyHolder(View itemView) {
            super(itemView);
            civ_item_vote_list = (CircleImageView) itemView.findViewById(R.id.civ_item_vote_list);
            iv_item_chosen_vote_list = (ImageView) itemView.findViewById(R.id.iv_item_chosen_vote_list);
            tv_item_vote_list = (TextView) itemView.findViewById(R.id.tv_item_vote_list);
        }
    }

}
