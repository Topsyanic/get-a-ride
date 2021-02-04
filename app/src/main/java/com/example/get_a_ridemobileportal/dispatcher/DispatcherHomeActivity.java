package com.example.get_a_ridemobileportal.dispatcher;

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
import com.example.get_a_ridemobileportal.dispatcher.AssignBookingFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DispatcherHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
   static String  username;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher_home);
        Toolbar toolbar = findViewById(R.id.nav_toolbar_dispatcher);
        setSupportActionBar(toolbar);
        username=getIntent().getExtras().getString("username");
        System.out.println(username);
        NavigationView navigationView = findViewById(R.id.navigation_view_dispatcher);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView)headerView.findViewById(R.id.username);
        navUsername.setText(username);
        drawerLayout = findViewById(R.id.drawer_layout_dispatcher);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new DispatcherMenuFragment()).commit();
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new DispatcherMenuFragment());
            navigationView.setCheckedItem(R.id.nav_home_dispatcher);

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
            case R.id.nav_home_dispatcher:getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new DispatcherMenuFragment()).commit();
                break;
            case R.id.nav_view_driver:getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new ViewDriversFragment()).commit();
                break;
            case R.id.nav_booking_dispatcher:getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new AssignBookingFragment()).commit();
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