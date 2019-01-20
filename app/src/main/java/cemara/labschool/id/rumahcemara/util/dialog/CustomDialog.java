package cemara.labschool.id.rumahcemara.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import cemara.labschool.id.rumahcemara.R;

public class CustomDialog extends Dialog  {
    public int layout;
    private Context mContext;

    public CustomDialog(Context mContext, int layout) {
        super(mContext);
        this.mContext = mContext;
        this.layout = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        //SET TITLE
        dialog.setTitle("Biomedical");

        //set content
        dialog.setContentView(R.layout.dialog_biomedical);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();

    }
}