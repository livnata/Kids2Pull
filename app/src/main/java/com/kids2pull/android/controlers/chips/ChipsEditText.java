package com.kids2pull.android.controlers.chips;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.QwertyKeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.kids2pull.android.R;
import com.kids2pull.android.models.Contact;
import com.kids2pull.android.models.RecipientEntry;
import com.kids2pull.android.models.SelectableItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by pasha on 29/11/2015.
 */
public class ChipsEditText extends EditText {

    public static final int INFINIT_CHIPS_NUMBER = Integer.MAX_VALUE;

    private static final int COMMIT_CHAR_COMMA = ',';
    private static final int MIN_NUM_OF_CHARS = 2;


    private MultiAutoCompleteTextView.Tokenizer mTokenizer;
    private Drawable mChipBackground;
    private Drawable mChipBackgroundPressed;
    private float mChipHeight;
    private float mChipFontSize;
    private float mLineSpacingExtra;
    private int mChipPadding;
    private IChipsListener mListener;
    private IRecipientChip mSelectedChip;
    private RecipientTextWatcher mTextWatcher;
    private int mMaxChips = INFINIT_CHIPS_NUMBER;
    private boolean isRestrictedToExistContacts = false;
    private boolean isCursorVisible = true;
    private GestureDetector mGestureDetector;
    private boolean isScrollingNow;
    private boolean isDoubleTapped;

    private final Runnable mAddTextWatcher = new Runnable() {
        @Override
        public void run() {
            if (mTextWatcher == null) {
                mTextWatcher = new RecipientTextWatcher();
                addTextChangedListener(mTextWatcher);
            }
        }
    };


    public ChipsEditText(Context context) {
        super(context);
        init();
    }

