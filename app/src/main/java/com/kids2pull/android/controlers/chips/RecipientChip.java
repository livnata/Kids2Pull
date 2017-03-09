package com.kids2pull.android.controlers.chips;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.kids2pull.android.models.RecipientEntry;


/**
 * Created by pasha on 30/11/2015.
 */
public class RecipientChip extends ReplacementDrawableSpan
        implements IRecipientChip {

    private final SimpleRecipientChip mDelegate;

    public RecipientChip(final Drawable drawable, final RecipientEntry entry) {
        super(drawable);

        mDelegate = new SimpleRecipientChip(entry);
    }

    @Override
    public Rect getBounds() {
        return super.getBounds();
    }

    @Override
    public void draw(Canvas canvas) {
        getDrawable().draw(canvas);
    }

    @Override
    public void setSelected(boolean selected) {
        mDelegate.setSelected(selected);
    }

    @Override
    public boolean isSelected() {
        return mDelegate.isSelected();
    }

    @Override
    public CharSequence getDisplay() {
        return mDelegate.getDisplay();
    }

    @Override
    public RecipientEntry getEntry() {
        return mDelegate.getEntry();
    }

    @Override
    public void setOriginalText(String text) {
        mDelegate.setOriginalText(text);
    }

    @Override
    public CharSequence getOriginalText() {
        return mDelegate.getOriginalText();
    }
}
