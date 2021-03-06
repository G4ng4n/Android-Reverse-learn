package com.example.androiddemo.Activity;

import android.content.Intent;

public class FridaActivity2 extends BaseFridaActivity {
    private static boolean static_bool_var = false;
    private boolean bool_var = false;

    public String getNextCheckTitle() {
        return "当前第2关";
    }

    private static void setStatic_bool_var() {
        static_bool_var = true;
    }

    private void setBool_var() {
        this.bool_var = true;
    }

    public void onCheck() {
        if (!static_bool_var || !this.bool_var) {
            super.CheckFailed();
            return;
        }
        CheckSuccess();
        startActivity(new Intent(this, FridaActivity3.class));
        finishActivity(0);
    }
}
