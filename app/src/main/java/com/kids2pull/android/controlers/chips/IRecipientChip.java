package com.kids2pull.android.controlers.chips;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by pasha on 30/11/2015.
 */
public interface IRecipientChip extends IBaseRecipientChip {


    /**
     * Get the bounds of the chip; may be 0,0 if it is not visibly rendered.
     */
    Rect getBounds();

    /**
     * Draw the chip.
     */
    void draw(Canvas canvas);
}
