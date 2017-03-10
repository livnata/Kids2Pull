package com.kids2pull.android.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kids2pull.android.R;
import com.kids2pull.android.models.HobbyType;

import java.util.ArrayList;

/**
 * Created by livnata on 11/08/16.
 */
public class HobbyTypeListSheetAdapter
        extends RecyclerView.Adapter<HobbyTypeListSheetAdapter.ItemViewHolder> {
        private static final int ITEM_TYPE_LIBRATY = 1 ;
        private static final int ITEM_TYPE_CAMERA = 0;
        private Context mContext;
        private LayoutInflater mInflater;
        private IEditHobbyTypeClickedSheetActionsListener mListener;
        private ArrayList<String> mBottomSheetMenuTextItems ;
        HobbyType hType;
        public HobbyTypeListSheetAdapter(Context context, ArrayList<String> bottomSheetMenuTextItems,
                                         final IEditHobbyTypeClickedSheetActionsListener listener) {
            mContext = context;
            mBottomSheetMenuTextItems = bottomSheetMenuTextItems;
            mInflater = LayoutInflater.from(context);
            mListener = listener;



        }

        public void setData(ArrayList<String> bottomSheetMenuTextItems) {
            mBottomSheetMenuTextItems = bottomSheetMenuTextItems;
            notifyDataSetChanged();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//

            return new ItemViewHolder(mInflater.inflate(R.layout.hobby_type_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {



            //TODO handle data according to hobby type
//            if (position != -1) {

            hType.StringToHobyType(mBottomSheetMenuTextItems.get(position));

                holder.itemView.setTag(position);
                holder.textViewItemListName.setText(hType.getHobbyName());
                holder.imageViewListItem.setImageResource(hType.getDrawableId());
                holder.itemView.setTag(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onHobbyTypeItemClicked();
                    }
                });


        }

        @Override
        public int getItemCount() {
            return mBottomSheetMenuTextItems != null ? mBottomSheetMenuTextItems.size()  : 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position ==  ITEM_TYPE_CAMERA)
                return ITEM_TYPE_CAMERA;
            else if (position ==  ITEM_TYPE_LIBRATY)
                return ITEM_TYPE_LIBRATY;

                return super.getItemViewType(position);

        }

        protected static class ItemViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewItemListName ;
            public ImageView imageViewListItem;

            public ItemViewHolder(View itemView) {
                super(itemView);
                textViewItemListName = (TextView) itemView.findViewById(R.id.textTemplate);
                imageViewListItem = (ImageView) itemView.findViewById(R.id.imageTemplate);
            }
        }

        public interface IEditHobbyTypeClickedSheetActionsListener {

            void onHobbyTypeItemClicked();

        }
    }
