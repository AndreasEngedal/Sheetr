package com.engedaludvikling.sheetr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.engedaludvikling.sheetr.R;

import butterknife.BindView;

public class PDFCrawlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfcrawler);
    }
    
    public void onButtonClick(View v) {
        Toast.makeText(this, "eyyy", Toast.LENGTH_SHORT).show();
    }
}
