package cemara.labschool.id.rumahcemara.mylist.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.mylist.fragment.Adapter.MyListAdapter;


public class MyListFragment extends Fragment {

    @BindView(R.id.view)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int activeTab;
    private String id;

    public MyListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_list, parent, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        MyListAdapter tabAdapter = new MyListAdapter(getFragmentManager(), getActivity(),id);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_mylist:
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
