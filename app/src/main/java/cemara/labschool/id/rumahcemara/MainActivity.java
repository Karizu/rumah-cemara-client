package cemara.labschool.id.rumahcemara;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.util.adapter.NoSwipePager;
import cemara.labschool.id.rumahcemara.util.adapter.ViewPagerAdapter;
import cemara.labschool.id.rumahcemara.home.fragment.HomeFragment;
import cemara.labschool.id.rumahcemara.mylist.fragment.MyListFragment;
import cemara.labschool.id.rumahcemara.options.fragment.OptionsFragment;

public class MainActivity extends AppCompatActivity {

    private NoSwipePager viewPager;
    MenuItem prevMenuItem;
    HomeFragment fragmentHome;
    MyListFragment fragmentMyList;
    OptionsFragment fragmentOptions;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Objects.requireNonNull(getSupportActionBar()).hide(); //<< this
                    } catch (Exception ignored) {
                    }
                    viewPager.setCurrentItem(0);
//                    mTextMessage.setText(R.string.title_home);
                    break;
                case R.id.navigation_mylist:
                    viewPager.setCurrentItem(1);
//                    mTextMessage.setText(R.string.title_mylist);
                    break;
                case R.id.navigation_options:
                    viewPager.setCurrentItem(3);
//                    mTextMessage.setText(R.string.title_options);
                    break;
            }
            return false;
        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentHome = new HomeFragment();
        fragmentMyList = new MyListFragment();
        fragmentOptions = new OptionsFragment();
        viewPagerAdapter.addFragment(fragmentHome);
        viewPagerAdapter.addFragment(fragmentMyList);
        viewPagerAdapter.addFragment(fragmentOptions);
        viewPager.setAdapter(viewPagerAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        TextView mTextMessage = (TextView) findViewById(R.id.message);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
    }

}
