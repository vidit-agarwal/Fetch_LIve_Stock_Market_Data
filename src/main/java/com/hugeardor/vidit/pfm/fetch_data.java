package com.hugeardor.vidit.pfm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import android.graphics.Color ;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by vidit on 12/10/17.
 */


public class fetch_data extends AppCompatActivity {


    TextView share_name  , share_symbol , share_price , price_time , net_change , percent_change , volume , open_price , prev_close_price , bid , market_capital , p_e , div , face_value , ct;
    ImageView iv ;
    ArrayAdapter<String> adapter ;
    String address = "http://18.216.17.138/get_data.php " ;
    InputStream is = null ;
    String line = null;
    String result = null ;
    String sn , sy , sp , pt , nc , pc , vol , op , pcp , bd , mc , pe , div_y , fv , live, status;

    String rs = "  \u20B9" ;
    ConnectionDetector cd ;
    Calendar cal = Calendar.getInstance() ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        cd = new ConnectionDetector(this) ;

        share_name = (TextView)findViewById(R.id.sn);
        share_symbol = (TextView)findViewById(R.id.sy);
        share_price = (TextView)findViewById(R.id.sp);
        price_time = (TextView)findViewById(R.id.pt);
        net_change = (TextView)findViewById(R.id.nc);
        percent_change = (TextView)findViewById(R.id.pc);
        volume = (TextView)findViewById(R.id.vol);
        open_price = (TextView)findViewById(R.id.op);
        prev_close_price = (TextView)findViewById(R.id.pcp);

        bid = (TextView)findViewById(R.id.bid);
        market_capital = (TextView)findViewById(R.id.mc);
        p_e = (TextView)findViewById(R.id.p_e);
        div = (TextView)findViewById(R.id.dy);
        face_value = (TextView)findViewById(R.id.fv);
        ct = (TextView)findViewById(R.id.live) ;

        iv= (ImageView)findViewById(R.id.nc_image) ;


        StrictMode.setThreadPolicy((new  StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        //retrive

        get_data();

        //set the value

        update() ;









    }
    public void update()
    {
            String percent = "  %" ;
        int hour = cal.get(Calendar.HOUR_OF_DAY) ;
        int minute = cal.get(Calendar.MINUTE) ;



        share_name.setText(sn);
        share_symbol.setText(sy);
        share_price.setText(sp + rs);

        ct.setText(status); // for setting live or end sesion

        price_time.setText(pt);
        net_change.setText(nc + rs);

        if(Float.parseFloat(nc)>=0)
                iv.setImageResource(R.drawable.green_arrow);
        else
                iv.setImageResource(R.drawable.red_arrow);

        if(Float.parseFloat(pc)>=0)

        {
            percent_change.setTextColor(Color.GREEN) ;
            }
        else {
            percent_change.setTextColor(Color.RED) ;
        }
        percent_change.setText(pc + percent);


        volume.setText(vol);
        open_price.setText(op + rs);
        prev_close_price.setText(pcp + rs);
        bid.setText(bd);
        market_capital.setText(mc + rs);


        p_e.setText(pe);
        div.setText(div_y + percent);
        face_value.setText(fv + rs);
    }


    private void get_data()
    {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection)url.openConnection() ;

            con.setRequestMethod("GET");

            is= new BufferedInputStream(con.getInputStream()) ;




        }catch (Exception e){
            e.printStackTrace();
        }

        //read is content into a string

        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;

            StringBuilder sb = new StringBuilder() ;

            while((line = br.readLine()) != null){

                    sb.append(line+"\n") ;

            }
            is.close();
            result = sb.toString() ;



        }catch(Exception e){
            e.printStackTrace();
        }

        // parse json data

        try{

            JSONArray  ja = new JSONArray(result) ;
            JSONObject  jo = null ;


           // for(int i =0 ; i<ja.length() ; i++){
                jo =ja.getJSONObject(0) ;

                sn = jo.getString("Share_Name") ;
                sy = jo.getString("Share_Symbol") ;
                sp = jo.getString("Share_Price") ;

                pt = jo.getString("Price_Time") ;

                nc = jo.getString("Net_Change") ;

                pc = jo.getString("Percent_Change") ;

                vol = jo.getString("Volume") ;

                op = jo.getString("Open_Price") ;

                pcp = jo.getString("Previous_Close_Price") ;

                bd = jo.getString("Bid") ;

                mc = jo.getString("Market_Capital") ;

                pe = jo.getString("PE_Ratio") ;

                div_y = jo.getString("Divident_Yeild") ;

                fv = jo.getString("Face_Value");
            status = jo.getString("status") ;







         //   }


        }catch(Exception e){
            e.printStackTrace();
        }




    }



    public void refresh(View view)
    {
        if(cd.isConnected()) {

            Toast.makeText(fetch_data.this, "Refreshing", Toast.LENGTH_SHORT).show();
            get_data();
            update();
        }
        else
        {
            Toast.makeText(fetch_data.this, "Please Connect With Internet !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }










}

