package data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.cbpaperwork.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperworkDataWrapper implements Parcelable {
    private int idCounter;

    private Map<String, PaperworkData> datas;


    public PaperworkDataWrapper() {
        idCounter = 0;
        datas = new HashMap<>();
    }

    public PaperworkData getData(String id) {
        if (datas.get(id) != null) {
            return datas.get(id);
        } else {
            throw new NullPointerException();
        }

    }

    public void addData(PaperworkData data) {
        datas.put(data.getId(), data);
    }

    public List<PaperworkData> getDatas() {
        return new ArrayList<>(datas.values());
    }


    public String getNewId() {
        idCounter += 1;
        return "" + this.idCounter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idCounter);
        dest.writeInt(this.datas.size());
        for (Map.Entry<String, PaperworkData> entry : this.datas.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    protected PaperworkDataWrapper(Parcel in) {
        this.idCounter = in.readInt();
        int datasSize = in.readInt();
        this.datas = new HashMap<String, PaperworkData>(datasSize);
        for (int i = 0; i < datasSize; i++) {
            String key = in.readString();
            PaperworkData value = in.readParcelable(PaperworkData.class.getClassLoader());
            this.datas.put(key, value);
        }
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
