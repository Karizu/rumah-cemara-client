package cemara.labschool.id.rumahcemara.home.service.behavioral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.behavioral.FindOutreachWorker.FindOutreachWorkerActivity;
import cemara.labschool.id.rumahcemara.home.service.behavioral.FindServiceProvider.FindServiceProviderActivity;

public class CounselingAppointmentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.behavioral_appointment_activity);
        ButterKnife.bind(this);
        setToolbar();
    }

    @OnClick(R.id.find_service_provider)
    public void toFindServiceProvider(){
        startActivity(new Intent(this, FindServiceProviderActivity.class));
    }
    @OnClick(R.id.find_outreach_worker)
    public void toFindOutreachWorker(){
        startActivity(new Intent(this, FindOutreachWorkerActivity.class));
    }
    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(R.string.counseling_appointment);
        toolbarImg.setImageResource(R.drawable.icon_behavioral_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }

}
