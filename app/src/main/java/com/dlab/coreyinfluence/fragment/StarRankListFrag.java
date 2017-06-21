package com.dlab.coreyinfluence.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dlab.coreyinfluence.R;
import com.dlab.coreyinfluence.adapter.DialogRCVAdapter;
import com.dlab.coreyinfluence.adapter.StarRankRCVAdapter;

import java.util.ArrayList;

/**
 * 明星排行榜的Fragment
 */
public class StarRankListFrag extends Fragment implements View.OnClickListener {

    // 投票按钮
    private Button btn_vote_star_frag;

    //***********************************榜单列表相关************************************//

    // SwipeRefreshLayout下拉刷新
    private SwipeRefreshLayout srl_ranklist_star;
    // RecyclerView布局
    private RecyclerView rcv_ranklist_star;

    // 榜单列表的图片
    private ArrayList<String> listImgUrls;
    // 榜单列表的姓名
    private ArrayList<String> listName;
    // 榜单列表的票数
    private ArrayList<String> listCount;

    // 榜单列表的适配器
    private StarRankRCVAdapter rankListRCVAdapter;
    private LinearLayoutManager rankListRCVManager;

    //***********************************投票Dialog列表相关************************************//

    // Dialog对象，通过builder.create方法创建
    private AlertDialog voteListDialog;

    // 整体Dialog布局
    private LinearLayout ll_dialog_vote;
    // 嵌套的RCV，展示可以投票的明星（不需要上下拉）
    private RecyclerView rcv_vote_list;

    // 投票Dialog列表的图片
    private ArrayList<String> voteImgUrls;
    // 投票Dialog列表的姓名
    private ArrayList<String> voteNames;

    // 投票Dialog中RCV的适配器
    private DialogRCVAdapter dialogRCVAdapter;
    private GridLayoutManager dialogRCVManager;

    private String nameVoteStar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_star_rank_list, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 找控件
        initView(view);

        // 获取数据源
        initData();

        // 设置适配器
        setAdapter();

        // 设置上拉下拉监听
        setPullUpAndDownListener();

        // 设置点击时间监听
        btn_vote_star_frag.setOnClickListener(this);

    }

    private void setPullUpAndDownListener() {
        //rcv的上拉
        rcv_ranklist_star.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 松手
                    Log.i("", "lastposition=====  " + rankListRCVManager.findLastCompletelyVisibleItemPosition());
                    Log.i("", "size=====  " + listName.size());
                    //获取最后一条完全可见的item的位置
                    if (rankListRCVManager.findLastCompletelyVisibleItemPosition() == (listName.size() - 3)) {
                        //代表拖动到底部
                        for (int i = 0; i < 10; i++) {
                            listName.add("Way  " + i);
                            listCount.add("票数  " + i + "0");
                            listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");

                        }
                        rankListRCVAdapter.update(listName, listCount, listImgUrls);

                    }
                }
            }
        });

        // SwipeRefreshLayout下拉效果
        srl_ranklist_star.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新
                srl_ranklist_star.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl_ranklist_star.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void setAdapter() {
        rankListRCVManager = new LinearLayoutManager(getContext());
        rcv_ranklist_star.setLayoutManager(rankListRCVManager);

        // 当不是瀑布流时，设置这个可以避免重复的增删造成而外的浪费资源，因为要计算item的尺寸，重新绘制
        rcv_ranklist_star.setHasFixedSize(true);
        // 设置是否允许嵌套滑动
//        rcv_ranklist_star.setNestedScrollingEnabled(false);

        rankListRCVAdapter = new StarRankRCVAdapter(getContext(), listName, listCount, listImgUrls);
        rcv_ranklist_star.setAdapter(rankListRCVAdapter);
    }

    private void initData() {

        //***********************************榜单列表数据源************************************//

        // 头像的图片数据源
        listImgUrls = new ArrayList<>();
        // 姓名
        listName = new ArrayList<>();
        // 得票数
        listCount = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listName.add("Corey  " + i);
            listCount.add("票数  " + i + "0");
            listImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");

        }

        //***********************************投票Dialog列表数据源************************************//
        // 图片
        voteImgUrls = new ArrayList<>();
        // 姓名
        voteNames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            voteNames.add("Corey  " + i);
            voteImgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495866559519&di=4e9c7246461ecf5b386019300f53612e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4291741428%2C33422658%26fm%3D214%26gp%3D0.jpg");
        }

    }

    private void initView(View view) {

        // 下拉的srl
        srl_ranklist_star = (SwipeRefreshLayout) view.findViewById(R.id.srl_ranklist_star);
        // 上拉的rcv
        rcv_ranklist_star = (RecyclerView) view.findViewById(R.id.rcv_ranklist_star);

        // 设置SwipeRefreshLayout下拉的时候指示球的位置（80是起始位置，180是最低位置）
        srl_ranklist_star.setProgressViewOffset(true, 80, 180);
        srl_ranklist_star.setColorSchemeResources(R.color.text_yellow);

        // 投票按钮
        btn_vote_star_frag = (Button) view.findViewById(R.id.btn_vote_star_frag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vote_star_frag:

                // 先把上次的投票记录清掉，到时候换成bean对象的list
                nameVoteStar = "";

                // 投票按钮被点击
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //    设置我们自己定义的布局文件作为弹出框的Content
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_vote_list, null);
                // Dialog整体布局
                ll_dialog_vote = (LinearLayout) view.findViewById(R.id.ll_dialog_vote);
                // 从布局中找到rcv
                rcv_vote_list = (RecyclerView) view.findViewById(R.id.rcv_vote_list);
                // 设置成表格布局(3列)
                dialogRCVManager = new GridLayoutManager(getContext(), 3);
                rcv_vote_list.setLayoutManager(dialogRCVManager);

                // 设置投票Dialog中RCV的适配器
                dialogRCVAdapter = new DialogRCVAdapter(getContext(), voteNames, voteImgUrls);
                rcv_vote_list.setAdapter(dialogRCVAdapter);

                // 设置投票列表的item点击事件
                dialogRCVAdapter.setOnItemClickListener(new DialogRCVAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int isItemChosen) {
                        if (position != -1) {
                            // 如果被选中
                            nameVoteStar = voteNames.get(isItemChosen);
                            Toast.makeText(getContext(), voteNames.get(isItemChosen) + "---被选中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 如果被取消
                            nameVoteStar = "";
                            Toast.makeText(getContext(), voteNames.get(isItemChosen) + "---被取消", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                // 把自定义view设置进去
                builder.setView(view);

                // 设置Dialog按钮的点击监听
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nameVoteStar != null) {
                            if (nameVoteStar.length() != 0) {
                                Toast.makeText(getContext(), "投票给--- " + nameVoteStar, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "没有选中任何明星", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "没有选中任何明星", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "取消被点击---", Toast.LENGTH_SHORT).show();
                    }
                });

                // 通过dialog设置动画
                voteListDialog = builder.create();
                Window dialogWindow = voteListDialog.getWindow();
                // dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setWindowAnimations(R.style.VoteListDialog);
                voteListDialog.show();

                break;
            default:
                break;
        }
    }
}
