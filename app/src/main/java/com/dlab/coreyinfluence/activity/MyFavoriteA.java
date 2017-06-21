package com.dlab.coreyinfluence.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.adapter.MyFavoriteRCVAdapter;
import com.dlab.coreyinfluence.utils.RCVItemDecoration;

import java.util.ArrayList;

/**
 * 我的关注页面
 */
public class MyFavoriteA extends AppCompatActivity implements View.OnClickListener{

    // 返回按钮
    private ImageView iv_back_my_favorite;
    // 编辑按钮
    private TextView tv_edit_my_favorite;
    // 我的关注列表
    private RecyclerView rcv_my_favorite_list;

    // 关注列表的图片
    private ArrayList<String> listImgUrls;
    // 关注列表的姓名
    private ArrayList<String> listName;
    // 关注列表的票数
    private ArrayList<String> listCount;

    // 关注列表适配器
    private MyFavoriteRCVAdapter mFavoriteRCVAdapter;
    private LinearLayoutManager mFavoriteRCVManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_favorite);

        // 找控件
        initView();
        // 获取数据
        getData();
        // 设置RecyclerView适配器
        setFavoriteListAdapter();
        // 设置点击事件
        setOnClickListener();

    }

    private void setOnClickListener() {
        iv_back_my_favorite.setOnClickListener(this);
        tv_edit_my_favorite.setOnClickListener(this);
    }

    private void setFavoriteListAdapter() {

        // 设置布局管理器
        mFavoriteRCVManager = new LinearLayoutManager(this);
        rcv_my_favorite_list.setLayoutManager(mFavoriteRCVManager);

        // 设置适配器
        mFavoriteRCVAdapter = new MyFavoriteRCVAdapter(this, listName, listCount, listImgUrls);
        rcv_my_favorite_list.setAdapter(mFavoriteRCVAdapter);

        // 设置RCV的自定义分割线
        rcv_my_favorite_list.addItemDecoration(new RCVItemDecoration(this, RCVItemDecoration.VERTICAL_LIST));

        // 设置rcv中的拉票按钮点击事件
        mFavoriteRCVAdapter.setOnItemVoteClickListener(new MyFavoriteRCVAdapter.OnFavoriteVoteClickListener() {
            @Override
            public void onItemVoteClick(View view, int position) {
                Toast.makeText(MyFavoriteA.this, "为 " + listName.get(position) + " 拉票", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData() {

        // 头像的图片数据源
        listImgUrls = new ArrayList<>();
        // 姓名
        listName = new ArrayList<>();
        // 票数
        listCount = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            listName.add("Corey  " + i);
            listCount.add(1000 + i + "");
            listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");

        }

    }

    private void initView() {
        iv_back_my_favorite = (ImageView) findViewById(R.id.iv_back_my_favorite);
        tv_edit_my_favorite = (TextView) findViewById(R.id.tv_edit_my_favorite);
        rcv_my_favorite_list = (RecyclerView) findViewById(R.id.rcv_my_favorite_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_my_favorite:
                // 返回按钮被点击
                finish();
                break;
            case R.id.tv_edit_my_favorite:
                // 编辑按钮被点击
                Intent intent_to_choose_favor = new Intent(MyFavoriteA.this, ChooseFavoriteA.class);
                startActivity(intent_to_choose_favor);
                break;
            default:
                break;
        }
    }
}
