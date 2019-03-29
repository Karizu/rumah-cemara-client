package cemara.labschool.id.rumahcemara.util.firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "RumahCemaraPref";
    private static final String NOTIFICATION = "notification";

    @SuppressLint("CommitPrefEdits")
    public SessionManagement(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public void setNotification(int notification) {
        editor.putInt(NOTIFICATION, notification);
        editor.commit();
    }

    public int getNotification() {
        int notification;
        notification = pref.getInt(NOTIFICATION, 0);
        return notification;
    }
}


