package cemara.labschool.id.rumahcemara.home.highlight.event;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.home.highlight.event.adapter.TabEventAdapter;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Category;
import cemara.labschool.id.rumahcemara.model.CategoryModel;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;

public class EventActivity extends AppCompatActivity {

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
        Loading.show(EventActivity.this);
        EventHelper.getEventCategory(new RestCallback<ApiResponse<List<CategoryModel>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<CategoryModel>> body) {
                Loading.hide(EventActivity.this);
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
                        }
                        TabEventAdapter tabAdapter = new TabEventAdapter(getSupportFragmentManager(), EventActivity.this, categoryList, id);
                        viewPager.setAdapter(tabAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }else {
                        Toast.makeText(EventActivity.this, "Category is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(EventActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(EventActivity.this);
                Toast.makeText(EventActivity.this, "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(EventActivity.this);
            }
        });
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(getString(R.string.events));
        toolbarImg.setImageResource(R.drawable.icon_event_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }
}
