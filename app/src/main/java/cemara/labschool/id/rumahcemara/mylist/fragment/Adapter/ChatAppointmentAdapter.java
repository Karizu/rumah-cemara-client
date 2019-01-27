package cemara.labschool.id.rumahcemara.mylist.fragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.model.Chat;
import cemara.labschool.id.rumahcemara.model.Profile;

public class ChatAppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Chat> chats;
    private Context context;
    private Profile profile;

    public ChatAppointmentAdapter(List<Chat> mChats, Profile profile, Context context){
        this.chats = mChats;
        this.context = context;
        this.profile = profile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v1 = inflater.inflate(R.layout.row_chat, viewGroup, false);
        viewHolder = new ThreadViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Chat dataResponse = chats.get(position);
        ThreadViewHolder threadViewHolder = (ThreadViewHolder) viewHolder;
        if (this.profile.getUserId().equals(dataResponse.getFrom_id())){
            threadViewHolder.threadLayout.setVisibility(View.VISIBLE);
            threadViewHolder.threadLayoutTheir.setVisibility(View.GONE);

            threadViewHolder.content.setText(dataResponse.getMessage());

            String result = "Now";
            try {
                PrettyTime timeAgo = new PrettyTime();
                result = timeAgo.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataResponse.getCreated_at()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            threadViewHolder.timeAgo.setText(result);
        }
        else{
            threadViewHolder.threadLayout.setVisibility(View.GONE);
            threadViewHolder.threadLayoutTheir.setVisibility(View.VISIBLE);

            threadViewHolder.contentTheir.setText(dataResponse.getMessage());

            String result = "Now";
            try {
                PrettyTime timeAgo = new PrettyTime();
                result = timeAgo.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataResponse.getCreated_at()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            threadViewHolder.timeAgoTheir.setText(result);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Chat dataResponse = chats.get(position);
        if (this.profile.getUserId().equals(dataResponse.getFrom_id())){
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ThreadViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView timeAgo;
        public TextView content;
        public LinearLayout threadLayout;

        public TextView timeAgoTheir;
        public TextView contentTheir;
        public LinearLayout threadLayoutTheir;

        public ThreadViewHolder(View v){
            super(v);

            timeAgo = v.findViewById(R.id.tvThradTime);
            content = v.findViewById(R.id.tvThreadComment);
            threadLayout = v.findViewById(R.id.thread_me_container);

            timeAgoTheir = v.findViewById(R.id.tvThradTimeTheir);
            contentTheir = v.findViewById(R.id.tvThreadCommentTheir);
            threadLayoutTheir = v.findViewById(R.id.thread_their_container);
        }
    }
}
