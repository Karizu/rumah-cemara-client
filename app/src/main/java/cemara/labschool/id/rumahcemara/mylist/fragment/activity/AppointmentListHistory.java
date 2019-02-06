package cemara.labschool.id.rumahcemara.mylist.fragment.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.HistoryList;
import cemara.labschool.id.rumahcemara.model.HistoryListModel;
import cemara.labschool.id.rumahcemara.model.ListSaved;
import cemara.labschool.id.rumahcemara.model.response.HistoryListResponse;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.adapter.ListHistoryAdapter;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ListHistoryAdapter historyAdapter;
//    List<News> newsList = new ArrayList<>();

    private List<HistoryListModel> historyList;
    private Context activity;
    private LinearLayoutManager layoutManager;
    Dialog dialog;
    String ratingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list_appointment_history_activity);
        ButterKnife.bind(this);
        setToolbar();
        activity = this;
        layoutManager = new LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        getListHistory();
//        layoutCompleted.setOnClickListener(v1 -> startActivity(new Intent(AppointmentListHistory.this, MyListBiomedicalDetail.class)));
    }

    private void getListHistory() {
        Log.d("Saved", "start");
        Loading.show(this);
        ListHelper.getListAppointmentHistory(new RestCallback<ApiResponse<List<HistoryListResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<HistoryListResponse>> body) {
                Loading.hide(getApplicationContext());
                if (body != null && body.isStatus()) {
                    List<HistoryListResponse> res = body.getData();
                    System.out.println("Response: " + body.getData());
                    historyList = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
                        HistoryListResponse article = res.get(i);
                        historyList.add(new HistoryListModel(article.getId(),
                                article.getGroup_id(),
                                article.getUser_id(),
                                article.getProvider_id(),
                                article.getService_type_id(),
                                article.getWorker_id(),
                                article.getStart_date(),
                                article.getEnd_date(),
                                article.getDescription(),
                                article.getAttachment(),
                                article.getType_provider(),
                                article.getStatus(),
                                article.getStatus_report(),
                                article.getCreated_at(),
                                article.getUpdated_at(),
                                article.getDeleted_at(),
                                article.getLocation(),
                                article.getGroup(),
                                article.getUser(),
                                article.getService_type(),
                                article.getRating(),
                                article.getProvider_worker()
                                ));
                    }

                    historyAdapter = new ListHistoryAdapter(historyList, activity);
                    recyclerView.setAdapter(historyAdapter);
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Log.d("onFailed", error.getMessage());
            }

            @Override
            public void onCanceled() {

            }
        });
    }

//    @OnClick(R.id.row_appointment)
//    public void rating() {
//        showRatingDialog(R.layout.my_list_appointment_history_dialog);
//        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
//        ratingBar.setOnRatingBarChangeListener((ratingBar1, v, b) -> {
//            switch ((int) ratingBar1.getRating()) {
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//                case 5:
//                    break;
//                default:
//            }
//        });
//        ImageView close = dialog.findViewById(R.id.ic_close);
//        close.setOnClickListener(view -> dialog.dismiss());
//        Button btnRate = dialog.findViewById(R.id.btn_rate);
//        EditText txtKomentar = dialog.findViewById(R.id.txt_komentar);
//        btnRate.setOnClickListener(view -> {
//            if (txtKomentar.getText().toString().isEmpty()) {
//                Toast.makeText(AppointmentListHistory.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
//            } else {
//                txtKomentar.setText("");
//                ratingBar.setRating(0);
//                Toast.makeText(AppointmentListHistory.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
//            }
//        });
////    }

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
