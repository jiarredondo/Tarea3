package com.example.tarea3;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarea3.Fragments.Frtipocambio;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
   public StringBuilder descarga = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = "http://www.apilayer.net/api/live?access_key=bd179b8245761835672c1433a23e904b&currencies=USD,MXN,CRC,BRL,NGN,QAR&format=1";
        if (hasInternetAccess()){
            DescargaCodigo(url);
            //TextView txv = (TextView) findViewById(R.id.json);
            //txv.setText(descarga.toString());

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            Frtipocambio fragment3 = new Frtipocambio();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Carga", descarga.toString());
            fragment3.setArguments(bundle);
            ft.replace(android.R.id.content, fragment3);
            ft.addToBackStack(null); //Add fragment in back stack
            ft.commit();

        }
        else{
            Toast.makeText(getApplicationContext(), "No hay Internet", Toast.LENGTH_SHORT).show();

        }

    }

    public boolean hasInternetAccess()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    public String DescargaCodigo (String url){
        //StringBuilder descarga = new StringBuilder();
    HttpURLConnection urlConnection = null;
    try{
        URL urljson = new URL(url);
        urlConnection=(HttpURLConnection) urljson.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null){
            descarga.append(line);
        }
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        assert  urlConnection != null;
        urlConnection.disconnect();
    }
        return descarga.toString();

    }


}
