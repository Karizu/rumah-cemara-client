package cemara.labschool.id.rumahcemara.home.highlight.news.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cemara.labschool.id.rumahcemara.home.highlight.news.fragment.NewsFragment;
import cemara.labschool.id.rumahcemara.model.Category;


public class TabNewsAdapter extends FragmentPagerAdapter {
    private Context context;
    private String id;
    private List<Category> categoryList;

    public TabNewsAdapter(FragmentManager fm, Context context, String id){
        super(fm);
        this.context = context;
        this.id = id;
    }

    public TabNewsAdapter(FragmentManager fm, Context context, List<Category> categoryList, String id) {
        super(fm);
        this.context = context;
        this.id = id;
        this.categoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("id", categoryList.get(position).getId());
        newsFragment.setArguments(args);
        return newsFragment;
//        switch (position) {
//            case 0:
//                return new NewsTrainingFragment();
//            case 1:
//                return new NewsCampaignFragment();
//            case 2:
//                return new NewsCapacityFragment();
//            default:
//                return null;
//        }
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return categoryList.get(position).getName();
//        switch (position) {
//            case 0:
//                return context.getString(cemara.labschool.id.rumahcemara.R.string.training_and_education);
//            case 1:
//                return context.getString(cemara.labschool.id.rumahcemara.R.string.campaign_and_nformation);
//            case 2:
//                return context.getString(cemara.labschool.id.rumahcemara.R.string.capacity_building);
//            default:
//                return null;
//        }
    }
}
