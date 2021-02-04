package com.example.get_a_ridemobileportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.get_a_ridemobileportal.dispatcher.DispatcherHomeActivity;
import com.example.get_a_ridemobileportal.driver.DriverHomeActivity;
import com.example.get_a_ridemobileportal.models.User;
import com.example.get_a_ridemobileportal.rider.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewRegister;
    private EditText mEmail;
    private EditText mPassword;
    private Button loginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewRegister=findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(this);
        mEmail=findViewById(R.id.loginEmailText);
        mPassword=findViewById(R.id.loginPasswordText);
        loginBtn=findViewById(R.id.loginBtn);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.textViewRegister:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.loginBtn:
                login();
                break;
        }
    }
    private void login()
    {
        String email=mEmail.getText().toString().trim();
        String password=mPassword.getText().toString().trim();


        if(email.isEmpty())
        {
            mEmail.setError("Email cannot be blank");
            mEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            mPassword.setError("Password cannot be blank");
            mPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mPassword.setError("Please provide  a valid email address");
            mPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            mPassword.setError("Password must be at least 6 characters long");
            mPassword.requestFocus();
            return;

        }

        final ArrayList<User> list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){

                   String userId= fAuth.getCurrentUser().getUid();
                   final String[] userRole = new String[1];
                   final String[] username = new String[1];
                   final String[] dbEmail = new String[1];
                   final String[] dbId = new String[1];
                   DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                   reference.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           list.clear();
                           for(DataSnapshot tempSnapshot : snapshot.getChildren() ) {
                               User user = tempSnapshot.getValue(User.class);
                               System.out.println(user.getEmail());
                               if (user.getEmail().equalsIgnoreCase(email)){
                                   userRole[0] = user.getUserRole();
                                   username[0] =user.getFirstName()+" "+user.getLastName();
                                   dbEmail[0] =user.getEmail();
                                   dbId[0]=user.getId();
                                   System.out.println(userRole[0]);

                               }
                           }
                           switch(userRole[0])
                           {
                               case "rider":{
                               Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                               progressBar.setVisibility(View.GONE);
                               Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                               intent.putExtra("username",username[0]);
                               intent.putExtra("email",dbEmail[0]);
                               intent.putExtra("userId",userId);
                               startActivity(intent);
                               finish();
                           }
                           break;
                               case "driver":{
                                   Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                                   progressBar.setVisibility(View.GONE);
                                   Intent intent = new Intent(getApplicationContext(), DriverHomeActivity.class);
                                   intent.putExtra("username",username[0]);
                                   intent.putExtra("email",dbEmail[0]);
                                   intent.putExtra("id",dbId[0]);
                                   startActivity(intent);
                                   finish();
                               }
                               break;
                               case "dispatcher":{
                                   Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                                   progressBar.setVisibility(View.GONE);
                                   Intent intent = new Intent(getApplicationContext(), DispatcherHomeActivity.class);
                                   intent.putExtra("username",username[0]);
                                   intent.putExtra("email",dbEmail[0]);
                                   startActivity(intent);
                                   finish();
                               }
                               break;
                               default: startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                               break;
                           }


                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });



               }else{
                   Toast.makeText(LoginActivity.this,"Log in Failed",Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);
               }
            }
        });
    }
}