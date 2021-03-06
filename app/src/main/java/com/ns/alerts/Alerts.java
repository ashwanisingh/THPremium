package com.ns.alerts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


public class Alerts {

    /**
     * Shows a toast with the given text.
     */
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToastAtTop(Context context, int text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 20);
        toast.show();
    }

    public static void showToast(Context context, int text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToastAtTop(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 20);
        toast.show();
    }
    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    public static void showSnackbar(Activity activity, final String text) {
        View container = activity.findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    public static void showSnackbarInfinite(Activity activity, final int mainTextStringId, final int actionStringId,
                                            View.OnClickListener listener) {
        Snackbar.make(activity.findViewById(android.R.id.content),
                activity.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();
    }


    public static void showAlertDialogWithYesNo(final Context context, String message, final int alertID, final AlertDialogClickListener alertDialogClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if(alertDialogClickListener != null) {
                            alertDialogClickListener.alertDialogClickYes(alertID);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        if(alertDialogClickListener != null) {
                            alertDialogClickListener.alertDialogClickNo(alertID);
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogNoBtn(final Context context, String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setTitle(title);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogNoBtnWithCancel(final Context context, String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle(title);

        final AlertDialog alert = builder.create();
        alert.show();
    }


    public static void showErrorDailog(FragmentManager fm, String title, String message) {
        ErrorDialog calendarViewDialogFragment = ErrorDialog.newInstance(title, message);
        calendarViewDialogFragment.show(fm, "errorDialog");
    }

    public static void showAlertDialogOKBtn(final Context context, String title, String msg) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }



}
