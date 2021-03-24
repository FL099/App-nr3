package com.example.cardshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MAGIC_KEY = "Card";
    private TextView name;
    private TextView typ;
    private TextView rarity;
    private TextView text;
    private TextView set;
    private TextView flavour;
    private Button btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = findViewById(R.id.name);
        typ = findViewById(R.id.type);
        rarity = findViewById(R.id.rarity);
        text = findViewById(R.id.text);
        set = findViewById(R.id.set);
        flavour = findViewById(R.id.flavour);
        btn_show = findViewById(R.id.btn_show);

        MagicCard p = getIntent().getParcelableExtra(EXTRA_MAGIC_KEY);

        name.setText(p.getName());
        name.setBackgroundResource(toCol(p.getRarity()));
        typ.setText(p.getType());
        typ.setBackgroundResource(R.color.grey);
        rarity.setText(p.getRarity());
        rarity.setBackgroundResource(toCol(p.getRarity()));
        text.setText(p.getText());
        set.setText(p.getSet());
        flavour.setText(p.getFlavor());


        btn_show.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(DetailActivity.this, "Artist: " + p.getArtist(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private int toCol(String rarity){
        switch (rarity.toUpperCase()){
            case "RARE":
                return R.color.rare;
            case "UNCOMMON":
                return R.color.uncommon;
            case "COMMON":
                return R.color.common;
            default:
                return R.color.grey;
        }
    }
}