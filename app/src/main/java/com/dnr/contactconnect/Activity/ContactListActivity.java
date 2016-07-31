package com.dnr.contactconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;
import com.dnr.contactconnect.ApiService;
import com.dnr.contactconnect.R;
import com.dnr.contactconnect.adapter.ContactListExpandableAdapter;
import com.dnr.contactconnect.di.InjectHelper;
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

    @Inject
    ApiService apiService;


    @BindView(R.id.empty)
    LinearLayout emptyView;
    @BindView((R.id.progress))
    LinearLayout progressView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.lv_contacts)
    FloatingGroupExpandableListView listView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<Contact> contactList = new ArrayList<>();
    private List<String> headerList = new ArrayList<>();
    private HashMap<String, List<Contact>> _listDataChild = new HashMap<>();
    private ContactListExpandableAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        InjectHelper.getRootComponent().inject(this);
        ButterKnife.bind(this);

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
        getUsers();
        //getDummyUsers();
    }


    @OnClick(R.id.fab)
    public void openAddContact() {
        gotoAddContactActivity();
    }


    private void getUsers() {
        showProgress();
        Call<List<Contact>> call = apiService.getUser();

        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {


                if (response.body().size() > 0) {
                    contactList.clear();
                    _listDataChild.clear();
                    headerList.clear();
                    contactList.addAll(response.body());
                    Collections.sort(contactList);
                    populateMap();
                    contactListAdapter.notifyDataSetChanged();
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

                hideProgress();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, R.string.no_internet, Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        });
    }

    private void gotoAddContactActivity() {
        Intent i = new Intent(this, AddContactActivity.class);
        startActivity(i);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);


        // Return true to display menu
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.refresh_item) {
            getUsers();
            //getDummyUsers();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgress() {
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressView.setVisibility(View.GONE);
        if (contactList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }


    }


}
