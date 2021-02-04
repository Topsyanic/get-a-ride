package com.example.get_a_ridemobileportal.dispatcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.get_a_ridemobileportal.R;
import com.example.get_a_ridemobileportal.driver.DriverHomeActivity;
import com.example.get_a_ridemobileportal.driver.DriverMyBookingsFragment;

public class DispatcherMenuFragment extends Fragment {
    View v;
    TextView greetings;
    ImageButton bookings,drivers;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_dispatcher_menu,container,false);
        greetings=v.findViewById(R.id.dispatcher_greetings);
        bookings=v.findViewById(R.id.pendingButton);
        drivers=v.findViewById(R.id.driversButton);
        greetings.setText("Hello, "+DispatcherHomeActivity.username);

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new AssignBookingFragment()).commit();
            }
        });
        drivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer_dispatcher,new ViewDriversFragment()).commit();
            }
        });

        return v;
    }
}
