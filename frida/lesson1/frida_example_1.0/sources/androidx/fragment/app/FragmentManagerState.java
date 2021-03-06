package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator<FragmentManagerState>() {
        public FragmentManagerState createFromParcel(Parcel parcel) {
            return new FragmentManagerState(parcel);
        }

        public FragmentManagerState[] newArray(int i) {
            return new FragmentManagerState[i];
        }
    };
    ArrayList<FragmentState> mActive;
    ArrayList<String> mAdded;
    BackStackState[] mBackStack;
    int mNextFragmentIndex;
    String mPrimaryNavActiveWho = null;

    public int describeContents() {
        return 0;
    }

    public FragmentManagerState() {
    }

    public FragmentManagerState(Parcel parcel) {
        this.mActive = parcel.createTypedArrayList(FragmentState.CREATOR);
        this.mAdded = parcel.createStringArrayList();
        this.mBackStack = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
        this.mPrimaryNavActiveWho = parcel.readString();
        this.mNextFragmentIndex = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mActive);
        parcel.writeStringList(this.mAdded);
        parcel.writeTypedArray(this.mBackStack, i);
        parcel.writeString(this.mPrimaryNavActiveWho);
        parcel.writeInt(this.mNextFragmentIndex);
    }
}