    public ChipsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChipsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChipsEditText(Context context, AttributeSet attrs, int defStyleAttr,
                         int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        setChipDimensions(getContext());
        mTextWatcher = new RecipientTextWatcher();
        addTextChangedListener(mTextWatcher);
        checkForChipLimit();
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public boolean onDoubleTap(MotionEvent e) {
                isDoubleTapped = true;
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                isScrollingNow = true;
                setCursorVisible(false);
                return false;
            }

        });
    }

    public void setTokenizer(MultiAutoCompleteTextView.Tokenizer tokenizer) {
        mTokenizer = tokenizer;

    }

    public void setOnQueryChangeListener(IChipsListener listener) {
        mListener = listener;
    }


    public boolean lastCharacterIsCommitCharacter(CharSequence s) {
        char last;
        int end = getSelectionEnd() == 0 ? 0 : getSelectionEnd() - 1;
        int len = length() - 1;
        if (end != len) {
            last = s.charAt(end);
        } else {
            last = s.charAt(len);
        }
        return last == COMMIT_CHAR_COMMA;
    }


    private boolean shouldCreateChip(int start, int end) {
        return hasFocus() && !alreadyHasChip(start, end) && end - start >= MIN_NUM_OF_CHARS;
    }

    private void setChipDimensions(Context context) {
        Resources r = context.getResources();
        mChipBackground = r.getDrawable(R.drawable.chip_background);
        mChipBackgroundPressed = r.getDrawable(R.drawable.chip_background_selected);
        mChipHeight = r.getDimension(R.dimen.chip_height);
        mChipFontSize = r.getDimension(R.dimen.chip_text_size);
        mLineSpacingExtra = r.getDimension(R.dimen.line_spacing_extra);
        mChipPadding = (int) r.getDimension(R.dimen.chip_padding);
    }

    private boolean commitChip(int start, int end, Editable editable) {
        clearComposingText();
        QwertyKeyListener.markAsReplaced(editable, start, end, "");

        Contact firstFromList = getFirstContact();
        if (firstFromList == null && !isRestrictedToExistContacts) {
            createReplacementChip(start, end, editable);
            checkForChipLimit();
            return true;
        } else if (firstFromList != null) {
            RecipientEntry entry = new RecipientEntry(firstFromList);
            if (!editable.toString().contains(entry.getName() + ",")) {
                CharSequence chip = createChip(entry);
                if (chip != null && start >= 0 && end >= 0) {
                    editable.replace(start, end, chip);
                    //append leading spase to separate chips
                    editable.append(" ");
                    onChipAdded(entry);
                    onQueryChanged("");
                    checkForChipLimit();
                    return true;
                }
            } else {
                editable.replace(start, end, "");
                onQueryChanged("");
            }


        }

        return false;
    }

    private void checkForChipLimit() {
        IRecipientChip[] recips = getSpannable()
                .getSpans(0, getText().length(), IRecipientChip.class);
        if (recips.length >= mMaxChips) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(getText().length());
            setFilters(filterArray);
        }
    }

    private void removeChipLimit() {
        setFilters(new InputFilter[0]);
    }


    void createReplacementChip(int tokenStart, int tokenEnd, Editable editable) {
        String token = editable.toString().substring(tokenStart, tokenEnd);
        final String trimmedToken = token.trim();
        int commitCharIndex = trimmedToken.lastIndexOf(COMMIT_CHAR_COMMA);

        if (commitCharIndex != -1 && commitCharIndex == trimmedToken.length() - 1) {
            token = trimmedToken.substring(0, trimmedToken.length() - 1);
        }

        RecipientEntry entry = new RecipientEntry();
        entry.setName(token);
        entry.getContacts().add(new SelectableItem(token, true));

        CharSequence chipText = createChip(entry);
        editable.replace(tokenStart, tokenEnd, chipText);
        editable.append(" ");
        onChipAdded(entry);
    }

    private boolean alreadyHasChip(int start, int end) {
        IRecipientChip[] chips =
                getSpannable().getSpans(start, end, IRecipientChip.class);
        if ((chips == null || chips.length == 0)) {
            return false;
        }
        return true;
    }

    Spannable getSpannable() {
        return getText();
    }


    private IRecipientChip constructChipSpan(RecipientEntry contact, boolean pressed) {
        TextPaint paint = getPaint();
        float defaultSize = paint.getTextSize();
        int defaultColor = paint.getColor();

        Bitmap tmpBitmap;
        if (pressed) {
            tmpBitmap = createSelectedChip(contact, paint);
        } else {
            tmpBitmap = createUnselectedChip(contact, paint);
        }

        // Pass the full text, un-ellipsized, to the chip.
        Drawable result = new BitmapDrawable(getResources(), tmpBitmap);
        result.setBounds(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight());
        RecipientChip recipientChip =
                new RecipientChip(result, contact);

        recipientChip.setExtraMargin(mLineSpacingExtra);

        // Return text to the original size.
        paint.setTextSize(defaultSize);
        paint.setColor(defaultColor);
        return recipientChip;

    }

    private Bitmap createSelectedChip(RecipientEntry contact, TextPaint paint) {
        paint.setColor(getContext().getResources().getColor(R.color.guid_c9));
        return createChipBitmap(contact, paint, mChipBackgroundPressed, Color.WHITE);
    }


    private Bitmap createUnselectedChip(RecipientEntry contact, TextPaint paint) {
        paint.setColor(getContext().getResources().getColor(R.color.clear_blue));
        return createChipBitmap(contact, paint, mChipBackground,
                getResources().getColor(R.color.clear_blue));
    }


    private Bitmap createChipBitmap(RecipientEntry contact, TextPaint paint, Drawable background,
                                    @ColorInt int textColor) {
        Rect backgroundPadding = new Rect();
        background.getPadding(backgroundPadding);

        int height = (int) mChipHeight;

        float[] widths = new float[1];
        paint.getTextWidths(" ", widths);

        CharSequence ellipsizedText = ellipsizeText(contact.getName(), paint,
                calculateAvailableWidth() - widths[0] - backgroundPadding.left
                        - backgroundPadding.right);
        int textWidth = (int) paint.measureText(ellipsizedText, 0, ellipsizedText.length());

        // Make sure there is a minimum chip width so the user can ALWAYS
        // tap a chip without difficulty.
        int width = Math.max(height * 2,
                textWidth + (mChipPadding * 2) + backgroundPadding.left + backgroundPadding.right);

        // Create the background of the chip.
        Bitmap tmpBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tmpBitmap);

        int offset = 0;//(int) (mChipHeight / 6);

        // Draw the background drawable
        background.setBounds(offset, 0, width - offset, height);
        background.draw(canvas);

        // Draw the text vertically aligned
        int textX = mChipPadding + backgroundPadding.left;
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        canvas.drawText(ellipsizedText, 0, ellipsizedText.length(),
                textX, getTextYOffset(ellipsizedText.toString(), paint, height), paint);
        return tmpBitmap;
    }

    /**
     * Given a height, returns a Y offset that will draw the text in the middle of the height.
     */
    protected float getTextYOffset(String text, TextPaint paint, int height) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int textHeight = bounds.bottom - bounds.top;
        return height - ((height - textHeight) / 2) - (int) paint.descent() / 2;
    }

    /**
     * Get the max amount of space a chip can take up. The formula takes into
     * account the width of the EditTextView, any view padding, and padding
     * that will be added to the chip.
     */
    private float calculateAvailableWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight() - (mChipPadding * 2);
    }


    private CharSequence ellipsizeText(CharSequence text, TextPaint paint, float maxWidth) {
        paint.setTextSize(mChipFontSize);
        return TextUtils.ellipsize(text, paint, maxWidth, TextUtils.TruncateAt.END);
    }

    public void submitEntry(RecipientEntry entry) {
        clearComposingText();
        int end = getSelectionEnd();
        int start = mTokenizer.findTokenStart(getText(), end);
        Editable editable = getText();
        QwertyKeyListener.markAsReplaced(editable, start, end, "");
        if (!editable.toString().contains(entry.getName() + ",")) {
            removeTextChangedListener(mTextWatcher);
            CharSequence chip = createChip(entry);
            if (chip != null && start >= 0 && end >= 0) {
                editable.replace(start, end, chip);
            } else if (chip != null && start == end) {
                editable.append(chip);
            }
            //append leading space to separate chips
            editable.append(" ");
            onQueryChanged("");
            checkForChipLimit();
            post(mAddTextWatcher);
        }
    }

    public void makeChipFromTail() {
        if (mSelectedChip != null) {
            unselectChip(mSelectedChip);
        }
        Editable text = getText();
        setSelection(text.length());
        int tokenEnd = mTokenizer.findTokenEnd(text, getSelectionEnd());
        int tokenStart = mTokenizer.findTokenStart(text, tokenEnd - 1);
        if (shouldCreateChip(tokenStart, tokenEnd)) {
            if (commitChip(tokenStart, tokenEnd, getText())) {
                setSelection(getText().length());
                onQueryChanged("");
            }
        }
    }

    private void onQueryChanged(CharSequence sequence) {
        if (mListener != null) {
            mListener.onQueryChanged(sequence);
        }
    }

    private void onChipDeleted(Contact delChip) {
        if (mListener != null) {
            mListener.onChipDeleted(delChip);
        }
    }

    private void onChipAdded(RecipientEntry entry) {
        if (mListener != null) {
            mListener.onChipAdded(entry);
        }
    }


    private CharSequence createChip(RecipientEntry entry) {
        final SpannableString chipText = new SpannableString(entry.getName() + ",");
        IRecipientChip chip = constructChipSpan(entry, false);
        chipText.setSpan(chip, 0, chipText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        chip.setOriginalText(chipText.toString());
        return chipText;

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        // When selection changes, see if it is inside the chips area.
        // If so, move the cursor back after the chips again.
        if (isCursorVisible && !isScrollingNow) {
            IRecipientChip last = getLastChip();
            if (last != null && selStart < getSpannable().getSpanEnd(last) + 1) {
                // Grab the last chip and set the cursor to after it.
                setSelection(Math.min(getSpannable().getSpanEnd(last) + 1, getText().length()));
                return;
            }
        }
        super.onSelectionChanged(selStart, selEnd);
    }


    IRecipientChip getLastChip() {
        IRecipientChip last = null;
        IRecipientChip[] chips = getSortedRecipients();
        if (chips != null && chips.length > 0) {
            last = chips[chips.length - 1];
        }
        return last;
    }


    IRecipientChip[] getSortedRecipients() {
        IRecipientChip[] recips = getSpannable()
                .getSpans(0, getText().length(), IRecipientChip.class);
        ArrayList<IRecipientChip> recipientsList = new ArrayList<>(Arrays.asList(recips));
        final Spannable spannable = getSpannable();
        Collections.sort(recipientsList, new Comparator<IRecipientChip>() {
            @Override
            public int compare(IRecipientChip first, IRecipientChip second) {
                int firstStart = spannable.getSpanStart(first);
                int secondStart = spannable.getSpanStart(second);
                if (firstStart < secondStart) {
                    return -1;
                } else if (firstStart > secondStart) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return recipientsList.toArray(new IRecipientChip[recipientsList.size()]);
    }

    public Contact getFirstContact() {
        if (mListener != null) {
            return mListener.getFirstFilteredContact();
        }
        return null;
    }

    public void removeEntry(RecipientEntry recipientEntry) {
        IRecipientChip[] sortedRecipients = getSortedRecipients();
        for (IRecipientChip chip : sortedRecipients) {
            if (chip.getEntry().getId() == recipientEntry.getId()) {
                removeChip(chip);
                break;
            }
        }
    }


    void removeChip(IRecipientChip chip) {
        Spannable spannable = getSpannable();
        int spanStart = spannable.getSpanStart(chip);
        int spanEnd = spannable.getSpanEnd(chip);
        Editable text = getText();

        boolean wasSelected = chip == mSelectedChip;
        // Clear that there is a selected chip before updating any text.
        if (wasSelected) {
            mSelectedChip = null;
        }
        // Always remove trailing spaces when removing a chip.
        int toDelete = spanEnd;
        while (toDelete >= 0 && toDelete < text.length() && text.charAt(toDelete) == ' ') {
            toDelete++;
        }

        spannable.removeSpan(chip);
        if (spanStart >= 0 && toDelete > 0) {
            text.delete(spanStart, toDelete);
        }
//        if (wasSelected) {
//            clearSelectedChip();
//        }
        setSelection(getText().length());
        setCursorVisible(true);
        removeChipLimit();
    }


    private int putOffsetInRange(int o) {
        int offset = o;
        Editable text = getText();
        int length = text.length();
        int beyondChips = length;

        IRecipientChip lastChip = getLastChip();
        if (lastChip != null) {
            beyondChips = getChipEnd(lastChip);
        }
        // If the offset is beyond or at the end of the text,
        // leave it alone.
        if (offset >= length || offset > beyondChips) {
            return offset;
        }
        while (offset >= 0 && findChip(offset) == null) {
            // Keep walking backward!
            offset--;
        }
        return offset;
    }

    private int getChipStart(IRecipientChip chip) {
        return getSpannable().getSpanStart(chip);
    }

    private int getChipEnd(IRecipientChip chip) {
        return getSpannable().getSpanEnd(chip);
    }

    private void unselectChip(IRecipientChip chip) {
        int start = getChipStart(chip);
        int end = getChipEnd(chip);
        Editable editable = getText();
        mSelectedChip = null;

        if (start > -1 && end > -1) {
            getSpannable().removeSpan(chip);
            QwertyKeyListener.markAsReplaced(editable, start, end, "");
            editable.removeSpan(chip);
            editable.setSpan(constructChipSpan(chip.getEntry(), false),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //setSelection(editable.length());
        enablePasteFunctionality(true);
    }

    private void enablePasteFunctionality(boolean enable) {
        ActionMode.Callback callback = null;
        setLongClickable(enable);

        if (!enable) {
            callback = new ActionMode.Callback() {

                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    // TODO Auto-generated method stub
                    return false;
                }

                public void onDestroyActionMode(ActionMode mode) {
                    // TODO Auto-generated method stub

                }

                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    // TODO Auto-generated method stub
                    return false;
                }

                public boolean onActionItemClicked(ActionMode mode,
                                                   MenuItem item) {
                    // TODO Auto-generated method stub
                    return false;
                }
            };
        }

        setCustomSelectionActionModeCallback(callback);
    }


    private IRecipientChip selectChip(IRecipientChip currentChip) {
        int start = getChipStart(currentChip);
        int end = getChipEnd(currentChip);
        getSpannable().removeSpan(currentChip);
        IRecipientChip newChip = constructChipSpan(currentChip.getEntry(), true);
        Editable editable = getText();
        //QwertyKeyListener.markAsReplaced(editable, start, end, "");
        editable.setSpan(newChip, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        newChip.setSelected(true);
        setCursorVisible(false);
        enablePasteFunctionality(false);
        return newChip;
    }

    private void clearSelectedChip() {
        if (mSelectedChip != null) {
            unselectChip(mSelectedChip);
            mSelectedChip = null;
        }
        setCursorVisible(true);
        isScrollingNow = false;
        isDoubleTapped = false;
    }

    private IRecipientChip findChip(int offset) {
        IRecipientChip[] chips = getSpannable()
                .getSpans(0, getText().length(), IRecipientChip.class);
        // Find the chip that contains this offset.
        IRecipientChip selected = null;
        for (int i = 0; i < chips.length; i++) {
            IRecipientChip chip = chips[i];
            int start = getChipStart(chip);
            int end = getChipEnd(chip);
            if (offset >= start && offset <= end) {
                selected = chip;
            }
        }
        if (selected != null) {
            return selected;
        }

        return null;
    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        //to be able to set ime action next with multi line we need to remove IME_FLAG_NO_ENTER_ACTION
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = false;
        if (!isFocused()) {
            // Ignore any chip taps until this view is focused.
            return super.onTouchEvent(event);
        }

        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }

        int action = event.getActionMasked();
        boolean chipWasSelected = false;

        if (action == MotionEvent.ACTION_UP) {
            if (isScrollingNow) {
                isScrollingNow = false;
                return true;
            } else if (isDoubleTapped) {
                isDoubleTapped = false;
                return true;
            }
            float x = event.getX();
            float y = event.getY();
            int offset = putOffsetInRange(getOffsetForPosition(x, y));
            IRecipientChip currentChip = findChip(offset);
            if (currentChip != null) {
                if (mSelectedChip != null && mSelectedChip != currentChip) {
                    clearSelectedChip();
                    mSelectedChip = selectChip(currentChip);
                } else {
                    mSelectedChip = selectChip(currentChip);
                }
//                QwertyKeyListener
//                        .markAsReplaced(getText(), getSelectionStart(), getSelectionEnd(), "");
                chipWasSelected = true;
                handled = true;
            }
        }
        boolean superHandled = super.onTouchEvent(event);
        if (action == MotionEvent.ACTION_UP && !chipWasSelected) {
            clearSelectedChip();
        }

        if (mSelectedChip != null) {
            //force cursor to be at the end of selected chip right after selection
            setSelection(getChipEnd(mSelectedChip));
        }

        return handled || superHandled;
    }

    /**
     * Dismiss any selected chips when the back key is pressed.
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mSelectedChip != null) {
            clearSelectedChip();
            setSelection(getText().length());
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DEL && mSelectedChip != null) {
            //handle delete manually to prevent double delete via this keycode and text change callback
            if (mTextWatcher != null) {
                mTextWatcher.beforeTextChanged(getText(), getChipEnd(mSelectedChip), 1, 0);
            }
            return true;
        }

        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean isSuggestionsEnabled() {
        //prevent suggestions when double click on chip
        return false;
    }

    //    @Override
//    public boolean dispatchKeyEvent(KeyEvent Event) {
//        if(Event.getKeyCode() == KeyEvent.KEYCODE_DEL && mSelectedChip != null){
//            removeChip(mSelectedChip);
//            return true;
//        }
//        return super.dispatchKeyEvent(Event);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent Event) {
//        if(keyCode == KeyEvent.KEYCODE_DEL && mSelectedChip != null){
//            removeChip(mSelectedChip);
//            return true;
//        }
//        return super.onKeyDown(keyCode, Event);
//    }

    @Override
    public void onEditorAction(int actionCode) {
        if (actionCode == EditorInfo.IME_ACTION_DONE) {
            if (mSelectedChip != null) {
                clearSelectedChip();
                setSelection(getText().length());
            } else {
                Editable text = getText();
                int tokenEnd = mTokenizer.findTokenEnd(text, getSelectionEnd());
                int tokenStart = mTokenizer.findTokenStart(text, tokenEnd - 1);
                if (shouldCreateChip(tokenStart, tokenEnd)) {
                    if (commitChip(tokenStart, tokenEnd, getText())) {
                        setSelection(getText().length());
                        onQueryChanged("");
                    }
                }
            }
        }
    }

    @Override
    public void setCursorVisible(boolean cursorVisible) {
        super.setCursorVisible(cursorVisible);
        isCursorVisible = cursorVisible;
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        mTextWatcher = null;
        super.removeTextChangedListener(watcher);
    }

    public int getMaxChips() {
        return mMaxChips;
    }

    public void setMaxChips(int maxChips) {
        mMaxChips = maxChips;
    }

    public boolean isRestrictedToExistContacts() {
        return isRestrictedToExistContacts;
    }

    public void setRestrictedToExistContacts(boolean restrictedToExistContacts) {
        this.isRestrictedToExistContacts = restrictedToExistContacts;
    }

    public void restoreFromSavedInstance(Bundle savedData) {
        if (savedData != null) {
            final String lastFilter = savedData.getString("mFilterQuery");
            final ArrayList<Contact> contacts = (ArrayList<Contact>) savedData
                    .getSerializable("mSelectableContacts");
            post(new Runnable() {
                @Override
                public void run() {
                    CharSequence chip;
                    Editable editable = getText();
                    removeTextChangedListener(mTextWatcher);
                    for (Contact contact : contacts) {
                        chip = createChip(new RecipientEntry(contact));
                        editable.append(chip);
                        editable.append(" ");
                    }

                    if (!TextUtils.isEmpty(lastFilter)) {
                        editable.append(lastFilter);
                    }
                    post(mAddTextWatcher);
                }
            });

        }
    }

    private class RecipientTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, final int start, int count, int after) {
            //hack to catch del button when there is selected chip
            if (after < count && mSelectedChip != null) {
                //stop watching after text changes
                removeTextChangedListener(mTextWatcher);
                //remove selected chip after all changes happend
                post(new Runnable() {
                    @Override
                    public void run() {
                        Contact contactToDelete = mSelectedChip.getEntry();
                        //return "deleted" space (must be after actual delete because of limit in length)
                        //but do not enter space if start position is 0
                        if (start > MIN_NUM_OF_CHARS) {
                            getText().insert(start, " ");
                        }
                        removeChip(mSelectedChip);
                        post(mAddTextWatcher);
                        onChipDeleted(contactToDelete);
                        removeChipLimit();
                    }
                });
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // If the text has been set to null or empty, make sure we remove
            // all the spans we applied.
            if (TextUtils.isEmpty(s)) {
                // Remove all the chips spans.
                Spannable spannable = getSpannable();
                IRecipientChip[] chips = spannable.getSpans(0, getText().length(),
                        IRecipientChip.class);
                for (IRecipientChip chip : chips) {
                    spannable.removeSpan(chip);
                }
                onQueryChanged("");
                return;
            }

            if (mSelectedChip != null) {
                setCursorVisible(true);
                setSelection(getText().length());
                clearSelectedChip();
            }
            // Make sure there is content there to parse and that it is
            // not just the commit character.
            if (s.length() > 1) {
                int tokenEnd = mTokenizer.findTokenEnd(s, getSelectionEnd());
                int tokenStart = mTokenizer.findTokenStart(s, tokenEnd - 1);
                boolean tokenCommitted = false;
                //end token end not equal to selection end then we are before existing token and need to make special case
                if (lastCharacterIsCommitCharacter(s)) {
                    if (shouldCreateChip(tokenStart, tokenEnd)) {
                        tokenCommitted = commitChip(tokenStart, tokenEnd, getText());
                    }
                    setSelection(getText().length());
                }
                if (!tokenCommitted) {
                    //there is space at the end from token so we need move start by one char
                    onQueryChanged(
                            s.subSequence(s.charAt(tokenStart) == ' ' ? tokenStart + 1 : tokenStart,
                                    tokenEnd));
                }
            } else {
                onQueryChanged(s);
            }

        }


        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            if (!TextUtils.isEmpty(charSequence)) {
                if (before > count) {
                    int selStart = getSelectionStart();
                    IRecipientChip[] repl = getSpannable().getSpans(selStart, selStart,
                            IRecipientChip.class);

                    if (repl.length > 0) {
                        // There is a chip there!
                        IRecipientChip toDelete = repl[0];
                        Editable editable = getText();

                        int deleteStart = editable.getSpanStart(toDelete);
                        int deleteEnd = editable.getSpanEnd(toDelete);
                        deleteEnd = deleteEnd + 1;
                        if (deleteEnd > editable.length()) {
                            deleteEnd = editable.length();
                        }
                        getSpannable().removeSpan(toDelete);
                        editable.delete(deleteStart, deleteEnd);
                        onChipDeleted(toDelete.getEntry());
                        removeChipLimit();
                    }

                } else if (!isCursorVisible) {

                    IRecipientChip lastChip = getLastChip();
                    if (lastChip != null && getChipEnd(lastChip) > getSelectionStart()) {
                        removeTextChangedListener(mTextWatcher);
                        final CharSequence newSequence = charSequence
                                .subSequence(start, start + count);

                        getText().delete(start, start + count);
                        setSelection(getText().length());
                        post(new Runnable() {
                            @Override
                            public void run() {
                                mTextWatcher = new RecipientTextWatcher();
                                addTextChangedListener(mTextWatcher);
                                getText().append(newSequence);
                            }
                        });
                    }
                }
            }

        }
    }

//    @Override
//    public void onSizeChanged(int width, int height, int oldw, int oldh) {
//        // Try to find the scroll view parent, if it exists.
//        if (mScrollView == null && !mTriedGettingScrollView) {
//            ViewParent parent = getParent();
//            while (parent != null && !(parent instanceof ScrollView)) {
//                parent = parent.getParent();
//            }
//            if (parent != null) {
//                mScrollView = (ScrollView) parent;
//            }
//            mTriedGettingScrollView = true;
//        }
//    }


    public interface IChipsListener {

        void onQueryChanged(CharSequence query);

        void onChipAdded(Contact newContact);

        void onChipDeleted(Contact delContact);

        Contact getFirstFilteredContact();
    }

}
