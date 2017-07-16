package data;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.android.cbpaperwork.R.id.drawer_layout;
import static com.example.android.cbpaperwork.R.id.gross;
import static com.example.android.cbpaperwork.R.id.voids;

public class PaperworkData implements Parcelable {



    
    private OverShort overShort;
    private Date date;
    private List<Till> tills;
    private BankBag bankBag;
    private String id;


    public PaperworkData(String id) {
        date = new Date();
        overShort = new OverShort();
        tills = new ArrayList<Till>();
        for (int i = 0; i < 3; i++) {
            tills.add(new Till(i));
        }
        bankBag = new BankBag();
        this.id = id;

    }

    public String getId(){return this.id;}



    //Date Stuff
    public int getYear() {
        return date.getYear();
    }
    public int getMonth() {
        return date.getMonth();
    }
    public int getDay() {
        return date.getDay();
    }
    public String getDateString(){
        return date.getDateString();
    }
    public void revertToPreDate(){
        date.revertToPreDate();
    }
    public void setDate(int day, int month, int year){
        date.setDate(day, month, year);
    }





    //Deposit Stuff
    public void setDepositData(int CURRENCY, int value){
        overShort.setDepositData(CURRENCY, value);
    }
    public int getDepositData(int CURRENCY){
        return overShort.getDepositData(CURRENCY);
    }
    public void addCheck(Check check){
        overShort.addCheck(check);
    }
    public double getDepositTotal() {
        return overShort.getDepositTotal();
    }
    public List<Check> getChecks(){
        return overShort.getChecks();
    }
    public double getBillTotal(){return overShort.getBillTotal();}
    public double getCoinTotal(){return overShort.getCoinTotal();}
    public double getCheckTotal(){return  overShort.getCheckTotal();}





    //OverShort stuff
    public double getOSValue(int CURRENCY){
        return overShort.getOSValue(CURRENCY);
    }
    public void setOSValue(int CURRENCY, double value) {
       overShort.setOSValue(CURRENCY, value);
    }




    //Till stuff
    public int getTillData(int CURRENCY, int tillNumber){
        return tills.get(tillNumber).getData(CURRENCY);
    }
    public void setTillData(int CURRENCY, int amount, int tillNumber) {
        tills.get(tillNumber).setData(CURRENCY, amount);
    }
    public double getTillTotal(int tillNumber){
        return tills.get(tillNumber).getTotal();
    }




    //Bank Bag Stuff
    public int getBankBagData(int CURRENCY) {
        return bankBag.getData(CURRENCY);
    }
    public double getBankBagTotal() {
        return bankBag.getTotal();
    }
    public void setBankBagData(int CURRENCY, int amount) {
        bankBag.setData(CURRENCY, amount);
    }
    public boolean needBankBagExtraQuarters() {
        return bankBag.needExtraQuarters();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.overShort, flags);
        dest.writeParcelable(this.date, flags);
        dest.writeTypedList(this.tills);
        dest.writeParcelable(this.bankBag, flags);
        dest.writeString(this.id);
    }

    protected PaperworkData(Parcel in) {
        this.overShort = in.readParcelable(OverShort.class.getClassLoader());
        this.date = in.readParcelable(Date.class.getClassLoader());
        this.tills = in.createTypedArrayList(Till.CREATOR);
        this.bankBag = in.readParcelable(BankBag.class.getClassLoader());
        this.id = in.readString();
    }

    public static final Creator<PaperworkData> CREATOR = new Creator<PaperworkData>() {
        @Override
        public PaperworkData createFromParcel(Parcel source) {
            return new PaperworkData(source);
        }

        @Override
        public PaperworkData[] newArray(int size) {
            return new PaperworkData[size];
        }
    };
    public static class IDCounter{
        private static int idCounter = 0;
        public static int getId(){
            idCounter++;
            return idCounter;
        }
    }
}
