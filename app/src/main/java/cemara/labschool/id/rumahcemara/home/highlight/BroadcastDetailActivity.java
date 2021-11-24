package cemara.labschool.id.rumahcemara.home.highlight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
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
import cemara.labschool.id.rumahcemara.api.BroadcastHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkBroadcastClickListener;
import cemara.labschool.id.rumahcemara.util.article.model.adapter.ArticleAdapter;
import cemara.labschool.id.rumahcemara.util.broadcast.adapter.BroadcastAdapter;
import cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import okhttp3.Headers;

public class BroadcastDetailActivity extends AppCompatActivity {

    BroadcastAdapter articleAdapter;
    List<Broadcast> articleList = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String articleId, flag;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.article_detail_text)
    WebView articleDetailContent;
    @BindView(R.id.banner_news)
    ImageView banner;
    @BindView(R.id.mark_news_top)
    ImageView markNewsTop;

    Context context;

    cemara.labschool.id.rumahcemara.model.Broadcast articleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_detail);

        ButterKnife.bind(this);
        context = this;
        Intent intent=getIntent();
        articleId=intent.getStringExtra("id");
        flag=intent.getStringExtra("flag");
        getListArticle();
        setToolbar();

    }

    private void getListArticle() {
/*        articleList.clear();
        articleList.add(new Article("1", "testing", "test", "June 20 2019", R.drawable.img_article));
        articleList.add(new Article("1", "testing", "test", "June 20 2019", R.drawable.img_article));
        articleAdapter = new ArticleAdapter(getApplicationContext(), articleList, "highlight_detail");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();*/
        Loading.show(context);
        BroadcastHelper.getBroadcastDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.Broadcast>>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.Broadcast> body) {
                Loading.hide(context);
                try {
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

                        Spanned htmlAsSpanned = Html.fromHtml(articleDetail.getContent());
//                        tvContent.setText(htmlAsSpanned);

                    articleDetailContent.getSettings().setJavaScriptEnabled(true);
                    articleDetailContent.setBackgroundColor(Color.TRANSPARENT);
                    articleDetailContent.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

                        if (flag!=null){
                            markNewsTop.setEnabled(false);
                            markNewsTop.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_black_24dp));
                        } else {
                            markNewsTop.setOnClickListener(new MarkBroadcastClickListener(context, articleDetail));
                        }


                    } else {
//                        loadingDialog.dismiss();
                        Toast.makeText(context, body.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(context);
                Toast.makeText(context,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(context);
            }
        });

    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
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
