package com.dlab.coreyinfluence.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlab.coreyinfluence.activity.MyFavoriteA;
import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.activity.PropertyManageA;
import com.dlab.coreyinfluence.utils.CircleImageView;


public class MineFragment extends Fragment implements View.OnClickListener{

    //缓存Fragment view
    private View rootView;

    // 头像
    private CircleImageView civ_my_head;
    // 昵称、票数、票权
    private TextView tv_my_nickname, tv_my_vote_count, tv_my_vote_weight;
    // 资产管理、我的交易、我的关注、我的兑换、我的银行卡、设置的RL，做点击
    private RelativeLayout rl_my_property, rl_my_deal, rl_my_exchange, rl_my_bankcard, rl_my_favorite, rl_my_setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
//        View v = inflater.inflate(R.layout.fragment_mine,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 找控件
        initView(view);
        // 获取数据源
        getData();
        // 设置点击事件监听
        setOnClickListener();

    }

    private void setOnClickListener() {
        rl_my_property.setOnClickListener(this);
        rl_my_deal.setOnClickListener(this);
        rl_my_favorite.setOnClickListener(this);
        rl_my_exchange.setOnClickListener(this);
        rl_my_bankcard.setOnClickListener(this);
        rl_my_setting.setOnClickListener(this);
    }

    private void getData() {

    }

    private void initView(View view) {

        civ_my_head = (CircleImageView) view.findViewById(R.id.civ_my_head);
        tv_my_nickname = (TextView) view.findViewById(R.id.tv_my_nickname);
        tv_my_vote_count = (TextView) view.findViewById(R.id.tv_my_vote_count);
        tv_my_vote_weight = (TextView) view.findViewById(R.id.tv_my_vote_weight);

        rl_my_property = (RelativeLayout) view.findViewById(R.id.rl_my_property);
        rl_my_deal = (RelativeLayout) view.findViewById(R.id.rl_my_deal);
        rl_my_favorite = (RelativeLayout) view.findViewById(R.id.rl_my_favorite);
        rl_my_exchange = (RelativeLayout) view.findViewById(R.id.rl_my_exchange);
        rl_my_bankcard = (RelativeLayout) view.findViewById(R.id.rl_my_bankcard);
        rl_my_setting = (RelativeLayout) view.findViewById(R.id.rl_my_setting);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_my_property:
                // TODO: 2017/6/21 资产管理 
                Intent intent_to_property_manager = new Intent(getActivity(), PropertyManageA.class);
                startActivity(intent_to_property_manager);
                break;
            case R.id.rl_my_deal:
                // TODO: 2017/6/21 我的交易 
                Toast.makeText(getContext(), "我的交易", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_my_exchange:
                // TODO: 2017/6/21 我的兑换 
                Toast.makeText(getContext(), "我的兑换", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_my_bankcard:
                // TODO: 2017/6/21 我的银行卡 
                Toast.makeText(getContext(), "我的银行卡", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_my_favorite:
                // 我的关注
                Intent intent_to_favorite = new Intent(getContext(), MyFavoriteA.class);
                startActivity(intent_to_favorite);
                break;
            case R.id.rl_my_setting:
                // TODO: 2017/6/21 设置 
                Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
