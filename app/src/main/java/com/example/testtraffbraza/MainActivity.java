package com.example.testtraffbraza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testtraffbraza.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGoToPlay.setOnClickListener(this);
        binding.buttonWebWiew.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if ( view == binding.buttonGoToPlay) {
            onGamePressed();
        } else if ( view == binding.buttonWebWiew) {
            onWebViewPressed();
        }
    }

    public void onGamePressed() {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void onWebViewPressed() {
        startActivity(new Intent(this, WebViewActivity.class));
    }
}