package com.kids2pull.android.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.kids2pull.android.R;
import com.kids2pull.android.controlers.ContactItemClosed;
import com.kids2pull.android.controlers.picker.ContactItemOpened;
import com.kids2pull.android.helpers.CircleTransform;
import com.kids2pull.android.models.Contact;
import com.kids2pull.android.models.SelectableItem;
import com.kids2pull.android.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pasha on 8/12/15.
 */
public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_TYPE_CLOSED = 0;
    private static final int ITEM_TYPE_OPENED = 1;


    private static int POSITION_COLUMN_ID = 100;
    private static int POSITION_DISPLAY_NAME = 101;
    private static int POSITION_PHOTO_ID = 102;


    private ContactListHeaderAdapter mHeaderAdapter;
    private Picasso mPicasso;
    private CircleTransform mCircleTransform;
    private SparseArray<Contact> mSelectableContacts;
    private Cursor mData;
    private ContentResolver mContentResolver;
    private View.OnClickListener mClosedContactClickListener;
    private View.OnClickListener mOpenedContactClickListener;
    private View.OnClickListener mContactItemClickListener;
    private String mFilterQuery;
    private ItemSelectionChangeListener mSelectionChangeCallback;
    private int mContactLimit;

    private boolean isPhoneOnlyMode;
    private boolean isSingleChoiceMode;


    public ContactListAdapter(Context context, ItemSelectionChangeListener callback) {
        mHeaderAdapter = new ContactListHeaderAdapter(context);
        mSelectableContacts = new SparseArray<>();
        mPicasso = Picasso.with(context);
        mCircleTransform = new CircleTransform();
        mContentResolver = context.getContentResolver();
        mSelectionChangeCallback = callback;
        mContactLimit = Integer.MAX_VALUE;
        mClosedContactClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ClosedItemViewHolder holder = (ClosedItemViewHolder) view.getTag();
                int contactId = holder.getContactId();
                Contact gtContact = mSelectableContacts.get(contactId);
                if (gtContact == null) {
                    gtContact = fetchContactDataById(contactId);
                    mSelectableContacts.put(contactId, gtContact);
                }
                if (gtContact != null) {
                    gtContact.toggleOpened();
                    boolean notifySelectionChanged = false;
                    //toggle first item if there was no selected items at all
                    SelectableItem firstSelectable = null;
                    for (SelectableItem contactItem : gtContact.getContacts()) {
                        if (contactItem.isSelected()) {
                            firstSelectable = contactItem;
                            break;
                        }
                    }

                    if (firstSelectable == null && mContactLimit > countSelectedItems()) {
                        firstSelectable = gtContact.getContacts().get(0);
                        firstSelectable.toggle();
                        notifySelectionChanged = true;
                    }

                    notifyItemChanged(holder.getPosition());
                    if(notifySelectionChanged) {
                        mSelectionChangeCallback.onItemSelectionChanged(gtContact, firstSelectable);
                    }
                }
            }
        };

        mOpenedContactClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenedItemViewHolder holder = (OpenedItemViewHolder) view.getTag();
                int contactId = holder.getContactId();
                Contact gtContact = mSelectableContacts.get(contactId);
                if (gtContact != null) {
                    gtContact.toggleOpened();
                    notifyItemChanged(holder.getPosition());
                }
            }
        };

        mContactItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectableItem item = (SelectableItem) view.getTag();
                Contact gtContact = mSelectableContacts.get((int)getItemId(item.getParentPosition()));
                if(gtContact.hasSelectedContacts() || mContactLimit > countSelectedItems()) {
                    item.toggle();

                    if (isSingleChoiceMode) {

                        for (SelectableItem selectableItem : gtContact.getContacts()) {
                            if (item.isSelected() && selectableItem.getValue()
                                    .equalsIgnoreCase(item.getValue())) {
                                continue;
                            }

                            selectableItem.setSelected(false);
                        }
                    }

                    notifyItemChanged(item.getParentPosition());
                    mSelectionChangeCallback.onItemSelectionChanged(gtContact, item);
                } else {
                    mSelectionChangeCallback.onMaxSelectionError();
                }
            }
        };
    }

    public int countSelectedItems() {
        int counter = 0;
        Contact gtContact;
        for (int i = 0, size = mSelectableContacts.size(); i < size; i++) {
            gtContact = mSelectableContacts.valueAt(i);
            for (SelectableItem item : gtContact.getContacts()) {
                if (item.isSelected()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CLOSED) {
            ContactItemClosed contactItemClosed = new ContactItemClosed(parent.getContext());
            contactItemClosed.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ClosedItemViewHolder(contactItemClosed);
        } else {
            ContactItemOpened contactItemOpened = new ContactItemOpened(parent.getContext());
            contactItemOpened.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            return new OpenedItemViewHolder(contactItemOpened);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mData.moveToPosition(position)) {

            if (holder.getItemViewType() == ITEM_TYPE_CLOSED) {

                ClosedItemViewHolder closedItemViewHolder = (ClosedItemViewHolder) holder;
                if (!TextUtils.isEmpty(mFilterQuery)) {
                    closedItemViewHolder.setLabel(StringUtils
                            .applyTextWeight(mData.getString(POSITION_DISPLAY_NAME), mFilterQuery,
                                    "sans-serif",
                                    Typeface.BOLD));
                } else {
                    closedItemViewHolder.setLabel(mData.getString(POSITION_DISPLAY_NAME));
                }

                closedItemViewHolder.setContactId(mData.getInt(POSITION_COLUMN_ID));

                Contact gtContact = mSelectableContacts.get(closedItemViewHolder.getContactId());

                if (mData.getString(POSITION_PHOTO_ID) != null) {
                    mPicasso.load(Contact.getContactPhotoUri(mData.getInt(POSITION_COLUMN_ID)))
                            .placeholder(R.mipmap.contact_default).transform(
                            mCircleTransform).into(closedItemViewHolder.getImageView());
                } else {
                    closedItemViewHolder.getImageView()
                            .setImageResource(R.mipmap.contact_default);
                }
                closedItemViewHolder.setOnClickListener(mClosedContactClickListener);
                closedItemViewHolder
                        .setChecked(gtContact != null && gtContact.hasSelectedContacts());
            } else {
                OpenedItemViewHolder openedItemViewHolder = (OpenedItemViewHolder) holder;

                if (!TextUtils.isEmpty(mFilterQuery)) {
                    openedItemViewHolder.setLabel(StringUtils
                            .applyTextWeight(mData.getString(POSITION_DISPLAY_NAME), mFilterQuery,
                                    "sans-serif",
                                    Typeface.BOLD));
                } else {
                    openedItemViewHolder.setLabel(mData.getString(POSITION_DISPLAY_NAME));
                }

                openedItemViewHolder.setContactId(mData.getInt(POSITION_COLUMN_ID));

                Contact gtContact = mSelectableContacts.get(openedItemViewHolder.getContactId());
                if (mData.getString(POSITION_PHOTO_ID) != null) {
                    mPicasso.load(Contact.getContactPhotoUri(mData.getInt(POSITION_COLUMN_ID)))
                            .placeholder(R.mipmap.contact_default).transform(
                            mCircleTransform).into(openedItemViewHolder.getImageView());
                } else {
                    openedItemViewHolder.getImageView()
                            .setImageResource(R.mipmap.contact_default);
                }
                openedItemViewHolder
                        .setContactItems(gtContact != null ? gtContact.getContacts() : null,
                                mContactItemClickListener);
                openedItemViewHolder.setOnClickListener(mOpenedContactClickListener);

            }

        }

    }

    private Contact fetchContactDataById(int contactId) {

        String[] projection = new String[]{
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.Data.PHOTO_ID,
                ContactsContract.Data.DATA1};

        String[] selectionArgs;
        if (isPhoneOnlyMode) {
            selectionArgs = new String[]{String.valueOf(contactId),
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
        } else {
            selectionArgs = new String[]{String.valueOf(contactId),
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
        }

        String selection = ContactsContract.Data.CONTACT_ID + " = ? AND ("
                + ContactsContract.Data.MIMETYPE + "=? OR "
                + ContactsContract.Data.MIMETYPE + "=?)";

        Cursor cursor = mContentResolver.query(ContactsContract.Data.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);

        if (cursor.moveToFirst()) {
            Contact contact = Contact.parse(cursor);
            int dataColumnPosition = cursor.getColumnIndex(
                    ContactsContract.Data.DATA1);
            while (cursor.moveToNext()) {
                contact.getContacts()
                        .add(new SelectableItem(cursor.getString(dataColumnPosition), false));
            }
            cursor.close();
            return contact;
        }

        cursor.close();
        return null;
    }

    @Override
    public int getItemCount() {
        return mData == null || mData.isClosed()? 0 : mData.getCount();
    }

    @Override
    public long getItemId(int position) {
        if (mData.moveToPosition(position)) {
            return mData.getInt(POSITION_COLUMN_ID);
        }
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        Contact gtContact = mSelectableContacts.get((int) getItemId(position));
        return gtContact != null && gtContact.isOpened() ? ITEM_TYPE_OPENED : ITEM_TYPE_CLOSED;
    }


    private void clearCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public void swapCursor(Cursor newCursor, String filterQuery) {
        if (mData != newCursor) {
            mFilterQuery = filterQuery != null ? filterQuery.toLowerCase() : null;
            Cursor temp = mData;
            mData = newCursor;
            if (mData != null && mData.moveToFirst()) {

                String DISPLAY_NAME_COLUMN = ContactsContract.Data.DISPLAY_NAME;
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                    DISPLAY_NAME_COLUMN = ContactsContract.Data.DISPLAY_NAME_PRIMARY;
                }

                POSITION_COLUMN_ID = mData.getColumnIndex(ContactsContract.Data.CONTACT_ID);
                POSITION_DISPLAY_NAME = mData.getColumnIndex(DISPLAY_NAME_COLUMN);
                POSITION_PHOTO_ID = mData.getColumnIndex(ContactsContract.Data.PHOTO_ID);
            }
            if (mHeaderAdapter != null) {
                mHeaderAdapter.setData(mData, POSITION_DISPLAY_NAME);
            }
            clearCursor(temp);
            notifyDataSetChanged();
        }
    }

    public List<Contact> getSelectedContacts() {
        List<Contact> result = new ArrayList<>(mSelectableContacts.size());
        Contact contact;
        for (int i = 0; i < mSelectableContacts.size(); i++) {
            contact = mSelectableContacts.valueAt(i);
            if(contact.hasSelectedContacts()){
                result.add(contact);
            }
        }
        return result;
    }




    public StickyHeadersAdapter getHeaderAdapter() {
        return mHeaderAdapter;
    }

    public void clear() {
        if (mData != null) {
            mData.close();
        }
        mContentResolver = null;
        mHeaderAdapter = null;
        mSelectionChangeCallback = null;
    }

    public Bundle buildSavedInstance() {
        Bundle data = new Bundle();
        data.putSerializable("mSelectableContacts", (ArrayList)getSelectedContacts());
        data.putString("mFilterQuery", mFilterQuery);
        return data;
    }

    public void restoreFromSaveInstance(Bundle savedData){
        if(savedData != null){
            mFilterQuery = savedData.getString("mFilterQuery");
            ArrayList<Contact> contacts = (ArrayList<Contact>) savedData.getSerializable("mSelectableContacts");
            if(contacts != null) {
                for (Contact contact : contacts) {
                    mSelectableContacts.put((int) contact.getId(), contact);
                }
            }
        }
    }

    /**
     * if enable then only contacts with phone number will be displayed
     * @param phoneOnlyMode true/false
     */
    public void setPhoneOnlyMode(boolean phoneOnlyMode) {
        this.isPhoneOnlyMode = phoneOnlyMode;
    }

    /**
     * if enable then only one number/email in contact can selected
     * @param isSingleChoiceMode true/false
     */
    public void setSingleChoiceMode(boolean isSingleChoiceMode) {
        this.isSingleChoiceMode = isSingleChoiceMode;
    }

    public void removeSelectedContact(Contact delContact) {
        int parentPosition = -1;
        for(SelectableItem item : delContact.getContacts()){
            if(item.isSelected()) {
                item.setSelected(false);
                parentPosition = item.getParentPosition();
            }
        }
        delContact.setOpened(false);
        mSelectableContacts.put((int) delContact.getId(), delContact);
        if(parentPosition >= 0) {
            notifyItemChanged(parentPosition);
        }
    }

    public Contact getFirstContact() {
        if(mData != null && mData.getCount() > 0){
            if(mData.moveToFirst()){
                int contactId = mData.getInt(mData.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                Contact contact = mSelectableContacts.get(contactId);
                if(contact == null){
                    contact = fetchContactDataById(contactId);
                }
                return contact;
            }
        }

        return null;
    }

    public void addSelectedContact(Contact newContact) {
        mSelectableContacts.put((int) newContact.getId(), newContact);
        int parentPosition = newContact.getContacts().get(0).getParentPosition();
        if(parentPosition >= 0){
            notifyItemChanged(parentPosition);
        }
    }

    public int getContactLimit() {
        return mContactLimit;
    }

    public void setContactLimit(int contactLimit) {
        mContactLimit = contactLimit;
    }

    public static class ClosedItemViewHolder extends RecyclerView.ViewHolder {

        private ContactItemClosed mContactItem;
        private int contactId;

        public ClosedItemViewHolder(View itemView) {
            super(itemView);
            mContactItem = (ContactItemClosed) itemView;
        }

        public void setLabel(String label) {
            mContactItem.setLabel(label);
        }

        public void setLabel(Spannable label) {
            mContactItem.setLabel(label);
        }

        public void setChecked(boolean isChecked) {
            mContactItem.setChecked(isChecked);
        }

        public ImageView getImageView() {
            return mContactItem.getImageView();
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
            itemView.setTag(this);
        }

        public int getContactId() {
            return contactId;
        }

        public void setContactId(int contactId) {
            this.contactId = contactId;
        }
    }

    public static class OpenedItemViewHolder extends RecyclerView.ViewHolder {

        private ContactItemOpened mContactItem;
        private int contactId;

        public OpenedItemViewHolder(View itemView) {
            super(itemView);
            mContactItem = (ContactItemOpened) itemView;
        }

        public void setLabel(String label) {
            mContactItem.setLabel(label);
        }

        public void setLabel(Spannable label) {
            mContactItem.setLabel(label);
        }

        public int getContactId() {
            return contactId;
        }

        public void setContactId(int contactId) {
            this.contactId = contactId;
        }

        public ImageView getImageView() {
            return mContactItem.getImageView();
        }

        public void setContactItems(List<SelectableItem> items, View.OnClickListener listener) {
            mContactItem.setContactItems(items, listener, getPosition());
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContactItem.getInfoGroup().setOnClickListener(listener);
            mContactItem.getInfoGroup().setTag(this);
        }
    }

    public interface ItemSelectionChangeListener {

        void onItemSelectionChanged(Contact contact, SelectableItem selectedItem);
        void onMaxSelectionError();
    }

}
