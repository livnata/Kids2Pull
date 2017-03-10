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
import com.kids2pull.android.models.HobbyType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Anna on 09/03/  jhiuhiotr2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private Activity acrivity;
    private RecyclerView eventeRecyclerView;
    private ArrayList<Event> mArrLstEvents;
    private ArrayList<Hobby> mArrLstHobbies;
    private EventHolder holder;
    private EventClicked callback;
    private String id_hobby;
    HobbyType hType;
    private SimpleDateFormat mSimpleDateFormat;


    public static interface EventClicked {
        public void onEventClicked(Event event, Hobby hobby);
    }

    public EventAdapter(Activity acrivity, RecyclerView eventeRecyclerView, ArrayList<Event> mArrLstEvents, ArrayList<Hobby> mArrLstHobbies) {
        this.acrivity = acrivity;
        this.eventeRecyclerView = eventeRecyclerView;
        this.mArrLstEvents = mArrLstEvents;
        this.mArrLstHobbies = mArrLstHobbies;
        mSimpleDateFormat = new SimpleDateFormat("hh:mm");
        callback = (EventClicked) this.acrivity;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_event_item, parent, false);
        holder = new EventHolder(view, new EventHolder.MyHolderClicks() {
            @Override
            public void onShortClick(View item) {
//                int position = eventeRecyclerView.getChildLayoutPosition(item);
                callback.onEventClicked( holder.mEvent, holder.mHobby);

            }

            @Override
            public void onLongClick(View item) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {

        id_hobby = mArrLstEvents.get(position).getHobby_id();

        for (Hobby hobby : mArrLstHobbies) {
            if (hobby.getHobby_id() == id_hobby) {
                holder.hobbyName.setText(hobby.getHobby_name());
                holder.setHobby( hobby);
            }
        }

        holder.setEvent( mArrLstEvents.get(position));

        holder.hobbyTime.setText(mSimpleDateFormat.format(mArrLstEvents.get(position).getEvent_date()));

    }

    public void setmArrLstHobbies(ArrayList<Hobby> mArrLstHobbies) {
        this.mArrLstHobbies = mArrLstHobbies;
    }

    @Override
    public int getItemCount() {
        return mArrLstEvents.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView hobbyName;
        TextView hobbyTime;
        ImageView iconHobby;
        ImageView iconCar;
        MyHolderClicks myHolderClicks;
        Event mEvent;
        Hobby mHobby;

        public EventHolder(View itemView, MyHolderClicks listener) {
            super(itemView);
            hobbyName = (TextView) itemView.findViewById(R.id.hobby_name);
            hobbyTime = (TextView) itemView.findViewById(R.id.hobby_time);
            iconHobby = (ImageView) itemView.findViewById(R.id.icon_hobby);
            iconCar = (ImageView) itemView.findViewById(R.id.icon_pick_up);
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
            void onShortClick(View item);
            void onLongClick(View item);
        }

        public void setEvent(Event mEvent) {
            this.mEvent = mEvent;
        }

        public void setHobby(Hobby mHobby) {
            this.mHobby = mHobby;
        }
    }
}
