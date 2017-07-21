package data;

import java.text.NumberFormat;
import java.util.Locale;

public class HTMLLoader {

    public static String getHtml(PaperworkData data) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String overshort = format.format(Math.abs(data.getOSValue(OverShort.OVER_SHORT)));
        String ifOverShortString;
        if(data.getOSValue(OverShort.OVER_SHORT) > 0){
            //positive  --  short
            ifOverShortString = "SHORT:";
        }else if (data.getOSValue(OverShort.OVER_SHORT) < 0){
            //negative -- over
            ifOverShortString = "OVER:";
        }else{
            //right on
            ifOverShortString = "RIGHT ON!!";
        }

        String needQuarters;
        if (data.needBankBagExtraQuarters()) {
            needQuarters = "+0.50";
        } else {
            needQuarters = "";
        }
        return
                "<html>\n" +
                        "<head></head>\n" +
                        "<body>\n" +
                        "<br><br><br>" +
                        "<table align =\"center\" cell-padding = \"5\">\n" +
                        "  <tr>\n" +
                        "    <td>Closers:</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">" + data.getCloser(0) + "</td>\n" +
                        "    <td> & </td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">" + data.getCloser(1) + "</td>\n" +
                        "</table>" +
                        "<br><br>" +
                        "<center>\n" +
                        "\n" +
                        "  <table>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Date: "+data.getDateString()+"</td>\n" +
                        "      <td></td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "<tr><td><br></td></tr>" +
                        "    <tr>\n" +
                        "      <td>Gross Sales:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.GROSS)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Gift Card Redeems:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.CARD_REDEEMS)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Gift Card Sales:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.CARD_SALES)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Adjusted Gross Sales:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.ADJUSTED_GROSS)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Voids:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.VOIDS)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Discounts:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.DISCOUNTS)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Adjusted Net Sales:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.ADJUSTED_NET)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Paid Out:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.PAID_OUT)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Credit Card:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getOSValue(OverShort.CARD_TOTAL)) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>Deposit:</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositTotal()) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "      <td>" + ifOverShortString+ "</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(Math.abs(data.getOSValue(OverShort.OVER_SHORT))) + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "  </table>\n" +
                        "<br><br>" +
                        "<hr>" +
                        "<p align = \"center\">Total Checks: " + format.format(data.getCheckTotal()) + "</p>\n" +
                        "<div align = \"center\">" +
                        "  <table cellpadding = \"5\" style=\" display: inline-block; margin:0 30px 30px 0; vertical-align:top\">\n" +
                        "<tr>\n" +
                        "      <td colspan=\"3\">Bills</td>\n" +
                        "      <td>"+format.format(data.getBillTotal())+"</td>\n" +
                        "    </tr>"+
                        "    <tr>\n" +
                        "      <td>$100</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.HUNDRED) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.HUNDRED) * 100) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>$50</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.FIFTY) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.FIFTY) * 50) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>$20</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.TWENTY) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.TWENTY) * 20) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>$10</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.TEN) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.TEN) * 10) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>$5</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.FIVE) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.FIVE) * 5) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>$2</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.TWO) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.TWO) * 2) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>$1</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.ONE) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.ONE) * 1) + "</td>\n" +
                        "    </tr>\n" +
                        "  </table>\n" +

                        "  <table cellpadding = \"5\" style=\" display: inline-block; margin:0 30px 30px 0; vertical-align:top\">\n" +
                        "<tr>\n" +
                        "      <td colspan=\"3\">Coins</td>\n" +
                        "      <td>"+ format.format(data.getCoinTotal())+"</td>\n" +
                        "    </tr>"+
                        "    <tr>\n" +
                        "      <td>1.00</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.DOLLAR_COIN) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.DOLLAR_COIN) * 1.00) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>0.50</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.FIFTY_CENT) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.FIFTY_CENT) * .50) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>0.25</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.QUARTER) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.QUARTER) * .25) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>0.10</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.DIME) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.DIME) * .10) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>0.05</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.NICKEL) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.NICKEL) * .05) + "</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>0.01</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + data.getDepositData(Deposit.PENNY) + "</td>\n" +
                        "      <td>=</td>\n" +
                        "      <td style =\"border-bottom:1px solid black;\">" + format.format(data.getDepositData(Deposit.PENNY) * .01) + "</td>\n" +
                        "    </tr>\n" +
                        "  </table>\n" +
                        "\n" +
                        "  <p align = \"center\">Deposit Total: "+format.format(data.getDepositTotal())+"</p>\n" +






