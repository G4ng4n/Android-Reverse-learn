package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.AutoSizeableTextView;
import java.lang.ref.WeakReference;

class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;
    private final TextView mView;

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x012b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadFromAttributes(android.util.AttributeSet r19, int r20) {
        /*
            r18 = this;
            r7 = r18
            r0 = r19
            r1 = r20
            android.widget.TextView r2 = r7.mView
            android.content.Context r2 = r2.getContext()
            androidx.appcompat.widget.AppCompatDrawableManager r3 = androidx.appcompat.widget.AppCompatDrawableManager.get()
            int[] r4 = androidx.appcompat.R.styleable.AppCompatTextHelper
            r5 = 0
            androidx.appcompat.widget.TintTypedArray r4 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r4, r1, r5)
            int r6 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_textAppearance
            r8 = -1
            int r6 = r4.getResourceId(r6, r8)
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableLeft
            boolean r9 = r4.hasValue(r9)
            if (r9 == 0) goto L_0x0032
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableLeft
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableLeftTint = r9
        L_0x0032:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableTop
            boolean r9 = r4.hasValue(r9)
            if (r9 == 0) goto L_0x0046
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableTop
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableTopTint = r9
        L_0x0046:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableRight
            boolean r9 = r4.hasValue(r9)
            if (r9 == 0) goto L_0x005a
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableRight
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableRightTint = r9
        L_0x005a:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableBottom
            boolean r9 = r4.hasValue(r9)
            if (r9 == 0) goto L_0x006e
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableBottom
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableBottomTint = r9
        L_0x006e:
            int r9 = android.os.Build.VERSION.SDK_INT
            r10 = 17
            if (r9 < r10) goto L_0x009c
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableStart
            boolean r9 = r4.hasValue(r9)
            if (r9 == 0) goto L_0x0088
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableStart
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableStartTint = r9
        L_0x0088:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableEnd
            boolean r9 = r4.hasValue(r9)
            if (r9 == 0) goto L_0x009c
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableEnd
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableEndTint = r9
        L_0x009c:
            r4.recycle()
            android.widget.TextView r4 = r7.mView
            android.text.method.TransformationMethod r4 = r4.getTransformationMethod()
            boolean r4 = r4 instanceof android.text.method.PasswordTransformationMethod
            r9 = 26
            r11 = 23
            if (r6 == r8) goto L_0x0130
            int[] r13 = androidx.appcompat.R.styleable.TextAppearance
            androidx.appcompat.widget.TintTypedArray r6 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes((android.content.Context) r2, (int) r6, (int[]) r13)
            if (r4 != 0) goto L_0x00c6
            int r13 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r13 = r6.hasValue(r13)
            if (r13 == 0) goto L_0x00c6
            int r13 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r13 = r6.getBoolean(r13, r5)
            r14 = r13
            r13 = 1
            goto L_0x00c8
        L_0x00c6:
            r13 = 0
            r14 = 0
        L_0x00c8:
            r7.updateTypefaceAndStyle(r2, r6)
            int r15 = android.os.Build.VERSION.SDK_INT
            if (r15 >= r11) goto L_0x0105
            int r15 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            boolean r15 = r6.hasValue(r15)
            if (r15 == 0) goto L_0x00de
            int r15 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            android.content.res.ColorStateList r15 = r6.getColorStateList(r15)
            goto L_0x00df
        L_0x00de:
            r15 = 0
        L_0x00df:
            int r10 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            boolean r10 = r6.hasValue(r10)
            if (r10 == 0) goto L_0x00ee
            int r10 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            android.content.res.ColorStateList r10 = r6.getColorStateList(r10)
            goto L_0x00ef
        L_0x00ee:
            r10 = 0
        L_0x00ef:
            int r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            boolean r12 = r6.hasValue(r12)
            if (r12 == 0) goto L_0x0103
            int r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            android.content.res.ColorStateList r12 = r6.getColorStateList(r12)
            r17 = r15
            r15 = r12
            r12 = r17
            goto L_0x0108
        L_0x0103:
            r12 = r15
            goto L_0x0107
        L_0x0105:
            r10 = 0
            r12 = 0
        L_0x0107:
            r15 = 0
        L_0x0108:
            int r8 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            boolean r8 = r6.hasValue(r8)
            if (r8 == 0) goto L_0x0117
            int r8 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            java.lang.String r8 = r6.getString(r8)
            goto L_0x0118
        L_0x0117:
            r8 = 0
        L_0x0118:
            int r11 = android.os.Build.VERSION.SDK_INT
            if (r11 < r9) goto L_0x012b
            int r11 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            boolean r11 = r6.hasValue(r11)
            if (r11 == 0) goto L_0x012b
            int r11 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            java.lang.String r11 = r6.getString(r11)
            goto L_0x012c
        L_0x012b:
            r11 = 0
        L_0x012c:
            r6.recycle()
            goto L_0x0137
        L_0x0130:
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x0137:
            int[] r6 = androidx.appcompat.R.styleable.TextAppearance
            androidx.appcompat.widget.TintTypedArray r6 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r6, r1, r5)
            if (r4 != 0) goto L_0x014e
            int r9 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r9 = r6.hasValue(r9)
            if (r9 == 0) goto L_0x014e
            int r9 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r14 = r6.getBoolean(r9, r5)
            r13 = 1
        L_0x014e:
            int r9 = android.os.Build.VERSION.SDK_INT
            r5 = 23
            if (r9 >= r5) goto L_0x017e
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            boolean r5 = r6.hasValue(r5)
            if (r5 == 0) goto L_0x0162
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            android.content.res.ColorStateList r12 = r6.getColorStateList(r5)
        L_0x0162:
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            boolean r5 = r6.hasValue(r5)
            if (r5 == 0) goto L_0x0170
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            android.content.res.ColorStateList r10 = r6.getColorStateList(r5)
        L_0x0170:
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            boolean r5 = r6.hasValue(r5)
            if (r5 == 0) goto L_0x017e
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            android.content.res.ColorStateList r15 = r6.getColorStateList(r5)
        L_0x017e:
            int r5 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            boolean r5 = r6.hasValue(r5)
            if (r5 == 0) goto L_0x018c
            int r5 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            java.lang.String r8 = r6.getString(r5)
        L_0x018c:
            int r5 = android.os.Build.VERSION.SDK_INT
            r9 = 26
            if (r5 < r9) goto L_0x01a0
            int r5 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            boolean r5 = r6.hasValue(r5)
            if (r5 == 0) goto L_0x01a0
            int r5 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            java.lang.String r11 = r6.getString(r5)
        L_0x01a0:
            int r5 = android.os.Build.VERSION.SDK_INT
            r9 = 28
            if (r5 < r9) goto L_0x01c1
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textSize
            boolean r5 = r6.hasValue(r5)
            if (r5 == 0) goto L_0x01c1
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textSize
            r9 = -1
            int r5 = r6.getDimensionPixelSize(r5, r9)
            if (r5 != 0) goto L_0x01c1
            android.widget.TextView r5 = r7.mView
            r9 = 0
            r16 = r3
            r3 = 0
            r5.setTextSize(r3, r9)
            goto L_0x01c3
        L_0x01c1:
            r16 = r3
        L_0x01c3:
            r7.updateTypefaceAndStyle(r2, r6)
            r6.recycle()
            if (r12 == 0) goto L_0x01d0
            android.widget.TextView r3 = r7.mView
            r3.setTextColor(r12)
        L_0x01d0:
            if (r10 == 0) goto L_0x01d7
            android.widget.TextView r3 = r7.mView
            r3.setHintTextColor(r10)
        L_0x01d7:
            if (r15 == 0) goto L_0x01de
            android.widget.TextView r3 = r7.mView
            r3.setLinkTextColor(r15)
        L_0x01de:
            if (r4 != 0) goto L_0x01e5
            if (r13 == 0) goto L_0x01e5
            r7.setAllCaps(r14)
        L_0x01e5:
            android.graphics.Typeface r3 = r7.mFontTypeface
            if (r3 == 0) goto L_0x01fb
            int r4 = r7.mFontWeight
            r5 = -1
            if (r4 != r5) goto L_0x01f6
            android.widget.TextView r4 = r7.mView
            int r5 = r7.mStyle
            r4.setTypeface(r3, r5)
            goto L_0x01fb
        L_0x01f6:
            android.widget.TextView r4 = r7.mView
            r4.setTypeface(r3)
        L_0x01fb:
            if (r11 == 0) goto L_0x0202
            android.widget.TextView r3 = r7.mView
            r3.setFontVariationSettings(r11)
        L_0x0202:
            if (r8 == 0) goto L_0x022e
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 24
            if (r3 < r4) goto L_0x0214
            android.widget.TextView r3 = r7.mView
            android.os.LocaleList r4 = android.os.LocaleList.forLanguageTags(r8)
            r3.setTextLocales(r4)
            goto L_0x022e
        L_0x0214:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 21
            if (r3 < r4) goto L_0x022e
            r3 = 44
            int r3 = r8.indexOf(r3)
            r4 = 0
            java.lang.String r3 = r8.substring(r4, r3)
            android.widget.TextView r4 = r7.mView
            java.util.Locale r3 = java.util.Locale.forLanguageTag(r3)
            r4.setTextLocale(r3)
        L_0x022e:
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r3 = r7.mAutoSizeTextHelper
            r3.loadFromAttributes(r0, r1)
            boolean r1 = androidx.core.widget.AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE
            if (r1 == 0) goto L_0x0274
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r1 = r7.mAutoSizeTextHelper
            int r1 = r1.getAutoSizeTextType()
            if (r1 == 0) goto L_0x0274
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r1 = r7.mAutoSizeTextHelper
            int[] r1 = r1.getAutoSizeTextAvailableSizes()
            int r3 = r1.length
            if (r3 <= 0) goto L_0x0274
            android.widget.TextView r3 = r7.mView
            int r3 = r3.getAutoSizeStepGranularity()
            float r3 = (float) r3
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x026e
            android.widget.TextView r1 = r7.mView
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r3 = r7.mAutoSizeTextHelper
            int r3 = r3.getAutoSizeMinTextSize()
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r4 = r7.mAutoSizeTextHelper
            int r4 = r4.getAutoSizeMaxTextSize()
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r5 = r7.mAutoSizeTextHelper
            int r5 = r5.getAutoSizeStepGranularity()
            r6 = 0
            r1.setAutoSizeTextTypeUniformWithConfiguration(r3, r4, r5, r6)
            goto L_0x0274
        L_0x026e:
            r6 = 0
            android.widget.TextView r3 = r7.mView
            r3.setAutoSizeTextTypeUniformWithPresetSizes(r1, r6)
        L_0x0274:
            int[] r1 = androidx.appcompat.R.styleable.AppCompatTextView
            androidx.appcompat.widget.TintTypedArray r8 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes((android.content.Context) r2, (android.util.AttributeSet) r0, (int[]) r1)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableLeftCompat
            r1 = -1
            int r0 = r8.getResourceId(r0, r1)
            r3 = r16
            if (r0 == r1) goto L_0x028b
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r4 = r0
            goto L_0x028c
        L_0x028b:
            r4 = 0
        L_0x028c:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTopCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x029a
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r5 = r0
            goto L_0x029b
        L_0x029a:
            r5 = 0
        L_0x029b:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableRightCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x02a9
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r6 = r0
            goto L_0x02aa
        L_0x02a9:
            r6 = 0
        L_0x02aa:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableBottomCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x02b8
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r9 = r0
            goto L_0x02b9
        L_0x02b8:
            r9 = 0
        L_0x02b9:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableStartCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x02c7
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r10 = r0
            goto L_0x02c8
        L_0x02c7:
            r10 = 0
        L_0x02c8:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableEndCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x02d6
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r11 = r0
            goto L_0x02d7
        L_0x02d6:
            r11 = 0
        L_0x02d7:
            r0 = r18
            r1 = r4
            r2 = r5
            r3 = r6
            r4 = r9
            r5 = r10
            r6 = r11
            r0.setCompoundDrawables(r1, r2, r3, r4, r5, r6)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTint
            boolean r0 = r8.hasValue(r0)
            if (r0 == 0) goto L_0x02f5
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTint
            android.content.res.ColorStateList r0 = r8.getColorStateList(r0)
            android.widget.TextView r1 = r7.mView
            androidx.core.widget.TextViewCompat.setCompoundDrawableTintList(r1, r0)
        L_0x02f5:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTintMode
            boolean r0 = r8.hasValue(r0)
            if (r0 == 0) goto L_0x030f
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTintMode
            r1 = -1
            int r0 = r8.getInt(r0, r1)
            r2 = 0
            android.graphics.PorterDuff$Mode r0 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r0, r2)
            android.widget.TextView r2 = r7.mView
            androidx.core.widget.TextViewCompat.setCompoundDrawableTintMode(r2, r0)
            goto L_0x0310
        L_0x030f:
            r1 = -1
        L_0x0310:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_firstBaselineToTopHeight
            int r0 = r8.getDimensionPixelSize(r0, r1)
            int r2 = androidx.appcompat.R.styleable.AppCompatTextView_lastBaselineToBottomHeight
            int r2 = r8.getDimensionPixelSize(r2, r1)
            int r3 = androidx.appcompat.R.styleable.AppCompatTextView_lineHeight
            int r3 = r8.getDimensionPixelSize(r3, r1)
            r8.recycle()
            if (r0 == r1) goto L_0x032c
            android.widget.TextView r4 = r7.mView
            androidx.core.widget.TextViewCompat.setFirstBaselineToTopHeight(r4, r0)
        L_0x032c:
            if (r2 == r1) goto L_0x0333
            android.widget.TextView r0 = r7.mView
            androidx.core.widget.TextViewCompat.setLastBaselineToBottomHeight(r0, r2)
        L_0x0333:
            if (r3 == r1) goto L_0x033a
            android.widget.TextView r0 = r7.mView
            androidx.core.widget.TextViewCompat.setLineHeight(r0, r3)
        L_0x033a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    public void setTypefaceByCallback(Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mView.setTypeface(typeface);
            this.mFontTypeface = typeface;
        }
    }

    public void runOnUiThread(Runnable runnable) {
        this.mView.post(runnable);
    }

    private static class ApplyTextViewCallback extends ResourcesCompat.FontCallback {
        private final int mFontWeight;
        private final WeakReference<AppCompatTextHelper> mParent;
        private final int mStyle;

        public void onFontRetrievalFailed(int i) {
        }

        private class TypefaceApplyCallback implements Runnable {
            private final WeakReference<AppCompatTextHelper> mParent;
            private final Typeface mTypeface;

            TypefaceApplyCallback(WeakReference<AppCompatTextHelper> weakReference, Typeface typeface) {
                this.mParent = weakReference;
                this.mTypeface = typeface;
            }

            public void run() {
                AppCompatTextHelper appCompatTextHelper = (AppCompatTextHelper) this.mParent.get();
                if (appCompatTextHelper != null) {
                    appCompatTextHelper.setTypefaceByCallback(this.mTypeface);
                }
            }
        }

        ApplyTextViewCallback(AppCompatTextHelper appCompatTextHelper, int i, int i2) {
            this.mParent = new WeakReference<>(appCompatTextHelper);
            this.mFontWeight = i;
            this.mStyle = i2;
        }

        public void onFontRetrieved(Typeface typeface) {
            int i;
            AppCompatTextHelper appCompatTextHelper = (AppCompatTextHelper) this.mParent.get();
            if (appCompatTextHelper != null) {
                if (Build.VERSION.SDK_INT >= 28 && (i = this.mFontWeight) != -1) {
                    typeface = Typeface.create(typeface, i, (this.mStyle & 2) != 0);
                }
                appCompatTextHelper.runOnUiThread(new TypefaceApplyCallback(this.mParent, typeface));
            }
        }
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 28) {
            this.mFontWeight = tintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            if (this.mFontWeight != -1) {
                this.mStyle = (this.mStyle & 2) | 0;
            }
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int i = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
            int i2 = this.mFontWeight;
            int i3 = this.mStyle;
            if (!context.isRestricted()) {
                try {
                    Typeface font = tintTypedArray.getFont(i, this.mStyle, new ApplyTextViewCallback(this, i2, i3));
                    if (font != null) {
                        if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                            this.mFontTypeface = font;
                        } else {
                            this.mFontTypeface = Typeface.create(Typeface.create(font, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                        }
                    }
                    this.mAsyncFontPending = this.mFontTypeface == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.mFontTypeface == null && (string = tintTypedArray.getString(i)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                    this.mFontTypeface = Typeface.create(string, this.mStyle);
                    return;
                }
                Typeface create = Typeface.create(string, 0);
                int i4 = this.mFontWeight;
                if ((this.mStyle & 2) != 0) {
                    z = true;
                }
                this.mFontTypeface = Typeface.create(create, i4, z);
            }
        } else if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
            this.mAsyncFontPending = false;
            int i5 = tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1);
            if (i5 == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
            } else if (i5 == 2) {
                this.mFontTypeface = Typeface.SERIF;
            } else if (i5 == 3) {
                this.mFontTypeface = Typeface.MONOSPACE;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onSetTextAppearance(Context context, int i) {
        String string;
        ColorStateList colorStateList;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(colorStateList);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize) && obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes);
        if (Build.VERSION.SDK_INT >= 26 && obtainStyledAttributes.hasValue(R.styleable.TextAppearance_fontVariationSettings) && (string = obtainStyledAttributes.getString(R.styleable.TextAppearance_fontVariationSettings)) != null) {
            this.mView.setFontVariationSettings(string);
        }
        obtainStyledAttributes.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAllCaps(boolean z) {
        this.mView.setAllCaps(z);
    }

    /* access modifiers changed from: package-private */
    public void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    /* access modifiers changed from: package-private */
    public void applyCompoundDrawablesTints() {
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (Build.VERSION.SDK_INT < 17) {
            return;
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
        }
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
        }
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    /* access modifiers changed from: package-private */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    /* access modifiers changed from: package-private */
    public void setTextSize(int i, float f) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(i, f);
        }
    }

    /* access modifiers changed from: package-private */
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    /* access modifiers changed from: package-private */
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int i, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i, f);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeWithDefaults(int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    /* access modifiers changed from: package-private */
    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintList(ColorStateList colorStateList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = colorStateList != null;
        setCompoundTints();
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintMode(PorterDuff.Mode mode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = mode != null;
        setCompoundTints();
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (Build.VERSION.SDK_INT >= 17 && (drawable5 != null || drawable6 != null)) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            TextView textView = this.mView;
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                Drawable[] compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
                if (!(compoundDrawablesRelative2[0] == null && compoundDrawablesRelative2[2] == null)) {
                    TextView textView2 = this.mView;
                    Drawable drawable7 = compoundDrawablesRelative2[0];
                    if (drawable2 == null) {
                        drawable2 = compoundDrawablesRelative2[1];
                    }
                    Drawable drawable8 = compoundDrawablesRelative2[2];
                    if (drawable4 == null) {
                        drawable4 = compoundDrawablesRelative2[3];
                    }
                    textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, drawable8, drawable4);
                    return;
                }
            }
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            TextView textView3 = this.mView;
            if (drawable == null) {
                drawable = compoundDrawables[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawables[1];
            }
            if (drawable3 == null) {
                drawable3 = compoundDrawables[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawables[3];
            }
            textView3.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }
}
