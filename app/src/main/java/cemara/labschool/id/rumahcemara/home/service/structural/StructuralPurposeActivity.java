package cemara.labschool.id.rumahcemara.home.service.structural;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import cemara.labschool.id.rumahcemara.R;

public class StructuralPurposeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutFindService, layoutFindOutreach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structural_purpose_activity);

        layoutFindService = findViewById(R.id.layout_find_service);
        layoutFindService.setOnClickListener(this);
        layoutFindOutreach = findViewById(R.id.layout_find_outreach);
        layoutFindOutreach.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_find_service:
                //layoutFindService.setOnClickListener(startActivity(new Intent(this, LaporanPendampingActivity.class)));
                break;
        }
    }
}
