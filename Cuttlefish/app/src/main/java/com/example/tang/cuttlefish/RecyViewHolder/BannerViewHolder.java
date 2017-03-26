package com.example.tang.cuttlefish.RecyViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.tang.cuttlefish.R;

/**
 * Created by Tang on 2017/3/12.
 */

//https://github.com/saiwu-bigkoo/Android-ConvenientBanner/blob/master/app/src/main/java/com/bigkoo/convenientbannerdemo/MainActivity.java

/**
 * 广告holder
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {
    private ConvenientBanner convenientBanner;
    public static final int ID=123;
    public BannerViewHolder(View itemView) {
        super(itemView);
        convenientBanner = (ConvenientBanner) itemView.findViewById(R.id.indexBanner);
    }

    public ConvenientBanner getConvenientBanner() {
        return convenientBanner;
    }
}
