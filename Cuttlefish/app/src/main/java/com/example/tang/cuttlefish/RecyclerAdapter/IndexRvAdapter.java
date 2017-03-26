package com.example.tang.cuttlefish.RecyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.tang.cuttlefish.R;
import com.example.tang.cuttlefish.RecyViewHolder.BannerViewHolder;
import com.example.tang.cuttlefish.RecyViewHolder.ClassifyViewHolder;
import com.example.tang.cuttlefish.RecyViewHolder.LocalImageHolderView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Tang on 2017/3/12.
 */

public class IndexRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    //头条总数
    private static final int HEADER_CONUNT=2;

    private static final int ERROR =-1 ;
    private static final int HEADER_BENNER=0;
    private static final int HEADER_CLASSIFY=1;

    public IndexRvAdapter(){
        /* 初始化轮转图片资源*/
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case HEADER_BENNER:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.index_banner, parent, false));
            case HEADER_CLASSIFY:
                return new ClassifyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_classify_recy,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType=getItemViewType(position);
        switch (viewType){
            case HEADER_BENNER:
                addBannerHolder(holder);
                break;
            case HEADER_CLASSIFY:
                break;
            default:
                System.out.println("id错误");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return HEADER_BENNER;
        }
        if (position==1)
            return HEADER_CLASSIFY;
        return ERROR;
    }

    /** 添加广告holder*/
    @Override
    public int getItemCount() {
        return HEADER_CONUNT;
    }
    /**添加Banner*/
    private void addBannerHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder myHolder= (BannerViewHolder)holder;
        ConvenientBanner myConvenientBanner=myHolder.getConvenientBanner();
        myConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        },localImages).startTurning(2000)//自动轮播
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }
    /** 获取轮转图片id*/
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
