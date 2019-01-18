package cemara.labschool.id.rumahcemara.Structural;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.util.Objects;

import cemara.labschool.id.rumahcemara.R;

public class StructuralViolationActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText DescMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structural_violation_activity);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Structural - Violation Report");
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
