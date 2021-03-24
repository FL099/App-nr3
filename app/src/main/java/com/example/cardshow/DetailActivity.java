package com.example.cardshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MAGIC_KEY = "Card";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MagicCard p = getIntent().getParcelableExtra(EXTRA_MAGIC_KEY);
        Toast.makeText(DetailActivity.this, p.getName(), Toast.LENGTH_SHORT).show();
    }
}