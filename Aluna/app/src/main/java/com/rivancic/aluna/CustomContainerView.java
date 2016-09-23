package com.rivancic.aluna;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.mxn.soul.slidingcard_core.ContainerView;

/**
 * Created by rivancic on 23/09/16.
 */
public class CustomContainerView extends ContainerView {
    public CustomContainerView(Context context) {

        super(context);
    }

    public CustomContainerView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    public void addToView(View child) {

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(child, 0, layoutParams);
    }
}
