package com.rivancic.aluna.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rivancic.aluna.AlunaApplication;
import com.rivancic.aluna.R;
import com.rivancic.aluna.repositories.AlunaRepository;
import com.squareup.otto.Bus;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected AlunaApplication application;
    protected AlunaRepository alunaRepository;
    protected Bus bus;
    protected int contentView;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.about_us) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.best_of) {
            Intent intent = new Intent(this, BestOfActivity.class);
            startActivity(intent);
        } else if (id == R.id.contact) {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(contentView);
        Toolbar toolbar = initializeToolbar();
        initializeNavigationView(toolbar);
        initializeMainFunctionality();
    }

    private void initializeNavigationView(Toolbar toolbar) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeMainFunctionality() {

        application = (AlunaApplication) getApplication();
        bus = application.getBus();
        alunaRepository = application.getAlunaRepository();
    }

    private Toolbar initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }
}
