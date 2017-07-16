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

import data.BankBag;
import data.PaperworkData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BankBagFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BankBagFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankBagFrag extends Fragment implements TextWatcher {


    public OnFragmentInteractionListener mListener;
    public static final String ARG_DATA = "data";


    private EditText twenty;
    private EditText ten;
    private EditText five;
    private EditText one;
    private EditText quarter;
    private EditText dime;
    private EditText nickle;
    private EditText penny;
    
    private TextView bankBagTotal;
    TextView needQuarters;
    
    private PaperworkData data;

    public BankBagFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BankBagFrag newInstance(String param1, String param2) {
        BankBagFrag fragment = new BankBagFrag();
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
        getBankBag();
        View view = inflater.inflate(R.layout.fragment_bank_bag, container, false);



        twenty = (EditText) view.findViewById(R.id.twentyBankBagEntry);
        ten = (EditText) view.findViewById(R.id.tenBankBagEntry);
        five = (EditText) view.findViewById(R.id.fiveBankBagEntry);
        one = (EditText) view.findViewById(R.id.oneBankBagEntry);
        quarter = (EditText) view.findViewById(R.id.quarterBankBagEntry);
        dime = (EditText) view.findViewById(R.id.dimeBankBagEntry);
        nickle = (EditText) view.findViewById(R.id.nickelBankBagEntry);
        penny = (EditText) view.findViewById(R.id.pennyBankBagEntry);


        needQuarters = view.findViewById(R.id.quarterWarning);
        bankBagTotal = view.findViewById(R.id.bankBagTotal);
        
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


    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.d("FUCK", "Text CHanged");
        getValues();
        displayTotal();
    }

    private void displayValues() {

        removeListeners();

        twenty.setText("" + data.getBankBagData(BankBag.TWENTY));
        ten.setText("" + data.getBankBagData(BankBag.TEN));
        five.setText("" + data.getBankBagData(BankBag.FIVE));
        one.setText("" + data.getBankBagData(BankBag.ONE));
        quarter.setText("" + data.getBankBagData(BankBag.QUARTER));
        dime.setText("" + data.getBankBagData(BankBag.DIME));
        nickle.setText("" + data.getBankBagData(BankBag.NICKEL));
        penny.setText("" + data.getBankBagData(BankBag.PENNY));

        displayTotal();

        addListeners();
    }

    private void displayTotal() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        bankBagTotal.setText("Total: " + format.format(data.getBankBagTotal()));

        if(data.needBankBagExtraQuarters()){
            needQuarters.setVisibility(TextView.VISIBLE);
        }else{
            needQuarters.setVisibility(TextView.INVISIBLE);
        }
    }


    private void getValues() {
        String[] strings = new String[8];

        strings[BankBag.TWENTY] = twenty.getText().toString();
        strings[BankBag.TEN] = ten.getText().toString();
        strings[BankBag.FIVE] = five.getText().toString();
        strings[BankBag.ONE] = one.getText().toString();
        strings[BankBag.QUARTER] = quarter.getText().toString();
        strings[BankBag.DIME] = dime.getText().toString();
        strings[BankBag.NICKEL] = nickle.getText().toString();
        strings[BankBag.PENNY] = penny.getText().toString();


        //Log.d("Hi", "getValues" + strings[0].equals(""));

        for (int i = 0; i < strings.length; i++) {
            if(strings[i].equals("")){
                strings[i] = "0";
            }
            strings[i].replaceAll("\\s+","");
        }


        data.setBankBagData(BankBag.TWENTY, Integer.parseInt(strings[BankBag.TWENTY]));
        data.setBankBagData(BankBag.TEN, Integer.parseInt(strings[BankBag.TEN]));
        data.setBankBagData(BankBag.FIVE, Integer.parseInt(strings[BankBag.FIVE]));
        data.setBankBagData(BankBag.ONE, Integer.parseInt(strings[BankBag.ONE]));
        data.setBankBagData(BankBag.QUARTER, Integer.parseInt(strings[BankBag.QUARTER]));
        data.setBankBagData(BankBag.DIME, Integer.parseInt(strings[BankBag.DIME]));
        data.setBankBagData(BankBag.NICKEL, Integer.parseInt(strings[BankBag.NICKEL]));
        data.setBankBagData(BankBag.PENNY, Integer.parseInt(strings[BankBag.PENNY]));


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
    public void onResume() {
        super.onResume();
        getBankBag();
    }

    public void getBankBag(){
        if (getArguments() != null) {
            data = getArguments().getParcelable(ARG_DATA);
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
        void onFragmentInteraction(int callerId, PaperworkData data);
    }
}
