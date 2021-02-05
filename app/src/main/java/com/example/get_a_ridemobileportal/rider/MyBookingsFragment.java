package com.example.get_a_ridemobileportal.rider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.get_a_ridemobileportal.models.Booking;
import com.example.get_a_ridemobileportal.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBookingsFragment extends Fragment {
    @Nullable
            View v;
    DatabaseReference reference;
    RecyclerView myBookingList;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_my_bookings,container,false);
        myBookingList=(RecyclerView)v.findViewById(R.id.mybookings_list);
        myBookingList.setLayoutManager(new LinearLayoutManager(getContext()));
        reference=FirebaseDatabase.getInstance().getReference().child("Bookings").child(HomeActivity.userId);

       return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Booking>()
                .setQuery(reference,Booking.class)
                .build();
        FirebaseRecyclerAdapter<Booking,BookingViewHolder> adapter
                = new FirebaseRecyclerAdapter<Booking, BookingViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookingViewHolder bookingViewHolder, int i, @NonNull Booking booking) {
                reference.addValueEventListener(new ValueEventListener() {
                    String id = getRef(i).getKey();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(id))
                        {

                            if (snapshot.child(id).child("customerEmail").getValue().toString().equalsIgnoreCase(HomeActivity.userEmail)) {

                                String dbDestination = snapshot.child(id).child("destination").getValue().toString();

                            String dbDate=snapshot.child(id).child("date").getValue().toString();
                            String dbTime=snapshot.child(id).child("time").getValue().toString();
                            bookingViewHolder.myDestination.setText(dbDestination);
                            bookingViewHolder.myTime.setText(dbTime);
                            bookingViewHolder.myDate.setText(dbDate);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.mybookings_display_layout,parent,false);
               BookingViewHolder viewHolder = new BookingViewHolder(view);
               return viewHolder;
            }
        };
        myBookingList.setAdapter(adapter);
        adapter.startListening();
    }
    public static class BookingViewHolder extends RecyclerView.ViewHolder
    {
        TextView myDestination,myDate,myTime;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            myDestination=itemView.findViewById(R.id.booking_destination);
            myDate=itemView.findViewById(R.id.booking_date);
            myTime=itemView.findViewById(R.id.booking_time);

        }
    }
}
