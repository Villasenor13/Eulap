package com.example.eulap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TimeInTimeOut extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_in_time_out);


        CardView fingertimein = findViewById(R.id.fingertimein);
        fingertimein.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TimeInTimeOut.this, FingerPrint.class);
                startActivity(intent);
            }
        });

        CardView fingertimeout = findViewById(R.id.fingertimeout);
        fingertimeout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TimeInTimeOut.this, fingerPrintTimeOut.class);
                startActivity(intent);
            }
        });


    }
}