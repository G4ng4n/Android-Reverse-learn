package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public abstract class FragmentManager {
    static final FragmentFactory DEFAULT_FACTORY = new FragmentFactory();
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    private FragmentFactory mFragmentFactory = null;

    public interface BackStackEntry {
        CharSequence getBreadCrumbShortTitle();

        int getBreadCrumbShortTitleRes();

        CharSequence getBreadCrumbTitle();

        int getBreadCrumbTitleRes();

        int getId();

        String getName();
    }

    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
        }

        public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        }
    }

    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    public abstract void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract FragmentTransaction beginTransaction();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean executePendingTransactions();

    public abstract Fragment findFragmentById(int i);

    public abstract Fragment findFragmentByTag(String str);

    public abstract BackStackEntry getBackStackEntryAt(int i);

    public abstract int getBackStackEntryCount();

    public abstract Fragment getFragment(Bundle bundle, String str);

    public abstract List<Fragment> getFragments();

    public abstract Fragment getPrimaryNavigationFragment();

    public abstract boolean isDestroyed();

    public abstract boolean isStateSaved();

    public abstract void popBackStack();

    public abstract void popBackStack(int i, int i2);

    public abstract void popBackStack(String str, int i);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int i, int i2);

    public abstract boolean popBackStackImmediate(String str, int i);

    public abstract void putFragment(Bundle bundle, String str, Fragment fragment);

    public abstract void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z);

    public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract Fragment.SavedState saveFragmentInstanceState(Fragment fragment);

    public abstract void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks);

    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    public void setFragmentFactory(FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    public FragmentFactory getFragmentFactory() {
        if (this.mFragmentFactory == null) {
            this.mFragmentFactory = DEFAULT_FACTORY;
        }
        return this.mFragmentFactory;
    }

    public static void enableDebugLogging(boolean z) {
        FragmentManagerImpl.DEBUG = z;
    }
}
