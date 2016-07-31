package com.dnr.contactconnect.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnr.contactconnect.ApiService;
import com.dnr.contactconnect.di.InjectHelper;
import com.dnr.contactconnect.R;
import com.dnr.contactconnect.model.Contact;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddContactActivity extends AppCompatActivity {
    private static final String PHONE_REGEX = "^[0-9]{12,15}$";

    @Inject
    ApiService apiService;
    @BindView(R.id.et_first_name)
    EditText et_first_name;

    @BindView(R.id.et_last_name)
    EditText et_last_name;

    @BindView(R.id.et_email_address)
    EditText et_email_address;

    @BindView(R.id.et_phone_number)
    EditText et_phone_number;

    @BindView(R.id.btn_save_contact)
    Button btn_save_contact;
    private ProgressDialog progressDialog;

    private Contact newContact;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        InjectHelper.getRootComponent().inject(this);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        newContact = new Contact();


    }

    @OnClick(R.id.btn_save_contact)
    public void saveContact(View view) {

        if (isValidated()) {
            Toast.makeText(getApplicationContext(), "Ready to add", Toast.LENGTH_SHORT).show();
            addContact(newContact);
        }
    }

    public final static boolean isValidEmail(String target) {
        return (target.isEmpty() || android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }

    public static boolean isPhoneNumber(String text, boolean required) {
        // pattern doesn't match so returning false
        return !(required && !Pattern.matches(PHONE_REGEX, text));

    }

    public static boolean isValidName(String text, boolean required) {
        return required && text.length() > 3;
    }


    private boolean isValidated() {
        boolean isValidated = true;
        if (!isValidEmail(et_email_address.getText().toString())) {
            et_email_address.setError("Invalid email id.");
            isValidated = false;
        } else {
            newContact.setEmail(et_email_address.getText().toString());
        }
        if (!isPhoneNumber(et_phone_number.getText().toString(), true)) {
            et_phone_number.setError("Invalid phone number");
            isValidated = false;
        } else {
            newContact.setPhoneNumber(et_phone_number.getText().toString());
        }
        if (!isValidName(et_first_name.getText().toString(), true)) {
            et_first_name.setError("Name should have at least four characters");
            isValidated = false;
        } else {
            newContact.setFirstName(et_first_name.getText().toString());
        }
        newContact.setLastName(et_last_name.getText().toString());
        return isValidated;
    }


    private void addContact(Contact contact) {

        Call<Object> call = apiService.createContact(contact);
        progressDialog.show();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
