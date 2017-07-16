package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Deposit implements Parcelable {

    public static final int HUNDRED = 0;
    public static final int FIFTY = 1;
    public static final int TWENTY = 2;
    public static final int TEN = 3;
    public static final int FIVE = 4;
    public static final int TWO = 5;
    public static final int ONE = 6;
    public static final int DOLLAR_COIN = 7;
    public static final int FIFTY_CENT = 8;
    public static final int QUARTER = 9;
    public static final int DIME = 10;
    public static final int NICKEL = 11;
    public static final int PENNY = 12;

    private int[] data;

    private double checkTotal;
    private double billTotal;
    private double coinTotal;

    private List<Check> checks;

    private double totalDeposit;

    public Deposit() {
        checks = new ArrayList<Check>();
        data = new int[13];

    }

    public void setCurrencyNumber(int currencyType, int value) {
        data[currencyType] = value;
        updateDeposit();
    }

    public int getCurrencyNumber(int currencyType) {
        return data[currencyType];
    }

    public double getBillTotal() {
        updateDeposit();
        return billTotal;
    }

    public double getCheckTotal() {
        updateDeposit();
        return checkTotal;
    }

    public double getCoinTotal() {
        updateDeposit();
        return coinTotal;
    }

    public double getTotalDeposit() {
        updateDeposit();
        return totalDeposit;
    }

    public void updateDeposit() {
        totalDeposit = checkTotal = billTotal = coinTotal = 0;

        totalDeposit += (data[PENNY] * 0.01) +
                (data[NICKEL] * 0.05) +
                (data[DIME] * 0.1) +
                (data[QUARTER] * 0.25) +
                (data[FIFTY_CENT] * 0.5) +
                (data[DOLLAR_COIN]) +
                (data[ONE]) +
                (data[TWO] * 2) +
                (data[FIVE] * 5) +
                (data[TEN] * 10) +
                (data[TWENTY] * 20) +
                (data[FIFTY] * 50) +
                (data[HUNDRED] * 100);
        for (int i = 0; i < checks.size(); i++) {
            totalDeposit += checks.get(i).getAmount();
        }

        billTotal += (data[ONE]) +
                (data[TWO] * 2) +
                (data[FIVE] * 5) +
                (data[TEN] * 10) +
                (data[TWENTY] * 20) +
                (data[FIFTY] * 50) +
                (data[HUNDRED] * 100);


        coinTotal += (data[PENNY] * 0.01) +
                (data[NICKEL] * 0.05) +
                (data[DIME] * 0.1) +
                (data[QUARTER] * 0.25) +
                (data[FIFTY_CENT] * 0.5) +
                (data[DOLLAR_COIN]);
    }

    public void addCheck(Check check) {
        checks.add(check);
        updateDeposit();
    }

    public List<Check> getChecks() {
        return this.checks;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.data);
        dest.writeDouble(this.checkTotal);
        dest.writeDouble(this.billTotal);
        dest.writeDouble(this.coinTotal);
        dest.writeTypedList(this.checks);
        dest.writeDouble(this.totalDeposit);
    }

    protected Deposit(Parcel in) {
        this.data = in.createIntArray();
        this.checkTotal = in.readDouble();
        this.billTotal = in.readDouble();
        this.coinTotal = in.readDouble();
        this.checks = in.createTypedArrayList(Check.CREATOR);
        this.totalDeposit = in.readDouble();
    }

    public static final Parcelable.Creator<Deposit> CREATOR = new Parcelable.Creator<Deposit>() {
        @Override
        public Deposit createFromParcel(Parcel source) {
            return new Deposit(source);
        }

        @Override
        public Deposit[] newArray(int size) {
            return new Deposit[size];
        }
    };
}
