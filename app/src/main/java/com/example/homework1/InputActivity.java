package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InputActivity extends AppCompatActivity {
    //Author Joshua Pulido
    private LinearLayout linearLayout;
    private TextView storyTitle;
    private String storyTitleString;
    //TODO: Parsed data to be put into lists, rather than String
    private String parsedData;

    private JSONArray storyText;
    private JSONArray textArray;
    private TextView[] textViews;
    private EditText[] editTexts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_input);

        linearLayout = findViewById(R.id.scrollLinearStory);
        storyTitle = findViewById(R.id.titleText);
        Intent intent = getIntent();
        String data = intent.getStringExtra(getString(R.string.input_intent_data));
        //TextView tv = new TextView(this);
        //tv.setText(data);
        //linearLayout.addView(tv);

        grabJsonData(data);
        storyTitle.setText(storyTitleString);

        putData();
    }
    public void generateButton(View v) {
        boolean filled = checkFilled();
        if(filled) {
            String[] texts = grabStrings();
            String story = "";
            try {
                story = storyText.getString(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < textArray.length(); i++) {
                try {
                    story += texts[i] + storyText.getString(i+1);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(InputActivity.this,StoryActivity.class);
            intent.putExtra(getString(R.string.story_intent_data),story);
            startActivity(intent);
        } else{
            String toastText = getString(R.string.missing_field);
            Toast toast = Toast.makeText(this,toastText,Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private void grabJsonData(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);
            textArray = json.getJSONArray("blanks");
            storyText = json.getJSONArray("value");
            storyTitleString = json.getString("title");
        } catch (JSONException e) {
            Log.e("json_error","Json could not be read");
            e.printStackTrace();
        }
    }
    private void putData() {
        //TODO: Implement parse api with ArrayList
        int dataSize = textArray.length();
        textViews = new TextView[dataSize];
        editTexts = new EditText[dataSize];
        //REMOVE: remove temp code
        //TEMP:
        for(int i = 0; i < dataSize; i ++) {
            textViews[i] = new TextView(this);
            try {
                textViews[i].setText(textArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editTexts[i] = new EditText(this);
            linearLayout.addView(textViews[i]);
            linearLayout.addView(editTexts[i]);
        }

    }
    private boolean checkFilled() {
        for(int i = 0; i < editTexts.length;i++) {
            if(!checkString(String.valueOf(editTexts[i].getText()))) return false;
        }
        return true;
    }
    private boolean checkString(String str) {
        if(str.length() == 0) return false;
        for(int i = 0; i < str.length(); i++){
            if(!str.substring(i,i+1).equals(" ")) return true;
        }
        return false;
    }
    private String[] grabStrings() {
        String[] texts = new String[editTexts.length];
        for(int i = 0; i < editTexts.length; i++) {
            texts[i] = pruneString(String.valueOf(editTexts[i].getText()));
        }
        return texts;
    }
    private String pruneString(String str) {

        while(str.length() > 1 && str.substring(0,1).equals(" ")) {
            str = str.substring(1);
        }
        while(str.length() > 1 && str.substring(str.length()-1,str.length()).equals(" ")) {
            str = str.substring(0,str.length()-1);
        }
        return str;
    }
}