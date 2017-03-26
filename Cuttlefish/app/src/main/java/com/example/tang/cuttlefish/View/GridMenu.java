package com.example.tang.cuttlefish.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tang.cuttlefish.R;

/**
 * Created by Tang on 2017/3/14.
 */

public class GridMenu extends FrameLayout{

    TextView titleText;
    ImageView textImage;
    public GridMenu(Context context,AttributeSet attrs) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.grid_activity, this);
        titleText= (TextView) findViewById(R.id.classify_text);
        textImage = (ImageView) findViewById(R.id.classify_image);

    }

    public void setTextImage(Bitmap textImage) {
        this.textImage.setImageBitmap(textImage);
    }

    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }

    public ImageView getTextImage() {
        return textImage;
    }

    public TextView getTitleText() {
        return titleText;
    }
}
