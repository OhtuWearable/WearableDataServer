package com.ohtu.wearable.wearabledataservice.list;

import android.content.Context;
import android.hardware.Sensor;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ohtu.wearable.wearabledataservice.fragments.FragmentOne;
import com.ohtu.wearable.wearabledataservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to pass selections of the sensors to the view.
 *
 */
public final class WearableListAdapter extends WearableListView.Adapter {
    private List<Sensor> mDataset;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private FragmentOne fragmentOne;
    /** Boolean array to check which checkbox positions are clicked: */
    boolean[] positions;

    /**
     * Constructor for Adapter;
     * Setting fragment to adapter is a temporary solution to transfer selected data for fragment
     * in the BindViewHolder; to set the sensors context is needed
     * @param fragmentOne Fragment holding the list
     * @param sensors List of sensors on the device
     */
    public WearableListAdapter(FragmentOne fragmentOne, List<Sensor> sensors) {
        mContext = fragmentOne.getActivity();
        this.fragmentOne = fragmentOne;
        mInflater = LayoutInflater.from(fragmentOne.getActivity());
        mDataset = sensors;
        positions = new boolean[sensors.size()];
    }

    /**
     * A wrapper for the items displayed in the listView.
     * In this list view it uses TextView and CheckBox.
     */
    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

    }

    /**
     * Creates new views for list items
     * @param parent ViewGroup
     * @param viewType The view type of the new view
     * @return Inflated custom layout for list items
     */
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    /**
     * Replaces the contents of a list item. Instead of always creating new views, the list tries to recycle
     * existing ones.
     * @param holder wrapper of the items displayed in listView.
     * @param position Position of the list item.
     */
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 final int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        TextView view = itemHolder.textView;
        /** Replacing the text in the textbox: */
        view.setText(mDataset.get(position).getName());

        ((ItemViewHolder) holder).checkBox.setFocusable(false);

        /** Setting checkbox a value if it has been checked already: */
        ((ItemViewHolder) holder).checkBox.setChecked(positions[position]);
        ((ItemViewHolder) holder).checkBox.setTag(String.valueOf(position));
        /** Setting a click listener for checkbox: */
        ((ItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    int pos = Integer.parseInt( v.getTag().toString()) ;
                    positions[pos] = true;
                }

                else {
                    int pos = Integer.parseInt( v.getTag().toString()) ;
                    positions[pos] = false;
                }

                /** Calling fragmentOne to set the sensors for the MainActivity */
                fragmentOne.setSensors(getSelectedSensors());
            }});
        holder.itemView.setTag(position);
    }

    /**
     * @return Integer as number of items in the collection.
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    /**
     * A method to get a list of selected sensors.
     * @return List of sensors that are selected.
     */
    public List<Sensor> getSelectedSensors() {
        List<Sensor> sensors = new ArrayList<>();
        for (int i = 0; i < mDataset.size(); i++) {
            if (positions[i]) {
                sensors.add(mDataset.get(i));
            }
        }
        return sensors;
    }


}
