package cemara.labschool.id.rumahcemara.home.service.asktheexpert;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AskTheExpertHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Topic;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.NearestAdapter;
import cemara.labschool.id.rumahcemara.util.nearest.modal.Nearest;
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AskTheExpertActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Dialog dialog;

    private Context activity;

    private RecyclerView recyclerView;

    private List<Topic> topicList;

    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_the_expert_activity);
        ButterKnife.bind(this);
        setToolbar();

        activity = this;

        layoutManager = new LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false);

        recyclerView = findViewById(R.id.rvTopicList);
        recyclerView.setLayoutManager(layoutManager);

        Realm realm = LocalData.getRealm();

        user = realm.where(User.class).findFirst();

        populateData();

    }

    private void populateData() {
        Loading.show(activity);
        AskTheExpertHelper.getAllTopic(new RestCallback<ApiResponse<List<Topic>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<Topic>> body) {
                try {
                    Loading.hide(activity);
                    if (body != null && body.isStatus()) {
                        List<Topic> res = body.getData();
                        topicList = new ArrayList<>();

                        for (int i = 0; i < res.size(); i++) {
                            Topic topicData = res.get(i);
                            topicList.add(topicData);
                        }

                        adapter = new AskTheExpertAdapter(topicList, activity);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailed(ErrorResponse error) {

            }

            @Override
            public void onCanceled() {

            }
        });
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.create_topic)
    public void createTopic(View view) {
        showDialo(R.layout.dialog_new_topic);
    }

    private void showDialo(int layout) {
        dialog = new Dialog(Objects.requireNonNull(AskTheExpertActivity.this));
        //SET TITLE
        dialog.setTitle("Biomedical");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        EditText newTopic = dialog.findViewById(R.id.topic);
        Button submitTopic = dialog.findViewById(R.id.btnCreateTopic);

        submitTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show(activity);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("user_id", user.getId())
                        .addFormDataPart("question", newTopic.getText().toString())
                        .build();
                publishTopic(requestBody);
            }
        });
    }

    private void publishTopic(RequestBody request) {
        AskTheExpertHelper.createTopic(request, new RestCallback<ApiResponse>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse body) {
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailed(ErrorResponse error) {

            }

            @Override
            public void onCanceled() {

            }
        });
    }

}
