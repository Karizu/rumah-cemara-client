package cemara.labschool.id.rumahcemara.mylist.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.util.events.adapter.EventsAdapter;
import cemara.labschool.id.rumahcemara.util.events.model.Events;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListReminder extends Fragment {

    @BindView(R.id.event_list_recycler_view)
    RecyclerView recyclerView;
    EventsAdapter eventsAdapter;
    List<Events> eventList = new ArrayList<>();
    public MyListReminder() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.my_list_reminder_fragment, container, false);
        ButterKnife.bind(this,rootView);
        getEventsList();
        return rootView;
    }
    private void getEventsList() {
        eventList.clear();
        eventList.add(new Events("1", "Seminar Pencegahan Aids", "Jln Buah Batu no. 29 Bandung", "June 20 2019", R.drawable.img_news));
        eventList.add(new Events("1", "Makan Bareng (Martabak besar) :D", "Batik Saketi no 7", "February 2 2019", R.drawable.img_news));
        eventsAdapter = new EventsAdapter(getActivity(), eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventsAdapter);
        eventsAdapter.notifyDataSetChanged();
    }

}
