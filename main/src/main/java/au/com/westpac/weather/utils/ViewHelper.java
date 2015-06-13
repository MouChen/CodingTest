package au.com.westpac.weather.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import au.com.westpac.weather.R;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 4:17 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: An UI Helper class
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class ViewHelper {

    private final Context mContext;

    public ViewHelper(Context context) {
        this.mContext = context;
    }

    public Dialog getDialog() {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_loading_indicator);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(params);

        return dialog;
    }

    /**
     * Draw a native loading indicator while
     * async task is in progress
     */
    public Dialog drawLoadingIndicator() {
        final Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }
}
