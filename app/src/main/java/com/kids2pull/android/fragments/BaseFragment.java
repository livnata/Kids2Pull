package com.kids2pull.android.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kids2pull.android.R;


public class BaseFragment extends Fragment {

    private ProgressDialog mMaskDialog;
    private boolean dialogShouldBeShown;

    @Override
    public void onDestroy() {
        super.onDestroy();
        unmask();
    }

    protected void hideKeyboard(View view) {
        if (view != null && getActivity() != null) {
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    protected void showKeyboard(final View view) {
        if( view != null){
            view.post(new Runnable() {
                @Override
                public void run() {
                    if(getActivity() != null) {
                        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                    }
                }
            });
        }
    }

    public void mask() {
        if (isVisible()) {
            mask(getString(R.string.WaitText));
        } else {
            //mark that on start there should be opened progress dialog
            dialogShouldBeShown = true;
        }
    }

    public void mask(String text) {
        if (mMaskDialog != null) {
            return;
        }
        mMaskDialog = ProgressDialog.show(getActivity(), "", text, false, false);
    }

    public void unmask() {
        if (mMaskDialog != null) {
            try {
                mMaskDialog.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            mMaskDialog = null;
            dialogShouldBeShown = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(dialogShouldBeShown){
            mask();
        }
    }

    protected boolean isPreHoneycomb(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
    }

//    public UseCasesRunner getUseCaseRunner() {
//        return new BoundUseCaseRunner(getContext(), getLoaderManager());
//    }
}
