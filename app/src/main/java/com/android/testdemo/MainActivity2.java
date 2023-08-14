package com.android.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Slide slide = new Slide();
        slide.setDuration(800);
        slide.setSlideEdge(Gravity.RIGHT);
        slide.setMode(Visibility.MODE_IN);
        getWindow().setEnterTransition(slide);

        Slide slide_out = new Slide();
        slide_out.setDuration(2000);
        slide_out.setSlideEdge(Gravity.RIGHT);
        slide_out.setMode(Visibility.MODE_OUT);
        getWindow().setReturnTransition(slide_out);
        getWindow().setExitTransition(slide_out);

        login = findViewById(R.id.login);
        login.setOnClickListener(view -> {
            Log.d("zhang", "click login");
//                signIn();
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
//            overridePendingTransition(0,0);
        });
    }
}