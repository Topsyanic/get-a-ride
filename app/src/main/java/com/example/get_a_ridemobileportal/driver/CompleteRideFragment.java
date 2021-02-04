package com.example.get_a_ridemobileportal.driver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.get_a_ridemobileportal.R;
import com.example.get_a_ridemobileportal.Resources.JavaMailAPI;
import com.example.get_a_ridemobileportal.rider.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompleteRideFragment extends AppCompatActivity {
    TextView  complete_email,complete_amount;
    Button completeButton,homeButton;
    String customerEmail,bookingId;
    DatabaseReference reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_complete_ride);
        Intent intent = getIntent();
        customerEmail = intent.getStringExtra("customerEmail");
        bookingId=intent.getStringExtra("bookingId");
        complete_amount=findViewById(R.id.complete_amount);
        complete_email=findViewById(R.id.complete_email);
        completeButton=findViewById(R.id.send_button);
        complete_email.setText(customerEmail);
        homeButton=findViewById(R.id.home_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkAmount = complete_amount.getText().toString().trim();
                if(checkAmount.isEmpty())
                {
                    complete_amount.setError("Charge Amount Cannot be Blank");
                    complete_amount.requestFocus();
                    return;
                }
                sendMail();

            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference= FirebaseDatabase.getInstance().getReference("DriverBookings").child(DriverHomeActivity.username).child(bookingId);
                reference.removeValue();
                finish();
                Intent intent = new Intent(getApplicationContext(), DriverHomeActivity.class);
                intent.putExtra("username", DriverHomeActivity.username);
                intent.putExtra("email",DriverHomeActivity.email);
                intent.putExtra("id",DriverHomeActivity.userId);
                startActivity(intent);
                finish();
            }
        });
    }
    public void sendMail()
    {
        String message = "Thank you for using out service.\nYour charge for last ride is Rs."+complete_amount.getText().toString();
        String subject = "Get-A-Ride Billing Information";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,customerEmail,subject,message);
        javaMailAPI.execute();

    }
}
