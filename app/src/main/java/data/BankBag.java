package data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mtwichel on 7/5/17.
 */

public class BankBag implements Parcelable {
    private int[] data;

    public static final int QUARTER = 0;
    public static final int DIME = 1;
    public static final int NICKEL = 2;
    public static final int PENNY = 3;
    public static final int TWENTY = 4;
    public static final int TEN = 5;
    public static final int FIVE = 6;
    public static final int ONE = 7;

    private int total;

    private boolean needExtraQuarters;


    public BankBag() {
        total = 0;
        data = new int[8];
    }

    public int getData(int CURRENCY) {
        return data[CURRENCY];
    }

    public double getTotal() {
        updateTotal();
        return this.total;
    }
    public void setData(int CURRENCY, int amount) {
        this.data[CURRENCY] = amount;
    }

    private void updateTotal() {
        total = 0;
        total +=
                (data[Till.QUARTER] * 10) +
                        (data[Till.DIME] * 5) +
                        (data[Till.NICKEL] * 2) +
                        (data[Till.TWENTY] * 20) +
                        (data[Till.TEN] * 10) +
                        (data[Till.FIVE] * 5) +
                        (data[Till.ONE] * 1);

        if(data[Till.PENNY] % 2 ==0){
            //pennys even
            needExtraQuarters = false;
            total += (data[Till.PENNY]/2) * 1;
        }else {
            //pennys odd
            needExtraQuarters = true;
            total += ((data[Till.PENNY] + 1) / 2) * 1;
        }
    }

    public boolean needExtraQuarters() {
        return needExtraQuarters;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.data);
        dest.writeInt(this.total);
        dest.writeByte(this.needExtraQuarters ? (byte) 1 : (byte) 0);
    }

    protected BankBag(Parcel in) {
        this.data = in.createIntArray();
        this.total = in.readInt();
        this.needExtraQuarters = in.readByte() != 0;
    }

    public static final Parcelable.Creator<BankBag> CREATOR = new Parcelable.Creator<BankBag>() {
        @Override
        public BankBag createFromParcel(Parcel source) {
            return new BankBag(source);
        }

        @Override
        public BankBag[] newArray(int size) {
            return new BankBag[size];
        }
    };
}
