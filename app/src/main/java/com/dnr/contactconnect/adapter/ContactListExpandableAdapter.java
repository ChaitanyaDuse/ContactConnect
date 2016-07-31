package com.dnr.contactconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnr.contactconnect.R;
import com.dnr.contactconnect.model.Contact;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactListExpandableAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<Contact>> _listDataChild;


    public ContactListExpandableAdapter(Context context, List<String> listDataHeader,
                                        HashMap<String, List<Contact>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        ContactSectionHeaderViewHolder contactSectionHeaderViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.item_section_header, parent, false);
            contactSectionHeaderViewHolder = new ContactSectionHeaderViewHolder(convertView);
            convertView.setTag(contactSectionHeaderViewHolder);
        } else {
            contactSectionHeaderViewHolder = (ContactSectionHeaderViewHolder) convertView.getTag();
        }


        if (headerTitle.contentEquals(Contact.IS_FAVORITE)) {
            contactSectionHeaderViewHolder.iv_is_favorite.setVisibility(View.VISIBLE);
            contactSectionHeaderViewHolder.tv_section_header.setVisibility(View.GONE);
        } else {
            contactSectionHeaderViewHolder.iv_is_favorite.setVisibility(View.GONE);
            contactSectionHeaderViewHolder.tv_section_header.setVisibility(View.VISIBLE);
            contactSectionHeaderViewHolder.tv_section_header.setText(headerTitle);
        }


        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Contact user = (Contact) getChild(groupPosition, childPosition);
        // Check if an existing view is being reused, otherwise inflate the view
        ContactViewHolder contactViewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(_context).inflate(R.layout.item_user, parent, false);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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

    static class ContactSectionHeaderViewHolder {
        @BindView(R.id.tv_section_header)
        TextView tv_section_header;
        @BindView(R.id.iv_is_favorite)
        ImageView iv_is_favorite;

        public ContactSectionHeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
