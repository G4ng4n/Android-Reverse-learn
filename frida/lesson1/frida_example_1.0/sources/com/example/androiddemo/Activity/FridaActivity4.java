package com.example.androiddemo.Activity;

import android.content.Intent;

public class FridaActivity4 extends BaseFridaActivity {
    public String getNextCheckTitle() {
        return "当前第4关";
    }

    private static class InnerClasses {
        public static boolean check1() {
            return false;
        }

        public static boolean check2() {
            return false;
        }

        public static boolean check3() {
            return false;
        }

        public static boolean check4() {
            return false;
        }

        public static boolean check5() {
            return false;
        }

        public static boolean check6() {
            return false;
        }

        private InnerClasses() {
        }
    }

    public void onCheck() {
        if (!InnerClasses.check1() || !InnerClasses.check2() || !InnerClasses.check3() || !InnerClasses.check4() || !InnerClasses.check5() || !InnerClasses.check6()) {
            super.CheckFailed();
            return;
        }
        CheckSuccess();
        startActivity(new Intent(this, FridaActivity5.class));
        finishActivity(0);
    }
}
