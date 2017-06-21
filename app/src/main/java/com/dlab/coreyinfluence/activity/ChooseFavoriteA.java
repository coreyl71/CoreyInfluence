package com.dlab.coreyinfluence.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.adapter.ChooseFavBottomRCVAdapter;
import com.dlab.coreyinfluence.adapter.ChooseFavRightRCVAdapter;

import java.util.ArrayList;

/**
 * 选择关注页面
 */
public class ChooseFavoriteA extends AppCompatActivity {

    //******************************顶部标题栏相关******************************//
    private ImageView iv_back_choose_favorite;  // 返回按钮
    private TextView tv_done_choose_favorite;   // 完成按钮

    //******************************搜索框相关******************************//
    private EditText et_search_star;


    //******************************左侧选择按钮相关******************************//
    private TextView tv_choose_fav_hot, tv_choose_fav_star, tv_choose_fav_ostar;
    private int cateNo = 0; // 默认第一项被选中


    //******************************右侧明星竖向列表相关******************************//
    private RecyclerView rcv_choose_fav_hot, rcv_choose_fav_star, rcv_choose_fav_ostar;
    // 列表的图片源
    private ArrayList<String> chooseFavImgUrls, chooseFavNames;
    // 右侧rcv的适配器
    private ChooseFavRightRCVAdapter mRightRCVAdapter;


    //******************************底部已关注横向滚动条相关******************************//
    private RecyclerView rcv_has_attention_list;
    // 底部数据源
    private ArrayList<String> mHasAttentionImgUrls;
    // 底部rcv的适配器
    private ChooseFavBottomRCVAdapter mBottomRCVAdapter;

