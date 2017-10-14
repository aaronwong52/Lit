package com.example.aaron.lit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    protected drawMap screenmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // Example of a call to a native method
    TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setVisibility(View.GONE);
    tv.setText(stringFromJNI());
        Intent intent = new Intent(this, geofinder.class);
        startService(intent);
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart(){
        super.onStart();
  //      screenmap = new drawMap();
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume(){
        super.onResume();

    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onStop(){
        super.onStop();
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onPause(){
        super.onPause();
    }

    /** Called just before the activity is destroyed. */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
