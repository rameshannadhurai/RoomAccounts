package checking.app.sample.helper;

import android.content.Context;

import checking.app.sample.R;

public class CustomDialog {

    public static CustomProgressDialog getInstanse(Context context) {
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(context);
        customProgressDialog.setContentView(R.layout.loading_dialog);
        customProgressDialog.setCancelable(false);
        return customProgressDialog;
    }
}
