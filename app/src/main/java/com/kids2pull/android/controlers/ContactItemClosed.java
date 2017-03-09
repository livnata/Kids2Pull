package com.kids2pull.android.controlers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kids2pull.android.R;
import com.kids2pull.android.utils.LocalizationManager;


/**
 * Created by pasha on 8/13/15.
 */
public class ContactItemClosed extends FrameLayout {


    private boolean isRTL;
    private ImageView mImage;
    private TextView mLabel;
    private CheckableImageView mCheckMark;
    private int imageMargin;

    public ContactItemClosed(Context context) {
        super(context);
        init();
    }

    public ContactItemClosed(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContactItemClosed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContactItemClosed(Context context, AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        setClickable(true);
        isRTL = LocalizationManager.isRTL();

        int imageSize = getResources().getDimensionPixelSize(R.dimen.contact_list_image_size);
        imageMargin = getResources().getDimensionPixelSize(R.dimen.contact_list_image_margin);

        mImage = new ImageView(getContext());
        mImage.setAdjustViewBounds(true);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImage.setLayoutParams(new MarginLayoutParams(imageSize, imageSize));

        addView(mImage);

        mCheckMark = new CheckableImageView(getContext(), null);
        mCheckMark.setImageResource(R.drawable.circle_checkbox_selector);
        mCheckMark.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        addView(mCheckMark);

        mLabel = new TextView(getContext());
        mLabel.setTextColor(getResources().getColor(R.color.guid_c6));
        mLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.guid_dim_9));
        mLabel.setSingleLine(true);
        mLabel.setEllipsize(TextUtils.TruncateAt.END);
        mLabel.setGravity(isRTL ? Gravity.RIGHT : Gravity.LEFT);
        mLabel.setLayoutParams(new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT,
                MarginLayoutParams.WRAP_CONTENT));

        addView(mLabel);

        int[] attrs = new int[]{R.attr.selectableItemBackground};

        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        setForeground(ta.getDrawable(0));
        ta.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthConstraints = getPaddingLeft() + getPaddingRight();
        int heightConstraints = getPaddingTop() + getPaddingBottom();
        int width = 0;
        int height = 0;

        measureChildWithMargins(
                mImage,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints);

        widthConstraints += mImage.getMeasuredWidth();
        width += mImage.getMeasuredWidth();
        height = mImage.getMeasuredHeight() + imageMargin + imageMargin;

        measureChildWithMargins(
                mCheckMark,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints);

        widthConstraints += mCheckMark.getMeasuredWidth();
        width += mCheckMark.getMeasuredWidth();

        int verticalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - widthConstraints,
                MeasureSpec.getMode(widthMeasureSpec));

        int verticalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstraints,
                MeasureSpec.getMode(heightMeasureSpec));

        measureChildWithMargins(
                mLabel,
                verticalWidthMeasureSpec,
                0,
                verticalHeightMeasureSpec,
                0);

        width += mLabel.getMeasuredWidth();

        setMeasuredDimension(
                resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int height = bottom - top;
        int checkMarkOffset = (height - mCheckMark.getMeasuredHeight()) / 2;
        int labelGroupOffset = (height - mLabel.getMeasuredHeight()) / 2;
        if (isRTL) {
            mImage.layout(right - mImage.getMeasuredWidth() - imageMargin, imageMargin, right - imageMargin, imageMargin + mImage.getMeasuredHeight());
            mCheckMark.layout(imageMargin, checkMarkOffset, imageMargin + mCheckMark.getMeasuredWidth(),
                    checkMarkOffset + mCheckMark.getMeasuredHeight());
            mLabel.layout(mCheckMark.getRight() + imageMargin, labelGroupOffset, mImage.getLeft() - imageMargin,
                    labelGroupOffset + mLabel.getMeasuredHeight());
        } else {
            mImage.layout(imageMargin, imageMargin,
                    imageMargin + mImage.getMeasuredWidth(), imageMargin + mImage.getMeasuredHeight());
            mCheckMark.layout(right - mCheckMark.getMeasuredWidth() - imageMargin, checkMarkOffset,
                    right - imageMargin,
                    checkMarkOffset + mCheckMark.getMeasuredHeight());

            mLabel.layout(mImage.getRight() + imageMargin, labelGroupOffset, mCheckMark.getLeft() - imageMargin,
                    labelGroupOffset + mLabel.getMeasuredHeight());
        }
    }


    public ImageView getImageView() {
        return mImage;
    }

    public void setLabel(String label) {
        mLabel.setText(label);
    }

    public void setLabel(Spannable label) {
        mLabel.setText(label);
    }

    public void setChecked(boolean checked) {
        mCheckMark.setChecked(checked);
    }

}
