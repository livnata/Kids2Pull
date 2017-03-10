package com.kids2pull.android.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kids2pull.android.R;
import com.kids2pull.android.helpers.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by elinaor on 01/12/2015.
 */
public class HobbyTypeBottomSheetFragment extends BottomSheetFragment implements
        HobbyTypeListSheetAdapter.IEditHobbyTypeClickedSheetActionsListener
{


    private ArrayList<String> mHobbies_types;
    public static final String FRAGMENT_TAG = "HobbyTypeBottomSheetFragment";
    private FirebaseDatabase database;

    private RecyclerView mRecyclerList;
    private HobbyTypeListSheetAdapter mAdapter;
    private int mListDividerHeight;

    private IHobbyTypeBottomPanel activityCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IHobbyTypeBottomPanel) {
            activityCallback = (IHobbyTypeBottomPanel) activity;
        }
    }

    @Override
    public void onResume(){


        database = FirebaseDatabase.getInstance();
        DatabaseReference mEventsDatabaseRef = database.getReference("hobbies_Types");
        mEventsDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean itemFound = false;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Object value = child.getValue();
                    mHobbies_types.add(((Map) value).get("hobby_name").toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public View getContentView(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.hobby_type_bottom_sheet, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        //TODO
        //mHobbies_types should come from DB ;
        mRecyclerList = (RecyclerView) getView().findViewById(R.id.edit_select_hobby_type_list);
        mRecyclerList.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new HobbyTypeListSheetAdapter(getContext(), mHobbies_types, this);
        mRecyclerList.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST);
        mListDividerHeight = dividerItemDecoration.getDividerHeight();

        mRecyclerList.addItemDecoration(dividerItemDecoration);
        patchPeekedSize(mHobbies_types.size());//TODO add 1?
    }

    private void patchPeekedSize(int count) {
        TypedArray a = getContext().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        float actionbarSize = a.getDimension(0, -1);
        a.recycle();

        float itemSize = getResources().getDisplayMetrics().density * 70;
        float constantOffset = mListDividerHeight * count;
        float itemsSize = itemSize * count + actionbarSize + constantOffset;
        if (itemsSize < getMaxPeekedHeight()) {
            setPeekedContentViewSize(itemsSize);
        } else {
            setPeekedContentViewSize(getMaxPeekedHeight());
        }
    }

    // we override this function to patch the dim animation
    @Override
    protected float getDimAlpha(float translation, float maxTranslation) {
        float progress = translation / (getPeekedContentViewSize());
        return progress * MAX_DIM_ALPHA_POP;
    }

    @Override
    protected void finalizeFragment() {
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    protected void onDimViewClicked() {
        finalizeFragmentAnim();
    }

    private void finalizeFragmentAnim() {
        if (isInitialStateWasPeeked()) {
            dismissContentView();
        }
    }

    public boolean onBackPressed() {
        if (getState() == STATE_PEEKED) {
            dismissContentView();
        }
        return true;
    }


    @Override
    public void onHobbyTypeItemClicked() {
        activityCallback.chooseHobbyType();
    }


}
