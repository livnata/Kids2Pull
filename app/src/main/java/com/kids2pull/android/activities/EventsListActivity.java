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
import com.kids2pull.android.models.User;

import java.util.ArrayList;

/**
 * Created by Anna on 09/03/2017.
 */

public class EventsListActivity extends AppCompatActivity implements View.OnClickListener,
        EventAdapter.EventClicked {

    private RecyclerView eventsRecyclerView;
    private Activity activity;
    private ArrayList<Hobby> hobbies;
    private ArrayList<Event> Events;
    private EventAdapter adapter;
    private LinearLayoutManager manager;
    private FloatingActionButton addbtn;
    //DB
    private User mUser;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_events_list);



        addbtn = (FloatingActionButton) findViewById(R.id.floating_button_add_new);
        Events = new ArrayList<Event>();
        hobbies = new ArrayList<Hobby>();
        eventsRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        eventsRecyclerView.hasFixedSize();
        manager = new LinearLayoutManager(this);
        eventsRecyclerView.setLayoutManager(manager);
        adapter = new EventAdapter(this, eventsRecyclerView, Events, hobbies);
        eventsRecyclerView.setAdapter(adapter);
        addbtn.setOnClickListener(this);
        //Read from DB
        //get reference to events
        database = FirebaseDatabase.getInstance();
        DatabaseReference referenceEvents = database.getReference("events");
        DatabaseReference referenceHobbies = database.getReference("hobbies");

        //attach a listener to read the data at events reference
        referenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    Events.add(event);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EventsListActivity.this, "The read failed", Toast.LENGTH_SHORT).show();
            }
        });
        referenceHobbies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Hobby hobby = snapshot.getValue(Hobby.class);
                    hobbies.add(hobby);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String userid;

        userid = getIntent().getStringExtra("user_id");

        DatabaseReference referenceUsers = database.getReference("users").child( userid);

        referenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
            }
        });
        referenceHobbies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Hobby hobby = snapshot.getValue(Hobby.class);
                    hobbies.add(hobby);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onEventClicked(Event event, Hobby hobby) {
        Intent intent = new Intent(EventsListActivity.this, EventDetails.class);
        intent.putExtra("hobby", hobby);
        intent.putExtra("event", event);
        intent.putExtra("user", mUser);
        startActivity(intent);
    }
}
