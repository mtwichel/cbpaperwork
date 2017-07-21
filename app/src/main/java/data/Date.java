package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Date implements Parcelable {

    private int year;
    private int month;
    private int day;

    private int yearPre;
    private int monthPre;
    private int dayPre;

    public Date() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    public static String getMonthName(int month){
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "We done fucked up";
        }
    }

    public String toId(){
        return "" +  day + month + year;
    }

    public String getDateString(){
        return "" + getMonthName(getMonth()) + " " + getDay() + ", " + getYear();
    }

    public void revertToPreDate(){
        day=dayPre;
        month=monthPre;
        year=yearPre;
    }

    public void setDate(int day, int month, int year) {
        dayPre = this.day;
        monthPre = this.month;
        yearPre = this.year;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.day);
        dest.writeInt(this.yearPre);
        dest.writeInt(this.monthPre);
        dest.writeInt(this.dayPre);
    }

    protected Date(Parcel in) {
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.yearPre = in.readInt();
        this.monthPre = in.readInt();
        this.dayPre = in.readInt();
    }

    public static final Parcelable.Creator<Date> CREATOR = new Parcelable.Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel source) {
            return new Date(source);
        }

        @Override
        public Date[] newArray(int size) {
            return new Date[size];
        }
    };
}
