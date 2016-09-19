package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rivancic.aluna.R;


/**
 * TODO Add fields to send message through email or some other means...
 */
public class ContactActivity extends BaseActivity {

    private static final String CONTACT_IMAGE_URL = "http://alunaweddings.com/wp-content/uploads/2014/11/pisalni-strojc__ek-ala-pisite-nama_v-izdelavi_ang-1.jpg";
    private ImageView contactIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.contact_activity;
        super.onCreate(savedInstanceState);
        initializeView();
    }

    private void initializeView() {

        contactIv = (ImageView) findViewById(R.id.contact_iv);
        Glide.with(this).load(CONTACT_IMAGE_URL).into(contactIv);

    }
}
