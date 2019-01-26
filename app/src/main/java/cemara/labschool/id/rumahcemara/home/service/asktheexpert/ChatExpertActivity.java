package cemara.labschool.id.rumahcemara.home.service.asktheexpert;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AskTheExpertHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Topic;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;

public class ChatExpertActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.topic)
    TextView topicTV;

    @BindView(R.id.date_topic)
    TextView dateTopicTV;

    @BindView(R.id.message_text)
    TextView messageTV;

    @BindView(R.id.messageContainer)
    RelativeLayout messageContainer;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_expert);
        ButterKnife.bind(this);
        setToolbar();
        messageContainer.setVisibility(View.INVISIBLE);
        Loading.show(this);

        activity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String topicId = intent.getStringExtra("topic_id");

        init(topicId);
    }

    private void init(String topicId){
        AskTheExpertHelper.getTopicDetail(topicId, new RestCallback<ApiResponse<Topic>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<Topic> body) {
                Loading.hide(activity);
                if (body != null && body.isStatus()) {
                    Topic topic = body.getData();

                    topicTV.setText(topic.getQuestion());
                    dateTopicTV.setText(dateFormater(topic.getCreated_at(), "dd MMMM yyyy", "yyyy-MM-dd HH:mm:ss"));

                    if (topic.getMessage().size() > 0){
                        messageContainer.setVisibility(View.VISIBLE);
                        messageTV.setText(topic.getMessage().get(0).getMessage());
                    }

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

    public static String dateFormater(String dateFromJSON, String expectedFormat, String oldFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dateFromJSON);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
            convertedDate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }
}
