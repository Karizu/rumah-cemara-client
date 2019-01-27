package cemara.labschool.id.rumahcemara.util.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import java.util.Objects;

import cemara.labschool.id.rumahcemara.R;

public class Loading{
    private static Dialog dialog;
    public static void show(final Context context){
        if(!isShowing(context)) {
            dialog = new Dialog(context);
            //SET TITLE
            dialog.setTitle("Loading");

            //set content
            dialog.setContentView(R.layout.dialog_progress);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }
    }
    public static void hide(final Context context){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public static boolean isShowing(final Context context)
    {
        if(dialog != null){
            return dialog.isShowing();
        }
        return false;
    }

}