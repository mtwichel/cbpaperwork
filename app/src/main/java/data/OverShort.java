package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import static com.example.android.cbpaperwork.R.id.gross;
import static com.example.android.cbpaperwork.R.id.up;
import static com.example.android.cbpaperwork.R.id.voids;

public class OverShort implements Parcelable {

    public static final int GROSS = 0;
    public static final int CARD_REDEEMS = 1;
    public static final int CARD_SALES = 2;
    public static final int ADJUSTED_GROSS = 3;
    public static final int VOIDS = 4;
    public static final int DISCOUNTS = 5;
    public static final int ADJUSTED_NET = 6;
    public static final int VISA = 7;
    public static final int MASTER_CARD = 8;
    public static final int AMEX = 9;
    public static final int DISCOVER = 10;
    public static final int CARD_TOTAL = 11;
    public static final int PAID_OUT = 12;
    public static final int OVER_SHORT = 13;
    public static final boolean OVER = true;
    public static  final boolean SHORT = false;

    private final Deposit deposit;

    private boolean overShort;

    private double[] data;
    
    public OverShort() {
        data = new double[14];
        this.deposit = new Deposit();
    }
    
    public double getOSValue(int VALUE_TYPE){
        return data[VALUE_TYPE];
    }

    public void setOSValue(int VALUE_TYPE, double value) {
        data[VALUE_TYPE] = value;
        updatePaperwork();
    }
    public boolean isOverShort(){
        return overShort;
    }
    public void updatePaperwork(){

        deposit.updateDeposit();
        data[ADJUSTED_GROSS] = ((data[GROSS] + data[CARD_SALES]) - data[CARD_REDEEMS]);
        data[ADJUSTED_NET] = (data[ADJUSTED_GROSS] - data[DISCOUNTS] - data[VOIDS]);
        data[CARD_TOTAL] = data[VISA] + data[MASTER_CARD] + data[AMEX] + data[DISCOVER];
        data[OVER_SHORT] = (data[ADJUSTED_NET] - deposit.getTotalDeposit() - data[PAID_OUT] - data[CARD_TOTAL]);
    }

    //Deposit Stuff
    public void setDepositData(int CURRENCY, int value){
        deposit.setCurrencyNumber(CURRENCY, value);
    }
    public int getDepositData(int CURRENCY){
        return deposit.getCurrencyNumber(CURRENCY);
    }
    public void addCheck(Check check){
        deposit.addCheck(check);
    }
    public double getDepositTotal() {
        return deposit.getTotalDeposit();
    }
    public List<Check> getChecks(){
        return deposit.getChecks();
    }
    public double getBillTotal(){return deposit.getBillTotal();}
    public double getCoinTotal(){return deposit.getCoinTotal();}
    public double getCheckTotal(){return  deposit.getCheckTotal();}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.deposit, flags);
        dest.writeByte(this.overShort ? (byte) 1 : (byte) 0);
        dest.writeDoubleArray(this.data);
    }

    protected OverShort(Parcel in) {
        this.deposit = in.readParcelable(Deposit.class.getClassLoader());
        this.overShort = in.readByte() != 0;
        this.data = in.createDoubleArray();
    }

    public static final Creator<OverShort> CREATOR = new Creator<OverShort>() {
        @Override
        public OverShort createFromParcel(Parcel source) {
            return new OverShort(source);
        }

        @Override
        public OverShort[] newArray(int size) {
            return new OverShort[size];
        }
    };
}
