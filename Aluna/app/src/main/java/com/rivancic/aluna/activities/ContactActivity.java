package com.rivancic.aluna.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.rivancic.aluna.R;

import timber.log.Timber;


/**
 * TODO Add fields to send message through email or some other means...
 */
public class ContactActivity extends BaseActivity {

    private ImageView contactIv;
    private ImageView emailIv;
    private ImageView phoneIv;
    private PercentRelativeLayout contactContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.contact_activity;
        super.onCreate(savedInstanceState);
        initializeView();
    }

    private void initializeView() {

        contactIv = (ImageView) findViewById(R.id.contact_iv);
        emailIv = (ImageView) findViewById(R.id.email);
        phoneIv = (ImageView) findViewById(R.id.phone);
        contactIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.contact));
        contactIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sendEmail();
                call();
            }
        });
        contactContainer = (PercentRelativeLayout) findViewById(R.id.contact_container_prl);
        contactIv.post(new Runnable() {
            @Override
            public void run() {

                int imageHeight = contactIv.getHeight();
                Timber.i("Image height: %d", imageHeight);
                emailIv.setY(Math.round(imageHeight*16/100));
                phoneIv.setY(Math.round(imageHeight*34/100));
            }
        });
    }

    private void call() {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+38651397570"));
      //  startActivity(intent);
    }

    private void sendEmail() {

        Intent openEmailIntent = new Intent(ContactActivity.this, EmailActivity.class);
        startActivity(openEmailIntent);
    }
}
