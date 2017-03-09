package com.kids2pull.android.models;

import java.io.Serializable;

public class SelectableItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1581913895006294908L;
    private boolean isSelected;
    private String value;
    private int parentPosition;

    public SelectableItem() {

    }

    public SelectableItem(String value, boolean selected) {
        this.value = value;
        this.isSelected = selected;
    }

    public SelectableItem(String value) {
        this(value, false);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public void toggle() {
        isSelected = !isSelected;
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }
}
