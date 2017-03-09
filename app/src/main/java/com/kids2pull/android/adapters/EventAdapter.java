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

import java.util.ArrayList;

/**
 * Created by Anna on 09/03/  jhiuhiotr2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private Activity acrivity;
    private RecyclerView eventeRecyclerView;
    private ArrayList<Event> Events;
    private EventHolder holder;
    private EventClicked callback;

    public static interface EventClicked {
        public void onEventClicked(Event Event, Activity activity);
    }

    public EventAdapter(Activity acrivity, RecyclerView eventeRecyclerView, ArrayList<Event> Events) {
        this.acrivity = acrivity;
        this.eventeRecyclerView = eventeRecyclerView;
        this.Events = Events;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_event_item, parent, false);
        holder = new EventHolder(view, new EventHolder.MyHolderClicks() {
            @Override
            public void onShortClick(View item) {

            }

            @Override
            public void onLongClick(View item) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
         holder.hobbyName.setText(Events.get(position).getEvent_id());
         holder.hobbyTime.setText(Events.get(position).getEvent_date().toString());
        //holder.pickUp.setText(Events.get(position).getUser_ids_picker());
        // holder.drop.setText(Events.get(position).getUser_ids_spreader());


    }

    @Override
    public int getItemCount() {
        return Events.size();
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
