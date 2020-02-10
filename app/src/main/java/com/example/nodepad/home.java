package com.example.nodepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {
    TextView phone,username;
    DatabaseReference databaseReference;
    Button loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phone=findViewById(R.id.phoneReadId);
        username=findViewById(R.id.userReadId);
        loadButton=findViewById(R.id.loadDataId);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Details").child("GdEGgYOuembAtjj5Ln1yaOjQsTL2");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String Username=dataSnapshot.child("Username").getValue().toString();
                            String Phone=dataSnapshot.child("Phone").getValue().toString();
                            phone.setText(Phone);
                            username.setText(Username);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),databaseError.toException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch(Exception ex){
                    System.out.println("Error"+ex);

                }


            }
        });

    }
}
