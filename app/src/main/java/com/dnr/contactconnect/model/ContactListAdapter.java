package com.dnr.contactconnect.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dnr.contactconnect.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactListAdapter extends ArrayAdapter<Contact> implements SectionIndexer {
    public static char[] ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    HashMap<String, Integer> mapIndex;
    String[] sections;
    List<Contact> contactList;

    public ContactListAdapter(Context context, List<Contact> users) {
        super(context, 0, users);
        contactList = users;
        mapIndex = new LinkedHashMap<>();


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ContactViewHolder contactViewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            contactViewHolder = new ContactViewHolder(convertView);
            convertView.setTag(contactViewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            contactViewHolder = (ContactViewHolder) convertView.getTag();
        }
        // Lookup view for data population

        // Populate the data into the template view using the data object
        contactViewHolder.tv_user_first_name.setText(user.getFirstName());
        contactViewHolder.tv_user_last_name.setText(user.getLastName());
        // Return the completed view to render on screen
        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {


        for (int x = 0; x < contactList.size(); x++) {
            String fruit = contactList.get(x).getFirstName();
            String ch = fruit.substring(0, 1);
            ch = ch.toUpperCase(Locale.US);

            // HashMap will prevent duplicates
            mapIndex.put(ch, x);
        }

        Set<String> sectionLetters = mapIndex.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<>(sectionLetters);

        Log.d("sectionList", sectionList.toString());
        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);
        super.notifyDataSetChanged();

    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        Log.d("section", "" + sectionIndex);
        return mapIndex.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

     static class ContactViewHolder {
        @BindView(R.id.tv_user_first_name)
        TextView tv_user_first_name;
        @BindView(R.id.tv_user_last_name)
        TextView tv_user_last_name;

        public ContactViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
