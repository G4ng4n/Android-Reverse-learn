package com.example.androiddemo.Activity;

import android.content.Intent;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class FridaActivity1 extends BaseFridaActivity {
    private static final char[] table = {'L', 'K', 'N', 'M', 'O', 'Q', 'P', 'R', 'S', 'A', 'T', 'B', 'C', 'E', 'D', 'F', 'G', 'H', 'I', 'J', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'o', 'd', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'e', 'f', 'g', 'h', 'j', 'i', 'k', 'l', 'm', 'n', 'y', 'z', '0', '1', '2', '3', '4', '6', '5', '7', '8', '9', '+', '/'};

    public String getNextCheckTitle() {
        return "当前第1关";
    }

    public void onCheck() {
        try {
            if (a(b("请输入密码:")).equals("R4jSLLLLLLLLLLOrLE7/5B+Z6fsl65yj6BgC6YWz66gO6g2t65Pk6a+P65NK44NNROl0wNOLLLL=")) {
                CheckSuccess();
                startActivity(new Intent(this, FridaActivity2.class));
                finishActivity(0);
                return;
            }
            super.CheckFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String a(byte[] bArr) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= bArr.length - 1; i += 3) {
            byte[] bArr2 = new byte[4];
            byte b = 0;
            for (int i2 = 0; i2 <= 2; i2++) {
                int i3 = i + i2;
                if (i3 <= bArr.length - 1) {
                    bArr2[i2] = (byte) (b | ((bArr[i3] & 255) >>> ((i2 * 2) + 2)));
                    b = (byte) ((((bArr[i3] & 255) << (((2 - i2) * 2) + 2)) & 255) >>> 2);
                } else {
                    bArr2[i2] = b;
                    b = 64;
                }
            }
            bArr2[3] = b;
            for (int i4 = 0; i4 <= 3; i4++) {
                if (bArr2[i4] <= 63) {
                    sb.append(table[bArr2[i4]]);
                } else {
                    sb.append('=');
                }
            }
        }
        return sb.toString();
    }

    public static byte[] b(String str) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return byteArray;
            } catch (Exception e) {
                e.printStackTrace();
                return byteArray;
            }
        } catch (Exception unused) {
            return null;
        }
    }
}
