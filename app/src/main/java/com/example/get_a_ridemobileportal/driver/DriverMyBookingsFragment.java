package com.example.get_a_ridemobileportal.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.get_a_ridemobileportal.models.Booking;
import com.example.get_a_ridemobileportal.R;
import com.example.get_a_ridemobileportal.dispatcher.AssignDriverFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverMyBookingsFragment extends Fragment {
    @Nullable
    View v;
    DatabaseReference reference;
    RecyclerView driverBookingList;
    List<Booking> bookingList;
    String driverName;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_driver_mybookings,container,false);
        driverBookingList=(RecyclerView)v.findViewById(R.id.driverBookings_list);
        driverBookingList.setLayoutManager(new LinearLayoutManager(getContext()));
        driverName= DriverHomeActivity.username.trim();
        reference= FirebaseDatabase.getInstance().getReference().child("DriverBookings").child(driverName);
        bookingList = new ArrayList<Booking>();


        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Booking>()
                .setQuery(reference,Booking.class)
                .build();
        FirebaseRecyclerAdapter<Booking, DriverBookingViewHolder> adapter
                = new FirebaseRecyclerAdapter<Booking, DriverBookingViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DriverBookingViewHolder bookingViewHolder, int i, @NonNull Booking booking) {
                reference.addValueEventListener(new ValueEventListener() {
                    String id = getRef(i).getKey();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(id))
                        {
                            String dbName = snapshot.child(id).child("customerName").getValue().toString();
                            String dbEmail=snapshot.child(id).child("customerEmail").getValue().toString();
                            String dbPhone =snapshot.child(id).child("phoneNumber").getValue().toString();
                            String dbDestination = snapshot.child(id).child("destination").getValue().toString();
                            String dbPickup = snapshot.child(id).child("pickup").getValue().toString();
                            String dbDate=snapshot.child(id).child("date").getValue().toString();
                            String dbTime=snapshot.child(id).child("time").getValue().toString();
                            String dbStatus=snapshot.child(id).child("status").getValue().toString();
                            Booking b = snapshot.child(id).getValue(Booking.class);
                            bookingList.add(b);
                            bookingViewHolder.driverName.setText(dbName);
                            bookingViewHolder.driverDestination.setText(dbDestination);
                            bookingViewHolder.driverTime.setText(dbTime);
                            bookingViewHolder.driverStatus.setText(dbStatus);
                            bookingViewHolder.driverDate.setText(dbDate);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


            @NonNull
            @Override
            public DriverBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_mybookings_display_layout,parent,false);
               DriverBookingViewHolder viewHolder = new DriverBookingViewHolder(view);
                return viewHolder;
            }
        };
        driverBookingList.setAdapter(adapter);
        adapter.startListening();
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);
        driverBookingList.addItemDecoration(divider);
    }
    public class DriverBookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView driverDestination,driverDate,driverTime,driverStatus,driverName;

        public DriverBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            driverDestination=itemView.findViewById(R.id.driver_booking_destination);
            driverDate=itemView.findViewById(R.id.driver_booking_date);
            driverTime=itemView.findViewById(R.id.driver_booking_time);
            driverStatus=itemView.findViewById(R.id.driver_booking_status);
            driverName=itemView.findViewById(R.id.driver_booking_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(getContext(), StartRideFragment.class);
            intent.putExtra("customerName",bookingList.get(position).getCustomerName());
            intent.putExtra("customerPhone",bookingList.get(position).getPhoneNumber());
            intent.putExtra("customerDestination",bookingList.get(position).getDestination());
            intent.putExtra("customerDate",bookingList.get(position).getDate());
            intent.putExtra("customerTime",bookingList.get(position).getTime());
            intent.putExtra("customerPickup",bookingList.get(position).getPickup());
            intent.putExtra("customerEmail",bookingList.get(position).getCustomerEmail());
            intent.putExtra("bookingId",bookingList.get(position).getId());

            getContext().startActivity(intent);

        }
    }
}
