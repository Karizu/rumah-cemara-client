package cemara.labschool.id.rumahcemara.home.highlight;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkEventClickListener;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.event.adapter.EventAdapter;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import okhttp3.Headers;

public class EventDetailActivity extends AppCompatActivity {
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
    @BindView(R.id.article_detail_text)
    WebView articleDetailContent;
    @BindView(R.id.banner_news)
    ImageView banner;
    @BindView(R.id.mark_news_top)
    ImageView markNewsTop;

    Activity context;


    cemara.labschool.id.rumahcemara.model.Event articleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_activity);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        articleId=intent.getStringExtra("id");
        getListEvent();
        setToolbar();
        context = this;
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
                     articleDetail=body.getData();
                    Log.d("aa","sss");

                    tvAuthor.setText(articleDetail.getUserCreator().getProfile().getFullname());
                    tvTitle.setText(articleDetail.getTitle());
                    tvDate.setText(DateHelper.dateFormat(DateHelper.stringToDate(articleDetail.getCreatedAt())));
                    String content = "<html><head>"
                            + "<style type=\"text/css\">body{color: #8f8f8f} p{font-size: 13px}"
                            + "</style></head>"
                            + "<body>"
                            + articleDetail.getContent()
                            + "</body></html>";

                    Glide.with(context)
                            .load(articleDetail.getBanner())
                            .into(banner);

                    articleDetailContent.getSettings().setJavaScriptEnabled(true);
                    articleDetailContent.setBackgroundColor(Color.TRANSPARENT);
                    articleDetailContent.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");

                    markNewsTop.setOnClickListener(new MarkEventClickListener(articleDetail));

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


    @OnClick(R.id.mark_share)
    public void shareArticle(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, articleDetail.getTitle()+
                "\n"+ articleDetail.getBanner());
        startActivity(Intent.createChooser(sharingIntent, "Share using:"));
    }
}
