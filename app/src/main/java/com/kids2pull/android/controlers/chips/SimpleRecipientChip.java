/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kids2pull.android.controlers.chips;

import android.text.TextUtils;

import com.kids2pull.android.models.RecipientEntry;


class SimpleRecipientChip implements IBaseRecipientChip {

    private final CharSequence mDisplay;
    private final RecipientEntry mEntry;
    private boolean mSelected = false;
    private CharSequence mOriginalText;

    public SimpleRecipientChip(final RecipientEntry entry) {
        mDisplay = entry.getName();
        mEntry = entry;
    }

    @Override
    public void setSelected(final boolean selected) {
        mSelected = selected;
    }

    @Override
    public boolean isSelected() {
        return mSelected;
    }

    @Override
    public CharSequence getDisplay() {
        return mDisplay;
    }

    @Override
    public RecipientEntry getEntry() {
        return mEntry;
    }

    @Override
    public void setOriginalText(final String text) {
        if (TextUtils.isEmpty(text)) {
            mOriginalText = text;
        } else {
            mOriginalText = text.trim();
        }
    }

    @Override
    public CharSequence getOriginalText() {
        return !TextUtils.isEmpty(mOriginalText) ? mOriginalText : mDisplay;
    }

//    @Override
//    public String toString() {
//        return mDisplay + " <" + TextUtils.join(", ", mEntry.getContacts()) + ">";
//    }
}