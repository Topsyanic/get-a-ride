package com.example.get_a_ridemobileportal.driver;

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
public class DriverMenuFragment extends Fragment {
    View v;
    TextView greetings;
    ImageButton status,earnings,bookings;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_driver_menu,container,false);
        greetings=v.findViewById(R.id.greetings);
        greetings.setText("Hello, "+DriverHomeActivity.username);
        status=v.findViewById(R.id.statusButton);
        earnings=v.findViewById(R.id.earningsButton);
        bookings=v.findViewById(R.id.bookingButton);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer_driver,new DriverMyBookingsFragment()).commit();
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer_driver,new UpdateStatusFragment()).commit();
            }
        });
        return v;
    }

}
