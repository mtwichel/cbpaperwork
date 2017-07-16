package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.cbpaperwork.NewCheck;
import com.example.android.cbpaperwork.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import data.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DepositFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DepositFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DepositFrag extends Fragment implements TextWatcher {

    public static final String ARG_DATA = "data";

    // TODO: Rename and change types of parameters
    private PaperworkData data;

    private OnFragmentInteractionListener mListener;


    private List<String> checksDisplay = new ArrayList<String>();

    private EditText hundred;
    private EditText fifty;
    private EditText twenty;
    private EditText ten;
    private EditText five;
    private EditText two;
    private EditText one;
    private EditText dollarCoin;
    private EditText fiftyCent;
    private EditText quarter;
    private EditText dime;
    private EditText nickle;
    private EditText penny;

    private TextView bills;
    private TextView coins;
    private TextView totalDepoist;


    public DepositFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DepositFrag newInstance(PaperworkData data) {
        DepositFrag fragment = new DepositFrag();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposit, container, false);
        // Inflate the layout for this fragment
        Button addCheckButton = view.findViewById(R.id.addCheck);
        addCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewCheck.class);
                getActivity().startActivityForResult(intent, 2);
            }
        });

        hundred = (EditText) view.findViewById(R.id.hundredEntry);
        fifty = (EditText) view.findViewById(R.id.fiftyEntry);
        twenty = (EditText) view.findViewById(R.id.twentyEntry);
        ten = (EditText) view.findViewById(R.id.tenEntry);
        five = (EditText) view.findViewById(R.id.fiveEntry);
        two = (EditText) view.findViewById(R.id.twoEntry);
        one = (EditText) view.findViewById(R.id.oneEntry);
        dollarCoin = (EditText) view.findViewById(R.id.dollarCoinEntry);
        fiftyCent = (EditText) view.findViewById(R.id.fiftyCentEntry);
        quarter = (EditText) view.findViewById(R.id.quarterEntry);
        dime = (EditText) view.findViewById(R.id.dimeEntry);
        nickle = (EditText) view.findViewById(R.id.nickleEntry);
        penny = (EditText) view.findViewById(R.id.pennyEntry);

        totalDepoist = view.findViewById(R.id.totalDeposit);
        bills = view.findViewById(R.id.bills);
        coins = view.findViewById(R.id.coins);

        addListeners();

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {

            data = getArguments().getParcelable(ARG_DATA);
        }

        displayChecks();
        dispayValues();
        updateTotal();


    }

    private void getValues() {

        String[] strings = new String[13];

        strings[Deposit.HUNDRED] = hundred.getText().toString();
        strings[Deposit.FIFTY] = fifty.getText().toString();
        strings[Deposit.TWENTY] = twenty.getText().toString();
        strings[Deposit.TEN] = ten.getText().toString();
        strings[Deposit.FIVE] = five.getText().toString();
        strings[Deposit.TWO] = two.getText().toString();
        strings[Deposit.ONE] = one.getText().toString();
        strings[Deposit.DOLLAR_COIN] = dollarCoin.getText().toString();
        strings[Deposit.FIFTY_CENT] = fiftyCent.getText().toString();
        strings[Deposit.QUARTER] = quarter.getText().toString();
        strings[Deposit.DIME] = dime.getText().toString();
        strings[Deposit.NICKEL] = nickle.getText().toString();
        strings[Deposit.PENNY] = penny.getText().toString();

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("")) {
                strings[i] = "0";
            }
            strings[i].replaceAll("\\s+","");
        }

        for (int i = 0; i < 13; i++) {
            data.setDepositData(i, Integer.parseInt(strings[i]));
        }
        
    }



    private void dispayValues() {
        removeListeners();

        hundred.setText("" + data.getDepositData(Deposit.HUNDRED));
        fifty.setText("" + data.getDepositData(Deposit.FIFTY));
        twenty.setText("" + data.getDepositData(Deposit.TWENTY));
        ten.setText("" + data.getDepositData(Deposit.TEN));
        five.setText("" + data.getDepositData(Deposit.FIVE));
        two.setText("" + data.getDepositData(Deposit.TWO));
        one.setText("" + data.getDepositData(Deposit.ONE));
        dollarCoin.setText("" + data.getDepositData(Deposit.DOLLAR_COIN));
        fiftyCent.setText("" + data.getDepositData(Deposit.FIFTY_CENT));
        quarter.setText("" + data.getDepositData(Deposit.QUARTER));
        dime.setText("" + data.getDepositData(Deposit.DIME));
        nickle.setText("" + data.getDepositData(Deposit.NICKEL));
        penny.setText("" + data.getDepositData(Deposit.PENNY));

        addListeners();
        updateTotal();
    }

    private void updateTotal() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

        totalDepoist.setText("Total Deposit: " + format.format(data.getDepositTotal()));
        bills.setText("Bills: " + format.format(data.getBillTotal()));
        coins.setText("Coins: " + format.format(data.getCoinTotal()));
    }

    public void addListeners(){
        hundred.addTextChangedListener(this);
        fifty.addTextChangedListener(this);
        twenty.addTextChangedListener(this);
        ten.addTextChangedListener(this);
        five.addTextChangedListener(this);
        two.addTextChangedListener(this);
        one.addTextChangedListener(this);
        dollarCoin.addTextChangedListener(this);
        fiftyCent.addTextChangedListener(this);
        quarter.addTextChangedListener(this);
        dime.addTextChangedListener(this);
        nickle.addTextChangedListener(this);
        penny.addTextChangedListener(this);
    }
    public void removeListeners(){
        hundred.removeTextChangedListener(this);
        fifty.removeTextChangedListener(this);
        twenty.removeTextChangedListener(this);
        ten.removeTextChangedListener(this);
        five.removeTextChangedListener(this);
        two.removeTextChangedListener(this);
        one.removeTextChangedListener(this);
        dollarCoin.removeTextChangedListener(this);
        fiftyCent.removeTextChangedListener(this);
        quarter.removeTextChangedListener(this);
        dime.removeTextChangedListener(this);
        nickle.removeTextChangedListener(this);
        penny.removeTextChangedListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        getValues();
        mListener.onFragmentInteraction(this.getId(), data);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.d("Hello", "textChanged");

        getValues();
        updateTotal();


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int callerID, PaperworkData data);
    }

    public void displayChecks() {

        for (Check check : data.getChecks()) {
            if (check != null) {
                if (!checksDisplay.contains(check.toString())) {
                    checksDisplay.add(check.toString());
                }

            }
        }


        ListView listView = getView().findViewById(R.id.checks);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                checksDisplay);

        listView.setAdapter(arrayAdapter);

        resizeView(listView);

        updateTotal();
    }

    public void resizeView(ListView listView) {

        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.d("Hello", String.valueOf(totalHeight));

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}
