//package com.kids2pull.android.fragments;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.ContactsContract;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.SparseIntArray;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.MultiAutoCompleteTextView;
//
//import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
//import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
//import com.kids2pull.android.R;
//import com.kids2pull.android.controlers.chips.ChipsEditText;
//import com.kids2pull.android.models.Contact;
//import com.kids2pull.android.models.RecipientEntry;
//import com.kids2pull.android.models.SelectableItem;
//import com.kids2pull.android.utils.DisplayUtils;
//import com.kids2pull.android.utils.InteropConstants;
//
//import java.util.List;
//
//import static com.google.android.gms.R.id.contact;
//
///**
// * Created by pasha on 8/12/15.
// */
//
//public class ContactListFragment extends BaseFragment
//        implements ContactListAdapter.ItemSelectionChangeListener,
//        ChipsEditText.IChipsListener,
//        LoaderManager.LoaderCallbacks<Cursor> {
//
//    public static final String FRAGMENT_TAG = "GT/ContactListFragment";
//
//    public static final String SOURCE_INVITE_FRIENDS = "invite_friends";
//    private static final int PERMISSION_REQUEST_CONTACTS = 9;
//
//    private ContactListAdapter mItemAdapter;
//    private ChipsEditText mSearchEditText;
//    private String mFilterQuery;
//    private int mSelectedContactCount;
//    private IInviteContacts callback;
//    private boolean isPhoneOnlyMode;
//    private boolean isSingleChoiceMode;
//    private String mSource;
//    private int mOrderId;
//    private int mMaxParticipants;
//    private int mMaxContacts;
//    private View mPermissionEmptyView;
//    private boolean isRestrictedToExistingContacts;
//    private View mProgressBar;
//    private boolean isClickedAppSettings;
//    private SparseIntArray mContactPositionIndex;
//    private Contact mPendingContactForScroll;
//    private int listHeaderHeight;
//    private boolean isShowActionDone;
//
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof IInviteContacts) {
//            callback = (IInviteContacts) activity;
//        } else {
//            throw new IllegalStateException("parent activity must implement IInviteContacts");
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        mContactPositionIndex = new SparseIntArray();
//        listHeaderHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//        if (getArguments() != null) {
//            isPhoneOnlyMode = getArguments()
//                    .getBoolean(InteropConstants.PARAM_PHONE_ONLY_MODE, false);
//            isSingleChoiceMode = getArguments()
//                    .getBoolean(InteropConstants.PARAM_SINGLE_CHOICE_MODE, false);
//            mSource = getArguments().getString(InteropConstants.PARAM_ORIGIN_SOURCE);
//            mOrderId = getArguments().getInt(InteropConstants.PARAM_ORDER_ID);
//            mMaxParticipants = getArguments().getInt(InteropConstants.PARAM_MAX_PARTICIPANTS);
//            mMaxContacts = getArguments()
//                    .getInt(InteropConstants.PARAM_MAX_CONTACTS, Integer.MAX_VALUE);
//            isRestrictedToExistingContacts = getArguments()
//                    .getBoolean(InteropConstants.PARAM_RESTRICT_EXISTING_CONTACT, false);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.contact_list_fragment, container, false);
//    }
//
//    @Override
//    public void onDestroyView() {
//        mSearchEditText = null;
//        mItemAdapter.clear();
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
//                == PackageManager.PERMISSION_GRANTED
//                && mPermissionEmptyView.getVisibility() == View.VISIBLE) {
//
//            mPermissionEmptyView.setVisibility(View.GONE);
//            getLoaderManager().restartLoader(0, null, ContactListFragment.this);
//        }
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        RecyclerView list = (RecyclerView) getView().findViewById(R.id.list);
//        list.setItemAnimator(null);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.VERTICAL, false);
//        list.setLayoutManager(layoutManager);
//
//        mItemAdapter = new ContactListAdapter(getActivity(), this);
//        mItemAdapter.setPhoneOnlyMode(isPhoneOnlyMode);
//        mItemAdapter.setSingleChoiceMode(isSingleChoiceMode);
//        mItemAdapter.setHasStableIds(true);
//        mItemAdapter.setContactLimit(mMaxContacts);
//        list.setAdapter(mItemAdapter);
//
//        StickyHeadersItemDecoration headerItemDecoration
//                = new StickyHeadersBuilder()
//                .setAdapter(mItemAdapter)
//                .setRecyclerView(list)
//                .setStickyHeadersAdapter(mItemAdapter.getHeaderAdapter())
//                .setSticky(true)
//                .build();
//
//        list.addItemDecoration(headerItemDecoration);
//        list.addItemDecoration(
//                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//
//        //put color over search icon
//        ((ImageView) getView().findViewById(R.id.img_search)).setColorFilter(Color.GRAY,
//                PorterDuff.Mode.MULTIPLY);
//
//
//        mProgressBar = getView().findViewById(R.id.progress);
//
//        mSearchEditText = (ChipsEditText) getView().findViewById(R.id.txt_search);
//        mSearchEditText.setOnQueryChangeListener(this);
//        mSearchEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
//        mSearchEditText.setMaxChips(mMaxContacts);
//        mSearchEditText.setRestrictedToExistContacts(isRestrictedToExistingContacts);
//
//        if (!isRestrictedToExistingContacts) {
//            mSearchEditText.setHint(R.string.ContactListFragment_SearchHint1);
//        }
//
//        if (savedInstanceState != null) {
//            mItemAdapter.restoreFromSaveInstance(savedInstanceState);
//            mSelectedContactCount = savedInstanceState.getInt("mSelectedContactCount");
//            mFilterQuery = savedInstanceState.getString("mFilterQuery");
//
//        mPermissionEmptyView = getView().findViewById(R.id.permission_view);
//        mPermissionEmptyView.findViewById(R.id.btn_permission_settings).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        callback.openAppSettings();
//                    }
//                });
//
//
//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
//                == PackageManager.PERMISSION_GRANTED) {
//            mPermissionEmptyView.setVisibility(View.GONE);
//            getLoaderManager().initLoader(0, null, this);
//        } else {
//            mPermissionEmptyView.setVisibility(View.VISIBLE);
//            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
//                GeneralFragmentDialog fragmentDialog = DisplayUtils
//                        .fragmentDialog(getChildFragmentManager(),
//                                new Handler(),
//                                getString(R.string.general_pop_up_dialog_title_notice),
//                                getString(R.string.Permission_IviteFriendsContactRationale),
//                                getString(R.string.general_pop_up_dialog_btn_ok),
//                                getString(R.string.general_pop_up_dialog_btn_cancel));
//
//                fragmentDialog.setAnonymousCallback(new IGeneralPopup() {
//                    @Override
//                    public void onPopupPositiveClickListener(DialogFragment usedDialog) {
//                        usedDialog.dismiss();
//                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
//                                PERMISSION_REQUEST_CONTACTS);
//                    }
//
//                    @Override
//                    public void onPopupNegativeClickListener(DialogFragment usedDialog) {
//                        usedDialog.dismiss();
//                    }
//                });
//            } else {
//
//                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
//                        PERMISSION_REQUEST_CONTACTS);
//            }
//        }
//
//        View btnAddNewContact = getView().findViewById(R.id.img_add_new_contact);
//        btnAddNewContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSearchEditText.makeChipFromTail();
//            }
//        });
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        String DISPLAY_NAME_COLUMN = ContactsContract.Data.DISPLAY_NAME;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
//            DISPLAY_NAME_COLUMN = ContactsContract.Data.DISPLAY_NAME_PRIMARY;
//        }
//
//        String query = DISPLAY_NAME_COLUMN + " IS NOT NULL AND "
//                + ContactsContract.Data.IN_VISIBLE_GROUP + " = 1"
//                + " AND (" + ContactsContract.Data.MIMETYPE + "=? OR "
//                + ContactsContract.Data.MIMETYPE + "=?)";
//
//        String[] queryArgs;
//
//        if (!TextUtils.isEmpty(mFilterQuery)) {
//            query += " AND (" + DISPLAY_NAME_COLUMN + " LIKE ?";
//            query += " OR " + DISPLAY_NAME_COLUMN + " LIKE ?)";
//
//            //set upper case to every first letter after space in string
//            char[] chars = mFilterQuery.toCharArray();
//            chars[0] = Character.toUpperCase(mFilterQuery.charAt(0));
//            for (int index = mFilterQuery.indexOf(" ");
//                    index >= 0;
//                    index = mFilterQuery.indexOf(" ", index + 1)) {
//                if (index + 1 < chars.length) {
//                    chars[index + 1] = Character.toUpperCase(mFilterQuery.charAt(index + 1));
//                }
//            }
//
//            String upperLatterQuery = new String(chars);
//
//            if (isPhoneOnlyMode) {
//                queryArgs = new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//                        "%" + mFilterQuery + "%", "%" + upperLatterQuery + "%"};
//            } else {
//                queryArgs = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//                        "%" + mFilterQuery + "%", "%" + upperLatterQuery + "%"};
//            }
//
//        } else {
//            if (isPhoneOnlyMode) {
//                queryArgs = new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
//            } else {
//                queryArgs = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
//            }
//
//        }
//        mProgressBar.setVisibility(View.VISIBLE);
//        return new CursorLoader(getActivity().getApplicationContext(),
//                ContactsContract.Data.CONTENT_URI,
//                new String[]{ContactsContract.Data.CONTACT_ID,
//                        DISPLAY_NAME_COLUMN,
//                        ContactsContract.Data.PHOTO_ID},
//                query,
//                queryArgs,
//                DISPLAY_NAME_COLUMN + " COLLATE LOCALIZED ASC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mProgressBar.setVisibility(View.GONE);
//        mItemAdapter.swapCursor(data, mFilterQuery);
//        View btnAddNewContact = getView().findViewById(R.id.img_add_new_contact);
//        View lblAddNewContact = getView().findViewById(R.id.new_contact_hint);
//
//        if(data.getCount() == 0 && !TextUtils.isEmpty(mFilterQuery) && !isRestrictedToExistingContacts){
//            btnAddNewContact.setVisibility(View.VISIBLE);
//            lblAddNewContact.setVisibility(View.VISIBLE);
//        }else{
//            btnAddNewContact.setVisibility(View.GONE);
//            lblAddNewContact.setVisibility(View.GONE);
//        }
//
//        if (mContactPositionIndex.size() == 0 && TextUtils.isEmpty(mFilterQuery) && data.moveToFirst()) {
//            int position = 0;
//            int idColumnPosition = data.getColumnIndex(ContactsContract.Data.CONTACT_ID);
//            do {
//                mContactPositionIndex.put(data.getInt(idColumnPosition), position);
//                position++;
//            } while (data.moveToNext());
//        }else
//        if(mPendingContactForScroll != null){
//            int scrollPosition = mContactPositionIndex.get((int)mPendingContactForScroll.getId());
//            mPendingContactForScroll = null;
//            RecyclerView list = (RecyclerView) getView().findViewById(R.id.list);
//
//            list.scrollToPosition(scrollPosition);
//            //fix scroll by header height
//            list.scrollBy(0, -listHeaderHeight);
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mItemAdapter.swapCursor(null, mFilterQuery);
//    }
//
//    @Override
//    public void onItemSelectionChanged(Contact contact, SelectableItem selectedItem) {
//        int oldValue = mSelectedContactCount;
//        mSelectedContactCount = mItemAdapter.countSelectedItems();
//
//        //if we selected first item then show next button
//        if (oldValue == 0) {
//            getActivity().supportInvalidateOptionsMenu();
//        } else if (mSelectedContactCount == 0) { //if we deselect last item then hide next button
//            getActivity().supportInvalidateOptionsMenu();
//        }
//
//        if (selectedItem.isSelected()) {
//            mSearchEditText.submitEntry(new RecipientEntry(contact));
//        } else {
//            if (!contact.hasSelectedContacts()) {
//                mSearchEditText.removeEntry(new RecipientEntry(contact));
//            }
//        }
//        mPendingContactForScroll = contact;
//
//    }
//
//    @Override
//    public void onMaxSelectionError() {
//        DisplayUtils.fragmentDialog(getFragmentManager(),
//                new Handler(),
//                getString(R.string.general_pop_up_dialog_title_notice),
//                getString(R.string.split_fare_max_error_body, String.valueOf(mMaxParticipants - 1)),
//                getString(R.string.general_pop_up_dialog_btn_ok),
//                null);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        if (mSelectedContactCount > 0 || isShowActionDone) {
//            inflater.inflate(R.menu.contact_next_menu, menu);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_next) {
//            sendInvites(true);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void sendInvites(boolean fromMenu) {
//        if (!TextUtils.isEmpty(mFilterQuery)) {
//            mSearchEditText.makeChipFromTail();
//        }
//        if (mSelectedContactCount > 0) {
//            hideKeyboard(mSearchEditText);
//
//            if ("split".equalsIgnoreCase(mSource)) {
////                MixPanelNew.Instance().eventSplitContactScreen(
////                        mItemAdapter.getSelectedContacts().size(),
////                        ContextCompat.checkSelfPermission(getActivity(),
////                                Manifest.permission.READ_CONTACTS)
////                                == PackageManager.PERMISSION_GRANTED,
////                        false,
////                        String.valueOf(mOrderId),
////                        isClickedAppSettings);
//            }
//            if (fromMenu && SOURCE_INVITE_FRIENDS.equalsIgnoreCase(mSource)) {
////                ClientEvents.getInstance().eventInviteFriendsContactScreenNextClicked(
////                        mItemAdapter.getSelectedContacts().size(),
////                        getSelectablePhonesCount(mItemAdapter.getSelectedContacts()),
////                        getSelectableEmailsCount(mItemAdapter.getSelectedContacts()));
//            }
//            callback.onContactSelectionComplete(mItemAdapter.getSelectedContacts());
//        }
//    }
//
//    private int getSelectablePhonesCount(List<Contact> selectedContacts) {
//        int counter = 0;
//        for (Contact contact : selectedContacts) {
//            for (SelectableItem channel : contact.getContacts()) {
//                if (channel.isSelected()) {
//                    if (!channel.getValue().contains("@")) {
//                        counter ++;
//                    }
//                }
//            }
//        }
//        return counter;
//    }
//
//    private int getSelectableEmailsCount(List<Contact> selectedContacts) {
//        int counter = 0;
//        for (Contact contact : selectedContacts) {
//            for (SelectableItem channel : contact.getContacts()) {
//                if (channel.isSelected()) {
//                    if (channel.getValue().contains("@")) {
//                        counter ++;
//                    }
//                }
//            }
//        }
//        return counter;
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putString("mFilterQuery", mFilterQuery);
//        outState.putInt("mSelectedContactCount", mSelectedContactCount);
//        if(mItemAdapter != null) {
//            outState.putAll(mItemAdapter.buildSavedInstance());
//        }
//
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//
//        mSearchEditText.setText("");
//        mSearchEditText.restoreFromSavedInstance(savedInstanceState);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CONTACTS) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (Manifest.permission.READ_CONTACTS.equalsIgnoreCase(permissions[i])) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        mPermissionEmptyView.setVisibility(View.GONE);
////                        MixPanelNew.Instance().eventNativeContactPermission(true, mSource);
//                        getLoaderManager().restartLoader(0, null, ContactListFragment.this);
//                    } else if (
//                            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
//                                    && grantResults[i] == PackageManager.PERMISSION_DENIED) {
////                        MixPanelNew.Instance().eventNativeContactPermission(false, mSource);
//                    } else if (!shouldShowRequestPermissionRationale(
//                            Manifest.permission.READ_CONTACTS)) {
//                        GeneralFragmentDialog fragmentDialog = DisplayUtils
//                                .fragmentDialog(getChildFragmentManager(),
//                                        new Handler(),
//                                        getString(R.string.general_pop_up_dialog_title_notice),
//                                        getString(R.string.Permission_ContactsAccessDenayed),
//                                        getString(R.string.drawer_item_settings),
//                                        getString(R.string.general_pop_up_dialog_btn_cancel));
//
//                        fragmentDialog.setAnonymousCallback(new IGeneralPopup() {
//                            @Override
//                            public void onPopupPositiveClickListener(DialogFragment usedDialog) {
//                                usedDialog.dismiss();
//                                isClickedAppSettings = true;
//                                callback.openAppSettings();
//                            }
//
//                            @Override
//                            public void onPopupNegativeClickListener(DialogFragment usedDialog) {
//                                usedDialog.dismiss();
//                            }
//                        });
//                    }
//                }
//            }
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public void onQueryChanged(CharSequence query) {
//        if (!query.toString()
//                .equalsIgnoreCase(mFilterQuery)) {
//            mFilterQuery = query.toString();
//            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
//                    == PackageManager.PERMISSION_GRANTED) {
//                getLoaderManager().destroyLoader(0);
//                getLoaderManager().restartLoader(0, null, ContactListFragment.this);
//            }
//        }
//
//        // if !isRestrictedToExistingContacts we need to check if user has query for show action mode done
//        if (!isRestrictedToExistingContacts){
//            if (query.length() > 0 && !isShowActionDone){
//                isShowActionDone = true;
//                getActivity().invalidateOptionsMenu();
//            } else if (query.length() == 0 && isShowActionDone){
//                isShowActionDone = false;
//                getActivity().invalidateOptionsMenu();
//            }
//        }
//    }
//
//    @Override
//    public void onChipAdded(Contact newContact) {
//        newContact.setOpened(true);
//        newContact.getContacts().get(0).setSelected(true);
//        mItemAdapter.addSelectedContact(newContact);
//        int oldValue = mSelectedContactCount;
//        mSelectedContactCount = mItemAdapter.countSelectedItems();
//
//        //if we selected first item then show next button
//        if (oldValue == 0) {
//            getActivity().supportInvalidateOptionsMenu();
//        }
//
//        mPendingContactForScroll = newContact;
//    }
//
//    @Override
//    public void onChipDeleted(Contact delContact) {
//        mItemAdapter.removeSelectedContact(delContact);
//
//        //if we deselect last item then hide next button (keyboard delete)
//        mSelectedContactCount = mItemAdapter.countSelectedItems();
//        if (mSelectedContactCount == 0) {
//            isShowActionDone = false;
//            getActivity().supportInvalidateOptionsMenu();
//        }
//    }
//
//    @Override
//    public Contact getFirstFilteredContact() {
//        return mItemAdapter.getFirstContact();
//    }
//
//    public void onOptionsItemSelected() {
////        MixPanelNew.Instance().eventSplitContactScreen(
////                0,
////                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
////                        == PackageManager.PERMISSION_GRANTED,
////                false,
////                String.valueOf(mOrderId),
////                isClickedAppSettings);
//    }
//
//    public void onBackPressed() {
////        MixPanelNew.Instance().eventSplitContactScreen(
////                0,
////                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
////                        == PackageManager.PERMISSION_GRANTED,
////                false,
////                String.valueOf(mOrderId),
////                isClickedAppSettings);
//    }
//}
