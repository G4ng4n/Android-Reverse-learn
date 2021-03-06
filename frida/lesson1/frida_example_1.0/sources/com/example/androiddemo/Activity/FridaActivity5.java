package com.example.androiddemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.androiddemo.Dynamic.CheckInterface;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;

public class FridaActivity5 extends BaseFridaActivity {
    private CheckInterface DynamicDexCheck = null;

    public String getNextCheckTitle() {
        return "当前第5关";
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0043 A[SYNTHETIC, Splitter:B:28:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x004b A[Catch:{ IOException -> 0x0047 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0058 A[SYNTHETIC, Splitter:B:39:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0060 A[Catch:{ IOException -> 0x005c }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFiles(android.content.Context r2, java.lang.String r3, java.io.File r4) {
        /*
            r0 = 0
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ IOException -> 0x003c, all -> 0x0039 }
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ IOException -> 0x003c, all -> 0x0039 }
            java.io.InputStream r2 = r2.open(r3)     // Catch:{ IOException -> 0x003c, all -> 0x0039 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0035, all -> 0x0033 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ IOException -> 0x0035, all -> 0x0033 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0035, all -> 0x0033 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ IOException -> 0x0031, all -> 0x002f }
        L_0x001a:
            int r0 = r2.read(r4)     // Catch:{ IOException -> 0x0031, all -> 0x002f }
            r1 = -1
            if (r0 == r1) goto L_0x0026
            r1 = 0
            r3.write(r4, r1, r0)     // Catch:{ IOException -> 0x0031, all -> 0x002f }
            goto L_0x001a
        L_0x0026:
            if (r2 == 0) goto L_0x002b
            r2.close()     // Catch:{ IOException -> 0x0047 }
        L_0x002b:
            r3.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x0052
        L_0x002f:
            r4 = move-exception
            goto L_0x0055
        L_0x0031:
            r4 = move-exception
            goto L_0x0037
        L_0x0033:
            r4 = move-exception
            goto L_0x0056
        L_0x0035:
            r4 = move-exception
            r3 = r0
        L_0x0037:
            r0 = r2
            goto L_0x003e
        L_0x0039:
            r4 = move-exception
            r2 = r0
            goto L_0x0056
        L_0x003c:
            r4 = move-exception
            r3 = r0
        L_0x003e:
            r4.printStackTrace()     // Catch:{ all -> 0x0053 }
            if (r0 == 0) goto L_0x0049
            r0.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x0049
        L_0x0047:
            r2 = move-exception
            goto L_0x004f
        L_0x0049:
            if (r3 == 0) goto L_0x0052
            r3.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x0052
        L_0x004f:
            r2.printStackTrace()
        L_0x0052:
            return
        L_0x0053:
            r4 = move-exception
            r2 = r0
        L_0x0055:
            r0 = r3
        L_0x0056:
            if (r2 == 0) goto L_0x005e
            r2.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x005e
        L_0x005c:
            r2 = move-exception
            goto L_0x0064
        L_0x005e:
            if (r0 == 0) goto L_0x0067
            r0.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0067
        L_0x0064:
            r2.printStackTrace()
        L_0x0067:
            goto L_0x0069
        L_0x0068:
            throw r4
        L_0x0069:
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.androiddemo.Activity.FridaActivity5.copyFiles(android.content.Context, java.lang.String, java.io.File):void");
    }

    private void loaddex() {
        File cacheDir = getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        String str = cacheDir.getAbsolutePath() + File.separator + "DynamicPlugin.dex";
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
                copyFiles(this, "DynamicPlugin.dex", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.DynamicDexCheck = (CheckInterface) new DexClassLoader(str, cacheDir.getAbsolutePath(), (String) null, getClassLoader()).loadClass("com.example.androiddemo.Dynamic.DynamicCheck").newInstance();
            if (this.DynamicDexCheck == null) {
                Toast.makeText(this, "loaddex Failed!", 1).show();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public CheckInterface getDynamicDexCheck() {
        if (this.DynamicDexCheck == null) {
            loaddex();
        }
        return this.DynamicDexCheck;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        loaddex();
    }

    public void onCheck() {
        if (getDynamicDexCheck() == null) {
            Toast.makeText(this, "onClick loaddex Failed!", 1).show();
        } else if (getDynamicDexCheck().check()) {
            CheckSuccess();
            startActivity(new Intent(this, FridaActivity6.class));
            finishActivity(0);
        } else {
            super.CheckFailed();
        }
    }
}
