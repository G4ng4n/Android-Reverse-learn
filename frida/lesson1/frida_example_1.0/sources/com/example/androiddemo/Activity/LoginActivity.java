package com.example.androiddemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androiddemo.BuildConfig;
import com.example.androiddemo.R;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Context mContext;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        setContentView((int) R.layout.activity_login);
        final EditText editText = (EditText) findViewById(R.id.username);
        final EditText editText2 = (EditText) findViewById(R.id.password);
        ((Button) findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2)) {
                    Toast.makeText(LoginActivity.this.mContext, "username or password is empty.", 1).show();
                } else if (LoginActivity.a(obj, obj).equals(obj2)) {
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.mContext, FridaActivity1.class));
                    LoginActivity.this.finishActivity(0);
                } else {
                    Toast.makeText(LoginActivity.this.mContext, "Login failed.", 1).show();
                }
            }
        });
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (bArr != null && i < bArr.length) {
            String hexString = Integer.toHexString(bArr[i] & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
            i++;
        }
        return sb.toString().toLowerCase();
    }

    /* access modifiers changed from: private */
    public static String a(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "HmacSHA256");
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(secretKeySpec);
            return a(instance.doFinal(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }
}
