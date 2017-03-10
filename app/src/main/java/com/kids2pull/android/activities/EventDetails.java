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
import com.kids2pull.android.lib.DatePickerFragment;
import com.kids2pull.android.lib.TimePickerFragment;
import com.kids2pull.android.models.Event;
import com.kids2pull.android.models.Hobby;

public class EventDetails extends AppCompatActivity {

    Event mEvent;
    Hobby mHobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        readEvent();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void readEvent()
    {
        //read event from db
        
        EditText eventName = (EditText)findViewById(R.id.eventName);
        if(eventName != null)
        {
//            eventName.setText(mEvent.getEventName);
        }

        Button setTime = (Button)findViewById(R.id.SetTime);
        if(setTime != null)
        {
            setTime.setText(mEvent.getEvent_date().toString());
        }

        Button setDate = (Button)findViewById(R.id.SetDate);
        if(setDate != null)
        {
            setDate.setText(mEvent.getEvent_date().toString());
        }

        EditText location = (EditText)findViewById(R.id.location);
        if(location != null)
        {
//            location.setText(mEvent.getLocation());
        }

    }

    public void saveEvent()
    {
        //save event to db
//        Event event = new Event();
//        event.setEvent_date();
    }

}
