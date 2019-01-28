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
import cemara.labschool.id.rumahcemara.api.ArticleHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.article.model.adapter.ArticleAdapter;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.article.model.Article;
import okhttp3.Headers;

public class ArticleDetailActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    List<Article> articleList = new ArrayList<>();
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
        Loading.show(ArticleDetailActivity.this);
        ArticleHelper.getArticleDetail(articleId,new RestCallback<ApiResponse<cemara.labschool.id.rumahcemara.model.Article>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<cemara.labschool.id.rumahcemara.model.Article> body) {
                Loading.hide(ArticleDetailActivity.this);
                if (body != null && body.isStatus()) {
                    cemara.labschool.id.rumahcemara.model.Article articleDetail=body.getData();
                    Log.d("aa","sss");

                    tvAuthor.setText(articleDetail.getUserCreator().getProfile().getFullname());
                    tvTitle.setText(articleDetail.getTitle());
                    tvDate.setText(DateHelper.dateFormat(DateHelper.stringToDate(articleDetail.getCreatedAt())));
                    tvContent.setText(articleDetail.getContent());
                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(ArticleDetailActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Toast.makeText(ArticleDetailActivity.this,"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                Loading.hide(ArticleDetailActivity.this);
            }

            @Override
            public void onCanceled() {
                Loading.hide(ArticleDetailActivity.this);
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
