package cemara.labschool.id.rumahcemara;

import android.app.Application;
import android.content.Context;

import com.rezkyatinnov.kyandroid.Kyandroid;
import com.rezkyatinnov.kyandroid.localdata.KyandroidRealmModule;

import cemara.labschool.id.rumahcemara.api.ApiInterface;
import cemara.labschool.id.rumahcemara.model.RumahCemaraRealmModule;

public class RumahCemaraApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Kyandroid.init(this, ApiInterface.BASE_URL, ApiInterface.class, "rumahcemaraapp", Context.MODE_PRIVATE
                , "rumahcemara_db", 1, false, new KyandroidRealmModule(), new RumahCemaraRealmModule());
    }
}
