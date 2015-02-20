package com.ohtu.wearable.wearabledataservice;

import android.content.Context;
import android.hardware.Sensor;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to pass information of the sensors to the view.
 *
 */
public final class Adapter extends WearableListView.Adapter {
    private List<Sensor> mDataset;
    private final Context mContext;
    private final LayoutInflater mInflater;
    /** Boolean array to check which checkbox positions are clicked: */
    boolean[] positions;

    /**
     * Constructor for Adapter
     * @param context Context of the service
     * @param sensors List of sensors on the device
     */
    public Adapter(Context context, List<Sensor> sensors) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataset = sensors;
        positions = new boolean[sensors.size()];
    }

    /**
     * Provides a reference to the type of views used:
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
     * @param parent
     * @param viewType
     * @return Inflated custom layout for list items
     */
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    /**
     * Replaces the contents of a list item.
     * Instead of creating new views, the list tries to recycle existing ones
     * @param holder
     * @param position Position of the list item
     */
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 final int position) {
        // retrieve the text view
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        TextView view = itemHolder.textView;
        // replace text contents
        view.setText(mDataset.get(position).getName());

        //setting up checkbox:
        ((ItemViewHolder) holder).checkBox.setFocusable(false);

        //Setting checkbox a value if they have been checked already:
        ((ItemViewHolder) holder).checkBox.setChecked(positions[position]);
        //Set tag for checkbox to track the position:
        ((ItemViewHolder) holder).checkBox.setTag(String.valueOf(position));
        //setting listener for checkbox:
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
            }});
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }




    public List<Sensor> getSelectedSensors() {
        List<Sensor> sensors = new ArrayList<>();
        for (int i = 0; i < mDataset.size(); i++) {
            if (positions[i]) {
                sensors.add(mDataset.get(i));
            }
        }
        //for (int i = 0; i < sensors.size(); i++) {
        //    System.out.println(sensors.get(i).getName());
        //}
        return sensors;
    }


}
