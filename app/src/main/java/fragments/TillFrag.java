package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cbpaperwork.R;

import data.PaperworkData;

import static fragments.OneTillFrag.ARG_TILL_DATA;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TillFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TillFrag#} factory method to
 * create an instance of this fragment.
 */
public class TillFrag extends Fragment {
    public final static String ARG_DATA = "till_data";




    private OnFragmentInteractionListener mListener;

    public OneTillFrag till1;
    public OneTillFrag till2;
    public OneTillFrag till3;
    private PaperworkData data;


    public TillFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_till, container, false);

        getTills();

        till1 = new OneTillFrag();
        till2 = new OneTillFrag();
        till3 = new OneTillFrag();

        Bundle till1Bundle = new Bundle();
        till1Bundle.putInt(OneTillFrag.ARG_TILL_NUMBER, 0);
        till1Bundle.putParcelable(ARG_TILL_DATA, data);
        till1.setArguments(till1Bundle);

        Bundle till2Bundle = new Bundle();
        till2Bundle.putInt(OneTillFrag.ARG_TILL_NUMBER, 1);
        till2Bundle.putParcelable(ARG_TILL_DATA, data);
        till2.setArguments(till2Bundle);

        Bundle till3Bundle = new Bundle();
        till3Bundle.putInt(OneTillFrag.ARG_TILL_NUMBER, 2);
        till3Bundle.putParcelable(ARG_TILL_DATA, data);
        till3.setArguments(till3Bundle);


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.till_content_frame, till1);
        ft.add(R.id.till_content_frame, till2);
        ft.add(R.id.till_content_frame, till3);
        ft.commit();

        return view;
    }


    public void onResume() {
        super.onResume();
        getTills();
    }

    public void getTills(){
        if (getArguments() != null) {
            data = getArguments().getParcelable(ARG_DATA);

        }
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
    public void onPause() {
        super.onPause();
        till1.mListener.onFragmentInteraction(till1.getId(), data);
        till2.mListener.onFragmentInteraction(till2.getId(), data);
        till3.mListener.onFragmentInteraction(till3.getId(), data);
        
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
        void onFragmentInteraction(int id, PaperworkData data);
    }
}
