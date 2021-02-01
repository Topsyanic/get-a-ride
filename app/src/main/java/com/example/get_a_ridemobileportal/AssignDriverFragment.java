package com.example.get_a_ridemobileportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssignDriverFragment extends AppCompatActivity  {
TextView name,phone,location,date,time,pickup;
Button assignBtn;
Spinner spinner;
String customerName,customerPhone,customerLocation,customerDate,customerTime,driver,customerPickup,customerEmail;
DatabaseReference reference;
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
        pickup=findViewById(R.id.assign_driver_pickup);
        Intent intent = getIntent();
        customerName = intent.getStringExtra("customerName");
        customerPhone = intent.getStringExtra("customerPhone");
        customerLocation = intent.getStringExtra("customerDestination");
        customerDate = intent.getStringExtra("customerDate");
        customerTime = intent.getStringExtra("customerTime");
        customerPickup = intent.getStringExtra("customerPickup");
        customerEmail = intent.getStringExtra("customerEmail");
        name.setText("Customer Name : " + customerName);
        phone.setText("Phone Number : " + customerPhone);
        location.setText("Drop off location : " + customerLocation);
        date.setText("Date : " + customerDate);
        time.setText("Time : " + customerTime);
        pickup.setText("Pick up location : " + customerPickup);
        driver=spinner.getSelectedItem().toString();



        assignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driver.equalsIgnoreCase("John Taitor")){
                    reference= FirebaseDatabase.getInstance().getReference("DriverBookings").child("johntaitor");
                    String id = reference.push().getKey();
                    Booking booking = new  Booking(customerPickup, customerLocation, customerDate, customerTime, customerPhone, customerEmail, "johntaitor@email.com", "unavailable","Confirmed",customerName);
                    reference.child(id).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())//if user added to db
                            {
                                Toast.makeText(v.getContext(),"Successfully assigned",Toast.LENGTH_LONG).show();
                            }
                            else// user not added to db
                            {
                                Toast.makeText(v.getContext(),"Assigning Driver Failed",Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                }

            }
        });
    }


}
