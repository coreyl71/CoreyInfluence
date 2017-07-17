package com.dlab.coreyinfluence.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dlab.coreyinfluence.R;

/**
 * 充值页面
 */
public class RechargeA extends AppCompatActivity implements View.OnClickListener{

    // 返回按钮
    private ImageView iv_back_recharge;
    // 选择充值金额的RCV布局
    private RecyclerView rcv_recharge_choose_sum;
    private GridLayoutManager rechargeGridManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recharge);

        // 找控件
        initView();

        // 设置点击事件监听
        setOnClickListener();

    }

    private void setOnClickListener() {
        iv_back_recharge.setOnClickListener(this);
    }

    private void initView() {
        iv_back_recharge = (ImageView) findViewById(R.id.iv_back_recharge);
        rcv_recharge_choose_sum = (RecyclerView) findViewById(R.id.rcv_recharge_choose_sum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_recharge:
                // 返回按钮被点击
                finish();
                break;
            default:
                break;
        }
    }
}