                        "<div align = \"center\">\n" +
                        "<table cellpadding = \"10\" style=\" display: inline-block; margin:0 30px 30px 0;\">\n" +
                        "  <tr>\n" +
                        "    <td colspan=\"3\">Till Name:</td>\n" +
                        "    <td>" + data.getTillName(0) + "</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$20</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.TWENTY, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.TWENTY, 0)*20)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.TEN, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.TEN, 0)*10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$5</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.FIVE, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.FIVE, 0)*5)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$1</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.ONE, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.ONE, 0)*1)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.25</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.QUARTER, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.QUARTER, 0)*0.25)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.DIME, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.DIME, 0)*0.10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.05</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.NICKEL, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.NICKEL, 0)*0.05)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.01</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.PENNY, 0)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.PENNY, 0)*0.01)+"</td>\n" +
                        "  </tr>\n" +
                        "<tr>\n" +
                        "    <td colspan=\"3\" align = \"right\">Till Total:</td>\n" +
                        "    <td>"+format.format(data.getTillTotal(0))+"</td>\n" +
                        "  </tr>" +
                        "</table>\n" +
                        "\n" +
                        "\n" +
                        "<table cellpadding = \"10\" style=\" display: inline-block; margin:0 30px 30px 0;\">\n" +
                        "  <tr>\n" +
                        "    <td colspan=\"3\">Till Name:</td>\n" +
                        "    <td>" + data.getTillName(1) + "</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$20</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.TWENTY, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.TWENTY, 1)*20)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.TEN, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.TEN, 1)*10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$5</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.FIVE, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.FIVE, 1)*5)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$1</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">" + data.getTillData(Till.ONE, 1) + "</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.ONE, 1)*1)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.25</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.QUARTER, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.QUARTER, 1)*0.25)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.DIME, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.DIME, 1)*0.10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.05</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.NICKEL, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.NICKEL, 1)*0.05)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.01</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.PENNY, 1)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.PENNY, 1)*0.01)+"</td>\n" +
                        "  </tr>\n" +
                        "<tr>\n" +
                        "    <td colspan=\"3\" align = \"right\">Till Total:</td>\n" +
                        "    <td>"+format.format(data.getTillTotal(1))+"</td>\n" +
                        "  </tr>" +
                        "</table>\n" +
                        "\n" +
                        "<table cellpadding = \"10\" style=\" display: inline-block; margin:0 30px 30px 0;\">\n" +
                        "  <tr>\n" +
                        "    <td colspan=\"3\">Till Name:</td>\n" +
                        "    <td>" + data.getTillName(2) + "</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$20</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.TWENTY, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.TWENTY, 2)*20)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.TEN, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.TEN, 2)*10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$5</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.FIVE, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.FIVE, 2)*5)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$1</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.ONE, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.ONE, 2)*1)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.25</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.QUARTER, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.QUARTER, 2)*0.25)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.DIME, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.DIME, 2)*0.10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.05</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.NICKEL, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.NICKEL, 2)*0.05)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>0.01</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getTillData(Till.PENNY, 2)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getTillData(Till.PENNY, 2)*0.01)+"</td>\n" +
                        "  </tr>\n" +
                        "<tr>\n" +
                        "    <td colspan=\"3\" align = \"right\">Till Total:</td>\n" +
                        "    <td>"+format.format(data.getTillTotal(2))+"</td>\n" +
                        "  </tr>" +
                        "</table>\n" +
                        "\n" +
                        "<table cellpadding = \"10\" style=\" display: inline-block; margin:0 30px 30px 0;\">\n" +
                        "  <tr>\n" +
                        "    <td colspan=\"4\">Change Bag:</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$20</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.TWENTY)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.TWENTY)*20)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$10</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.TEN)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.TEN)*10)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$5</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.FIVE)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.FIVE)*5)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>$1</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.ONE)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.ONE))+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>QR</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.QUARTER)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.QUARTER)*10)+"</td>\n" +
                        "<td>" + needQuarters + "</td>" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>DR</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.DIME)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.DIME)*5)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>NR</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.NICKEL)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.NICKEL)*2)+"</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>PR</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+data.getBankBagData(BankBag.PENNY)+"</td>\n" +
                        "    <td>=</td>\n" +
                        "    <td style =\"border-bottom:1px solid black;\">"+format.format(data.getBankBagData(BankBag.PENNY)*0.50)+"</td>\n" +
                        "  </tr>\n" +
                        "<tr>\n" +
                        "    <td colspan=\"3\" align = \"right\">Change Bag Total:</td>\n" +
                        "    <td>"+format.format(data.getBankBagTotal())+"</td>\n" +
                        "  </tr>" +
                        "</table>\n" +
                        "</div>"+
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>\n";
    }
}