    // 被选中的姓名、头像
    private String nameChosenFav, imgUrlChosenFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choose_favorite);

        // 找控件
        initView();

        // 获取数据
        getData();

        // 设置适配器
        setAdapter();

        // 设置键盘点击监听
        setOnClickListener();

    }

    private void getData() {

        // 获取底部数据源
        mHasAttentionImgUrls = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mHasAttentionImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");
        }

        // 获取最热右侧列表数据源
        getRightRCVData();


    }

    private void getRightRCVData() {
        chooseFavImgUrls = new ArrayList<>();
        chooseFavNames = new ArrayList<>();
        switch (cateNo) {
            case 0:
                for (int i = 0; i < 10; i++) {
                    chooseFavNames.add("Corey  " + i);
                    chooseFavImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");
                }
                break;
            case 1:
                for (int i = 0; i < 10; i++) {
                    chooseFavNames.add("Way  " + i);
                    chooseFavImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497966578472&di=94fe2b4e4d0852ba3805be63a4e3248e&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fladyproduct%2F1410%2F17%2Fc0%2F39763708_1413508728339.jpg");
                }
                break;
            case 2:
                for (int i = 0; i < 10; i++) {
                    chooseFavNames.add("CoreyWay  " + i);
                    chooseFavImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497966639503&di=b90c4ae0b7825ee4aa2134258c489a7f&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F37d12f2eb9389b50b6395e188535e5dde6116eb2.jpg");
                }
                break;
        }
    }

    private void setAdapter() {

        //******************************底部适配器相关******************************//
        // 设置底部适配器
        mBottomRCVAdapter = new ChooseFavBottomRCVAdapter(ChooseFavoriteA.this, mHasAttentionImgUrls);
        rcv_has_attention_list.setAdapter(mBottomRCVAdapter);

        // TODO: 2017/6/21  设置底部列表的item点击事件




        //******************************右侧适配器相关******************************//
        // 右侧适配器
        mRightRCVAdapter = new ChooseFavRightRCVAdapter(ChooseFavoriteA.this, chooseFavNames, chooseFavImgUrls);
        rcv_choose_fav_hot.setAdapter(mRightRCVAdapter);

        // 设置右侧列表的item点击事件
        mRightRCVAdapter.setOnChooseFavRightItemClickListener(new ChooseFavRightRCVAdapter.OnChooseFavRightItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int isItemChosen) {
                if (position != -1) {
                    // 如果被选中
                    nameChosenFav = chooseFavNames.get(isItemChosen);
                    imgUrlChosenFav = chooseFavImgUrls.get(isItemChosen);
                    // 添加底部列表数据源
                    mHasAttentionImgUrls.add(0, imgUrlChosenFav);
                    // 刷新底部已关注列表适配器
                    mBottomRCVAdapter.update(ChooseFavoriteA.this, mHasAttentionImgUrls);
                    Toast.makeText(ChooseFavoriteA.this, chooseFavNames.get(isItemChosen) + "---被选中", Toast.LENGTH_SHORT).show();
                } else {
                    // 如果被取消
                    nameChosenFav = "";
                    imgUrlChosenFav = chooseFavImgUrls.get(isItemChosen);
                    for (int i = 0; i < mHasAttentionImgUrls.size(); i++) {
                        if (imgUrlChosenFav.equals(mHasAttentionImgUrls.get(i))) {
                            mHasAttentionImgUrls.remove(i);
                            mBottomRCVAdapter.update(ChooseFavoriteA.this, mHasAttentionImgUrls);
                            break;
                        }
                    }
                    Toast.makeText(ChooseFavoriteA.this, chooseFavNames.get(isItemChosen) + "---被取消", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void setOnClickListener() {

        // 返回按钮被点击
        iv_back_choose_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 完成按钮被点击
        tv_done_choose_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/6/21 完成点击之后需要请求网络吧？ 暂定先关闭页面
            }
        });

        // 搜索框的软键盘点击"搜索"
        et_search_star.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(ChooseFavoriteA.this,"呵呵",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        // 最热按钮被点击，按钮颜色改变，rcv数据改变
        tv_choose_fav_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choose_fav_hot.setBackgroundResource(R.drawable.bg_choose_cate_selected);
                tv_choose_fav_hot.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_yellow));
                tv_choose_fav_star.setBackgroundResource(R.drawable.bg_choose_cate_normal);
                tv_choose_fav_star.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_black));
                tv_choose_fav_ostar.setBackgroundResource(R.drawable.bg_choose_cate_normal);
                tv_choose_fav_ostar.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_black));
                cateNo = 0;
                getRightRCVData();
                mRightRCVAdapter.update(ChooseFavoriteA.this, chooseFavNames, chooseFavImgUrls);
            }
        });

        tv_choose_fav_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choose_fav_hot.setBackgroundResource(R.drawable.bg_choose_cate_normal);
                tv_choose_fav_hot.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_black));
                tv_choose_fav_star.setBackgroundResource(R.drawable.bg_choose_cate_selected);
                tv_choose_fav_star.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_yellow));
                tv_choose_fav_ostar.setBackgroundResource(R.drawable.bg_choose_cate_normal);
                tv_choose_fav_ostar.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_black));
                cateNo = 1;
                getRightRCVData();
                mRightRCVAdapter.update(ChooseFavoriteA.this, chooseFavNames, chooseFavImgUrls);
            }
        });

        tv_choose_fav_ostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choose_fav_hot.setBackgroundResource(R.drawable.bg_choose_cate_normal);
                tv_choose_fav_hot.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_black));
                tv_choose_fav_star.setBackgroundResource(R.drawable.bg_choose_cate_normal);
                tv_choose_fav_star.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_black));
                tv_choose_fav_ostar.setBackgroundResource(R.drawable.bg_choose_cate_selected);
                tv_choose_fav_ostar.setTextColor(ChooseFavoriteA.this.getResources().getColor(R.color.text_yellow));
                cateNo = 2;
                // 获取数据
                getRightRCVData();
                // 刷新适配器
                mRightRCVAdapter.update(ChooseFavoriteA.this, chooseFavNames, chooseFavImgUrls);

            }
        });

    }

    private void initView() {

        //******************************返回按钮******************************//
        iv_back_choose_favorite = (ImageView) findViewById(R.id.iv_back_choose_favorite);
        tv_done_choose_favorite = (TextView) findViewById(R.id.tv_done_choose_favorite);

        //******************************搜索框******************************//
        et_search_star = (EditText) findViewById(R.id.et_search_star);

        //控制搜索图标大小
        Drawable drawable1 = getResources().getDrawable(R.drawable.vector_ic_favorite_search);
        drawable1.setBounds(0, 0, 32, 32);  //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        et_search_star.setCompoundDrawables(drawable1, null, null, null);   //只放左边


        //******************************左侧选择按钮相关******************************//
        tv_choose_fav_hot = (TextView) findViewById(R.id.tv_choose_fav_hot);
        tv_choose_fav_star = (TextView) findViewById(R.id.tv_choose_fav_star);
        tv_choose_fav_ostar = (TextView) findViewById(R.id.tv_choose_fav_ostar);

        // 设置按钮的初始化
        tv_choose_fav_hot.setBackgroundResource(R.drawable.bg_choose_cate_selected);
        tv_choose_fav_hot.setTextColor(this.getResources().getColor(R.color.text_yellow));
        tv_choose_fav_star.setBackgroundResource(R.drawable.bg_choose_cate_normal);
        tv_choose_fav_star.setTextColor(this.getResources().getColor(R.color.text_black));
        tv_choose_fav_ostar.setBackgroundResource(R.drawable.bg_choose_cate_normal);
        tv_choose_fav_ostar.setTextColor(this.getResources().getColor(R.color.text_black));


        //******************************右侧明星列表******************************//
        rcv_choose_fav_hot = (RecyclerView) findViewById(R.id.rcv_choose_fav_hot);
        rcv_choose_fav_ostar = (RecyclerView) findViewById(R.id.rcv_choose_fav_ostar);
        rcv_choose_fav_star = (RecyclerView) findViewById(R.id.rcv_choose_fav_star);

        //设置竖向布局管理器
        LinearLayoutManager verticalLLManager = new LinearLayoutManager(this);
        verticalLLManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_choose_fav_hot.setLayoutManager(verticalLLManager);
//        rcv_choose_fav_ostar.setLayoutManager(verticalLLManager);
//        rcv_choose_fav_star.setLayoutManager(verticalLLManager);


        //******************************底部已关注横向滚动条******************************//
        rcv_has_attention_list = (RecyclerView) findViewById(R.id.rcv_has_attention_list);

        //设置横向布局管理器
        LinearLayoutManager horizontalLLManager = new LinearLayoutManager(this);
        horizontalLLManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_has_attention_list.setLayoutManager(horizontalLLManager);

    }
}
