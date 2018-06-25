package com.engedaludvikling.sheetr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.engedaludvikling.sheetr.R;
import com.engedaludvikling.sheetr.fragments_create_character.CreateCharacterOneFragment;

public class CreateCharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
    }
}