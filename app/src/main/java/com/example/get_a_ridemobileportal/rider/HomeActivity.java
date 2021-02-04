package com.example.get_a_ridemobileportal.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.get_a_ridemobileportal.LoginActivity;
import com.example.get_a_ridemobileportal.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    static String  username;
   static String userEmail;
   static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        username=getIntent().getExtras().getString("username");
        userEmail=getIntent().getExtras().getString("email");
        userId=getIntent().getExtras().getString("userId");
        System.out.println(username);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView)headerView.findViewById(R.id.username);
        navUsername.setText(username);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new RiderMenuFragment()).commit();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RiderMenuFragment());
            navigationView.setCheckedItem(R.id.nav_booking);

        }

    }
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_booking:getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new BookingFragment()).commit();
                break;
            case R.id.nav_rides:getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MyBookingsFragment()).commit();
                break;
            case R.id.nav_home:getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new RiderMenuFragment()).commit();
                break;
            case R.id.nav_logout:{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            }



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}