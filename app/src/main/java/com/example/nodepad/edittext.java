package com.example.nodepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class edittext extends AppCompatActivity implements View.OnClickListener {
    TextView login;
    EditText email,password,username,phone;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    SubmitButton submit;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registation);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference =FirebaseDatabase.getInstance().getReference().child("Details");

        login = findViewById(R.id.loginId);
        email=findViewById(R.id.emailId);
        password=findViewById(R.id.passwordId);
        username=findViewById(R.id.userId);
        phone=findViewById(R.id.numberId);
        submit=findViewById(R.id.sparkRegisterButtonId);
        login.setOnClickListener(this);
        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();

        if (v.getId() == R.id.loginId) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.sparkRegisterButtonId){



            final String Email,Password,Username,Phone;
            Email=email.getText().toString().trim();
            Password=password.getText().toString().trim();
            Username=username.getText().toString().trim();
            Phone=phone.getText().toString().trim();

            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5200);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            });
            thread.start();

            if(Email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                email.setError("Enter your valid email");
                email.requestFocus();
                return;
            }
            if(Password.length()<6){
                password.setError("Enter password at least 6 charecter");
                password.requestFocus();
                return;
            }
            if(Username.isEmpty()){
                username.setError("Enter your name");
                return;
            }

           if(null!=networkInfo){

               if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI |networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                   firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Usename").setValue(Username);databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Phone").setValue(Phone)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful()){
                                                   Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                   startActivity(intent);
                                                   Toast.makeText(getApplicationContext(),"User is  create sucessfully!",Toast.LENGTH_SHORT).show();
                                               }
                                               else {

                                                   Toast.makeText(getApplicationContext(),"User is not created!",Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       });
                           }
                           else {

                               Toast.makeText(getApplicationContext(),"User is not created!",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

               }
           }


            else{
              Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
            }

        }


        }
}
