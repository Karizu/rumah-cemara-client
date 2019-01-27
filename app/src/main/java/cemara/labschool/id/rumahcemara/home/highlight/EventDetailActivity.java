package cemara.labschool.id.rumahcemara.home.highlight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.event.adapter.EventAdapter;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import okhttp3.Headers;

public class EventDetailActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    EventAdapter articleAdapter;
    List<cemara.labschool.id.rumahcemara.util.event.model.Event> articleList = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String articleId=null;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvContent)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_activity);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        articleId=intent.getStringExtra("id");
        getListEvent();
        setToolbar();
    }

    private void getListEvent() {
/*        articleList.clear();
        articleList.add(new Event("1", "testing", "test", "June 20 2019", R.drawable.img_article));
        articleList.add(new Event("1", "testing", "test", "June 20 2019", R.drawable.img_article));
        articleAdapter = new EventAdapter(getApplicationContext(), articleList, "highlight_detail");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();*/
        Loading.show(EventDetailActivity.this);
        EventHelper.getEventDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.Event>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.Event> body) {
                Loading.hide(EventDetailActivity.this);
                if (body != null && body.isStatus()) {
                    cemara.labschool.id.rumahcemara.model.Event articleDetail=body.getData();
                    Log.d("aa","sss");

                    tvAuthor.setText(articleDetail.getUserCreator().getProfile().getFullname());
                    tvTitle.setText(articleDetail.getTitle());
                    tvDate.setText(DateHelper.dateFormat(DateHelper.stringToDate(articleDetail.getCreatedAt())));
                    tvContent.setText(articleDetail.getContent());
                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(EventDetailActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Toast.makeText(EventDetailActivity.this,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                Loading.hide(EventDetailActivity.this);
            }

            @Override
            public void onCanceled() {
                Loading.hide(EventDetailActivity.this);
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
}
