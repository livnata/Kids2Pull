package com.kids2pull.android.controlers.picker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kids2pull.android.R;


/**
 * Created by elinaor on 04/01/2016.
 */

@SuppressLint("NewApi")
public class FrameLayoutWithTextView extends FrameLayout {

    private EqualWidthTextView mEqualWidthTextView;

    public FrameLayoutWithTextView(Context context) {
        super(context);
    }

    public FrameLayoutWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public FrameLayoutWithTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public FrameLayoutWithTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mEqualWidthTextView = new EqualWidthTextView(context);
        mEqualWidthTextView.setLayoutParams(new FrameLayout.
                LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        mEqualWidthTextView.setGravity(Gravity.CENTER);

        addView(mEqualWidthTextView);

        if (attrs != null) {
            TypedArray styledAttrs = context
                    .obtainStyledAttributes(attrs, R.styleable.FrameLayoutWithTextView);
            setText(styledAttrs.getText(R.styleable.FrameLayoutWithTextView_setText));
            setTextSize(styledAttrs.getDimension(R.styleable.FrameLayoutWithTextView_textSize, 10f));
            setTextColor(styledAttrs.getColor(R.styleable.FrameLayoutWithTextView_textColor, getResources().getColor(R.color.guid_c3)));
            setFontFamily(styledAttrs.getInt(R.styleable.FrameLayoutWithTextView_fontFamily, -1));
            styledAttrs.recycle();
        }
    }

    public void setText(CharSequence text) {
        if (text != null) {
            mEqualWidthTextView.setText(text);
        }
    }

    public void setTextColor(int color) {
        mEqualWidthTextView.setTextColor(color);
    }

    public void setTextSize(float size) {
        mEqualWidthTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setFontFamily(int fontFamilyIndex) {
        switch (fontFamilyIndex) {
            case 0:
                mEqualWidthTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                break;
            case 1:
                mEqualWidthTextView.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
                break;
            case 2:
                mEqualWidthTextView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
                break;
            case 3:
                mEqualWidthTextView.setTypeface(Typeface.create("sans-serif-light", Typeface.BOLD));
                break;
            default:
                mEqualWidthTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                break;
        }
    }

    public void append(CharSequence text) {
        if (text != null) {
            mEqualWidthTextView.append(text);
        }
    }
}
