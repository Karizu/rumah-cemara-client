package cemara.labschool.id.rumahcemara.home.highlight.news.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import cemara.labschool.id.rumahcemara.home.highlight.news.fragment.NewsCampaignFragment;
import cemara.labschool.id.rumahcemara.home.highlight.news.fragment.NewsCapacityFragment;
import cemara.labschool.id.rumahcemara.home.highlight.news.fragment.NewsTrainingFragment;


public class TabNewsAdapter extends FragmentPagerAdapter {
    private Context context;
    private String id;

    public TabNewsAdapter(FragmentManager fm, Context context, String id){
        super(fm);
        this.context = context;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewsTrainingFragment();
            case 1:
                return new NewsCampaignFragment();
            case 2:
                return new NewsCapacityFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(cemara.labschool.id.rumahcemara.R.string.training_and_education);
            case 1:
                return context.getString(cemara.labschool.id.rumahcemara.R.string.campaign_and_nformation);
            case 2:
                return context.getString(cemara.labschool.id.rumahcemara.R.string.capacity_building);
            default:
                return null;
        }
    }
}
