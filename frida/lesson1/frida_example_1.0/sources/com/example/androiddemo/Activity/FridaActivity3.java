package com.example.androiddemo.Activity;

import android.content.Intent;
import android.util.Log;

public class FridaActivity3 extends BaseFridaActivity {
    private static boolean static_bool_var = false;
    private boolean bool_var = false;
    private boolean same_name_bool_var = false;

    public String getNextCheckTitle() {
        return "当前第3关";
    }

    private void same_name_bool_var() {
        Log.d("Frida", static_bool_var + " " + this.bool_var + " " + this.same_name_bool_var);
    }

    public void onCheck() {
        if (!static_bool_var || !this.bool_var || !this.same_name_bool_var) {
            super.CheckFailed();
            return;
        }
        CheckSuccess();
        startActivity(new Intent(this, FridaActivity4.class));
        finishActivity(0);
    }
}
