package com.kids2pull.android.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.kids2pull.android.R;


/**
 * Created by pasha on 8/12/15.
 */
public class ContactListHeaderAdapter implements
        StickyHeadersAdapter<ContactListHeaderAdapter.HeaderViewHolder> {

    private static int COLUMN_NAME_POSITION = 1;
    private LayoutInflater mInflater;
    private Cursor mData;

    public ContactListHeaderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HeaderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(mInflater.inflate(R.layout.contact_header_item, parent, false));
    }

    @Override
    public void onBindViewHolder(HeaderViewHolder headerViewHolder, int position) {
        headerViewHolder.headerText.setText(getFirstLatterOfNameAt(position));
    }

    @Override
    public long getHeaderId(int position) {
        return getFirstLatterOfNameAt(position).charAt(0);
    }


    private String getFirstLatterOfNameAt(int position) {
        if (mData.moveToPosition(position)) {
            return mData.getString(COLUMN_NAME_POSITION).substring(0, 1).toUpperCase();
        }
        return " ";
    }

    public void setData(Cursor newCursor, int positionColumnDisplayName) {
        mData = newCursor;
        COLUMN_NAME_POSITION = positionColumnDisplayName;
    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView headerText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.label);
        }
    }

}
