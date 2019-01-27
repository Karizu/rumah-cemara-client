package cemara.labschool.id.rumahcemara.mylist.fragment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;

public class AppointmentListHistory extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.layout_completed)
    LinearLayout layoutCompleted;
    @BindView(R.id.layout_completed1)
    LinearLayout layoutCompleted1;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_appointment_history);
        ButterKnife.bind(this);
        setToolbar();

        layoutCompleted.setOnClickListener(v1 -> startActivity(new Intent(AppointmentListHistory.this, MyListBiomedicalDetail.class)));
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Appointment History");
        toolbarImg.setVisibility(View.GONE);
        }
}
