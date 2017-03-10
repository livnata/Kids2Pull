package com.kids2pull.android.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.kids2pull.android.R;
import com.kids2pull.android.models.Event;
import com.kids2pull.android.models.Hobby;

import java.util.ArrayList;

/**
 * Created by Anna on 09/03/  jhiuhiotr2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private Activity acrivity;
    private RecyclerView eventeRecyclerView;
    private ArrayList<Event> events;
    private ArrayList<Hobby> hobbies;
    private EventHolder holder;
    private EventClicked callback;
    private String id_hobby;


    public static interface EventClicked {
        public void onEventClicked(Event event,Hobby hobby);
    }

    public EventAdapter(Activity acrivity, RecyclerView eventeRecyclerView, ArrayList<Event> events, ArrayList<Hobby>hobbies) {
        this.acrivity = acrivity;
        this.eventeRecyclerView = eventeRecyclerView;
        this.events = events;
        this.hobbies = hobbies;
        callback = (EventClicked) this.acrivity;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_event_item, parent, false);
        holder = new EventHolder(view, new EventHolder.MyHolderClicks() {
            @Override
            public void onShortClick(View item) {
                int position = eventeRecyclerView.getChildLayoutPosition(item);
                Event event = events.get(position);
                Hobby hobby = hobbies.get(position);
                callback.onEventClicked(event,hobby);

            }

            @Override
            public void onLongClick(View item) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
          id_hobby = events.get(position).getHobby_id();

        for (Hobby hobby:hobbies) {
            if(hobby.getHobby_id() == id_hobby) {
                holder.hobbyName.setText(hobby.getHobby_name());
            }
        }

         holder.hobbyTime.setText(String.valueOf( events.get(position).getEvent_date()));



    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView hobbyName;
        TextView hobbyTime;
        ImageView iconHobby;
        ImageView iconCar;
        ImageView iconFinish;
        ImageView indicatorPickUp;
        ImageView indicatorDrop;
        MyHolderClicks myHolderClicks;

        public EventHolder(View itemView, MyHolderClicks listener) {
            super(itemView);
            hobbyName = (TextView) itemView.findViewById(R.id.hobby_name);
            hobbyTime = (TextView) itemView.findViewById(R.id.time_hobby);
            iconHobby = (ImageView) itemView.findViewById(R.id.icon_hobby);
            iconCar = (ImageView) itemView.findViewById(R.id.icon_car);
            iconFinish = (ImageView) itemView.findViewById(R.id.icon_finish);
            indicatorPickUp = (ImageView) itemView.findViewById(R.id.pick_up_indicator);
            indicatorDrop = (ImageView) itemView.findViewById(R.id.drop_indicator);
            myHolderClicks = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myHolderClicks.onShortClick(v);

        }

        @Override
        public boolean onLongClick(View v) {
            myHolderClicks.onLongClick(v);
            return true;
        }

        public interface MyHolderClicks {
            public void onShortClick(View item);

            public void onLongClick(View item);
        }
    }
}
