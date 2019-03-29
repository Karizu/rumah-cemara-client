package cemara.labschool.id.rumahcemara.home.highlight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import cemara.labschool.id.rumahcemara.api.ArticleHelper;
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.api.NewsHelper;
import cemara.labschool.id.rumahcemara.home.highlight.news.NewsActivity;
import cemara.labschool.id.rumahcemara.home.highlight.news.adapter.TabNewsAdapter;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Category;
import cemara.labschool.id.rumahcemara.model.CategoryModel;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkEventClickListener;
import cemara.labschool.id.rumahcemara.util.MarkNewsClickListener;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import okhttp3.Headers;

public class DetailHighlightActivity extends AppCompatActivity {

//    ArticleAdapter articleAdapter;
//    List<Article> articleList = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String articleId = null;
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

    String categoryId, title, sBanner;
    Activity context;
    Article articleDetail;
    News newsDetail;
    Event eventDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_highlight);
        ButterKnife.bind(this);
        populateData();
        getCategoryNews();
        getCategoryArticle();
        getCategoryEvent();
        setToolbar();
        context = this;
    }

    private void populateData() {
        Bundle bundle = getIntent().getBundleExtra("myData");
        articleId = bundle.getString("id");
        categoryId = bundle.getString("categoryId");
        title = bundle.getString("title");
        sBanner = bundle.getString("banner");
    }

    private void getCategoryEvent() {

        Loading.show(DetailHighlightActivity.this);
        EventHelper.getEventCategory(new RestCallback<ApiResponse<List<CategoryModel>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<CategoryModel>> body) {
                Loading.hide(DetailHighlightActivity.this);
                if (body.isStatus()) {
                    if (body.getData() != null && body.getData().size() > 0) {
                        List<CategoryModel> categoryModelList = body.getData();

                        for (int i = 0; i < categoryModelList.size(); i++) {
                            if (categoryModelList.get(i).getId().equals(categoryId)){

                                EventHelper.getEventDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.Event>>() {
                                    @SuppressLint("SetJavaScriptEnabled")
                                    @Override
                                    public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.Event> body) {
                                        Loading.hide(DetailHighlightActivity.this);
                                        if (body != null && body.isStatus()) {
                                            eventDetail=body.getData();
                                            Log.d("aa","sss");

                                            tvAuthor.setText(eventDetail.getUserCreator().getProfile().getFullname());
                                            tvTitle.setText(eventDetail.getTitle());
                                            tvDate.setText(DateHelper.dateFormat(DateHelper.stringToDate(eventDetail.getCreatedAt())));
                                            String content = "<html><head>"
                                                    + "<style type=\"text/css\">body{color: #8f8f8f} p{font-size: 13px}"
                                                    + "</style></head>"
                                                    + "<body>"
                                                    + eventDetail.getContent()
                                                    + "</body></html>";

                                            Glide.with(context)
                                                    .load(eventDetail.getBanner())
                                                    .into(banner);

                                            articleDetailContent.getSettings().setJavaScriptEnabled(true);
                                            articleDetailContent.setBackgroundColor(Color.TRANSPARENT);
                                            articleDetailContent.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");

                                            markNewsTop.setOnClickListener(new MarkEventClickListener(context, eventDetail));

                                        } else {
                                            Toast.makeText(DetailHighlightActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailed(ErrorResponse error) {
                                        Toast.makeText(DetailHighlightActivity.this,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                                        Loading.hide(DetailHighlightActivity.this);
                                    }

                                    @Override
                                    public void onCanceled() {
                                        Loading.hide(DetailHighlightActivity.this);
                                    }
                                });

                            }
                        }

                    } else {
                        Toast.makeText(DetailHighlightActivity.this, "Category is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailHighlightActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(DetailHighlightActivity.this);
                Log.d("onFailed Event", error.getMessage());
            }

            @Override
            public void onCanceled() {
                Loading.hide(DetailHighlightActivity.this);
            }
        });

    }

    private void getCategoryArticle() {

        Loading.show(DetailHighlightActivity.this);
        ArticleHelper.getArticleCategory(new RestCallback<ApiResponse<List<CategoryModel>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<CategoryModel>> body) {
                Loading.hide(DetailHighlightActivity.this);
                if (body.isStatus()) {
                    if (body.getData() != null && body.getData().size() > 0) {
                        List<CategoryModel> categoryModelList = body.getData();

                        for (int i = 0; i < categoryModelList.size(); i++) {
                            if (categoryModelList.get(i).getId().equals(categoryId)){

                                ArticleHelper.getArticleDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.Article>>() {
                                    @Override
                                    public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.Article> body) {
                                        Loading.hide(DetailHighlightActivity.this);
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

                                            markNewsTop.setOnClickListener(new MarkArticleClickListener(context, articleDetail));

                                        } else {
//                        loadingDialog.dismiss();
                                            Toast.makeText(DetailHighlightActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailed(ErrorResponse error) {
                                        Toast.makeText(DetailHighlightActivity.this,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                                        Loading.hide(DetailHighlightActivity.this);
                                    }

                                    @Override
                                    public void onCanceled() {
                                        Loading.hide(DetailHighlightActivity.this);
                                    }
                                });

                            }
                        }

                    } else {
                        Toast.makeText(DetailHighlightActivity.this, "Category is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailHighlightActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(DetailHighlightActivity.this);
                Log.d("onFailed Article", error.getMessage());
            }

            @Override
            public void onCanceled() {
                Loading.hide(DetailHighlightActivity.this);
            }
        });

    }

    private void getCategoryNews() {
        Loading.show(DetailHighlightActivity.this);
        NewsHelper.getNewsCategory(new RestCallback<ApiResponse<List<CategoryModel>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<CategoryModel>> body) {
                Loading.hide(DetailHighlightActivity.this);
                if (body.isStatus()) {
                    if (body.getData() != null && body.getData().size() > 0) {
                        List<CategoryModel> categoryModelList = body.getData();

                        for (int i = 0; i < categoryModelList.size(); i++) {
                            if (categoryModelList.get(i).getId().equals(categoryId)){

                                NewsHelper.getNewsDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.News>>() {
                                    @Override
                                    public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.News> body) {
                                        Loading.hide(DetailHighlightActivity.this);
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

                                            markNewsTop.setOnClickListener(new MarkNewsClickListener(context, newsDetail));

                                        } else {
//                        loadingDialog.dismiss();
                                            Toast.makeText(DetailHighlightActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailed(ErrorResponse error) {
                                        Toast.makeText(DetailHighlightActivity.this,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                                        Loading.hide(DetailHighlightActivity.this);
                                    }

                                    @Override
                                    public void onCanceled() {
                                        Loading.hide(DetailHighlightActivity.this);
                                    }
                                });

                            }
                        }

                    } else {
                        Toast.makeText(DetailHighlightActivity.this, "Category is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailHighlightActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(DetailHighlightActivity.this);
                Log.d("onFailed News", error.getMessage());
            }

            @Override
            public void onCanceled() {
                Loading.hide(DetailHighlightActivity.this);
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
        sharingIntent.putExtra(Intent.EXTRA_TEXT, title+
                "\n"+ sBanner);
        startActivity(Intent.createChooser(sharingIntent, "Share using:"));
    }
}
