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


import com.kids2pull.android.models.RecipientEntry;

/**
 * BaseIRecipientChip defines an object that contains information relevant to a
 * particular recipient.
 */
interface IBaseRecipientChip {

    /**
     * Set the selected state of the chip.
     */
    void setSelected(boolean selected);

    /**
     * Return true if the chip is selected.
     */
    boolean isSelected();

    /**
     * Get the text displayed in the chip.
     */
    CharSequence getDisplay();


    /**
     * Get associated RecipientEntry.
     */
    RecipientEntry getEntry();

    /**
     * Set the text in the edittextview originally associated with this chip
     * before any reverse lookups.
     */
    void setOriginalText(String text);

    /**
     * Get the text in the edittextview originally associated with this chip
     * before any reverse lookups.
     */
    CharSequence getOriginalText();
}
