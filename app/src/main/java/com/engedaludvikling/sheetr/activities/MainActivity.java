package com.engedaludvikling.sheetr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.engedaludvikling.sheetr.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLoginActivity();
    }

    private void startLoginActivity() {
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onCharacterActivityButtonClick(View v) {
        Intent intent = new Intent(this, SpreadsheetActivity.class);
        startActivity(intent);
    }

    public void onPDFCrawlerActivityButtonClick(View v) {
        Intent intent = new Intent(this, PDFCrawlerActivity.class);
        startActivity(intent);
    }

    public void onSignOutButtonClick(View v) {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        startLoginActivity();
    }
}
