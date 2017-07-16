package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.cbpaperwork.R;

import java.util.Locale;

import data.OverShort;
import data.PaperworkData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverShortFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverShortFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverShortFrag extends Fragment implements TextWatcher{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_DATA = "data";


    private PaperworkData data;

    private OnFragmentInteractionListener mListener;

    
    private EditText gross;
    private EditText cardRedeems;
    private EditText cardSales;
    private TextView adjustedGross;
    private EditText voids;
    private EditText discounts;
    private TextView adjustedNet;
    private TextView deposit;
    private EditText visa;
    private EditText masterCard;
    private EditText amex;
    private EditText discover;
    private EditText paidOut;
    private TextView overShort;
    private TextView overShortDisplay;

    public OverShortFrag() {
        // Required empty public constructor
    }

    
    public static OverShortFrag newInstance(String param1, String param2) {
        OverShortFrag fragment = new OverShortFrag();
        Bundle args = new Bundle();
        args.putString(ARG_DATA, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_over_short, container, false);

        
        gross = (EditText) view.findViewById(R.id.grossEntry);
        cardRedeems = (EditText) view.findViewById(R.id.card_redeemsEntry);
        cardSales = (EditText) view.findViewById(R.id.card_salesEntry);
        adjustedGross = (TextView) view.findViewById(R.id.adjusted_gross_display);
        voids = (EditText) view.findViewById(R.id.voidsEntry);
        discounts = (EditText) view.findViewById(R.id.discountsEntry);
        adjustedNet = (TextView) view.findViewById(R.id.adjusted_net_display);
        deposit = view.findViewById(R.id.deposit_display);
        visa = (EditText) view.findViewById(R.id.visaEntry);
        masterCard = (EditText) view.findViewById(R.id.master_cardEntry);
        amex = (EditText) view.findViewById(R.id.amexEntry);
        discover = (EditText) view.findViewById(R.id.discoverEntry);
        paidOut = (EditText) view.findViewById(R.id.paid_outEntry);
        overShort = (TextView) view.findViewById(R.id.over_short_display);
        overShortDisplay = (TextView) view.findViewById(R.id.over_short);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            data = getArguments().getParcelable(ARG_DATA);
        }

        dispayValues();


        
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mListener.onFragmentInteraction(this.getId(), data);
    }

    private void getValues() {

        String[] strings = new String[14];

        strings[OverShort.GROSS] = gross.getText().toString();
        strings[OverShort.CARD_REDEEMS] = cardRedeems.getText().toString();
        strings[OverShort.CARD_SALES] = cardSales.getText().toString();
        strings[OverShort.VOIDS] = voids.getText().toString();
        strings[OverShort.DISCOUNTS] = discounts.getText().toString();
        strings[OverShort.VISA] = visa.getText().toString();
        strings[OverShort.MASTER_CARD] = masterCard.getText().toString();
        strings[OverShort.AMEX] = amex.getText().toString();
        strings[OverShort.DISCOVER] = discover.getText().toString();
        strings[OverShort.PAID_OUT] = paidOut.getText().toString();


        for (int i = 0; i < strings.length; i++) {
            if(strings[i] != null) {
                if (strings[i].equals("")) {
                    strings[i] = "0.0";
                }
                strings[i].replaceAll("\\s+", "");
            }
        }

        data.setOSValue(OverShort.GROSS, Double.parseDouble(strings[OverShort.GROSS]));
        data.setOSValue(OverShort.CARD_REDEEMS, Double.parseDouble(strings[OverShort.CARD_REDEEMS]));
        data.setOSValue(OverShort.CARD_SALES, Double.parseDouble(strings[OverShort.CARD_SALES]));
        data.setOSValue(OverShort.VOIDS, Double.parseDouble(strings[OverShort.VOIDS]));
        data.setOSValue(OverShort.DISCOUNTS, Double.parseDouble(strings[OverShort.DISCOUNTS]));
        data.setOSValue(OverShort.VISA, Double.parseDouble(strings[OverShort.VISA]));
        data.setOSValue(OverShort.MASTER_CARD, Double.parseDouble(strings[OverShort.MASTER_CARD]));
        data.setOSValue(OverShort.AMEX, Double.parseDouble(strings[OverShort.AMEX]));
        data.setOSValue(OverShort.DISCOVER, Double.parseDouble(strings[OverShort.DISCOVER]));
        data.setOSValue(OverShort.PAID_OUT, Double.parseDouble(strings[OverShort.PAID_OUT]));
    }

    private void dispayValues() {
        removeListeners();

        gross.setText("" + data.getOSValue(OverShort.GROSS));
        cardRedeems.setText("" + data.getOSValue(OverShort.CARD_REDEEMS));
        cardSales.setText("" + data.getOSValue(OverShort.CARD_SALES));
        voids.setText("" + data.getOSValue(OverShort.VOIDS));
        discounts.setText("" + data.getOSValue(OverShort.DISCOUNTS));
        visa.setText("" + data.getOSValue(OverShort.VISA));
        masterCard.setText("" + data.getOSValue(OverShort.MASTER_CARD));
        amex.setText("" + data.getOSValue(OverShort.AMEX));
        discover.setText("" + data.getOSValue(OverShort.DISCOVER));
        paidOut.setText("" + data.getOSValue(OverShort.PAID_OUT));

        addListeners();
        getValues();
        updateText();
    }

    private void addListeners() {
        gross.addTextChangedListener(this);
        cardRedeems.addTextChangedListener(this);
        cardSales.addTextChangedListener(this);
        voids.addTextChangedListener(this);
        discounts.addTextChangedListener(this);
        visa.addTextChangedListener(this);
        masterCard.addTextChangedListener(this);
        amex.addTextChangedListener(this);
        discover.addTextChangedListener(this);
        paidOut.addTextChangedListener(this);
    }

    private void removeListeners() {
        gross.removeTextChangedListener(this);
        cardRedeems.removeTextChangedListener(this);
        cardSales.removeTextChangedListener(this);
        voids.removeTextChangedListener(this);
        discounts.removeTextChangedListener(this);
        visa.removeTextChangedListener(this);
        masterCard.removeTextChangedListener(this);
        amex.removeTextChangedListener(this);
        discover.removeTextChangedListener(this);
        paidOut.removeTextChangedListener(this);
    }

    public void updateText(){

        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(Locale.US);
       
        adjustedGross.setText(format.format(data.getOSValue(OverShort.ADJUSTED_GROSS)));
        adjustedNet.setText(format.format(data.getOSValue(OverShort.ADJUSTED_NET)));
        deposit.setText(format.format(data.getDepositTotal()));
        overShort.setText(format.format(Math.abs(data.getOSValue(OverShort.OVER_SHORT))));
        if(data.getOSValue(OverShort.OVER_SHORT) < 0){
            overShortDisplay.setText("Over");
        }else if (data.getOSValue(OverShort.OVER_SHORT) > 0){
            overShortDisplay.setText("Short");
        }else{
            overShortDisplay.setText("RIGHT ON!");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        getValues();
        updateText();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int callerId, PaperworkData data);
    }
}
