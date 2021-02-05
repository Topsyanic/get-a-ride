package com.example.get_a_ridemobileportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.get_a_ridemobileportal.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView firstNameText,lastNameText,emailText,passwordText,confirmPasswordText;
    private ProgressBar progressBar;
    private Button registerBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firstNameText=findViewById(R.id.firstNameText);
        lastNameText=findViewById(R.id.lastNameText);
        emailText=findViewById(R.id.emailText);
        passwordText=findViewById(R.id.passwordText);
        confirmPasswordText=findViewById(R.id.passwordText2);
        registerBtn=findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
        progressBar=findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registerBtn:
                registerUser();
                break;
        }
    }
    public void registerUser()
    {

        String firstName = firstNameText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();
        String email=emailText.getText().toString().trim();
        String password=passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();

        if(firstName.isEmpty())
        {
            firstNameText.setError("First Name cannot be blank");
            firstNameText.requestFocus();
            return;
        }
        if(lastName.isEmpty())
        {
            lastNameText.setError("Last Name cannot be blank");
            lastNameText.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            emailText.setError("Email cannot be blank");
            emailText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordText.setError("Password cannot be blank");
            passwordText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordText.setError("Password cannot be blank");
            passwordText.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty())
        {
            confirmPasswordText.setError("Confirm Password cannot be blank");
            confirmPasswordText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailText.setError("Please provide  a valid email address");
            emailText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            passwordText.setError("Password must be at least 6 characters long");
            passwordText.requestFocus();
            return;

        }
        if(!password.equalsIgnoreCase(confirmPassword))
        {
            confirmPasswordText.setError("Password is not matching");
            confirmPasswordText.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())//if user is created user in firebase auth
                {
                    User user = new User(firstName,lastName,email,"rider","none");
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())//if user added to db
                            {
                                Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                            else// user not added to db
                            {
                                Toast.makeText(RegisterActivity.this,"User NOT registered successfully",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else//user not created in firebase Auth
                {
                    Toast.makeText(RegisterActivity.this,"User NOT registered successfully",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}