package com.example.get_a_ridemobileportal.dispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.get_a_ridemobileportal.models.Booking;
import com.example.get_a_ridemobileportal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssignDriverFragment extends AppCompatActivity  {
TextView name,phone,location,date,time,pickup;
Button assignBtn;
Spinner spinner;
String customerName,customerPhone,customerLocation,customerDate,customerTime,driver,customerPickup,customerEmail,bookingId;
DatabaseReference reference;
DatabaseReference reference2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_assign_driver);
        name = findViewById(R.id.assign_driver_name);
        phone = findViewById(R.id.assign_driver_phone);
        location = findViewById(R.id.assign_driver_location);
        date = findViewById(R.id.assign_driver_date);
        time = findViewById(R.id.assign_driver_time);
        assignBtn = findViewById(R.id.assign_button);
        spinner = findViewById(R.id.driver_spinner);
        Intent intent = getIntent();
        customerName = intent.getStringExtra("customerName");
        customerPhone = intent.getStringExtra("customerPhone");
        customerLocation = intent.getStringExtra("customerDestination");
        customerDate = intent.getStringExtra("customerDate");
        customerTime = intent.getStringExtra("customerTime");
        customerPickup = intent.getStringExtra("customerPickup");
        customerEmail = intent.getStringExtra("customerEmail");
        bookingId=intent.getStringExtra("bookingId");
        name.setText(customerName);
        phone.setText(customerPhone);
        location.setText( customerLocation);
        date.setText(customerDate);
        time.setText( customerTime);
        driver=spinner.getSelectedItem().toString().trim();



        assignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    reference= FirebaseDatabase.getInstance().getReference("DriverBookings").child(driver);
                    reference2= FirebaseDatabase.getInstance().getReference("AssignBookings").child(bookingId);
                    Booking booking = new  Booking(bookingId,customerPickup, customerLocation, customerDate, customerTime, customerPhone, customerEmail, driver.toLowerCase().replaceAll(" ", "")+"@email.com", "unavailable","Confirmed",customerName);
                    reference.child(bookingId).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())//if user added to db
                            {
                                reference2.removeValue();
                                Toast.makeText(v.getContext(),"Successfully assigned",Toast.LENGTH_LONG).show();
                            }
                            else// user not added to db
                            {
                                Toast.makeText(v.getContext(),"Assigning Driver Failed",Toast.LENGTH_LONG).show();
                            }
                        }

                    });
            }
        });
    }


}
