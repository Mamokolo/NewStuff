package com.example.newstuff;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("playerData");
        System.out.println("change activity "+bundle.get("skill"));
    }
}
