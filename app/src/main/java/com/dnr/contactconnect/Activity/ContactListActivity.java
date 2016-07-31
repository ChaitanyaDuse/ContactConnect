package com.dnr.contactconnect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;
import com.dnr.contactconnect.ApiService;
import com.dnr.contactconnect.di.InjectHelper;
import com.dnr.contactconnect.R;
import com.dnr.contactconnect.adapter.ContactListExpandableAdapter;
import com.dnr.contactconnect.model.Contact;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListActivity extends AppCompatActivity {
    public static char[] ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    @Inject
    ApiService apiService;

    ProgressDialog progressDialog;
    @BindView(R.id.lv_contacts)
    FloatingGroupExpandableListView listView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    List<Contact> contactList = new ArrayList<>();
    List<String> headerList = new ArrayList<>();
    private HashMap<String, List<Contact>> _listDataChild = new HashMap<>();
    ContactListExpandableAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        InjectHelper.getRootComponent().inject(this);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        listView.setFastScrollEnabled(true);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent i = new Intent(ContactListActivity.this, ContactDetailsActivity.class);
                i.putExtra(Contact.class.getSimpleName(), (Contact) contactListAdapter.getChild(groupPosition, childPosition));
                startActivity(i);
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        contactListAdapter = new ContactListExpandableAdapter(getApplicationContext(), headerList, _listDataChild);
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(contactListAdapter);
        listView.setAdapter(wrapperAdapter);
        //getUsers();
        getDummyUsers();
    }


   /* @OnItemClick(R.id.lv_contacts)
    public void onItemClick(int position) {
        Intent i = new Intent(this, ContactDetailsActivity.class);
        i.putExtra(Contact.class.getSimpleName(), contactList.get(position));
        startActivity(i);
    }*/


    @OnClick(R.id.fab)
    public void openAddContact() {
        gotoAddContactActivity();
    }


    private void getUsers() {

        Call<List<Contact>> call = apiService.getUser();
        progressDialog.show();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                contactList.addAll(response.body());

                Collections.sort(contactList);
                populateMap();
                contactListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void gotoAddContactActivity() {
        Intent i = new Intent(this, AddContactActivity.class);
        startActivity(i);
    }

    private void getDummyUsers() {

        contactList.add(new Contact("Chaitanya", "ab", "8980536994", true));
        contactList.add(new Contact("Archi", "ab", "8980536994", true));
        contactList.add(new Contact("Guddi", "ab", "8980536994", true));
        contactList.add(new Contact("Xenon", "ab", "8980536994", true));
        contactList.add(new Contact("Abhijit", "ab", "8980536994"));
        contactList.add(new Contact("Sukhada", "ab", "8980536994"));
        contactList.add(new Contact("Omkar", "ab", "8980536994"));
        contactList.add(new Contact("Udyam", "ab", "8980536994"));
        contactList.add(new Contact("Sagar", "ab", "8980536994"));
        contactList.add(new Contact("Leonerd", "ab", "8980536994"));
        contactList.add(new Contact("Kalki", "ab", "8980536994"));
        contactList.add(new Contact("Riya", "ab", "8980536994"));
        contactList.add(new Contact("Hardik", "ab", "8980536994"));
        contactList.add(new Contact("Bhuvin", "ab", "8980536994"));
        contactList.add(new Contact("Priyamvada", "ab", "8980536994"));
        contactList.add(new Contact("Falguni", "ab", "8980536994"));
        contactList.add(new Contact("Eicher", "ab", "8980536994"));
        contactList.add(new Contact("Dipen", "ab", "8980536994"));
        contactList.add(new Contact("Gaurav", "ab", "8980536994"));
        contactList.add(new Contact("Jahgirdar", "ab", "8980536994"));
        contactList.add(new Contact("Madhukar", "ab", "8980536994"));
        contactList.add(new Contact("Nimish", "ab", "8980536994"));
        contactList.add(new Contact("Orlando", "ab", "8980536994"));
        contactList.add(new Contact("Quandeel", "ab", "8980536994"));
        contactList.add(new Contact("Sofia", "ab", "8980536994"));
        contactList.add(new Contact("Tanmayee", "ab", "8980536994"));
        contactList.add(new Contact("Urchi", "ab", "8980536994"));
        contactList.add(new Contact("Vijay", "ab", "8980536994"));
        contactList.add(new Contact("Zeenat", "ab", "8980536994"));

        Collections.sort(contactList);
        populateMap();
        contactListAdapter.notifyDataSetChanged();
        // setadapter();
    }

    private void populateMap() {
        for (int j = 0; j < contactList.size(); j++) {
            if (contactList.get(j).getFavorite()) {
                if (!_listDataChild.containsKey(Contact.IS_FAVORITE)) {

                    _listDataChild.put(Contact.IS_FAVORITE, new ArrayList<Contact>());
                    headerList.add(Contact.IS_FAVORITE);
                }
                _listDataChild.get(Contact.IS_FAVORITE).add(contactList.get(j));
            }
            String ch = contactList.get(j).getFullName().substring(0, 1);
            ch = ch.toUpperCase();
            if (!_listDataChild.containsKey(ch)) {

                _listDataChild.put(ch, new ArrayList<Contact>());
                headerList.add(ch);
            }
            _listDataChild.get(ch).add(contactList.get(j));

        }

        Collections.sort(headerList);

    }

    private void setadapter() {
        contactListAdapter = new ContactListExpandableAdapter(getApplicationContext(), headerList, _listDataChild);
        listView.setAdapter(contactListAdapter);
    }


}
