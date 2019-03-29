package cemara.labschool.id.rumahcemara.mylist.fragment.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rezkyatinnov.kyandroid.localdata.LocalData;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.model.ChatCons;
import cemara.labschool.id.rumahcemara.model.User;
import io.realm.Realm;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ViewHolder> {
    private List<ChatCons> mData;
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private Context context;

    public AdapterChat(Context context, List<ChatCons> data) {
        this.context = context;
        this.mData = data;
    }

    @Override
    public AdapterChat.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();
        String userId = user.getId();

        if (!userId.equals(mData.get(position).getFromId())) {
            return MSG_TYPE_LEFT;
        } else {
            return MSG_TYPE_RIGHT;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(AdapterChat.ViewHolder holder, int position) {
        String message = mData.get(position).getMessage();
        String dateMessage = mData.get(position).getCreatedAt();


        holder.textViewMessage.setText(message);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(dateMessage);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PrettyTime p  = new PrettyTime();
        String datetime= p.format(convertedDate);
        holder.textViewDate.setText(datetime);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        TextView textViewDate;

        ViewHolder(View v) {
            super(v);
            textViewMessage = v.findViewById(R.id.text_chat);
            textViewDate = v.findViewById(R.id.text_date);
        }
    }
}
