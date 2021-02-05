package com.example.get_a_ridemobileportal.rider;

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

import com.example.get_a_ridemobileportal.models.Booking;
import com.example.get_a_ridemobileportal.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;

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
        Places.initialize(getContext(),"AIzaSyCPXc7ipAr0mlA7LgMGni5oN1TuYmfar5A");

        pickup.setFocusable(false);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(getContext());
                startActivityForResult(intent,100);
            }
        });
        destination.setFocusable(false);
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(getContext());
                startActivityForResult(intent,50);
            }
        });

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
                String checkDate=date.getText().toString().trim();
                String checkTime=timeText.getText().toString().trim();
                String checkPickup = pickup.getText().toString().trim();
                String checkDestination = destination.getText().toString().trim();
                String checkPhone = phone.getText().toString().trim();

                if(checkPickup.isEmpty())
                {
                    pickup.setError("Pick up cannot be blank");
                    pickup.requestFocus();
                    return;
                }
                if(checkDestination.isEmpty())
                {
                    destination.setError("Destination cannot be blank");
                    destination.requestFocus();
                    return;
                }
                if(checkPhone.isEmpty())
                {
                    phone.setError("Phone cannot be blank");
                    phone.requestFocus();
                    return;
                }
                if(checkPhone.length()<10)
                {
                    phone.setError("Length is to short");
                    phone.requestFocus();
                    return;
                }
                int firstNumber = Character.getNumericValue(checkPhone.charAt(0));
                if(firstNumber != 0  )
                {
                    phone.setError("Number must begin with a 0");
                    phone.requestFocus();
                    return;
                }
                if(checkDate.equalsIgnoreCase("Pick Date"))
                {
                    date.setError("Date cannot be blank");
                    date.requestFocus();
                    return;
                }
                if(checkTime.equalsIgnoreCase("Pick Time"))
                {
                    timeText.setError("Time cannot be blank");
                    timeText.requestFocus();
                    return;
                }



                reference=FirebaseDatabase.getInstance().getReference("Bookings").child(HomeActivity.userId);
                reference2=FirebaseDatabase.getInstance().getReference("AssignBookings");
                Booking booking = new Booking(pickup.getText().toString(),destination.getText().toString(),date.getText().toString(),timeText.getText().toString(),phone.getText().toString(),HomeActivity.userEmail,"none","none","Pending",HomeActivity.username);
                String id = reference.push().getKey();
                booking.setId(id);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            pickup.setText(place.getAddress());
        }
        else if(requestCode==50 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            destination.setText(place.getAddress());
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
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
