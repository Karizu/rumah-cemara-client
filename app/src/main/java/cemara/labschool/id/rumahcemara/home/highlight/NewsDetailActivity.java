package cemara.labschool.id.rumahcemara.home.highlight;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import cemara.labschool.id.rumahcemara.api.NewsHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.MarkEventClickListener;
import cemara.labschool.id.rumahcemara.util.MarkNewsClickListener;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import okhttp3.Headers;

public class NewsDetailActivity extends AppCompatActivity {
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
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


    cemara.labschool.id.rumahcemara.model.News newsDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_activity);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        articleId=intent.getStringExtra("id");
        getListNews();
        setToolbar();
        context = this;
    }

    private void getListNews() {
/*        newsList.clear();
        newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));
        newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));
        newsAdapter = new NewsAdapter(getApplicationContext(), newsList, "highlight_detail");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();*/
        Loading.show(NewsDetailActivity.this);
        NewsHelper.getNewsDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.News>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.News> body) {
                Loading.hide(NewsDetailActivity.this);
                if (body != null && body.isStatus()) {
                    newsDetail=body.getData();
                    Log.d("aa","sss");

                    tvAuthor.setText(newsDetail.getUserCreator().getProfile().getFullname());
                    tvTitle.setText(newsDetail.getTitle());
                    tvDate.setText(DateHelper.dateFormat(DateHelper.stringToDate(newsDetail.getCreatedAt())));
                    String content = "<html><head>"
                            + "<style type=\"text/css\">body{color: #8f8f8f} p{font-size: 13px}"
                            + "</style></head>"
                            + "<body>"
                            + newsDetail.getContent()
                            + "</body></html>";

                    Glide.with(context)
                            .load(newsDetail.getBanner())
                            .into(banner);

                    articleDetailContent.getSettings().setJavaScriptEnabled(true);
                    articleDetailContent.setBackgroundColor(Color.TRANSPARENT);
                    articleDetailContent.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");

                    markNewsTop.setOnClickListener(new MarkNewsClickListener(newsDetail));

                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(NewsDetailActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Toast.makeText(NewsDetailActivity.this,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                Loading.hide(NewsDetailActivity.this);
            }

            @Override
            public void onCanceled() {
                Loading.hide(NewsDetailActivity.this);
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
        sharingIntent.putExtra(Intent.EXTRA_TEXT, newsDetail.getTitle()+
                "\n"+ newsDetail.getBanner());
        startActivity(Intent.createChooser(sharingIntent, "Share using:"));
    }
}
