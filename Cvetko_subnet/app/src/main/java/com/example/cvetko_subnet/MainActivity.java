package com.example.cvetko_subnet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        EditText ipadr = findViewById(R.id.editTextIP);
        EditText subnet = findViewById(R.id.editTextSubnet);

        TextView ip = findViewById(R.id.ipAdresa);
        TextView sub = findViewById(R.id.subnetMaska);
        TextView wild = findViewById(R.id.wildcard);
        TextView netid = findViewById(R.id.netID);
        TextView minh = findViewById(R.id.rasponHost);
        TextView maxh = findViewById(R.id.rasponHost2);
        TextView brod = findViewById(R.id.broadcastAdr);
        TextView ukbr = findViewById(R.id.ukBrHost);
        TextView iskh = findViewById(R.id.brIskHost);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                if(!ipadr.getText().toString().equals("") && !subnet.getText().toString().equals("")){
                    String IP = ipadr.getText().toString();
                    String Subnet = subnet.getText().toString();

                    if(Subnet.charAt(0)=='/'){
                        Subnet = Subnet.replace("/","");
                        int x = 0;
                        try {
                            x = Integer.parseInt(Subnet);
                        } catch (NumberFormatException nfe) {
                        }
                        switch (x){
                            case 1:
                                Subnet = "128.0.0.0";
                                break;
                            case 2:
                                Subnet = "192.0.0.0";
                                break;
                            case 3:
                                Subnet = "224.0.0.0";
                                break;
                            case 4:
                                Subnet = "240.0.0.0";
                                break;
                            case 5:
                                Subnet = "248.0.0.0";
                                break;
                            case 6:
                                Subnet = "252.0.0.0";
                                break;
                            case 7:
                                Subnet = "254.0.0.0";
                                break;
                            case 8:
                                Subnet = "255.0.0.0";
                                break;
                            case 9:
                                Subnet = "255.128.0.0";
                                break;
                            case 10:
                                Subnet = "255.192.0.0";
                                break;
                            case 11:
                                Subnet = "255.224.0.0";
                                break;
                            case 12:
                                Subnet = "255.240.0.0";
                                break;
                            case 13:
                                Subnet = "255.248.0.0";
                                break;
                            case 14:
                                Subnet = "255.252.0.0";
                                break;
                            case 15:
                                Subnet = "255.254.0.0";
                                break;
                            case 16:
                                Subnet = "255.255.0.0";
                                break;
                            case 17:
                                Subnet = "255.255.128.0";
                                break;
                            case 18:
                                Subnet = "255.255.192.0";
                                break;
                            case 19:
                                Subnet = "255.255.224.0";
                                break;
                            case 20:
                                Subnet = "255.255.240.0";
                                break;
                            case 21:
                                Subnet = "255.255.248.0";
                                break;
                            case 22:
                                Subnet = "255.255.252.0";
                                break;
                            case 23:
                                Subnet = "255.255.254.0";
                                break;
                            case 24:
                                Subnet = "255.255.255.0";
                                break;
                            case 25:
                                Subnet = "255.255.255.128";
                                break;
                            case 26:
                                Subnet = "255.255.255.192";
                                break;
                            case 27:
                                Subnet = "255.255.255.224";
                                break;
                            case 28:
                                Subnet = "255.255.255.240";
                                break;
                            case 29:
                                Subnet = "255.255.255.248";
                                break;
                            case 30:
                                Subnet = "255.255.255.252";
                                break;
                            case 31:
                                Subnet = "255.255.255.254";
                                break;
                            case 32:
                                Subnet = "255.255.255.255";
                                break;
                        }
                    }

                    String[] IP1 = IP.split("\\.");
                    String[] Subnet1 = Subnet.split("\\.");

                    int[] IP2={0,0,0,0};
                    int[] Subnet2={0,0,0,0};

                    for(int i=0;i<4;i++) {
                        try {
                            IP2[i] = Integer.parseInt(IP1[i].toString());
                            Subnet2[i] = Integer.parseInt(Subnet1[i].toString());
                        } catch (NumberFormatException nfe) {

                        }
                    }
                    int[] NETID  = {IP2[0]&Subnet2[0],IP2[1]&Subnet2[1],IP2[2]&Subnet2[2],IP2[3]&Subnet2[3],};
                    int[] br1={Subnet2[0],Subnet2[1],Subnet2[2],Subnet2[3]};
                    int[] br2={Subnet2[0],Subnet2[1],Subnet2[2],Subnet2[3]};
                    for(int i=0;i<4;i++){
                        br1[i]=256-br1[i];
                        br2[i]=255-br2[i];
                    }
                    int briskh = br1[0]*br1[1]*br1[2]*br1[3];
                    int[] broadcast = {NETID[0]+br2[0],NETID[1]+br2[1],NETID[2]+br2[2],NETID[3]+br2[3]};

                    String klasa = "";
                    if (IP2[0]<128){
                        klasa = " (Klasa A)";
                    }
                    else if (IP2[0]<192){
                        klasa = " (Klasa B)";
                    }
                    else if (IP2[0]<224){
                        klasa = " (Klasa C)";
                    }

                    ip.setText("IP adresa: "+IP+klasa);
                    sub.setText("Subnet maska: "+Subnet);
                    wild.setText("Wildcard: "+br2[0]+"."+br2[1]+"."+br2[2]+"."+br2[3]);
                    netid.setText("Net ID: "+NETID[0]+"."+NETID[1]+"."+NETID[2]+"."+NETID[3]);
                    minh.setText("Min. host: "+NETID[0]+"."+NETID[1]+"."+NETID[2]+"."+(NETID[3]+1));
                    maxh.setText("Max. host: "+broadcast[0]+"."+broadcast[1]+"."+broadcast[2]+"."+(broadcast[3]-1));
                    brod.setText("Broadcast adresa: "+broadcast[0]+"."+broadcast[1]+"."+broadcast[2]+"."+broadcast[3]);
                    ukbr.setText("Ukupan broj hostova: "+(briskh));
                    iskh.setText("Broj iskoristivih hostova: "+(briskh-2));

                }
            }
        });
    }
}