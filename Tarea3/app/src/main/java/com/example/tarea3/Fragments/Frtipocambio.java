package com.example.tarea3.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarea3.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Frtipocambio extends Fragment {
   public String vContenido="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        //Button btnNext = (Button) view.findViewById(R.id.btn_next);

        try {
            setupUI(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;


    }

    public void setupUI(final View view) throws JSONException {
        final EditText sdolares = (EditText) view.findViewById(R.id.et_dolares);
        final EditText scolones = (EditText) view.findViewById(R.id.et_colones);
        final EditText spesomx = (EditText) view.findViewById(R.id.et_pesos);
        final EditText sreal = (EditText) view.findViewById(R.id.et_brasil);
        final EditText snaira = (EditText) view.findViewById(R.id.et_nigeria);
        final EditText srial = (EditText) view.findViewById(R.id.et_qatar);

        Bundle bundle = getArguments();

        vContenido = bundle.getString("Carga");
        JSONObject response = null;
        response = new JSONObject(vContenido);
        String success = response.getString("success");
        final JSONObject currencies = response.getJSONObject("quotes");

        Button btnNext = (Button) view.findViewById(R.id.btncalcular);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    double pesomx = currencies.getDouble("USDMXN");
                    double colon = currencies.getDouble("USDCRC");
                    double real = currencies.getDouble("USDBRL");//BRASIL
                    double naira = currencies.getDouble("USDNGN");//NIGERIA
                    double rial = currencies.getDouble("USDQAR");//QATAR
                    double dolares = Double.parseDouble(sdolares.getText().toString());

                    double calcol = colon * dolares;
                    String valorcol = String.valueOf(calcol);
                    scolones.setText(valorcol);

                    double calpeso = pesomx * dolares;
                    String valorpeso = String.valueOf(calpeso);
                    spesomx.setText(valorpeso);

                    double calbra = real * dolares;
                    String valorbra = String.valueOf(calbra);
                    sreal.setText(valorbra);

                    double calnigeria = rial * dolares;
                    String valornigeria = String.valueOf(calnigeria);
                    snaira.setText(valornigeria);

                    double calqatar = rial * dolares;
                    String valoqatar = String.valueOf(calqatar);
                    srial.setText(valoqatar);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }


}



