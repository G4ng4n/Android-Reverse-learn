package com.example.androiddemo.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androiddemo.R;

public abstract class BaseFridaActivity extends AppCompatActivity implements View.OnClickListener {
    public Button mNextCheck;

    public void CheckSuccess() {
    }

    public abstract String getNextCheckTitle();

    public abstract void onCheck();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_frida);
        this.mNextCheck = (Button) findViewById(R.id.next_check);
        this.mNextCheck.setOnClickListener(this);
        Button button = this.mNextCheck;
        button.setText(getNextCheckTitle() + "，点击进入下一关");
    }

    public void onClick(View view) {
        onCheck();
    }

    public void CheckFailed() {
        Toast.makeText(this, "Check Failed!", 1).show();
    }
}
