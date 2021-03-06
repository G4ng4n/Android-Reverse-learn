package com.example.androiddemo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public native String stringFromJNI();

    static {
        System.loadLibrary("native-lib");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        ((TextView) findViewById(R.id.sample_text)).setText(stringFromJNI());
    }
}
