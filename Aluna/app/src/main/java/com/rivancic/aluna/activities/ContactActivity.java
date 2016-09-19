package com.rivancic.aluna.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rivancic.aluna.R;


/**
 * TODO Add fields to send message through email or some other means...
 */
public class ContactActivity extends BaseActivity {

    private static final String CONTACT_IMAGE_URL = "http://alunaweddings.com/wp-content/uploads/2014/11/pisalni-strojc__ek-ala-pisite-nama_v-izdelavi_ang-1.jpg";
    private ImageView contactIv;
    private Button sendBtn;
    private EditText nameEt;
    private EditText phoneNumberEt;
    private EditText dateEt;
    private EditText messageEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.contact_activity;
        super.onCreate(savedInstanceState);
        initializeView();
    }

    /**
     * Gather and validate all the input and then send the request with delegating to some other application.
     */
    private void requestContact() {

        // TODO translate, TODO extract to DTO ContactRequest.
        String name = nameEt.getText().toString();
        String phoneNumber = phoneNumberEt.getText().toString();
        String date = dateEt.getText().toString();
        String message = messageEt.getText().toString();
        String body = "Phone number: " + phoneNumber + "\n\n";
        body += "Najin datum: " + date + "\n\n";
        body += "Sporocilo: " + message;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vbohinec@yahoo.de"});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact_email_subject) + name);
        i.putExtra(Intent.EXTRA_TEXT   , body);
        try {
            startActivity(Intent.createChooser(i, getString(R.string.send_mail_chooser)));
        } catch (android.content.ActivityNotFoundException ex) {
            Snackbar.make(sendBtn, R.string.no_email_clients_installed, Snackbar.LENGTH_LONG);
        }
    }

    private void initializeView() {

        contactIv = (ImageView) findViewById(R.id.contact_iv);
        Glide.with(this).load(CONTACT_IMAGE_URL).into(contactIv);
        sendBtn = (Button) findViewById(R.id.contact_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestContact();
            }
        });
        nameEt = ((TextInputLayout) findViewById(R.id.name_til)).getEditText();
        dateEt = ((TextInputLayout) findViewById(R.id.datum_til)).getEditText();
        phoneNumberEt = ((TextInputLayout) findViewById(R.id.phone_number_til)).getEditText();
        messageEt = ((TextInputLayout) findViewById(R.id.message_til)).getEditText();
    }
}
