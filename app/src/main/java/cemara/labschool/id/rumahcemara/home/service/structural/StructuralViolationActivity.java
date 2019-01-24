package cemara.labschool.id.rumahcemara.home.service.structural;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;

public class StructuralViolationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    EditText DescMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structural_violation_activity);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle("Structural - Violation Report");
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
