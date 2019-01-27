package cemara.labschool.id.rumahcemara.mylist.fragment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;

public class MyListBiomedicalDetail extends AppCompatActivity {

    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.txt_name)
    TextView namaUser;
    @BindView(R.id.txt_no_hp)
    TextView NoHp;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txt_komentar)
    EditText txtKomentar;
    @BindView(R.id.btn_rate)
    Button btnRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_biomedical_detail);
        ButterKnife.bind(this);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                switch ((int) ratingBar.getRating()) {
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
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtKomentar.getText().toString().isEmpty()) {
                    Toast.makeText(MyListBiomedicalDetail.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    txtKomentar.setText("");
                    ratingBar.setRating(0);
                    Toast.makeText(MyListBiomedicalDetail.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
