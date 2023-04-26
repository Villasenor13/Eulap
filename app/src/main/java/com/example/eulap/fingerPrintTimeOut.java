package com.example.eulap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class fingerPrintTimeOut extends AppCompatActivity
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_time_out);

        CardView fingerauthentication = findViewById(R.id.fingerauthentication);
        fingerauthentication.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Map<String,Object> user = new HashMap<>();
                user.put("Time", FieldValue.serverTimestamp());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                String dateString = simpleDateFormat.format(calendar.getTime());

                CollectionReference collectionReference = db.collection("eulap").document("Time_out").collection(dateString);

                collectionReference
                        .add(user);
                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Fingerprint Attendance")
                        .setDescription("Scan Fingerprint to Time out")
                        .setDeviceCredentialAllowed(true)
                        // Enable device credential fallback
                        .build();

                BiometricPrompt biometricPrompt = new BiometricPrompt(fingerPrintTimeOut.this,
                        new BiometricPrompt.AuthenticationCallback()
                        {
                            @Override
                            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result)
                            {
                                // User has successfully authenticated
                                // Implement your logic here
                                Intent intent = new Intent(fingerPrintTimeOut.this, SuccessfulPrompt.class);
                                startActivity(intent);


                                Toast.makeText(fingerPrintTimeOut.this, "Timed Out Sucessfully", Toast.LENGTH_LONG).show();
                                Date timeIn = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                                String timeIn12hr = sdf.format(timeIn);
                                Toast.makeText((Context) fingerPrintTimeOut.this, timeIn12hr.toString(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onAuthenticationFailed()
                            {
                                // Authentication failed
                                // Handle the error accordingly
                                Toast.makeText(fingerPrintTimeOut.this, "Biometric data not verified. Please try again or use device credential.", Toast.LENGTH_LONG).show();

                            }
                        });

                biometricPrompt.authenticate(promptInfo);
            }
        });

        Button ReturnHome = findViewById(R.id.ReturnHome);
        ReturnHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finishAffinity();
            }
        });


    }


    private BiometricPrompt getPrompt()
    {
        Executor executor= ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                notifyuser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                notifyuser("Time Out Sucess");

            }

            @Override
            public void onAuthenticationFailed()
            {
                super.onAuthenticationFailed();
                notifyuser("Authentication Failed");
            }
        };
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
        return biometricPrompt;
    }

    private void notifyuser(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}