package com.mrrobot.mh_wifi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class wifiSetup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    Spinner spin , spin1;

    String[] north = {"North", "YKB", "FRK", "SANPKA" , "JKS" , };
    String[] east = { "East" , "Ulberia", "Patna", "Haringhata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_setup);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, north);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, east);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(aa1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //            Add code here for East
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), east[position], Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        Log.d(TAG, "onItemSelected: North is selected ");
                        break;
                    case 1:
                        setYKB(); // Change this when you made code for respective site
                        Log.d(TAG, "onItemSelected: YKB is selected ");
                        Log.d(TAG, "Shared YKB Credential: YKB is selected ");
//               backtoMain();
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void backtoMain() {
        Intent intent1 = new Intent(wifiSetup.this, MainActivity.class);
        startActivity(intent1);
    }

    public void setYKB() {
        Intent myIntent = new Intent(wifiSetup.this, MainActivity.class);
        myIntent.putExtra("int", 1);
        startActivity(myIntent);
    }

    public void setFRK() {
        Intent myIntent = new Intent(wifiSetup.this, MainActivity.class);
        myIntent.putExtra("int", 2);
        startActivity(myIntent);
    }

    public void setSanpka() {
        Intent myIntent = new Intent(wifiSetup.this, MainActivity.class);
        myIntent.putExtra("int", 3);
        startActivity(myIntent);
    }
    // Add code here for North
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg0, int position, long id) {
        Toast.makeText(getApplicationContext(), north[position], Toast.LENGTH_SHORT).show();
        switch(position) {
            case 0:
                Log.d(TAG, "onItemSelected: North is selected ");
                break;
            case 1:
                setYKB();
                Log.d(TAG, "onItemSelected: YKB is selected ");
                Log.d(TAG, "Shared YKB Credential: YKB is selected ");
//               backtoMain();
                break;
            case 2:
                setFRK();
                Log.d(TAG, "onItemSelected: FRK is selected ");
//              backtoMain();
                break;
            case 3:
                setSanpka();
                Log.d(TAG, "onItemSelected: SANPKA is selected ");
//                backtoMain();
                break;
            case 4:

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}





