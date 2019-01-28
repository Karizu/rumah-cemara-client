package cemara.labschool.id.rumahcemara.mylist.fragment.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;

public class AppointmentListHistory extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
//    @BindView(R.id.layout_completed)
//    LinearLayout layoutCompleted;
//    @BindView(R.id.layout_completed1)
//    LinearLayout layoutCompleted1;
//    @BindView(R.id.layout_cancel)
//    LinearLayout layoutCancel;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list_appointment_history_activity);
        ButterKnife.bind(this);
        setToolbar();
//        layoutCompleted.setOnClickListener(v1 -> startActivity(new Intent(AppointmentListHistory.this, MyListBiomedicalDetail.class)));
    }

    @OnClick(R.id.row_appointment)
    public void rating() {
        showRatingDialog(R.layout.my_list_appointment_history_dialog);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener((ratingBar1, v, b) -> {
            switch ((int) ratingBar1.getRating()) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
            }
        });
        ImageView close = dialog.findViewById(R.id.ic_close);
        close.setOnClickListener(view -> dialog.dismiss());
        Button btnRate = dialog.findViewById(R.id.btn_rate);
        EditText txtKomentar = dialog.findViewById(R.id.txt_komentar);
        btnRate.setOnClickListener(view -> {
            if (txtKomentar.getText().toString().isEmpty()) {
                Toast.makeText(AppointmentListHistory.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
            } else {
                txtKomentar.setText("");
                ratingBar.setRating(0);
                Toast.makeText(AppointmentListHistory.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Appointment History");
        toolbarImg.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }

    private void showRatingDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(this));
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
