package data;

import android.icu.text.NumberFormat;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by mtwichel on 7/2/17.
 */

public class Check implements Parcelable {

    private String checkNumber;
    private String checkName;
    private double amount;

    public Check(String checkNumber, String checkName, double amount) {
        this.checkNumber = checkNumber;
        this.checkName = checkName;
        this.amount = amount;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getCheckName() {
        return checkName;
    }

    public double getAmount() {
        return amount;
    }




    @Override
    public String toString() {
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(Locale.US);
        String currency = format.format(amount);
        return "Check " +
                 checkNumber + ":   " + currency  +
                "   (" + checkName + ")"
                ;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.checkNumber);
        dest.writeString(this.checkName);
        dest.writeDouble(this.amount);
    }

    protected Check(Parcel in) {
        this.checkNumber = in.readString();
        this.checkName = in.readString();
        this.amount = in.readDouble();
    }

    public static final Parcelable.Creator<Check> CREATOR = new Parcelable.Creator<Check>() {
        @Override
        public Check createFromParcel(Parcel source) {
            return new Check(source);
        }

        @Override
        public Check[] newArray(int size) {
            return new Check[size];
        }
    };
}
