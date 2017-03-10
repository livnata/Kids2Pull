package com.kids2pull.android.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.kids2pull.android.utils.DeviceUtils;


public abstract class BottomSheetFragment extends BaseFragment {

    public static final String PARAM_INITIAL_MODE = "PARAM_INITIAL_MODE";

    public static final int STATE_HIDDEN = 0;
    public static final int STATE_PEEKED = 2;
    public static final int STATE_EXPANDED = 3;

    private static final int ANIMATING_FROM_HIDDEN_TO_PEEKED = 40;
    private static final int ANIMATING_FROM_PEEKED_TO_EXPANDED = 41;
    private static final int ANIMATING_FROM_EXPANDED_TO_PEEKED = 42;
    private static final int ANIMATING_FROM_PEEKED_TO_HIDDEN = 43;
    private static final int ANIMATING_FROM_EXPANDED_TO_HIDDEN = 44;
    private static final int ANIMATING_FROM_HIDDEN_TO_EXPANDED = 45;
    private static final int ANIMATION_DONE = 50;

    public static final float MAX_DIM_ALPHA = 0.8f;
    public static final float MAX_DIM_ALPHA_POP = 0.6f;

    protected static final long ANIMATION_DURATION = 300;
    private static final Property<BottomSheetFragment, Float> SHEET_TRANSLATION
            = new Property<BottomSheetFragment, Float>(Float.class, "contentViewTranslation") {
        @Override
        public Float get(BottomSheetFragment object) {
            return object.mContentViewTranslation;
        }

        @Override
        public void set(BottomSheetFragment object, Float value) {
            object.setContentViewTranslation(value);
        }
    };

    private DecelerateInterpolator animationInterpolator = new DecelerateInterpolator(1.6f);

    private View mDimView;
    private int mScreenHeight;
    private View mContentView;
    private int mState;
    private int mAnimationState;
    private float mContentViewTranslation;
    private Animator mCurrentAnimator;
    private float mPeekedViewSize;


    public BottomSheetFragment() {
        setState(STATE_HIDDEN);
    }

    protected abstract View getContentView(ViewGroup parent);
    protected abstract void finalizeFragment();
    protected abstract void onDimViewClicked();

    public boolean isAnimating() {
        return mCurrentAnimator != null;
    }

