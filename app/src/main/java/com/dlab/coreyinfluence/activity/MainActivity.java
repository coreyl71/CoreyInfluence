package com.dlab.coreyinfluence.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.fragment.HomeFragment;
import com.dlab.coreyinfluence.fragment.MineFragment;
import com.dlab.coreyinfluence.fragment.RankListFragment;
import com.dlab.coreyinfluence.utils.StatusBarColor;

/*
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                   O\  =  /O
 *                ____/`---'\____
 *              .'  \\|     |//  `.
 *             /  \\|||  :  |||//  \
 *            /  _||||| -:- |||||-  \
 *            |   | \\\  -  /// |   |
 *            | \_|  ''\---/''  |   |
 *            \  .-\__  `-`  ___/-. /
 *          ___`. .'  /--.--\  '. .'__
 *       ."" '<  `.___\_<|>_/___.'  >'"".
 *      | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *      \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *				  	`=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *               佛祖保佑       永无BUG
*/

public class MainActivity extends AppCompatActivity {

    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    private RelativeLayout rl_test;
    private ImageView iv_test;

    // 定义一个布局
    private LayoutInflater layoutInflater;

    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = {HomeFragment.class, RankListFragment.class, MineFragment.class};

    // Tab选项卡的文字
    private String mTextviewArray[] = {"首页", "榜单", "我的"};

    // 定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_ranklist_btn, R.drawable.tab_mine_btn};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 图片填充到状态栏
        //StatusBarColor.setTranslucent(MainActivity.this);

        // 找控件
        initViews();

        // 获取状态栏高度
        int statusHeight = StatusBarColor.getBarHeight(MainActivity.this);

        // 当版本大于4.4的时候，用了沉浸式布局，其他的局部布局会往上偏移，动态设置上边距
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_test.getLayoutParams();
            lp.topMargin = statusHeight;
            rl_test.setLayoutParams(lp);
        }
    }



    private void initViews() {

        rl_test = (RelativeLayout) findViewById(R.id.rl_test);
        iv_test = (ImageView) findViewById(R.id.iv_test);

        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            final int pos = i;
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 隐藏分割线
            mTabHost.getTabWidget().setDividerDrawable(null);
            // 设置Tab按钮的背景
            View tabBtn = mTabHost.getTabWidget().getChildAt(i);
            //tabBtn.setBackgroundColor(getResources().getColor(R.color.main_black));
            tabBtn.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout item_lin = (LinearLayout) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.item_lin);

        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tab_textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

}
