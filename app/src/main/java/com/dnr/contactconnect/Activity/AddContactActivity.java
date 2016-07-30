package com.dnr.contactconnect.Activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnr.contactconnect.R;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddContactActivity extends AppCompatActivity {
    private static final String PHONE_REGEX = "^[0-9]{12,15}$";


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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_save_contact)
    public void saveContact(View view) {

        if (isValidated()) {
            Toast.makeText(getApplicationContext(), "Ready to add", Toast.LENGTH_SHORT).show();
        }
    }

    public final static boolean isValidEmail(String target) {
        if (target.isEmpty()) {
            return true;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isPhoneNumber(String text, boolean required) {
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(PHONE_REGEX, text)) {
            return false;
        }

        return true;
    }

    public static boolean isValidName(String text, boolean required) {
        if (required && text.length() > 3) {

            return true;
        } else return false;
    }


    private boolean isValidated() {
        boolean isValidated = true;
        if (isValidEmail(et_email_address.getText().toString())) {

        } else {
            et_email_address.setError("Invalid email id.");
            isValidated = false;
        }

        if (isPhoneNumber(et_phone_number.getText().toString(), true)) {

        } else {
            et_phone_number.setError("Invalid phone number");
            isValidated = false;
        }
        if (isValidName(et_first_name.getText().toString(), true)) {

        } else {
            et_first_name.setError("Name should have at least four characters");
            isValidated = false;
        }
        return isValidated;
    }
}
