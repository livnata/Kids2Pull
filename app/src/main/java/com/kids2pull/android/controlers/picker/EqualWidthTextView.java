package com.kids2pull.android.controlers.picker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("NewApi")
public class EqualWidthTextView extends TextView {

    private final int WIDTH_PIXEL_CHANGE = 10;

    private boolean inLoop;
    private boolean isFirstAccess;
    private boolean isLastStep;
    private int mOriginalLineCount;
    private int mWidthMeasureSpec = 0;

    public EqualWidthTextView(Context context) {
        super(context);
    }

    public EqualWidthTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqualWidthTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EqualWidthTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isLastStep) {
            super.onMeasure(mWidthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        if (isFirstAccess) {
            initUI(widthMeasureSpec,heightMeasureSpec);
        }  if (inLoop) {
            resizeTextViewWidthLoop(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        isFirstAccess = true;
        super.setText(text, type);
    }

    private void initUI(int widthMeasureSpec, int heightMeasureSpec) {
        isFirstAccess = false;
        int textLineCount = getLayout().getLineCount();
        if (textLineCount > 1) {
            inLoop = true;
            isLastStep = false;
            mOriginalLineCount = textLineCount;
            mWidthMeasureSpec = widthMeasureSpec;
            resizeTextViewWidthLoop(widthMeasureSpec,heightMeasureSpec);
        }
    }

    private void resizeTextViewWidthLoop(int widthMeasureSpec, int heightMeasureSpec) {
        int currentLineCount = getLayout().getLineCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (isResizeAvailable(widthSize)) {
            if (currentLineCount != mOriginalLineCount) {
                inLoop = false;
                isLastStep = true;
                widthSize = widthSize + WIDTH_PIXEL_CHANGE;
                mWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
                measure(mWidthMeasureSpec, heightMeasureSpec);
            } else {
                widthSize = widthSize - WIDTH_PIXEL_CHANGE;
                measure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), heightMeasureSpec);
            }
        } else {
            inLoop = false;
            isLastStep = true;
            measure(mWidthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean isResizeAvailable(int widthSize) {
        if (widthSize <= (WIDTH_PIXEL_CHANGE * 2)) {
            return false;
        } else {
            return true;
        }
    }
}
