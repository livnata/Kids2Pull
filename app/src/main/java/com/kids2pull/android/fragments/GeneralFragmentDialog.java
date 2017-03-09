package com.kids2pull.android.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kids2pull.android.R;
import com.kids2pull.android.controlers.picker.FrameLayoutWithTextView;
import com.kids2pull.android.utils.StringUtils;


public class GeneralFragmentDialog extends DialogFragment {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_COMMENT = "comment";
    public static final String PARAM_POSITIVE_BUTTON = "positive";
    public static final String PARAM_NEGATIVE_BUTTON = "negative";
    public static final String PARAM_USE_HTML = "html";

    protected IGeneralPopup activityCallback;
    protected String mTitle, mText, mPositiveButton, mNegativeButton, mComment;
    protected boolean useHtmlTags;

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            //workaround for IllegalStateException: Activity has been destroyed
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IGeneralPopup) {
            activityCallback = (IGeneralPopup) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static GeneralFragmentDialog newInstance() {
        return new GeneralFragmentDialog();
    }

    public void setAnonymousCallback(IGeneralPopup generalPopup) {
        activityCallback = generalPopup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupGeneralTheme);

        if (getArguments() != null) {
            mTitle = getArguments().getString(PARAM_TITLE);
            mText = getArguments().getString(PARAM_TEXT);
            mComment = getArguments().getString(PARAM_COMMENT);
            mPositiveButton = getArguments().getString(PARAM_POSITIVE_BUTTON);
            mNegativeButton = getArguments().getString(PARAM_NEGATIVE_BUTTON);
            useHtmlTags = getArguments().getBoolean(PARAM_USE_HTML);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.general_popup_fragment_dialog, container, false);
        inflate.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setShowsDialog(true);

        initUI();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (activityCallback != null) {
            activityCallback.onPopupNegativeClickListener(GeneralFragmentDialog.this);
        } else {
            dismissAllowingStateLoss();
        }
    }

    private void initUI() {

        if (!TextUtils.isEmpty(mTitle)) {
            ((TextView) getView().findViewById(R.id.lbl_title)).setText(StringUtils.capitalizeFirstLetters(mTitle));
        } else if (getView().findViewById(R.id.lbl_title) != null) {
            getView().findViewById(R.id.lbl_title).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mText)) {
            View body = getView().findViewById(R.id.lbl_body);
            if (body instanceof FrameLayoutWithTextView) {
                ((FrameLayoutWithTextView) body).setText(useHtmlTags ? Html.fromHtml(mText) : mText);
            } else {
                ((TextView) body).setText(useHtmlTags ? Html.fromHtml(mText) : mText);
            }

        } else if (getView().findViewById(R.id.lbl_body) != null) {
            getView().findViewById(R.id.lbl_body).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mComment)) {
            ((TextView) getView().findViewById(R.id.lbl_comment)).setText(Html.fromHtml(mComment));
        } else if (getView().findViewById(R.id.lbl_comment) != null) {
            getView().findViewById(R.id.lbl_comment).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mPositiveButton)) {
            ((TextView) getView().findViewById(R.id.btn_yes)).setText(mPositiveButton);
            getView().findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View paramView) {
                    if (activityCallback != null) {
                        activityCallback.onPopupPositiveClickListener(GeneralFragmentDialog.this);
                    } else {
                        dismissAllowingStateLoss();
                    }

                }
            });
        } else {
            getView().findViewById(R.id.btn_yes).setVisibility(View.GONE);
            getView().findViewById(R.id.divider).setVisibility(View.GONE);

        }

        if (!TextUtils.isEmpty(mNegativeButton)) {
            ((TextView) getView().findViewById(R.id.btn_no)).setText(mNegativeButton);
            getView().findViewById(R.id.btn_no).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View paramView) {
                    if (activityCallback != null) {
                        activityCallback.onPopupNegativeClickListener(GeneralFragmentDialog.this);
                    } else {
                        dismissAllowingStateLoss();
                    }
                }
            });
        } else {
            getView().findViewById(R.id.btn_no).setVisibility(View.GONE);
            getView().findViewById(R.id.divider).setVisibility(View.GONE);
        }
    }

}
