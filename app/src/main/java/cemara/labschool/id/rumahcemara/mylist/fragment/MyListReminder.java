package cemara.labschool.id.rumahcemara.mylist.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cemara.labschool.id.rumahcemara.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListReminder extends Fragment {


    public MyListReminder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_list_reminder, container, false);
    }

}
