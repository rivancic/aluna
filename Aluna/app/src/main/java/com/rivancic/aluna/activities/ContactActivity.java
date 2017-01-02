package com.rivancic.aluna.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.rivancic.aluna.R;
import com.rivancic.aluna.models.EmailContent;
import com.rivancic.aluna.repositories.EmailSending;

import timber.log.Timber;


/**
 * TODO Add fields to send message through email or some other means...
 *
 *
 * TODO Add fields verification and validation
 * TODO Add action -> sending the email
 * TODO date should be controll for choosing date.
 * TODO format email message form fields.
 * TODO check that all fields have proper type.
 * TODO set message when email is sent.
 */
public class ContactActivity extends BaseActivity {

    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText phone;
    TextInputEditText date;
    TextInputEditText message;
    Button sendButton;

    private class SubmitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            EmailContent emailContent = new EmailContent();
            emailContent.date = date.getText().toString();
            emailContent.email = email.getText().toString();
            emailContent.message = message.getText().toString();
            emailContent.name = name.getText().toString();
            emailContent.phone = phone.getText().toString();
            EmailSending emailSending = new EmailSending();
            try {
                emailSending.send(emailContent);
            } catch (EmailSending.EmailContentValidationException emailContentValidationException) {

                // TODO show errors on UI.
                Timber.e(emailContentValidationException,"email content not valid");
                showErrors(emailContentValidationException);
            }
        }
    }

    private void showErrors(EmailSending.EmailContentValidationException emailContentValidationException) {

        emailContentValidationException.getValidationErrors();
        for (EmailSending.ValidationError validationE:
        emailContentValidationException.getValidationErrors()) {
            if(validationE == EmailSending.ValidationError.EMAIL_NOT_VALID) {
                email.setError("Email is not valid");
            }
            if(validationE == EmailSending.ValidationError.NAME_REQUIRED) {
                name.setError("Name is required");
            }

            if(validationE == EmailSending.ValidationError.EMAIL_REQUIRED) {
                email.setError("Email is required");
            }

            if(validationE == EmailSending.ValidationError.MESSAGE_REQUIRED) {
                message.setError("Message is required");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.contact_activity;
        super.onCreate(savedInstanceState);
        initializeView();
    }

    private void initializeView() {

        sendButton = (Button) findViewById(R.id.contact_submit);
        sendButton.setOnClickListener(new SubmitOnClickListener());
        name = (TextInputEditText) findViewById(R.id.contact_name);
        email = (TextInputEditText) findViewById(R.id.contact_email);
        phone = (TextInputEditText) findViewById(R.id.contact_phone);
        date = (TextInputEditText) findViewById(R.id.contact_date);
        message = (TextInputEditText) findViewById(R.id.contact_message);
    }

    private void call() {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+38651397570"));
    }

    private void sendEmail() {

        Intent openEmailIntent = new Intent(ContactActivity.this, EmailActivity.class);
        startActivity(openEmailIntent);
    }
}
