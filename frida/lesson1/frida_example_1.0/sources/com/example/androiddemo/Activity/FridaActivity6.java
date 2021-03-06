package com.example.androiddemo.Activity;

import android.content.Intent;
import com.example.androiddemo.Activity.Frida6.Frida6Class0;
import com.example.androiddemo.Activity.Frida6.Frida6Class1;
import com.example.androiddemo.Activity.Frida6.Frida6Class2;

public class FridaActivity6 extends BaseFridaActivity {
    public String getNextCheckTitle() {
        return "当前第6关";
    }

    public void onCheck() {
        if (!Frida6Class0.check() || !Frida6Class1.check() || !Frida6Class2.check()) {
            super.CheckFailed();
            return;
        }
        CheckSuccess();
        startActivity(new Intent(this, FridaActivity7.class));
        finishActivity(0);
    }
}
