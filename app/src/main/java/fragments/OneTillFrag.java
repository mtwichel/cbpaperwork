package fragments;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.cbpaperwork.R;

import data.PaperworkData;
import data.Till;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OneTillFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneTillFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneTillFrag extends Fragment implements TextWatcher {


    public OnFragmentInteractionListener mListener;
    public static final String ARG_TILL_NUMBER = "till_number";
    public static final String ARG_TILL_DATA = "till_data";


    private EditText twenty;
    private EditText ten;
    private EditText five;
    private EditText one;
    private EditText quarter;
    private EditText dime;
    private EditText nickle;
    private EditText penny;

    private TextView tillHeader;
    private TextView tillTotal;

    private int tillNumber;
    private PaperworkData data;
    private String tillName;

    public OneTillFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OneTillFrag newInstance(String param1, String param2) {
        OneTillFrag fragment = new OneTillFrag();
        Bundle args = new Bundle();

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
        if (getArguments() != null) {
            tillNumber = getArguments().getInt(ARG_TILL_NUMBER);

            data = getArguments().getParcelable(ARG_TILL_DATA);
            tillName = data.getTillName(tillNumber);
        }
        Log.i("Main", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_one_till, container, false);


        twenty = view.findViewById(R.id.twentyTillEntry);
        ten = view.findViewById(R.id.tenTillEntry);
        five = view.findViewById(R.id.fiveTillEntry);
        one = view.findViewById(R.id.oneTillEntry);
        quarter = view.findViewById(R.id.quarterTillEntry);
        dime = view.findViewById(R.id.dimeTillEntry);
        nickle = view.findViewById(R.id.nickelTillEntry);
        penny = view.findViewById(R.id.pennyTillEntry);

        tillHeader = view.findViewById(R.id.tillHeader);
        tillTotal = view.findViewById(R.id.tillTotal);

        tillHeader.setText("Till Name: " + tillName);
        displayValues();

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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        getValues();
        displayTotal();

    }

    @Override
    public void afterTextChanged(Editable editable) {
//        getValues();
//        displayValues();
    }

    private void displayValues() {

        removeListeners();

        twenty.setText("" + data.getTillData(Till.TWENTY, tillNumber));
        ten.setText("" + data.getTillData(Till.TEN, tillNumber));
        five.setText("" + data.getTillData(Till.FIVE, tillNumber));
        one.setText("" + data.getTillData(Till.ONE, tillNumber));
        quarter.setText("" + data.getTillData(Till.QUARTER, tillNumber));
        dime.setText("" + data.getTillData(Till.DIME, tillNumber));
        nickle.setText("" + data.getTillData(Till.NICKEL, tillNumber));
        penny.setText("" + data.getTillData(Till.PENNY, tillNumber));
        Log.i("Main", " " + data.getTillData(Till.PENNY, tillNumber));

       displayTotal();

        addListeners();
    }

    private void displayTotal() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        tillTotal.setText("Total: " + format.format(data.getTillTotal(tillNumber)));
    }


    private void getValues() {
        String[] strings = new String[8];

        strings[Till.TWENTY] = twenty.getText().toString();
        strings[Till.TEN] = ten.getText().toString();
        strings[Till.FIVE] = five.getText().toString();
        strings[Till.ONE] = one.getText().toString();
        strings[Till.QUARTER] = quarter.getText().toString();
        strings[Till.DIME] = dime.getText().toString();
        strings[Till.NICKEL] = nickle.getText().toString();
        strings[Till.PENNY] = penny.getText().toString();


        //Log.d("Hi", "getValues" + strings[0].equals(""));

        for (int i = 0; i < strings.length; i++) {
            if(strings[i].equals("")){
                strings[i] = "0";
            }
            strings[i].replaceAll("\\s+","");
        }


        for (int i = 0; i < 8; i++) {
            data.setTillData(i, Integer.parseInt(strings[i]), tillNumber);
        }
    }

    public void addListeners(){
        twenty.addTextChangedListener(this);
        ten.addTextChangedListener(this);
        five.addTextChangedListener(this);
        one.addTextChangedListener(this);
        quarter.addTextChangedListener(this);
        dime.addTextChangedListener(this);
        nickle.addTextChangedListener(this);
        penny.addTextChangedListener(this);
    }
    public void removeListeners(){
        twenty.removeTextChangedListener(this);
        ten.removeTextChangedListener(this);
        five.removeTextChangedListener(this);
        one.removeTextChangedListener(this);
        quarter.removeTextChangedListener(this);
        dime.removeTextChangedListener(this);
        nickle.removeTextChangedListener(this);
        penny.removeTextChangedListener(this);
    }



    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            tillNumber = getArguments().getInt(ARG_TILL_NUMBER);
            data = getArguments().getParcelable(ARG_TILL_DATA);
            tillName = data.getTillName(tillNumber);
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        mListener.onFragmentInteraction(this.getId(), data);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int callerID, PaperworkData data);
    }
}
