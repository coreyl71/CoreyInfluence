package com.dlab.coreyinfluence.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlab.coreyinfluence.R;

/**
 * 资产管理页面
 */
public class PropertyManageA extends AppCompatActivity implements View.OnClickListener{

    // 返回按钮
    private ImageView iv_back_property_manage;
    // 显示余额
    private TextView tv_balance_show;
    // 充值、提现RL
    private RelativeLayout rl_recharge, rl_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_property_manage);

        // 找控件
        initView();
        // 设置点击事件监听
        setOnClickListener();

    }

    private void setOnClickListener() {
        iv_back_property_manage.setOnClickListener(this);
        rl_recharge.setOnClickListener(this);
        rl_withdraw.setOnClickListener(this);
    }

    private void initView() {
        iv_back_property_manage = (ImageView) findViewById(R.id.iv_back_property_manage);
        tv_balance_show = (TextView) findViewById(R.id.tv_balance_show);
        rl_recharge = (RelativeLayout) findViewById(R.id.rl_recharge);
        rl_withdraw = (RelativeLayout) findViewById(R.id.rl_withdraw);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_property_manage:
                // 返回按钮被点击
                finish();
                break;
            case R.id.rl_recharge:
                // 充值RL被点击
                Intent intent_to_recharge = new Intent(PropertyManageA.this, RechargeA.class);
                startActivity(intent_to_recharge);
                break;
            case R.id.rl_withdraw:
                // 提现RL被点击
                Toast.makeText(PropertyManageA.this, "提现", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
