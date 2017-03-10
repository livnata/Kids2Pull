package com.kids2pull.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kids2pull.android.R;
import com.kids2pull.android.adapters.EventAdapter;
import com.kids2pull.android.models.Event;
import com.kids2pull.android.models.Hobby;

import java.util.ArrayList;

/**
 * Created by Anna on 09/03/2017.
 */

public class EventsListActivity extends AppCompatActivity implements View.OnClickListener,
        EventAdapter.EventClicked{

    private RecyclerView eventsRecyclerView;
    private Activity activity;
    private ArrayList<Hobby> hobbies;
    private ArrayList<Event> Events;
    private EventAdapter adapter;
    private LinearLayoutManager manager;
    private FloatingActionButton addbtn;
    //DB
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.user_events_list);
        addbtn = (FloatingActionButton) findViewById(R.id.floating_button_add_new);
        eventsRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        eventsRecyclerView.hasFixedSize();
        manager = new LinearLayoutManager(activity);
        eventsRecyclerView.setLayoutManager(manager);
        adapter = new EventAdapter(activity, eventsRecyclerView, Events,hobbies);
        eventsRecyclerView.setAdapter(adapter);
        addbtn.setOnClickListener(this);

        //Read from DB
        //get reference to events
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("events");
        reference = database.getReference("hobbies");
        //attach a listener to read the data at events reference
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                Hobby hobby = dataSnapshot.getValue(Hobby.class);
                Events.add(event);
                hobbies.add(hobby);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EventsListActivity.this,"The read failed",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onEventClicked(Event event, Hobby hobby, Activity activity) {
        Intent intent = new Intent(activity,EventDetails.class);
        intent.putExtra("name_hobby",hobby.getHobby_name());
        startActivity(intent);
    }
}
