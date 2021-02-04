package com.example.get_a_ridemobileportal.driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.get_a_ridemobileportal.dispatcher.AssignDriverFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.get_a_ridemobileportal.R;

public class StartRideFragment extends AppCompatActivity {
    private static final int CALL_REQUEST=1;
    TextView name,location,date,time;
String customerName,customerPhone,customerLocation,customerDate,customerTime,customerEmail,bookingId;
Button callButton,completeButton;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CALL_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ride_fragment);
        Intent intent = getIntent();
        customerName = intent.getStringExtra("customerName");
        customerPhone = intent.getStringExtra("customerPhone");
        customerLocation = intent.getStringExtra("customerDestination");
        customerDate = intent.getStringExtra("customerDate");
        customerTime = intent.getStringExtra("customerTime");
        customerEmail = intent.getStringExtra("customerEmail");
        bookingId=intent.getStringExtra("bookingId");
        name=findViewById(R.id.start_ride_name);
        location=findViewById(R.id.start_ride_location);
        date=findViewById(R.id.start_ride_date);
        time=findViewById(R.id.start_ride_time);
        callButton=findViewById(R.id.callButton);
        completeButton=findViewById(R.id.completeButton);
        name.setText(customerName);
        location.setText(customerLocation);
        date.setText(customerDate);
        time.setText(customerTime);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CompleteRideFragment.class);
                intent.putExtra("customerEmail",customerEmail);
                intent.putExtra("bookingId",bookingId);
                startActivity(intent);
                finish();
            }
        });


    }
    private void makeCall(){

        if(customerPhone.trim().length()>0) {
            if (ContextCompat.checkSelfPermission(StartRideFragment.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StartRideFragment.this,new String[]{Manifest.permission.CALL_PHONE},CALL_REQUEST);
            }
            else
            {
                String dial = "tel:"+customerPhone;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        }
        else
        {
            Toast.makeText(this,"Customer has invalid phone number",Toast.LENGTH_LONG).show();
        }
        Intent intent2 = new Intent(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel:"+customerPhone));
        startActivity(intent2);
    }

}