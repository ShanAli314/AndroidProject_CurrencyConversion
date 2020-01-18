package com.pucit.proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Person;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pucit.proj.DataModel.Country;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static boolean isBitCurrency=true;
    static boolean isEthCurrency=true;
    List<Country> countryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ACTION BAR SETTING======================================================
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.applogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setTitle("  " + "Converter");


        // LIst for country recycler View===========================================
        countryList= Country.getCountryList();


        // RecyclerView=============================================================
        RecyclerView rv  = findViewById(R.id.c_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(llm);
        rv.setAdapter(new C_Adapter(countryList,this));



        // on BitCoin-Etherum Button Click==================================================
        final Button bitcoin=findViewById(R.id.bitcoinbtn);
        final Button etherumbtn=findViewById(R.id.etherumbtn);
        final TextView ethView=findViewById(R.id.local_currency_view);
        final TextView bitView=findViewById(R.id.bitcoin_view);
        final TextView ethViewL=findViewById(R.id.local_currency_value);
        final TextView bitViewL=findViewById(R.id.currency_value);

        bitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBitCurrency=!isBitCurrency;
                if(isBitCurrency){
                    bitView.setVisibility(View.VISIBLE);
                    bitViewL.setVisibility(View.VISIBLE);
                    bitcoin.setBackgroundColor(getResources().getColor(R.color.red));
                }
                else {
                    bitViewL.setVisibility(View.INVISIBLE);
                    bitView.setVisibility(View.INVISIBLE);
                    bitcoin.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        etherumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    isEthCurrency=!isEthCurrency;
                    if(isEthCurrency){
                        ethView.setVisibility(View.VISIBLE);
                        ethViewL.setVisibility(View.VISIBLE);
                        etherumbtn.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    else {

                        ethView.setVisibility(View.INVISIBLE);
                        ethViewL.setVisibility(View.INVISIBLE);
                        etherumbtn.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                    }
            }
        });

    }
    public  void getBitCoinValue(final String cCode){

        final TextView textView =findViewById(R.id.bitcoin_view);
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request= new Request.Builder().
                        url("https://api.coindesk.com/v1/bpi/currentprice/"+cCode+".json").build();
                Response response= null;
                try {
                    response = client.newCall(request).execute();
                    return response.body().string();
                }catch ( IOException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                String jsonData = o.toString();
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(jsonData);
                    JSONObject Jobj2= Jobject.getJSONObject("bpi");
                    JSONObject Jobj3= Jobj2.getJSONObject(cCode);
                    textView.setText(Jobj3.getString("rate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    public  void getEtherumValue(final String cCode){

        final TextView textView =findViewById(R.id.local_currency_view);
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request= new Request.Builder().
                        url("https://api.coinmarketcap.com/v1/ticker/ethereum/").build();
                Response response= null;
                JSONArray jsonArray=null;
                JSONObject jsonObject=null;
                JSONObject object=null;
                try {
                    response = client.newCall(request).execute();
                    jsonArray= new JSONArray(response.body().string().toString());
                    jsonObject = jsonArray.getJSONObject(0);
                    float usd_wrt_eth=Float.parseFloat(jsonObject.getString("price_usd"));


                    Request request1=new Request.Builder().
                            url("http://www.apilayer.net/api/live?access_key=65a52a1f9115ad5379787813d3486516").build();

                    response = client.newCall(request1).execute();
                    object=new JSONObject(response.body().string().toString());
                    jsonObject = object.getJSONObject("quotes");
                    float local_curr=Float.parseFloat(jsonObject.getString("USD"+cCode));
                    float total_value=usd_wrt_eth*local_curr;
                    return String.valueOf(total_value);


                }catch ( IOException e){
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                textView.setText(o.toString());
            }
        }.execute();
    }
}