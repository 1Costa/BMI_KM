package com.example.konstantin.bmi_km;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private TextView bmiIndexResultView;
    private TextView weightResult;
    private TextView indexInBetween;
    private TextView descriptionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);

        bmiIndexResultView = (TextView)findViewById(R.id.bmiIndexResultView);
        weightResult = (TextView)findViewById(R.id.weightResult);
        indexInBetween = (TextView)findViewById(R.id.indexInBetween);
        descriptionView = (TextView)findViewById(R.id.descriptionView);

        Intent intent = getIntent();
        float respond = intent.getFloatExtra("message", 0f);

        bmiIndexResultView.setText(String.valueOf(respond));

        if (respond < 18.5f){
            weightResult.setText(getString(R.string.underweight));
            indexInBetween.setText(getString(R.string.uIndex));
            descriptionView.setText(getString(R.string.underweightDesc));
        }else if(respond < 25){
            weightResult.setText(getString(R.string.normal_weight));
            indexInBetween.setText(getString(R.string.nIndex));
            descriptionView.setText(getString(R.string.normal_weightDesc));
        }else if(respond < 30){
            weightResult.setText(getString(R.string.over_weight));
            indexInBetween.setText(getString(R.string.oIndex));
            descriptionView.setText(getString(R.string.over_weightDesc));
        }else if(respond < 35.1f){
            weightResult.setText(getString(R.string.obesity));
            indexInBetween.setText(getString(R.string.obIndex));
            descriptionView.setText(getString(R.string.obesitytDesc));
        }else{
            weightResult.setText(getString(R.string.morbid_obesity));
            indexInBetween.setText(getString(R.string.obExtraIndex));
            descriptionView.setText(getString(R.string.obesityExtraDesc));
        }

        //String resp = String.valueOf(respond);
        //Toast.makeText(DetailsActivity.this,resp, Toast.LENGTH_LONG).show();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
//@drawable/seekbar_thumb_material_anim  thumb
//@drawable/control_background_32dp_material
//@drawable/seekbar_track_material   intermediate
//@drawable/seekbar_track_material  progress draw