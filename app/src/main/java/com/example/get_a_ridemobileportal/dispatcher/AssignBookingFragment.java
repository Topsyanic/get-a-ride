package com.example.get_a_ridemobileportal.dispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import java.util.ArrayList;
import java.util.List;

public class AssignBookingFragment extends Fragment {
    @Nullable
    View v;
    DatabaseReference reference;
    RecyclerView assignBookingList;
    List<Booking> bookingList;
    TextView emptyText;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_assign_bookings,container,false);
       assignBookingList=(RecyclerView)v.findViewById(R.id.assignBookings_list);
        assignBookingList.setLayoutManager(new LinearLayoutManager(getContext()));
        reference= FirebaseDatabase.getInstance().getReference().child("AssignBookings");
        emptyText=v.findViewById(R.id.emptyText);
        bookingList = new ArrayList<Booking>();

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Booking>()
                .setQuery(reference,Booking.class)
                .build();
        FirebaseRecyclerAdapter<Booking, AssignBookingViewHolder> adapter
                = new FirebaseRecyclerAdapter<Booking, AssignBookingViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AssignBookingViewHolder bookingViewHolder, int i, @NonNull Booking booking) {
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
                                bookingViewHolder.assignName.setText(dbName);
                                bookingViewHolder.assignDestination.setText(dbDestination);
                                bookingViewHolder.assignTime.setText(dbTime);
                                bookingViewHolder.assignStatus.setText(dbStatus);
                                bookingViewHolder.assignDate.setText(dbDate);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


            @NonNull
            @Override
            public AssignBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.assignbookings_display_layout,parent,false);
                AssignBookingViewHolder viewHolder = new AssignBookingViewHolder(view);
                return viewHolder;
            }
        };

        assignBookingList.setAdapter(adapter);
        adapter.startListening();
    }
    public class AssignBookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView assignDestination,assignDate,assignTime,assignStatus,assignName;
        ConstraintLayout parentLayout;

        public AssignBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            assignDestination=itemView.findViewById(R.id.assign_booking_destination);
            assignDate=itemView.findViewById(R.id.assign_booking_date);
            assignTime=itemView.findViewById(R.id.assign_booking_time);
           assignStatus=itemView.findViewById(R.id.assign_booking_status);
           assignName=itemView.findViewById(R.id.assign_booking_name);
           itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(getContext(), AssignDriverFragment.class);
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
