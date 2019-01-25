package cemara.labschool.id.rumahcemara.options.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.FindServiceProviderActivity;
import cemara.labschool.id.rumahcemara.options.fragment.activity.EditAccountActivity;

public class OptionsFragment extends Fragment {

    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.text_toolbar_title)
    TextView title;

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        ButterKnife.bind(this, view);
        title.setText(getString(R.string.title_options));

        return view;
    }

    @OnClick(R.id.edit)
    public void toEdit(){
        startActivity(new Intent(getContext(), EditAccountActivity.class));
    }
}
