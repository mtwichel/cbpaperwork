package data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperworkDataWrapper implements Parcelable {

    public List<PaperworkData> dataList;



    public PaperworkDataWrapper() {
        dataList = new ArrayList<>();
    }

    public PaperworkData getData(Integer id) {
        if (dataList.get(id) != null) {
            return dataList.get(id);
        } else {
            throw new NullPointerException();
        }

    }

    public void addData(PaperworkData data) {
        Log.d("PaperworkActivity", "addData" + dataList.size());
        dataList.add(data);
    }

    public void removeData(Integer id) {
        dataList.remove(id);
    }

    public void reshuffleIds() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.dataList);
    }

    protected PaperworkDataWrapper(Parcel in) {
        this.dataList = in.createTypedArrayList(PaperworkData.CREATOR);
    }

    public static final Creator<PaperworkDataWrapper> CREATOR = new Creator<PaperworkDataWrapper>() {
        @Override
        public PaperworkDataWrapper createFromParcel(Parcel source) {
            return new PaperworkDataWrapper(source);
        }

        @Override
        public PaperworkDataWrapper[] newArray(int size) {
            return new PaperworkDataWrapper[size];
        }
    };
}
