package com.hugeardor.vidit.pfm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static android.support.design.widget.Snackbar.make;

public class MainActivity extends AppCompatActivity {

    TextView share_title , share_name;

    Button fetch_data ;

    ArrayList<String> items = new ArrayList<>() ;
    SpinnerDialog spinnerDialog ;
    Button search ;

    String sharename ;

    ConnectionDetector cd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = new ConnectionDetector(this) ;

        checkIntenetConnection();


            share_title = (TextView) findViewById(R.id.share_name_title);
            share_name = (TextView) findViewById(R.id.share_name);

            fetch_data = (Button) findViewById(R.id.fetch_data);


            initItems();
            spinnerDialog = new SpinnerDialog(MainActivity.this, items, "Select Stock : ");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {

                    share_name.setText(item);
                    sharename = item;

                }
            });

            search = (Button) findViewById(R.id.search);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinnerDialog.showSpinerDialog();
                }
            });







    }

    public void checkIntenetConnection(){

        if( ! (cd.isConnected())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("App Requires Internet Connection").setTitle("Network Permission") ;

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    //open the settings menu


                    Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);


                    if(!cd.isConnected()){
                        checkIntenetConnection();
                    }




                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog

                    finish();
                }
            });



            AlertDialog dialog = builder.create() ;
            dialog.show();






        }
        else
        {
           // Toast.makeText(MainActivity.this , "Network Connected !! " , Toast.LENGTH_SHORT).show();

        }


    }


    private void initItems() {

       items.add("Tata Steel Ltd.") ;
        items.add("Hidalco") ;
        items.add("ICICI Bank") ;
        items.add("Tata Motors") ;
        items.add("Sun Pharma") ;
        items.add("Bharti Airtel") ;
        items.add("Infra Tel") ;
        items.add("TCS") ;
        items.add("Wipro") ;
        items.add("Infosys") ;
        items.add("HCL Tech") ;
        items.add("BPCL") ;
        items.add("M&M") ;
        items.add("Hindustan Unilever") ;
        items.add("State Bank Of India") ;
        items.add("Yes Bank") ;
        items.add("VEDL") ;
        items.add("LUPIN") ;
        items.add("Coal India") ;
        items.add("Kotak Bank") ;
        items.add("Zeel") ;
        items.add("NTPC") ;
        items.add("Asian Paints") ;


    }

    public void fetch_data(View view){

        /*String type="fetch" ;
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type , sharename);*/

        if(share_name.getText().toString().equals("Tata Steel Ltd.")) {

        if(cd.isConnected()) {

            startActivity(new Intent(MainActivity.this, fetch_data.class));
        }
        else {
            Toast.makeText(MainActivity.this , "Connect To Internet !" , Toast.LENGTH_SHORT).show();
        }
    }}


    public void onResume(){
        super.onResume();
    }


    @Override
    public void onBackPressed() {
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
