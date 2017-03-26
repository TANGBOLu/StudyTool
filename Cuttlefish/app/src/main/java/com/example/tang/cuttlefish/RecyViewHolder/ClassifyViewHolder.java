package com.example.tang.cuttlefish.RecyViewHolder;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.tang.cuttlefish.R;
import com.example.tang.cuttlefish.View.GridMenu;

/**
 * Created by Tang on 2017/3/14.
 */

public class ClassifyViewHolder extends RecyclerView.ViewHolder {

    private GridMenu gridCat;
    private GridMenu gridJHS;
    private GridMenu grid_wm;
    private GridMenu grid_tmcs;
    private GridMenu gridTMgj;

    public ClassifyViewHolder(View itemView) {
        super(itemView);
        grid_tmcs=(GridMenu) itemView.findViewById(R.id.grid_tmcs);
        grid_wm=(GridMenu)itemView.findViewById(R.id.grid_wm);
        gridCat=(GridMenu)itemView.findViewById(R.id.gridCat);
        gridJHS=(GridMenu)itemView.findViewById(R.id.gridJHS);
        gridTMgj=(GridMenu)itemView.findViewById(R.id.gridTMgj);

//        grid_tmcs.getTitleText().setText("鞋子");
//        grid_tmcs.setTextImage(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bb_bottom_bar_top_shadow));
//
//        grid_wm.setTitleText("衣服");
//        grid_wm.setTextImage(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bb_bottom_bar_top_shadow));
//
//        gridCat.setTitleText("外套");
//        gridCat.setTextImage(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bb_bottom_bar_top_shadow));
//
//        gridJHS.setTitleText("袜子");
//        gridJHS.setTextImage(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bb_bottom_bar_top_shadow));
//
//        gridTMgj.setTitleText("火车");
//        gridTMgj.setTextImage(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bb_bottom_bar_top_shadow));
    }
}
