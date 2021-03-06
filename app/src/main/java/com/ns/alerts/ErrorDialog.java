package com.ns.alerts;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by ashwani on 03/06/16.
 */
public class ErrorDialog extends DialogFragment {

    public ErrorDialog() {
        // Empty constructor required for DialogFragment
    }

    public static ErrorDialog newInstance(String title, String message) {
        ErrorDialog frag = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        if(message != null && message.contains("Failed to connect to")) {
            message = "Failed to connect to server.";
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        return alertDialogBuilder.create();
    }
}
