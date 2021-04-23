package com.vilorg.mydraw;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PaintView paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        Button buttonRed = (Button) findViewById(R.id.btnRed);
        Button buttonYellow = (Button) findViewById(R.id.btnYellow);
        Button buttonBlack = (Button) findViewById(R.id.btnBlack);
        Button btnClearAll = (Button) findViewById(R.id.btnClearAll);

        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaintView.DEFAULT_COLOR = Color.RED;
                PaintView.currentColor = PaintView.DEFAULT_COLOR;
            }
        });
        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaintView.DEFAULT_COLOR = Color.YELLOW;
                PaintView.currentColor = PaintView.DEFAULT_COLOR;
            }
        });
        buttonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaintView.DEFAULT_COLOR = Color.BLACK;
                PaintView.currentColor = PaintView.DEFAULT_COLOR;
            }
        });
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaintView.DEFAULT_BG_COLOR = Color.WHITE;
                PaintView.backgroundColor = PaintView.DEFAULT_BG_COLOR;
                PaintView.paths.clear();
                v.invalidate();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}