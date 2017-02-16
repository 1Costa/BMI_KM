package com.example.konstantin.bmi_km;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.renderscript.Double2;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    // Background image.
    //http://androidwalls.net/nature/white-flower-petals-corner-light-blue-background-android-wallpaper.php  ********************

    private TextView cmValueView;
    private TextView ftValueView;
    private TextView inchValueView;
    private TextView kgValueView;
    private TextView stValueView;
    private TextView lbsValueView;
    private SeekBar seekBarHeight;
    private SeekBar seekBarWeight;
    //private Button buttonCalculate;
    private ImageView buttonCalculate;
    private ImageView imageDetails;
    private TextView bmiResultView;
    private TextView bmiTypeView;

    //Labels for measurements
    private TextView cmLabel;
    private TextView kgLabel;
    private TextView ftLabel;
    private TextView inchLabel;
    private TextView stLabel;
    private TextView lbsLabel;
    private TextView bmiResultLabel;

    // define the SharedPreferences object
    private SharedPreferences savedValues;
    //define text views as integers
    private int cmValueV;
//    private int cmLabelV;
//    private int kgValueV;
//    private int kgLabelV;
//    private int ftValueV;
//    private int ftLabelV;
//    private int inchValueV;
//    private int inchLabelV;
//    private int stValueV;
//    private int stLabelV;
//    private int lbsValueV;
//    private int lbsLabelV;
    private int imageDshown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        //https://itunes.apple.com/us/app/fitter-fitness-calculator/id441790436?mt=8  *************************


        cmValueView = (TextView)findViewById(R.id.cmValueView);
        ftValueView = (TextView)findViewById(R.id.ftValueView);
        inchValueView = (TextView)findViewById(R.id.inchValueView);
        kgValueView = (TextView)findViewById(R.id.kgValueView);
        stValueView = (TextView)findViewById(R.id.stValueView);
        lbsValueView = (TextView)findViewById(R.id.lbsValueView);
        seekBarHeight = (SeekBar)findViewById(R.id.seekBarHeight);
        seekBarWeight = (SeekBar)findViewById(R.id.seekBarWeight);
        bmiResultView = (TextView)findViewById(R.id.bmiResultView);
        bmiTypeView = (TextView)findViewById(R.id.bmiTypeView);
        //buttonCalculate = (Button)findViewById(R.id.buttonCalculate);
        buttonCalculate = (ImageView)findViewById(R.id.imageViewCalculate);
        // Image for Button https://www.fotolia.com/tag/%22calculator%20button%22 *********************************

        cmLabel = (TextView)findViewById(R.id.cmLabel);
        kgLabel = (TextView)findViewById(R.id.kgLabel);
        ftLabel = (TextView)findViewById(R.id.ftLabel);
        inchLabel = (TextView)findViewById(R.id.inchLabel);
        stLabel = (TextView)findViewById(R.id.stLabel);
        lbsLabel = (TextView)findViewById(R.id.lbsLabel);
        bmiResultLabel = (TextView)findViewById(R.id.bmiResultLabel);
        imageDetails = (ImageView)findViewById(R.id.imageDetails);
        ///own icon  ********************

        if(Float.parseFloat(bmiResultView.getText().toString()) < 10.0f)
        imageDetails.setVisibility(View.INVISIBLE);

        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int cmValue = seekBarHeight.getProgress()+ 100; // sets min value of seekBar to 100 cm
                cmValueView.setText(Integer.toString(cmValue));
                // 1 inch is 2.54 cm
                float inchFloatFull = cmValue/2.54f; // to get inches value from centimeters
                float feetFloat = inchFloatFull/12f; // to get feet value from inches
                int feetInt = (int)feetFloat;    // get feet integer value

                float reminder = feetFloat - feetInt;// get reminder of feet
                float inchFloat = reminder * 12f; // convert reminder to inches value
                int inchInt = (int)inchFloat;        // get inch integer value

                //float ftValue = cmValue/30.48f;
                //int singleFeet = (int)ftValue;

                ftValueView.setText(Integer.toString(feetInt));
                inchValueView.setText(Integer.toString(inchInt));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                bmiResultView.setText("0.0");
                bmiTypeView.setVisibility(View.INVISIBLE);
                imageDetails.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                kgValueView.setText(Integer.toString(progress));
                int kgValue = seekBarWeight.getProgress();
                // one pound is 2.2046226218 in one kilogram
                float lbsFloatFull = kgValue * 2.2046226218f; // to get pound from kilograms
                float stoneFloat = lbsFloatFull/14f; // to get stone value from pounds (one stone 14 pounds)
                int stoneInt = (int)stoneFloat;    // convert float stone to integer

                float reminderSt = stoneFloat - stoneInt;// get reminder of stone to get pounds
                float lbsFloat = reminderSt * 14f; // get value of whole pound
                int poundInt = (int)lbsFloat;        // convert float pound to Integer


                stValueView.setText(Integer.toString(stoneInt));
                lbsValueView.setText(Integer.toString(poundInt));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                bmiResultView.setText("0.0");
                bmiTypeView.setVisibility(View.INVISIBLE);
                imageDetails.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


