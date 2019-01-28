package cemara.labschool.id.rumahcemara.mylist.fragment.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import cemara.labschool.id.rumahcemara.mylist.fragment.MyListReport;
import cemara.labschool.id.rumahcemara.mylist.fragment.MyListSaved;
import cemara.labschool.id.rumahcemara.mylist.fragment.MyListReminder;
import cemara.labschool.id.rumahcemara.mylist.fragment.MyListAppointment;

public class MyListAdapter extends FragmentPagerAdapter {
    private Context context;
    private String id;

    public MyListAdapter(FragmentManager fm, Context context, String id){
        super(fm);
        this.context = context;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyListAppointment();
            case 1:
                Log.d("MyList","Reminder");
                return new MyListReminder();
            case 2:
                return new MyListSaved();
            case 3:
                return new MyListReport();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "My Appointment";
            case 1:
                return "My Reminder";
            case 2:
                return "Saved";
            case 3:
                return "Report Status";
            default:
                return null;
        }
    }
}
