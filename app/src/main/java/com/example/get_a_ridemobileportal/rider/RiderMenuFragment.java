package com.example.get_a_ridemobileportal.rider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.get_a_ridemobileportal.R;
import com.example.get_a_ridemobileportal.driver.DriverHomeActivity;
import com.example.get_a_ridemobileportal.driver.DriverMyBookingsFragment;
import com.example.get_a_ridemobileportal.driver.UpdateStatusFragment;

public class RiderMenuFragment extends Fragment {
    View v;
    TextView greetings;
    ImageButton rideButton,bookingsButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_rider_menu,container,false);
        greetings=v.findViewById(R.id.rider_greetings);
        rideButton=v.findViewById(R.id.ride_Button);

        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new BookingFragment()).commit();
            }
        });
        bookingsButton=v.findViewById(R.id.bookingsButton);
        bookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MyBookingsFragment()).commit();
            }
        });

        return v;
    }
}