// vibrator feature ***********************************
 final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.vibrate(50);
                calculateBMI();

            }
        });

        imageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
vibrator.vibrate(50);
                float message = Float.parseFloat(bmiResultView.getText().toString());

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("message", message);
                startActivity(intent);


            }
        });

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.centimeters:
                // User chose the "Metric System" item, set metrics show only
                hideImperialSystem();
                return true;

            case R.id.pounds:
                // User chose the "Imperial System" item, set imperial show only
                hideMetricSystem();
                return true;

            case R.id.use_both_systems:
                // User chose the "Display both" item, show the Imperial and Metric  UI...
                showBothSystems();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                //Log.d(String.valueOf(item), "strange number id message");
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onPause() {
        // save the instance variables

        int height = Integer.parseInt(cmValueView.getText().toString());
        int weight = Integer.parseInt(kgValueView.getText().toString());
        if(cmValueView.isShown()){
            cmValueV = 1;
        }
        if(ftValueView.isShown()){
            cmValueV = 3;
        }
        if(cmValueView.isShown() && ftValueView.isShown()){
            cmValueV = 2;
        }
        // get result fields for bmi type, value and arrow details
        //String bmiRL = bmiResultLabel.getText().toString();
        String bmiResView = bmiResultView.getText().toString();
        String bmiType = bmiTypeView.getText().toString();
        if(imageDetails.isShown()){imageDshown = 1;} // image arrow was on the screen

        //Log.d(String.valueOf(cmValueV), "value of cmLabel on pause");


        //Log.d(String.valueOf(height), "on Pause");
        SharedPreferences.Editor editor = savedValues.edit();

        editor.putInt("height", height);
        editor.putInt("weight", weight);
        editor.putInt("cmValueV", cmValueV);
        //editor.putString("bmiLabel", bmiRL);
        editor.putString("bmiRtype", bmiType);
        editor.putString("bmiRESULT", bmiResView);
        editor.putInt("imageShown", imageDshown);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //int height = savedValues.getInt("height", 0);
        //Log.d(String.valueOf(height), "on Resume");
        seekBarHeight.setProgress(savedValues.getInt("height", 0) - 100);
        int weight = savedValues.getInt("weight", 0);
        seekBarWeight.setProgress(weight);

        if(savedValues.getInt("cmValueV", 0) == 1){hideImperialSystem();} // if cm Metric Value was shown on pause, then do not open Imperial
        if(savedValues.getInt("cmValueV", 0) == 2){showBothSystems();}
        if(savedValues.getInt("cmValueV", 0) == 3){hideMetricSystem();}

        //bmiResultLabel.setText(savedValues.getString("bmiLabel",""));

        if(savedValues.getInt("imageShown",0)== 1){
            imageDetails.setVisibility(View.VISIBLE);
            bmiTypeView.setText(savedValues.getString("bmiRtype",""));
            bmiResultView.setText(savedValues.getString("bmiRESULT", ""));

        }

    }

    public void calculateBMI(){

        float heightCm = Float.parseFloat(cmValueView.getText().toString());// seekBarHeight.getProgress() + 100f;
        float weightKg = Float.parseFloat(kgValueView.getText().toString());//seekBarWeight.getProgress();
        if(weightKg >= 10) {
            //Log.d(Float.toString(heightCm), "centimeters");
            //Log.d(Float.toString(weightKg), "kilogram");

            float height = heightCm / 100f;

            float bmiMetric = (float) (weightKg / (Math.pow(height, 2)));
            float mBMI = Float.parseFloat(String.format("%.1f", bmiMetric));

            //Log.d(Float.toString(mBMI), "BMI");
            if (mBMI >= 5.0f) {
                imageDetails.setVisibility(View.VISIBLE);
                bmiTypeView.setVisibility(View.VISIBLE);
            }else{
                String message = "It is unreal to survive, try again";
                Toast.makeText(MainActivity.this,message, Toast.LENGTH_LONG).show();
            }


            bmiResultView.setText(Float.toString(mBMI));
//                float pounds = weightKg * 2.2046226218f;
//                float inches = heightCm/2.54f;
//                float bmiImperial = (float)(pounds/(Math.pow(inches, 2))* 703.06958f);
//                String iBMI = String.format("%.1f", bmiImperial);
//                Log.d(iBMI, "BMI");
            if (mBMI < 18.5f) {
                //Underweight
                bmiTypeView.setText(getString(R.string.underweight));
            } else if (mBMI < 25f) {
                //Normal weight
                bmiTypeView.setText(getString(R.string.normal_weight));
            } else if (mBMI < 30f) {
                //Overweight
                bmiTypeView.setText(getString(R.string.over_weight));
            } else if(mBMI < 35f){
                //Obesity
                bmiTypeView.setText(getString(R.string.obesity));
            }else{
                //Morbid obesity
                bmiTypeView.setText(getString(R.string.morbid_obesity));
            }
            //bmiResultLabel.setText(getString(R.string.bmi_index));
            //imageDetails.setVisibility(View.VISIBLE);
        }else{
            String message = "Weight must be at least 10 kg to Calculate";
            Toast.makeText(MainActivity.this,message, Toast.LENGTH_LONG).show();
        }

    }

    public void hideImperialSystem(){
        ftLabel.setVisibility(View.INVISIBLE);
        inchLabel.setVisibility(View.INVISIBLE);
        ftValueView.setVisibility(View.INVISIBLE);
        inchValueView.setVisibility(View.INVISIBLE);
        stValueView.setVisibility(View.INVISIBLE);
        lbsValueView.setVisibility(View.INVISIBLE);
        stLabel.setVisibility(View.INVISIBLE);
        lbsLabel.setVisibility(View.INVISIBLE);

        cmLabel.setVisibility(View.VISIBLE);
        cmValueView.setVisibility(View.VISIBLE);
        kgLabel.setVisibility(View.VISIBLE);
        kgValueView.setVisibility(View.VISIBLE);
    }

    public void hideMetricSystem(){

        cmLabel.setVisibility(View.INVISIBLE);
        cmValueView.setVisibility(View.INVISIBLE);
        kgLabel.setVisibility(View.INVISIBLE);
        kgValueView.setVisibility(View.INVISIBLE);

        ftLabel.setVisibility(View.VISIBLE);
        inchLabel.setVisibility(View.VISIBLE);
        ftValueView.setVisibility(View.VISIBLE);
        inchValueView.setVisibility(View.VISIBLE);
        stValueView.setVisibility(View.VISIBLE);
        lbsValueView.setVisibility(View.VISIBLE);
        stLabel.setVisibility(View.VISIBLE);
        lbsLabel.setVisibility(View.VISIBLE);
    }

    public void showBothSystems(){
        cmLabel.setVisibility(View.VISIBLE);
        cmValueView.setVisibility(View.VISIBLE);
        kgLabel.setVisibility(View.VISIBLE);
        kgValueView.setVisibility(View.VISIBLE);
        ftLabel.setVisibility(View.VISIBLE);
        inchLabel.setVisibility(View.VISIBLE);
        ftValueView.setVisibility(View.VISIBLE);
        inchValueView.setVisibility(View.VISIBLE);
        stValueView.setVisibility(View.VISIBLE);
        lbsValueView.setVisibility(View.VISIBLE);
        stLabel.setVisibility(View.VISIBLE);
        lbsLabel.setVisibility(View.VISIBLE);
    }
}
