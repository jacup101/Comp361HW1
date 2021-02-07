package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class StoryActivity extends AppCompatActivity {
    //Author Joshua Pulido
    LinearLayout scrollLinearStory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        scrollLinearStory = findViewById(R.id.scrollLinearStory);
        Intent intent = getIntent();
        String story = intent.getStringExtra(getString(R.string.story_intent_data));
        TextView tv = new TextView(this);
        tv.setText(story);
        tv.setTextSize(30f);
        scrollLinearStory.addView(tv);
    }

    public void goHome(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }



}
