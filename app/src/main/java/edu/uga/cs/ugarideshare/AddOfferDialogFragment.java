package edu.uga.cs.ugarideshare;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class AddOfferDialogFragment extends DialogFragment {

    private ListOffersRequestsFragment hostFragment;
    private EditText startingPointView;
    private EditText endingPointView;
    private EditText dateView;

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_add_offer_dialog, getActivity().findViewById(R.id.root));
        startingPointView = layout.findViewById(R.id.editText1);
        endingPointView = layout.findViewById(R.id.editText2);
        dateView = layout.findViewById(R.id.editText3);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);

        builder.setTitle("New Ride Offer");
        builder.setNegativeButton(android.R.string.cancel, (dialog, whichButton) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton(android.R.string.ok, new ButtonClickListener());

        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String startingPoint = startingPointView.getText().toString();
            String endingPoint = endingPointView.getText().toString();
            String date = dateView.getText().toString();

            hostFragment.saveNewRideOffer(startingPoint, endingPoint, date);

            dismiss();
        }
    }
}