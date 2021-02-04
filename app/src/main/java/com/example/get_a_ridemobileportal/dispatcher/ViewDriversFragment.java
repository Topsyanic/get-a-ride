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

import com.example.get_a_ridemobileportal.R;
import com.example.get_a_ridemobileportal.models.Booking;
import com.example.get_a_ridemobileportal.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDriversFragment extends Fragment {
    @Nullable
    View v;
    DatabaseReference reference;
    RecyclerView driverList;
    List<User> userList;


    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_view_drivers,container,false);
        driverList=(RecyclerView)v.findViewById(R.id.driver_list);
        driverList.setLayoutManager(new LinearLayoutManager(getContext()));
        reference= FirebaseDatabase.getInstance().getReference().child("Drivers");
        userList = new ArrayList<User>();

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(reference,User.class)
                .build();
        FirebaseRecyclerAdapter<User, DriverViewHolder> adapter
                = new FirebaseRecyclerAdapter<User, DriverViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DriverViewHolder driverViewHolder, int i, @NonNull User user) {
                reference.addValueEventListener(new ValueEventListener() {
                    String id = getRef(i).getKey();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(id))
                        {
                            User u = snapshot.child(id).getValue(User.class);

                                driverViewHolder.driverName.setText(u.getFirstName()+" "+u.getLastName());
                                driverViewHolder.driverStatus.setText(user.getStatus());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


            @NonNull
            @Override
            public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.view_driver_display_layout,parent,false);
                DriverViewHolder viewHolder = new DriverViewHolder(view);
                return viewHolder;
            }
        };

        driverList.setAdapter(adapter);
        adapter.startListening();
    }
    public class DriverViewHolder extends RecyclerView.ViewHolder
    {
        TextView driverName,driverStatus;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName=itemView.findViewById(R.id.driver_status__name);
            driverStatus=itemView.findViewById(R.id.driver_status_status);
        }


    }
}
