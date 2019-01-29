package cemara.labschool.id.rumahcemara.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.AppointmentFormActivity;

public class CustomDialogSuccess extends Dialog implements android.view.View.OnClickListener {


    public Activity c;
    public Dialog d;
    public TextView yes, myList;
    Context context;

    public CustomDialogSuccess(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_success);
        yes = (TextView) findViewById(R.id.appointment_ok);
        myList = (TextView) findViewById(R.id.appointment_gotomylist);
        yes.setOnClickListener(this);
        myList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointment_ok:
                Intent intent = new Intent(c, MainActivity.class);
                intent.putExtra("frag", "homeFragment");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
                break;
            case R.id.appointment_gotomylist:
                Intent mylist = new Intent(c, MainActivity.class);
                mylist.putExtra("frag", "mylistfragment");
                mylist.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(mylist);
                break;
            default:
                break;
        }
        dismiss();
    }
}