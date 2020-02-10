package com.example.nodepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.spark.submitbutton.SubmitButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView register;
    SubmitButton submitButton;
    EditText email,password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=findViewById(R.id.registerId);
        email=findViewById(R.id.emailId);
        password=findViewById(R.id.passwordId);
        submitButton=findViewById(R.id.sparkLoginButtonId);
        submitButton.setOnClickListener(this);
        register.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(v.getId()==R.id.registerId){
            Intent intent=new Intent(getApplicationContext(),edittext.class);
            startActivity(intent);
            finish();

        }
        if(v.getId()==R.id.sparkLoginButtonId){
           Thread thread=new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.sleep(3600);
                   } catch (InterruptedException e) {
                       System.out.println(e);
                   }
               }
           });
           thread.start();


            String Email,Password;
            Email=email.getText().toString().trim();
            Password=password.getText().toString().trim();
            if(Email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                email.setError("Enter your valid email");
                email.requestFocus();
                return;
            }
            if(Password.length()<6) {
                password.setError("Enter password at least 6 charecter");
                password.requestFocus();
                return;
            }


            if(null!=networkInfo){

                if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI |networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){

                    firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(MainActivity.this,home.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(MainActivity.this,"Login Sucessed!",Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(MainActivity.this,"Login not Sucessed!",Toast.LENGTH_SHORT).show();

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


    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
