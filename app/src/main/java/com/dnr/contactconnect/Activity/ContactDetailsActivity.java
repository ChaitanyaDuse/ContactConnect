package com.dnr.contactconnect.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dnr.contactconnect.R;
import com.dnr.contactconnect.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactDetailsActivity extends AppCompatActivity {
    private Contact contact;
    @BindView(R.id.tv_email_address)
    TextView tv_email_address;
    @BindView(R.id.tv_phone_number)
    TextView tv_phone_number;
    @BindView(R.id.tv_user_full_name)
    TextView tv_user_full_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        ButterKnife.bind(this);
        contact = getIntent().getParcelableExtra(Contact.class.getSimpleName());
        if (contact != null) {
            tv_email_address.setText(contact.getEmail());
            tv_phone_number.setText(contact.getPhoneNumber());
            tv_user_full_name.setText(contact.getFirstName());
        }

    }

    @OnClick(R.id.tv_phone_number)
    public void phoneNumberClick() {
        call(contact.getPhoneNumber());
    }

    @OnClick(R.id.tv_email_address)
    public void emailClick() {
        createEmailDraft(contact.getEmail());
    }

    @OnClick(R.id.tv_user_full_name)
    public void favoriteClick() {
        changeFavorite(contact.getFavorite());
    }

    private void changeFavorite(boolean favorite) {
    }


    private void createEmailDraft(String emailAddress) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");


        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }


    private void call(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }

   /* private Drawable getFvoriteIcon(boolean isFavorite)
    {
           Drawable favoriteIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_border);
        if(isFavorite)
        {
            favoriteIcon.setColorFilter(ContextCompat.getColor(R.color.colorPrimary, PorterDuff.Mode.OVERLAY);
        }
           return  favoriteIcon;
    }*/
}
