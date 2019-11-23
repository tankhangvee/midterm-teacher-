package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class gamedetail extends AppCompatActivity {

    private TextView tvTitle,tvRating,tvPrice,tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamedetail);

        findViews();
        initializeData();
    }

    private void findViews(){
        tvTitle = findViewById(R.id.tvtitle);
        tvPrice = findViewById(R.id.tvprice);
        tvRating = findViewById(R.id.tvrating);
        tvDescription = findViewById(R.id.tvdescription);
    }

    private void initializeData(){
        Bundle bundle = getIntent().getExtras();
        tvTitle.setText(bundle.getString("title"));
        tvRating.setText(bundle.getString("rating"));
        tvPrice.setText(bundle.getString("price"));
        tvDescription.setText(bundle.getString("description"));
    }
}
