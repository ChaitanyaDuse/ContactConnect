package com.dnr.contactconnect.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dnr.contactconnect.MyApiEndpointInterface;
import com.dnr.contactconnect.R;
import com.dnr.contactconnect.model.Contact;
import com.dnr.contactconnect.model.ContactListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactListActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @BindView(R.id.lv_contacts)
    ListView listView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    List<Contact> contactList = new ArrayList<>();
    ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        listView.setFastScrollEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        contactListAdapter = new ContactListAdapter(getApplicationContext(), contactList);
        listView.setAdapter(contactListAdapter);
        // getUsers();
        getDummyUsers();
    }

    @OnItemClick(R.id.lv_contacts)
    public void onItemClick(int position) {
        Intent i = new Intent(this, ContactDetailsActivity.class);
        i.putExtra(Contact.class.getSimpleName(), contactList.get(position));
        startActivity(i);
    }

    @OnClick(R.id.fab)
     public void openAddContact()
    {
        gotoAddContactActivity();
    }


    private void getUsers() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gojek-contacts-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MyApiEndpointInterface myApiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);
        Call<List<Contact>> call = myApiEndpointInterface.getUser();
        progressDialog.show();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                contactList.addAll(response.body());
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

        contactList.add(new Contact("Chaitanya", "ab", "8980536994"));
        contactList.add(new Contact("Archi", "ab", "8980536994"));
        contactList.add(new Contact("Guddi", "ab", "8980536994"));
        contactList.add(new Contact("Xenon", "ab", "8980536994"));
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

        contactListAdapter.notifyDataSetChanged();


    }


}
