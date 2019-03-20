package cemara.labschool.id.rumahcemara.home.highlight.article;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
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
import cemara.labschool.id.rumahcemara.home.highlight.article.adapter.TabArticleAdapter;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Category;
import cemara.labschool.id.rumahcemara.model.CategoryModel;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.view)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    List<Category> categoryList = new ArrayList<>();

    private int activeTab;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getCategory();


        setToolbar();
    }

    private void getCategory() {

        Loading.show(ArticleActivity.this);
        ArticleHelper.getArticleCategory(new RestCallback<ApiResponse<List<CategoryModel>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<CategoryModel>> body) {
                Loading.hide(ArticleActivity.this);
                if (body.isStatus()) {
                    if (body.getData() != null && body.getData().size() > 0) {
                        categoryList.clear();
                        List<CategoryModel> categoryModelList = body.getData();

                        // Insert Pager
//                    int maxPager=categoryList.size()> 0 ? 0: categoryList.size();
//                    for(int i=0;i<categoryList.size();i++){
//                        categoryList.add(categoryList.get(0));//Always get position 0 , because always delete item already get below
//                        categoryList.remove(0);
//                    }
                        for (int i = 0; i < categoryModelList.size(); i++) {
                            categoryList.add(new Category(categoryModelList.get(i).getId(), categoryModelList.get(i).getName(), categoryModelList.get(i).getCreated_at(), categoryModelList.get(i).getUpdated_at(), categoryModelList.get(i).getDeleted_at()));
                            Log.d("Category", categoryList.get(i).getId());
                        }
                        TabArticleAdapter tabAdapter = new TabArticleAdapter(getSupportFragmentManager(), ArticleActivity.this, categoryList, id);
                        viewPager.setAdapter(tabAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }else {
                        Toast.makeText(ArticleActivity.this, "Category is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(ArticleActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(ArticleActivity.this);
                Toast.makeText(ArticleActivity.this, "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(ArticleActivity.this);
            }
        });
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(getString(R.string.articles));
        toolbarImg.setImageResource(R.drawable.icon_news);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
