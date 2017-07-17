package com.dlab.coreyinfluence.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlab.coreyinfluence.R;

/**
 * 充值金额的适配器
 * Created by Corey on 2017/7/13.
 */

public class RechargeRCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tv_item_recharge_sum;

        //参数：当前item中显示的view对象
        public MyHolder(View itemView) {
            super(itemView);
            tv_item_recharge_sum = (TextView) itemView.findViewById(R.id.tv_item_recharge_sum);
        }
    }

}
