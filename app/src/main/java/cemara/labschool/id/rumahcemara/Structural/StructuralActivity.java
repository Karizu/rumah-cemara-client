package cemara.labschool.id.rumahcemara.Structural;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import cemara.labschool.id.rumahcemara.R;

public class StructuralActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutPurpose, layoutLegal, layoutViolation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structural_activity);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_close);
        toolbar.setNavigationOnClickListener(v -> finish());

        layoutPurpose = findViewById(R.id.layout_purpose);
        layoutPurpose.setOnClickListener(this);
        layoutLegal = findViewById(R.id.layout_legal_aid);
        layoutLegal.setOnClickListener(this);
        layoutViolation = findViewById(R.id.layout_violation);
        layoutViolation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_purpose:
                break;
            case R.id.layout_legal_aid:
                Intent intentLegalAid = new Intent(this, StructuralLegalAidActivity.class);
                startActivity(intentLegalAid);
                break;
            case R.id.layout_violation:
                Intent intentViolation = new Intent(this, StructuralViolationActivity.class);
                startActivity(intentViolation);
                break;
        }
    }
}
