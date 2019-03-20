package cemara.labschool.id.rumahcemara.home.highlight.article.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cemara.labschool.id.rumahcemara.home.highlight.article.fragment.ArticleFragment;
import cemara.labschool.id.rumahcemara.model.Category;


public class TabArticleAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Category> categoryList;
    private String id;

    public TabArticleAdapter(FragmentManager fm, Context context, String id){
        super(fm);
        this.context = context;
        this.id = id;
    }

    public TabArticleAdapter(FragmentManager fm, Context context, List<Category> categoryList, String id) {
        super(fm);
        this.context = context;
        this.categoryList = categoryList;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment articleFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString("id", categoryList.get(position).getId());
        articleFragment.setArguments(args);

        return articleFragment;
       /* switch (position) {
            case 0:
                return new ArticleTrainingFragment();
            case 1:
                return new ArticleCampaignFragment();
            case 2:
                return new ArticleCapacityFragment();
            default:
                return null;
        }*/
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        /*switch (position) {
            case 0:
                return categoryList.get(position).getName();
            case 1:
                return categoryList.get(position).getName();
            case 2:
                return categoryList.get(position).getName();
            default:
                return null;
        }*/
        return categoryList.get(position).getName();
    }
}
