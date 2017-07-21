package data;

import android.os.Parcel;
import android.os.Parcelable;

public class Till implements Parcelable {

    private int[] data;

    public static final int QUARTER = 0;
    public static final int DIME = 1;
    public static final int NICKEL = 2;
    public static final int PENNY = 3;
    public static final int TWENTY = 4;
    public static final int TEN = 5;
    public static final int FIVE = 6;
    public static final int ONE = 7;

    private int tillNumber;
    private String tillName;

    private double total;

    public Till(int tillNumber, String tillName) {
        total = 0.0;
        data = new int[8];
        this.tillNumber = tillNumber;
        this.tillName = tillName;
    }

    public int getData(int currency) {
        return data[currency];
    }

    public void setData(int currency, int amount) {
        this.data[currency] = amount;
        updateTotal();
    }

    public int getTillNumber() {
        return tillNumber;
    }

    public double getTotal() {
        updateTotal();
        return this.total;
    }

    private void updateTotal() {
        total = 0;
        total +=
                data[Till.QUARTER] * 0.25 +
                        data[Till.DIME] * 0.10 +
                        data[Till.NICKEL] * 0.05 +
                        data[Till.PENNY] * 0.01 +
                        data[Till.TWENTY] * 20 +
                        data[Till.TEN] * 10 +
                        data[Till.FIVE] * 5 +
                        data[Till.ONE] * 1;
    }

    public String getTillName() {
        return tillName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.data);
        dest.writeInt(this.tillNumber);
        dest.writeString(this.tillName);
        dest.writeDouble(this.total);
    }

    protected Till(Parcel in) {
        this.data = in.createIntArray();
        this.tillNumber = in.readInt();
        this.tillName = in.readString();
        this.total = in.readDouble();
    }

    public static final Creator<Till> CREATOR = new Creator<Till>() {
        @Override
        public Till createFromParcel(Parcel source) {
            return new Till(source);
        }

        @Override
        public Till[] newArray(int size) {
            return new Till[size];
        }
    };
}