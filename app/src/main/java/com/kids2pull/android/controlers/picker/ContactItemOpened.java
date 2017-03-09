package com.kids2pull.android.controlers.picker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kids2pull.android.R;
import com.kids2pull.android.controlers.CheckableImageView;
import com.kids2pull.android.loggers.Logger;
import com.kids2pull.android.models.SelectableItem;

import java.util.List;

import static com.kids2pull.android.utils.LocalizationManager.isRTL;

/**
 * Created by pasha on 8/16/15.
 */
public class ContactItemOpened extends LinearLayout {

    private ImageView mImage;
    private boolean isRTL;
    private TextView mLabel;
    private LinearLayout mContactGroup;
    private LinearLayout mContactInfoGroup;
    private int mBackgroundSelectorId;
    private int imageMargin;
    private LayoutInflater mInflater;

    public ContactItemOpened(Context context) {
        super(context);
        init();
    }

    public ContactItemOpened(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ContactItemOpened(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContactItemOpened(Context context, AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        setOrientation(VERTICAL);
        mInflater = LayoutInflater.from(getContext());
        isRTL = isRTL();

        int imageSize = getResources().getDimensionPixelSize(R.dimen.contact_list_image_size);
        imageMargin = getResources().getDimensionPixelSize(R.dimen.contact_list_image_margin);

        mContactInfoGroup = new LinearLayout(getContext());
        mContactInfoGroup.setOrientation(HORIZONTAL);
        mContactInfoGroup.setClickable(true);
        mContactInfoGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        mImage = new ImageView(getContext());
        mImage.setAdjustViewBounds(true);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mImage.setLayoutParams(new MarginLayoutParams(imageSize, imageSize));

        mContactInfoGroup.addView(mImage);

        mLabel = new TextView(getContext());
        mLabel.setTextColor(getResources().getColor(R.color.guid_c6));
        mLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.guid_dim_9));
        mLabel.setSingleLine(true);
        mLabel.setEllipsize(TextUtils.TruncateAt.END);
        mLabel.setGravity(isRTL ? Gravity.RIGHT : Gravity.LEFT);
        mLabel.setLayoutParams(new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT,
                MarginLayoutParams.WRAP_CONTENT));
        mLabel.setText("Test");

        mContactInfoGroup.addView(mLabel);

        int[] attrs = new int[]{R.attr.selectableItemBackground};

        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        mBackgroundSelectorId = ta.getResourceId(0, 0);
        ta.recycle();
        mContactInfoGroup.setBackgroundResource(mBackgroundSelectorId);

        addView(mContactInfoGroup);

        mContactGroup = new LinearLayout(getContext());
        mContactGroup.setOrientation(VERTICAL);
        mContactGroup.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        addView(mContactGroup);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthConstraints = getPaddingLeft() + getPaddingRight();
        int heightConstraints = getPaddingTop() + getPaddingBottom();
        int width = 0;
        int height = 0;

        measureChildWithMargins(
                mContactInfoGroup,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints);

        width = mContactInfoGroup.getMeasuredWidth();
        height = mContactInfoGroup.getMeasuredHeight() + imageMargin + imageMargin;

        measureChildWithMargins(
                mContactGroup,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints);

        height += mContactGroup.getMeasuredHeight();

        setMeasuredDimension(
                resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int infoGroupHeight = mContactInfoGroup.getMeasuredHeight() + imageMargin * 2;
        mContactInfoGroup.layout(0, 0, mContactInfoGroup.getMeasuredWidth(), infoGroupHeight);

        int labelGroupOffset = (infoGroupHeight - mLabel.getMeasuredHeight()) / 2;
        if (isRTL) {
            mImage.layout(right - mImage.getMeasuredWidth() - imageMargin, imageMargin,
                    right - imageMargin, imageMargin + mImage.getMeasuredHeight());
            mLabel.layout(imageMargin, labelGroupOffset, mImage.getLeft() - imageMargin,
                    labelGroupOffset + mLabel.getMeasuredHeight());
        } else {
            mImage.layout(imageMargin, imageMargin,
                    imageMargin + mImage.getMeasuredWidth(),
                    imageMargin + mImage.getMeasuredHeight());
            mLabel.layout(mImage.getRight() + imageMargin, labelGroupOffset, right - imageMargin,
                    labelGroupOffset + mLabel.getMeasuredHeight());
        }

        mContactGroup.layout(0, infoGroupHeight,
                mContactGroup.getMeasuredWidth(),
                infoGroupHeight + mContactGroup.getMeasuredHeight());
    }


    public ImageView getImageView() {
        return mImage;
    }

    public View getInfoGroup() {
        return mContactInfoGroup;
    }

    public void setLabel(String label) {
        mLabel.setText(label);
    }

    public void setLabel(Spannable label) {
        mLabel.setText(label);
    }

    public void setContactItems(List<SelectableItem> items, View.OnClickListener clickListener, int parentPosition) {
        if (items == null || items.isEmpty()) {
            mContactGroup.removeAllViews();
        } else {
            if (items.size() > mContactGroup.getChildCount()) {
                Logger.w("pasha", "added views " + (items.size() - mContactGroup.getChildCount()));
                for (int i = mContactGroup.getChildCount(); i < items.size(); i++) {
                    mInflater.inflate(R.layout.contact_list_subitem, mContactGroup, true);
                }
            } else if (items.size() < mContactGroup.getChildCount()) {
                Logger.w("pasha",
                        "removed views " + (mContactGroup.getChildCount() - items.size()));
                for (int i = mContactGroup.getChildCount() - 1; i >= items.size(); i--) {
                    mContactGroup.removeViewAt(i);
                }
            }

            View itemView;
            for (int i = 0; i < items.size(); i++) {
                items.get(i).setParentPosition(parentPosition);
                itemView = mContactGroup.getChildAt(i);
                itemView.setOnClickListener(clickListener);
                itemView.setTag(items.get(i));
                ((TextView) itemView.findViewById(R.id.text)).setText(items.get(i).getValue());
                ((CheckableImageView) itemView.findViewById(R.id.checkmark)).setChecked(
                        items.get(i).isSelected());
            }


        }

    }


}
