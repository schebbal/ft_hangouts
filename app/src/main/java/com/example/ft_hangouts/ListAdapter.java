package com.example.ft_hangouts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<ListContacts> listContacts;
    private LayoutInflater inflater;

    public ListAdapter(Context context, List<ListContacts> listContacts) {
        this.context = context;
        this.listContacts = listContacts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listContacts.size();
    }

    @Override
    public ListContacts getItem(int position) {
        return listContacts.get(position);
    }

    @Override
    public long getItemId(int position) {

        return listContacts.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_contact, null);

        ListContacts currentItem = getItem(position);

        int     itemId   = currentItem.get_id();
        String itemName  = currentItem.get_name();
        String itemPhone = currentItem.get_phone();

        TextView itemIdView = convertView.findViewById(R.id.contact_id);
        itemIdView.setId(itemId);

        TextView itemNameView = convertView.findViewById(R.id.contact_name);
        itemNameView.setText(itemName);

        TextView itemPhoneView = convertView.findViewById(R.id.contact_phone);
        itemPhoneView.setText(itemPhone);

        return convertView;
    }
    
}
