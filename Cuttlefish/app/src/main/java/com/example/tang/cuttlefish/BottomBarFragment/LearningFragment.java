package com.example.tang.cuttlefish.BottomBarFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.example.tang.cuttlefish.PlanContentActivity;
import com.example.tang.cuttlefish.R;
import com.example.tang.cuttlefish.db.MyDatabaseMe;
import com.example.tang.cuttlefish.db.PlanItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tang on 2016/12/24.
 */
/** 待办事项*/
public class LearningFragment extends Fragment {

    View view;
    RecyclerView mListRecycler;//列表

    @BindView(R.id.recySwipeRefresh)
    SwipeRefreshLayout recySwipeRefresh;

    private List<PlanItem> mDatas;
    private ListAdapter myListAdapter;
    private MyDatabaseMe myPlanDatabase;
    private int MAXID;//最大ID数
    private final int UPDATA_ITEM = 1;
    private final int INSERT_ITEM = 2;

    private int selectPositon = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_learning_plan, container, false);
        ButterKnife.bind(this, view);

        initItemData();
        myListAdapter = new ListAdapter(mDatas);
        initData();
        mListRecycler.setAdapter(myListAdapter);
        //((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.bottomBar));
        //setHasOptionsMenu(true);
        //下拉更新
        recySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myListAdapter.upDataAll();
                recySwipeRefresh.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.learnplanmenu, menu);//修改菜单
        super.onCreateOptionsMenu(menu, inflater);
    }

    //onMenuItemSelected
    
    //菜单项响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item_add:
                //菜单响应
                insertItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    //activity返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PlanItem result = null;
        switch (requestCode) {
            case UPDATA_ITEM:
                result = (PlanItem) data.getSerializableExtra("resultData");//得到新Activity 关闭后返回的数据
                myListAdapter.upData(result, selectPositon);
                myPlanDatabase.updataData(result);
                break;
            case INSERT_ITEM:
                result = (PlanItem) data.getSerializableExtra("resultData");//得到新Activity 关闭后返回的数据
                myPlanDatabase.insertData(result);
                myListAdapter.addData(result, 0);
                break;
        }
    }

    /**
     * 初始化数据,以及单击事件
     */
    private void initData() {

        //初始化
        mListRecycler = (RecyclerView) view.findViewById(R.id.List_RecyView);

        mListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));


        //设置Item增加、移除动画
        mListRecycler.setItemAnimator(new DefaultItemAnimator());
        mListRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));

        //单击事件
        myListAdapter.setOnItemClickLitener(new OnItemClickLitener() {

            //单击事件更新数据
            @Override
            public void onItemClick(View view, int position) {
                /*
                myPlanDatabase.insertData(data);
                */
                //myListAdapter.addData(data,position);
                updataItem(view, position);


            }

            //长单击事件
            @Override
            public void onItemLongClick(View view, final int position) {

                // myListAdapter.removeData(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());//对话框
                builder.setMessage("删除该任务");
                builder.setTitle("提示");

                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除任务
                        int id = mDatas.get(position).getId();
                        myListAdapter.removeData(position);
                        myPlanDatabase.delete(id);
                    }
                });

                builder.show();
            }
        });




    }

    /**
     * 初始化item
     */
    private void initItemData() {
        //数据初始化
        mDatas = new ArrayList<PlanItem>();
        //数据库连接
        myPlanDatabase = new MyDatabaseMe(view.getContext());
        myPlanDatabase.ConnectDatabase();
        mDatas = myPlanDatabase.queryData();//获取查询数据
        if (mDatas.size() != 0) {
            MAXID = mDatas.get(0).getId();
        }
        MAXID = 0;
    }

    /**
     * 列表
     */
    class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

        private List<PlanItem> mDatas;

        public ListAdapter(List<PlanItem> mDatas) {
            this.mDatas = mDatas;
        }

        //单击和长按事件
        private OnItemClickLitener mOnItemClickLitener = null;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textitem_view, parent, false);
            final ListViewHolder holder = new ListViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ListViewHolder holder, final int position) {

            holder.getText().setText(mDatas.get(position).getTitle());

            if (mDatas.get(position).getIsComp() != 0)
                holder.getCheck().setChecked(true);
            else holder.getCheck().setChecked(false);

            holder.getCheck().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.getCheck().isChecked())
                        mDatas.get(position).setIsComp(1);
                    else mDatas.get(position).setIsComp(0);

                    myPlanDatabase.updataData(mDatas.get(position));
                    notifyItemChanged(position);

                }
            });


            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        /**
         * 添加item
         */
        public void addData(PlanItem data, int position) {
            mDatas.add(position, data);
            notifyItemInserted(position);
        }

        //更新
        public void upData(PlanItem data, int position) {

            mDatas.set(position,data);
            notifyItemChanged(position);
        }

        /**
         * 删除item
         */
        public void removeData(int position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

        /**
         * 更新全部
         */
        public void upDataAll() {
            mDatas = myPlanDatabase.queryData();//获取查询数据
            notifyDataSetChanged();
        }
    }

    /**
     * recyclerViewItem布局
     */
    class ListViewHolder extends RecyclerView.ViewHolder {
        private CheckBox item;
        private TextView text;

        public ListViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.textPlanView);
            item = (CheckBox) itemView.findViewById(R.id.checkItem);
        }

        public CheckBox getCheck() {
            return item;
        }

        public TextView getText() {
            return text;
        }
    }


    //更新列表
    private void updataItem(View view, int position) {

        selectPositon = position;

        Intent intent = new Intent(view.getContext(), PlanContentActivity.class);
        //获取数据
        PlanItem data = new PlanItem();
        data.setContext(mDatas.get(position).getContext());
        data.setTitle(mDatas.get(position).getTitle());
        data.setIsComp(mDatas.get(position).getIsComp());
        data.setId(mDatas.get(position).getId());

        //添加传递数据
        Bundle bundle = new Bundle();
        bundle.putSerializable("ItemData", data);
        intent.putExtras(bundle);
        //开始并获取返回数据
        startActivityForResult(intent, UPDATA_ITEM);
    }

    //插入数据
    private void insertItem() {
        Intent intent = new Intent(view.getContext(), PlanContentActivity.class);
        //获取数据
        PlanItem data = new PlanItem();
        data.setContext("");
        data.setTitle("");
        data.setIsComp(0);
        data.setId(MAXID = MAXID + 1);

        //添加传递数据
        Bundle bundle = new Bundle();
        bundle.putSerializable("ItemData", data);
        intent.putExtras(bundle);
        //开始并获取返回数据
        startActivityForResult(intent, INSERT_ITEM);
    }

    /**
     * 单击事件接口
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}

