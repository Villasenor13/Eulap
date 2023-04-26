package com.example.eulap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    EditText firstname, middlename, lastname, age, contact, address, workposition, birthday;
   FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        Button home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });




        firstname = findViewById(R.id.firstname);
        middlename = findViewById(R.id.middlename);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact);
        address = findViewById(R.id.address);
        workposition = findViewById(R.id.workposition);
        birthday = findViewById(R.id.birthday);

        Button signin = findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Firstname = firstname.getText().toString();
                String Middlename = middlename.getText().toString();
                String Lastname = lastname.getText().toString();
                String Age = age.getText().toString();
                String Contact = contact.getText().toString();
                String Address = address.getText().toString();
                String Workposition = workposition.getText().toString();
                String Birthday = birthday.getText().toString();
                Map<String,Object> user = new HashMap<>();
                user.put("Firstname", Firstname);
                user.put("Middlename", Middlename);
                user.put("Lastname", Lastname);
                user.put("Age", Age);
                user.put("Contact", Contact);
                user.put("Address", Address);
                user.put("Work position", Workposition);
                user.put("Birthday", Birthday);

                db.collection("eulap_register")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                        {
                            @Override
                            public void onSuccess(DocumentReference documentReference)
                            {
                                Toast.makeText(CreateAccount.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(CreateAccount.this, "Failed", Toast.LENGTH_SHORT).show();

                            }
                        });
            }

        });

    }


}