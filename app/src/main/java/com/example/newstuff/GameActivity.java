package com.example.newstuff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private Button relogin,restart,finish;
    private Bundle infoFromMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        infoFromMain = getIntent().getBundleExtra("bundle");
        infoFromMain.remove("loginFlag");
        infoFromMain.putBoolean("loginFlag",false);
        System.out.println(infoFromMain.get("name")+"\n"+infoFromMain.getString("password"));
        relogin = findViewById(R.id.button_relogin);
        restart = findViewById(R.id.button_restartGame);
        finish = findViewById(R.id.button_finishGame);

        relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                intent.putExtra("whichFragment",1).putExtra("bundle",infoFromMain);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                intent.putExtra("whichFragment",2).putExtra("bundle",infoFromMain);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                intent.putExtra("whichFragment",3).putExtra("bundle",infoFromMain);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}
