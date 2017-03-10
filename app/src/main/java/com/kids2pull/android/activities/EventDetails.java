package com.kids2pull.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kids2pull.android.R;
import com.kids2pull.android.fragments.HobbyTypeBottomSheetFragment;
import com.kids2pull.android.fragments.HobbyTypeListSheetAdapter;
import com.kids2pull.android.lib.DatePickerFragment;
import com.kids2pull.android.lib.TimePickerFragment;
import com.kids2pull.android.models.Event;
import com.kids2pull.android.models.Hobby;
import com.kids2pull.android.models.User;

import java.text.SimpleDateFormat;

public class EventDetails extends AppCompatActivity implements HobbyTypeListSheetAdapter.IEditHobbyTypeClickedSheetActionsListener {

    private Event mEvent;
    private Hobby mHobby;
    private User mUser;
    private Button mBtnPickup;
    private Button mBtnDropOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        mBtnPickup = (Button)findViewById(R.id.PickUpButton);
        mBtnDropOff = (Button)findViewById(R.id.DropOffButton);

        mBtnDropOff.setOnClickListener( OnClickButtonDropOff);
        mBtnPickup.setOnClickListener( OnClickButtonPickup);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mUser = (User) getIntent().getSerializableExtra("user");
        mEvent = (Event) getIntent().getSerializableExtra("event");
        mHobby = (Hobby) getIntent().getSerializableExtra("hobby");

        readEvent();

    }

    private View.OnClickListener OnClickButtonDropOff = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickDropOff();
        }
    };

    private View.OnClickListener OnClickButtonPickup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickPickup();
        }
    };

    private void onClickDropOff(){

    }

    private void onClickPickup(){

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void readEvent() {
        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("hh:mm");

        //read event from db

        EditText eventName = (EditText) findViewById(R.id.eventName);
        if (eventName != null) {
//            eventName.setText(mEvent.getEventName);
        }

        Button setTime = (Button) findViewById(R.id.SetTime);
        if (setTime != null) {
           /* setTime.setText(mEvent.getEvent_date().toString);*/
            setTime.setText( simpleDateFormat.format( mEvent.getEvent_date()));
        }

        Button setDate = (Button) findViewById(R.id.SetDate);
        if (setDate != null) {
            /*setDate.setText(mEvent.getEvent_date().toString());*/
            setDate.setText(String.valueOf(mEvent.getEvent_date()));
        }

        EditText location = (EditText) findViewById(R.id.location);
        if (location != null) {
//            location.setText(mEvent.getLocation());
        }

    }

    private boolean dismissBottomSheet() {
        HobbyTypeBottomSheetFragment pEBottomSheetFragment
                = (HobbyTypeBottomSheetFragment) getSupportFragmentManager()
                .findFragmentByTag(HobbyTypeBottomSheetFragment.FRAGMENT_TAG);
        if (pEBottomSheetFragment != null && pEBottomSheetFragment.isAdded()) {
            pEBottomSheetFragment.onBackPressed();
            return true;
        } else {
            return false;
        }
    }

    public void saveEvent() {
        //save event to db
//        Event event = new Event();
//        event.setEvent_date();
    }

    @Override
    public void onHobbyTypeItemClicked() {
        dismissBottomSheet();
        // TODO update the icon Hobby Type drawable in Edit Event according to the hobby Type


    }
}
