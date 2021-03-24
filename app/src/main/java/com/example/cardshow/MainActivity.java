package com.example.cardshow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListAdapter mAdapter;
    private Button btn_swap;
    private boolean bool_new = false;
    private static final String KEY_INT = "Seitennummer";
    private int pgNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.rv_main);
        btn_swap = findViewById(R.id.btn_swap);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        mAdapter = new ListAdapter(generateContent());
        list.setAdapter(mAdapter);

        mAdapter.setOnListItemClickListener(new ListAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(MagicCard item) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_MAGIC_KEY, item);
                startActivity(i);
            }
        });

        btn_swap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn_swap.setEnabled(false);
                /*if(bool_new){
                    mAdapter.swapData(generateContent());
                    bool_new = false;
                } else {*/
                    //mAdapter.swapData(generateContentNew());
                    WebRunnable webRunnable = new WebRunnable("https://api.magicthegathering.io/v1/cards?page="+ pgNum);
                    new Thread(webRunnable).start();
                    bool_new = true;
                /*}*/
                pgNum++;

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(KEY_TEXT, outView.getText().toString());
        outState.putInt(KEY_INT, pgNum);
    }

    private List<MagicCard> generateContent() {
        List<MagicCard> data = new LinkedList<>();
        data.add(new MagicCard("Auf den Button drücken", "", "Uncommon", "blue"));
        data.add(new MagicCard("um Inhalte zu laden", "", "Common", "red"));
        return data;
    }

    /*private List<MagicCard> generateContentNew() {
        List<MagicCard> data = new LinkedList<>();
        data.add(new MagicCard("DASDA", "Wasser", "Rare", "yellow"));
        data.add(new MagicCard("Dudli", "Earth", "Grey", "brown"));
        return data;
    }*/

    class WebRunnable implements Runnable {

        URL url;
        List<MagicCard> list = new LinkedList<>();


        WebRunnable(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");





                if (scanner.hasNext()) {

                    JSONObject root = new JSONObject(scanner.next());
                    JSONArray results = root.getJSONArray("cards");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);

                        MagicCard mc = new MagicCard(result);
                        list.add(mc);


                    }

                    mainHandler.post(() -> mAdapter.swapData(list));
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> btn_swap.setEnabled(true));
        }
    }
}