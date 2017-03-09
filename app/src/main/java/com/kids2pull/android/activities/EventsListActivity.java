package com.kids2pull.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kids2pull.android.R;
import com.kids2pull.android.adapters.EventAdapter;
import com.kids2pull.android.models.Event;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Anna on 09/03/2017.
 */

public class EventsListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView eventsRecyclerView;
    private Activity activity;
    private ArrayList<Event> Events = new ArrayList<>();
    private EventAdapter adapter;
    private LinearLayoutManager manager;
    private FloatingActionButton addbtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.user_events_list);

        Event event = new Event();
        event.setHobby_id(1);
        event.setEvent_date(DateTime.now());
        event.setEvent_id(1);
        String[] pickers;
        pickers = new String[]{"1", "2", "3"};
        event.setUser_ids_picker(pickers);
        event.setUser_ids_spreader(pickers);
        Events.add(event);

        addbtn = (FloatingActionButton) findViewById(R.id.floating_button_add_new);
        eventsRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        eventsRecyclerView.hasFixedSize();
        manager = new LinearLayoutManager(activity);
        eventsRecyclerView.setLayoutManager(manager);
        adapter = new EventAdapter(activity, eventsRecyclerView, Events);
        eventsRecyclerView.setAdapter(adapter);
        addbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
    /*private void prepareData(){
        Events = new ArrayList<>();
        Event Event = ()
    }*/
}
