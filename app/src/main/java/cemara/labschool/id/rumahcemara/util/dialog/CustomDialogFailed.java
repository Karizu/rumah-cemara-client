package cemara.labschool.id.rumahcemara.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;

public class CustomDialogFailed extends Dialog implements View.OnClickListener {


    public Activity c;
    public Dialog d;
    public TextView yes, myList;
    Context context;

    public CustomDialogFailed(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_failed);
        yes = (TextView) findViewById(R.id.appointment_retry);
        myList = (TextView) findViewById(R.id.appointment_ok);
        yes.setOnClickListener(this);
        myList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointment_retry:
                d.findViewById(R.id.btn_send_appointment).callOnClick();
                break;
            case R.id.appointment_ok:
                d.dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}