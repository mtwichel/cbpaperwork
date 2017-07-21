package com.example.android.cbpaperwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import data.PaperworkData;


public class ChangeClosersDialog extends DialogFragment {

    private ChangeClosersDialogListeners mListener;
    public static final String ARG_DATA = "arg_data";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        PaperworkData data = getArguments().getParcelable(ARG_DATA);

        View view = inflater.inflate(R.layout.dialog_change_closers, null);
        EditText closer0Enter = view.findViewById(R.id.closer0);
        EditText closer1Enter = view.findViewById(R.id.closer1);

        closer0Enter.setText(data.getCloser(0));
        closer1Enter.setText(data.getCloser(1));
        // Inflate and set the layout for the dialog

        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog) dialog;
                        EditText closer0Enter = f.findViewById(R.id.closer0);
                        EditText closer1Enter = f.findViewById(R.id.closer1);
                        String closer0 = closer0Enter.getText().toString();
                        String closer1 = closer1Enter.getText().toString();
                        mListener.onChangeClosersDialogPositiveClick(ChangeClosersDialog.this, closer0, closer1);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeClosersDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ChangeClosersDialogListeners) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface ChangeClosersDialogListeners {
        void onChangeClosersDialogPositiveClick(DialogFragment dialog, String c1, String c2);

    }
}
