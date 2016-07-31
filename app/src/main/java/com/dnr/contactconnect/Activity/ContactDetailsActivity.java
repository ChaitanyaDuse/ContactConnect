package com.dnr.contactconnect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnr.contactconnect.ApiService;
import com.dnr.contactconnect.R;
import com.dnr.contactconnect.di.InjectHelper;
import com.dnr.contactconnect.model.Contact;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Kind;
import ezvcard.property.Revision;
import ezvcard.property.StructuredName;
import ezvcard.property.Uid;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactDetailsActivity extends AppCompatActivity {
    private Contact contact;

    @Inject
    ApiService apiService;
    @BindView((R.id.progress))
    LinearLayout progressView;
    @BindView(R.id.base_layout)
    LinearLayout base_layout;
    @BindView(R.id.tv_email_address)
    TextView tv_email_address;
    @BindView(R.id.tv_phone_number)
    TextView tv_phone_number;
    @BindView(R.id.tv_user_full_name)
    TextView tv_user_full_name;
    @BindView(R.id.iv_user_profile_pic)
    CircleImageView iv_user_profile_pic;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        InjectHelper.getRootComponent().inject(this);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        contact = getIntent().getParcelableExtra(Contact.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getContactDetails(contact.getId());


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
        contact.setFavorite(!contact.getFavorite());
        updateContactDetails();


    }


    private void createEmailDraft(String emailAddress) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");


        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }


    private void call(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);


        // Return true to display menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share_contact_vcard) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/x-vcard");
            try {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(getVCardFile(contact)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_share_contact_text) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");


            share.putExtra(Intent.EXTRA_SUBJECT, contact.getFirstName());
            share.putExtra(Intent.EXTRA_TEXT, contact.getPhoneNumber());

            startActivity(Intent.createChooser(share, "Share Contact!"));
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private Drawable getFvoriteIcon(boolean isFavorite) {
        Drawable favoriteIcon;
        if (isFavorite) {

            favoriteIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite);
            //favoriteIcon.setColorFilter(0xffff0000, PorterDuff.Mode.OVERLAY);
        } else {
            favoriteIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border);
        }
        return favoriteIcon;
    }


    private File getVCardFile(Contact contact) throws IOException {
        VCard vCard = new VCard();
        StructuredName structuredName = new StructuredName();
        structuredName.setFamily(contact.getLastName());
        structuredName.setGiven(contact.getFirstName());
        vCard.setFormattedName(contact.getFullName());
        vCard.addTelephoneNumber(contact.getPhoneNumber(), TelephoneType.WORK, TelephoneType.CELL);
        vCard.addEmail(contact.getEmail(), EmailType.HOME);
        vCard.setKind(Kind.individual());
        vCard.setUid(Uid.random());
        //vCard.addPhoto(new Photo(createFileFromInputStream(this.getAssets().open("ic_action_user")), ImageType.PNG));

        vCard.setRevision(Revision.now());

        File vcfFile = new File(this.getExternalFilesDir(null), contact.getFirstName() + ".vcf");

        Ezvcard.write(vCard).version(VCardVersion.V3_0).go(vcfFile);


        return vcfFile;

    }

    private void getContactDetails(int id) {
        showProgress(true);
        Call<Contact> call = apiService.getContactdetails(id);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                showProgress(false);
                contact = response.body();
                bindData();
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

                showProgress(false);
                Snackbar snackbar = Snackbar
                        .make(base_layout, R.string.no_internet, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

    }

    private void updateContactDetails() {
        progressDialog.show();
        Call<Contact> call = apiService.updateContact(contact.getId(), contact);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    contact = response.body();
                    bindData();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(base_layout, R.string.error_string_contact_details, Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(base_layout, R.string.no_internet, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });
    }

    private void bindData() {
        if (contact != null) {
            tv_email_address.setText(contact.getEmail());
            tv_phone_number.setText(contact.getPhoneNumber());
            tv_user_full_name.setText(contact.getFullName());
            tv_user_full_name.setCompoundDrawablesWithIntrinsicBounds(getFvoriteIcon(contact.getFavorite()), null, null, null);
            Glide.with(getApplicationContext())
                    .load(contact.getProfilePic())
                    .placeholder(R.drawable.ic_action_user)
                    .into(iv_user_profile_pic);

        }

    }

    private void showProgress(boolean show) {
        if (show) {
            base_layout.setVisibility(View.GONE);
            progressView.setVisibility(View.VISIBLE);
        } else {
            base_layout.setVisibility(View.VISIBLE);
            progressView.setVisibility(View.GONE);
        }
    }

}
