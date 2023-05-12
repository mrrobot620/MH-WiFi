package com.mrrobot.mh_wifi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String PREFERENCES_FILE_NAME = "MyAppPreferences";
        SharedPreferences settingsfile = getSharedPreferences(PREFERENCES_FILE_NAME, 0);

        Intent intent = getIntent();
        int intValue = intent.getIntExtra("int", 0);
        Log.d("this", "value of int" + intValue);

        if (intValue != 0) {
            SharedPreferences.Editor myeditor = settingsfile.edit();
            myeditor.putInt("intValue1", intValue);
            myeditor.apply();
        }
        SharedPreferences mysettings = getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        int intValue1 = mysettings.getInt("intValue1", 0);
        Log.d("This is the value of ", "intValue1 = " + intValue1);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.wbtn);


        findViewById(R.id.progressBar).setVisibility(View.GONE);

        run();
//        getMac();

// change this to add more sites in the app
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intValue1 == 0) {
                    Toast.makeText(getApplicationContext(), "Please Select the Site First", Toast.LENGTH_SHORT).show();
                } else if (intValue1 == 1) {
                    rigelConnect("YAKUBPUR-MH-2", "YKB-WiFi-Password");
                    Toast.makeText(getApplicationContext(), "Connecting to YKB Rigel Network in 5 seconds", Toast.LENGTH_SHORT).show();
                } else if (intValue1 == 2) {
                    rigelConnect("Frk-Wifi-Identity", "Frk-WiFi-Password");
                    Toast.makeText(getApplicationContext(), "Connecting to FRK Rigel Network in 5 seconds", Toast.LENGTH_SHORT).show();
                } else if (intValue1 == 3) {
                    rigelConnect("Snapka Identity", "Sanpka WiFi password");
                    Toast.makeText(getApplicationContext(), "Connecting to Sanpka Rigel Network in 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button setting2 = findViewById(R.id.setting);
        setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPasswordDialog();
            }
        });
    }
    private void showPasswordDialog() {
        AlertDialog.Builder pswdDialog = new AlertDialog.Builder(MainActivity.this);
        pswdDialog.setTitle("Enter Password");

        final EditText input = new EditText(MainActivity.this);
        pswdDialog.setView(input);

        pswdDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String password = input.getText().toString();
                if (password.equals("admin")) {
                    Intent intent = new Intent(MainActivity.this, wifiSetup.class);
                    startActivity(intent);
                    dialogInterface.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pswdDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        pswdDialog.show();
    }

    public void rigelConnect(String networkIdentity, String networkPasskey) {
        WifiEnterpriseConfig enterpriseConfig = new WifiEnterpriseConfig();
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"Rigel\"";
        config.priority = 1;
//        Log.d("this is" , "network id = " +networkIdentity);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
        enterpriseConfig.setIdentity(networkIdentity);
        enterpriseConfig.setPassword(networkPasskey);
        enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);
        enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAPV2);
        config.enterpriseConfig = enterpriseConfig;
        WifiManager myWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int id = myWifiManager.addNetwork(config);
        Log.d("addNetwork", "# addNetwork returned " + id);
        myWifiManager.enableNetwork(id, true);
        ImageView wifiImg = findViewById(R.id.wifiImg);
        DrawableCompat.setTint(wifiImg.getDrawable(), ContextCompat.getColor(MainActivity.this, R.color.green));
    }


    public void changeColor() {
        ImageView wifiImg = findViewById(R.id.wifiImg);


        if (isOnline() == true){
            DrawableCompat.setTint(wifiImg.getDrawable(), ContextCompat.getColor(MainActivity.this, R.color.green));
            Toast.makeText(getApplicationContext(), "Wifi is Already Connected", Toast.LENGTH_SHORT).show();
        }
        else if (isOnline() == false) {
            DrawableCompat.setTint(wifiImg.getDrawable(), ContextCompat.getColor(MainActivity.this, R.color.red));
        }
        //       Toast.makeText(getApplicationContext(), "Connected , Let's Get to Work", Toast.LENGTH_SHORT).show();
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public void run() {
        int testByte = 1;
        while (testByte == 0) ;
        changeColor();
    }

    public void getMac(){
        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(MainActivity.this.WIFI_SERVICE);
        WifiInfo wInfo = wifimanager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
//        Toast.makeText(getApplicationContext(), macAddress, Toast.LENGTH_SHORT).show();
        if (macAddress.startsWith("30:95:e3")){
        }
        else{
//            Intent intent = new Intent(MainActivity.this, error.class);
            startActivity(new Intent(MainActivity.this , error.class));
            finishAffinity();
        }
    }
}