    public void peekContentView() {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                peekContentView(false);
            }
        });

    }

    public void dismissContentView() {
        if (mAnimationState != ANIMATION_DONE || mState == STATE_HIDDEN) {
            return;
        }
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(this, SHEET_TRANSLATION, 0);
        anim.setDuration(ANIMATION_DURATION);
        anim.setInterpolator(animationInterpolator);
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                mCurrentAnimator = null;
                setState(STATE_HIDDEN);
                finalizeFragment();
            }
        });
        anim.start();
        mCurrentAnimator = anim;
        setAnimationState(mState == STATE_PEEKED ? ANIMATING_FROM_PEEKED_TO_HIDDEN
                : ANIMATING_FROM_EXPANDED_TO_HIDDEN);
    }


    public void expandContentView() {
        expandContentView(ANIMATION_DURATION);
    }

    public void expandContentView(final long time) {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                internalExpandContentView(time >= 0 ? time : 0);
            }
        });
    }


    public int getState() {
        return mState;
    }

    public void setPeekedContentViewSize(float size) {
        mPeekedViewSize = size;
        if (mContentView != null && (mState == STATE_HIDDEN || mState == STATE_PEEKED)) {
            adjustContentViewSize((int) (mPeekedViewSize + 0.5f));
            peekContentView(true);
        }
    }

    public float getMaxTransitionSize() {
        return getView().getHeight();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        FrameLayout root = new FrameLayout(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        root.setLayoutParams(layoutParams);
        mDimView = new View(getContext());
        mDimView.setBackgroundColor(Color.BLACK);
        mDimView.setAlpha(0);
        mDimView.setVisibility(View.INVISIBLE);
        mDimView.setClickable(true);
        mDimView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onDimViewClicked();
            }
        });
        root.addView(mDimView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        mContentView = getContentView((FrameLayout) getView());
        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
            mContentView.setClickable(true);
            root.addView(mContentView);
            mContentView.post(new Runnable() {
                @Override
                public void run() {
                    //set mPeekedViewSize only if wasn't set yet
                    if (mPeekedViewSize == 0) {
                        if (mContentView.getHeight() > getMaxPeekedHeight()) {
                            mPeekedViewSize = getMaxPeekedHeight();
                        } else {
                            mPeekedViewSize = mContentView.getHeight();
                        }
                    }
                    mContentView.setTranslationY(mPeekedViewSize);
                    mContentView.setVisibility(View.VISIBLE);

                }
            });
        } else {
            throw new IllegalStateException(
                    "You must return valid View in getContentView() function");
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScreenHeight = DeviceUtils.getScreenHeight(getActivity());
    }



    private void setState(int state) {
        mState = state;
        setAnimationState(ANIMATION_DONE);
        onContentViewStateChange(state);
    }

    protected void setAnimationState(int animationState) {
        mAnimationState = animationState;
    }



    protected float getDimAlpha(float translation, float maxTranslation) {
        float progress = translation / (maxTranslation);
        return progress * MAX_DIM_ALPHA;
    }

    private void setContentViewTranslation(float sheetTranslation) {
        mContentViewTranslation = sheetTranslation;
        if(getView() == null){
            return;
        }
        mContentView.setTranslationY(getView().getHeight() - sheetTranslation);
        float dimAlpha = getDimAlpha(sheetTranslation, getMaxTransitionSize());
        mDimView.setAlpha(dimAlpha);
        mDimView.setVisibility(dimAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
    }



    private void peekContentView(final boolean forceChange) {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                innerPeekContentview(forceChange);
            }
        });
    }

    private void innerPeekContentview(boolean force) {
        if (!force && (mAnimationState != ANIMATION_DONE || mState == STATE_PEEKED)) {
            return;
        }

        ObjectAnimator anim = ObjectAnimator
                .ofFloat(this, SHEET_TRANSLATION, getPeekedContentViewSize());
        anim.setDuration(ANIMATION_DURATION);
        anim.setInterpolator(animationInterpolator);
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                mCurrentAnimator = null;
                setState(STATE_PEEKED);
                adjustContentViewSize((int) mPeekedViewSize);

            }
        });
        anim.start();
        mCurrentAnimator = anim;
        setAnimationState(mState == STATE_HIDDEN ? ANIMATING_FROM_HIDDEN_TO_PEEKED
                : ANIMATING_FROM_EXPANDED_TO_PEEKED);
    }


    private void adjustContentViewSize(int adjustedSize) {
        mContentView.getLayoutParams().height = adjustedSize;
        mContentView.requestLayout();
    }

    private void internalExpandContentView(long time) {
        if (mAnimationState != ANIMATION_DONE || mState == STATE_EXPANDED) {
            return;
        }
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(this, SHEET_TRANSLATION, getView().getHeight());
        anim.setDuration(time);
        anim.setInterpolator(animationInterpolator);
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                mCurrentAnimator = null;
                setState(STATE_EXPANDED);
            }
        });
        anim.start();
        mCurrentAnimator = anim;
        setAnimationState(mState == STATE_PEEKED ? ANIMATING_FROM_PEEKED_TO_EXPANDED
                : ANIMATING_FROM_HIDDEN_TO_EXPANDED);
        adjustContentViewSize(getView().getHeight());

    }

    protected float getPeekedContentViewSize() {
        return (mPeekedViewSize == 0 ? getMaxPeekedHeight() : mPeekedViewSize);
    }

    //This function will be called when state of content view changed
    protected void onContentViewStateChange(int state) {
        //can be override for states changes
    }

    protected float getMaxPeekedHeight() {
        return mScreenHeight * 0.6f;
    }

    protected boolean isInitialStateWasPeeked() {
        return getArguments() == null || getArguments().getInt(PARAM_INITIAL_MODE) == STATE_PEEKED;
    }
}
