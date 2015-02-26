package com.ohtu.wearable.wearabledataservice.list;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ohtu.wearable.wearabledataservice.R;

/** Contains layout for the List Items
 *
 */
public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private TextView mName;
    private CheckBox mCheckBox;

    private final float mFadedTextAlpha;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);

        mFadedTextAlpha = getResources()
                .getInteger(R.integer.action_text_faded_alpha) / 100f;
    }

    /**
     * Sets references to the checkbox and text in the item layout definition
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mName = (TextView) findViewById(R.id.name);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
    }

    /**
     * Sets parameters when list item in on the center position of the screen.
     * @param animate Boolean value to specify if the item is animated
     */
    @Override
    public void onCenterPosition(boolean animate) {
        mName.setAlpha(1f);
        mCheckBox.setAlpha(1f);
    }

    /**
     * Sets parameters when list item in not on the center position of the screen.
     * @param animate Boolean value to specify if the item is animated
     */
    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setAlpha(mFadedTextAlpha);
        mCheckBox.setAlpha(mFadedTextAlpha);
    }
}