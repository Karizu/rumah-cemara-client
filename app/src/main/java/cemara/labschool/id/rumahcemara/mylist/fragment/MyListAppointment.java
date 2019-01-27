package cemara.labschool.id.rumahcemara.mylist.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.asktheexpert.ChatExpertActivity;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.ChatAppointmentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListAppointment extends Fragment {

    Dialog dialog;

    public MyListAppointment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list_appointment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.chat_appointment)
    public void toChat() {
        startActivity(new Intent(getContext(), ChatAppointmentActivity.class));
    }

    @OnClick(R.id.row_appointment)
    public void toDetail() {
        showDialog(R.layout.dialog_appointment_detail);
    }

    private void showDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(getContext()));
        //SET TITLE
        dialog.setTitle("Detail Biological Appointment");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
