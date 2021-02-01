package com.example.get_a_ridemobileportal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BookingFragment extends Fragment {
    TextView date;
    TextView timeText;
    TextView phone;
    Button confirm;
    EditText pickup;
    EditText destination;
    ProgressBar progressBar;
    DatabaseReference reference;
    DatabaseReference reference2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_booking,container,false);
        date = v.findViewById(R.id.date_text);
        timeText=v.findViewById(R.id.time_text);
        pickup = v.findViewById(R.id.pickup);
        confirm=v.findViewById(R.id.button);
        destination = v.findViewById(R.id.destination);
        phone=v.findViewById(R.id.phoneNumber);
        progressBar=v.findViewById(R.id.progressBar);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(v.getContext(), datePickerListener, year, month, day);
                dateDialog.show();

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference=FirebaseDatabase.getInstance().getReference("Bookings").child(HomeActivity.userId);
                reference2=FirebaseDatabase.getInstance().getReference("AssignBookings");
                Booking booking = new Booking(pickup.getText().toString(),destination.getText().toString(),date.getText().toString(),timeText.getText().toString(),phone.getText().toString(),HomeActivity.userEmail,"none","none","Pending",HomeActivity.username);
                String id = reference.push().getKey();
                reference.child(id).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())//if user added to db
                        {
                            reference2.child(id).setValue(booking);
                            Toast.makeText(v.getContext(),"Booking Successful",Toast.LENGTH_LONG).show();
                            date.setText("Pick Date");
                            timeText.setText("Pick Time");
                            pickup.setText("");
                            destination.setText("");
                            phone.setText("");
                            progressBar.setVisibility(View.GONE);
                        }
                        else// user not added to db
                        {
                            Toast.makeText(v.getContext(),"Booking failed",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

            });

        }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                      Calendar cal =  Calendar.getInstance();
                      cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                      cal.set(Calendar.MINUTE,minute);
                      cal.setTimeZone(TimeZone.getDefault());
                      SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                      String time = format.format(cal.getTime());
                      timeText.setText(time);
                    }
                },hours,mins,false);
                timePickerDialog.show();


            }
        });


        return v;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String format = new SimpleDateFormat("dd MM YYYY").format(c.getTime());
            date.setText(format);

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
