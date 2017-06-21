package com.dlab.coreyinfluence.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dlab.coreyinfluence.R;

public class MySettingA extends AppCompatActivity implements View.OnClickListener{

    // 返回按钮
    private ImageView iv_back_my_setting;

    // 昵称、绑定手机、绑定微信、绑定微博的RL
    private RelativeLayout rl_my_nickname, rl_my_bind_phone, rl_my_bind_wechat, rl_my_bind_weibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_setting);

        // 找控件
        initView();

        // 设置点击时间监听
        setOnClickListener();

    }

    private void setOnClickListener() {
        iv_back_my_setting.setOnClickListener(this);
        rl_my_nickname.setOnClickListener(this);
        rl_my_bind_phone.setOnClickListener(this);
        rl_my_bind_wechat.setOnClickListener(this);
        rl_my_bind_weibo.setOnClickListener(this);
    }

    private void initView() {

        // 返回按钮
        iv_back_my_setting = (ImageView) findViewById(R.id.iv_back_my_setting);

        // 昵称
        rl_my_nickname = (RelativeLayout) findViewById(R.id.rl_my_nickname);
        // 绑定手机
        rl_my_bind_phone = (RelativeLayout) findViewById(R.id.rl_my_bind_phone);
        // 绑定微信
        rl_my_bind_wechat = (RelativeLayout) findViewById(R.id.rl_my_bind_wechat);
        // 绑定微博
        rl_my_bind_weibo = (RelativeLayout) findViewById(R.id.rl_my_bind_weibo);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_my_setting:
                // 返回按钮被点击
                finish();
                break;
            case R.id.rl_my_nickname:
                // 昵称RL被点击

                break;
            case R.id.rl_my_bind_phone:
                // 绑定手机RL被点击

                break;
            case R.id.rl_my_bind_wechat:
                // 绑定微信RL被点击
                break;
            case R.id.rl_my_bind_weibo:
                // 绑定微博RL被点击
                break;
        }
    }
}
