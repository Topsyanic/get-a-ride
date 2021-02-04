package com.example.get_a_ridemobileportal.driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.get_a_ridemobileportal.R;
import com.example.get_a_ridemobileportal.models.Booking;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateStatusFragment extends Fragment {
Spinner status;
Button updateButton;
View v;
DatabaseReference reference;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_update_status,container,false);
        status=v.findViewById(R.id.status_spinner);
        updateButton=v.findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap hashMap = new HashMap();
                hashMap.put("status",status.getSelectedItem().toString().trim());
                reference= FirebaseDatabase.getInstance().getReference("Drivers");
                reference.child(DriverHomeActivity.userId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(v.getContext(),"Status Update Successfully",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        return v;

    }

}
