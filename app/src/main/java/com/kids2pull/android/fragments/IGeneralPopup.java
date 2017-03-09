package com.kids2pull.android.fragments;

import android.support.v4.app.DialogFragment;

public interface IGeneralPopup {

    void onPopupPositiveClickListener(DialogFragment usedDialog);

    void onPopupNegativeClickListener(DialogFragment usedDialog);
}
