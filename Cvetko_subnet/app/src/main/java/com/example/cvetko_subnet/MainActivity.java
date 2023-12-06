package com.example.cvetko_subnet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText ipAddressEditText, subnetMaskEditText;
    private Button calculateButton;
    private TextView resultTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipAddressEditText = findViewById(R.id.editTextIpAddress);
        subnetMaskEditText = findViewById(R.id.editTextSubnetMask);
        calculateButton = findViewById(R.id.buttonCalculate);
        resultTextView = findViewById(R.id.textViewResult);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateAndDisplay();
            }
        });
    }

    private void calculateAndDisplay() {
        String ipAddress = ipAddressEditText.getText().toString().trim();
        String subnetMask = subnetMaskEditText.getText().toString().trim();
        ;

        if (isValidIpAddress(ipAddress) && isValidSubnetMask(subnetMask)) {
            // Perform calculations and display results in the resultTextView
            // You can implement the logic for calculations here

            // For example:
            String wildcard = calculateWildcard(subnetMask);
            String netId = calculateNetId(ipAddress, subnetMask);
            String broadcast = calculateBroadcast(netId, wildcard);
            String usableHostRange = calculateUsableHostRange(netId, broadcast);

            // Display the results
            String result = "Wildcard: " + wildcard + "\n"
                    + "Net ID: " + netId + "\n"
                    + "Broadcast: " + broadcast + "\n"
                    + "Upotrebljivi raspon IP-a hosta:" + usableHostRange +"\n";
                    // Add more results as needed...
                    ;

            resultTextView.setText(result);
        } else {
            resultTextView.setText("Invalid IP address or subnet mask");
        }
    }

    private boolean isValidIpAddress(String ipAddress) {
        // Implement your IP address validation logic
        // For simplicity, a basic validation is provided here
        return ipAddress.matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
    }

    private boolean isValidSubnetMask(String subnetMask) {
        // Implement your subnet mask validation logic
        // For simplicity, a basic validation is provided here
        return subnetMask.matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
    }

    private String calculateWildcard(String subnetMask) {
        // Calculate the wildcard mask (inverse of subnet mask)
        String[] subnetMaskOctets = subnetMask.split("\\.");
        StringBuilder wildcardMask = new StringBuilder();
        for (String octet : subnetMaskOctets) {
            int value = Integer.parseInt(octet);
            wildcardMask.append(255 - value).append(".");
        }
        return wildcardMask.substring(0, wildcardMask.length() - 1);
    }

    private String calculateNetId(String ipAddress, String subnetMask) {
        // Calculate the network ID (AND operation between IP and subnet)
        String[] ipOctets = ipAddress.split("\\.");
        String[] subnetMaskOctets = subnetMask.split("\\.");
        StringBuilder netId = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int ipValue = Integer.parseInt(ipOctets[i]);
            int subnetValue = Integer.parseInt(subnetMaskOctets[i]);
            netId.append(ipValue & subnetValue).append(".");
        }
        return netId.substring(0, netId.length() - 1);
    }

    private String calculateBroadcast(String netId, String wildcard) {
        // Calculate the broadcast address
        String[] netIdOctets = netId.split("\\.");
        String[] wildcardOctets = wildcard.split("\\.");
        StringBuilder broadcast = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int netIdValue = Integer.parseInt(netIdOctets[i]);
            int wildcardValue = Integer.parseInt(wildcardOctets[i]);
            broadcast.append((netIdValue | wildcardValue) & 255).append(".");
        }
        return broadcast.substring(0, broadcast.length() - 1);
    }
    private String calculateUsableHostRange(String netId, String broadcast) {
        // Calculate usable host IP range (exclude network address and broadcast address)
        String[] netIdOctets = netId.split("\\.");
        String[] broadcastOctets = broadcast.split("\\.");

        StringBuilder startIp = new StringBuilder();
        StringBuilder endIp = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                // Increment the last octet for the start IP
                startIp.append(Integer.parseInt(netIdOctets[i]) + 1).append(".");
                // Decrement the last octet for the end IP
                endIp.append(Integer.parseInt(broadcastOctets[i]) - 1).append(".");
            } else {
                startIp.append(netIdOctets[i]).append(".");
                endIp.append(broadcastOctets[i]).append(".");
            }
        }

        return startIp.substring(0, startIp.length() - 1) + " - " + endIp.substring(0, endIp.length() - 1);
    }

}