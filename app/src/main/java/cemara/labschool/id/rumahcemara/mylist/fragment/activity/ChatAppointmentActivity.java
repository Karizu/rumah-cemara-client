package cemara.labschool.id.rumahcemara.mylist.fragment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.centrifugal.centrifuge.android.Centrifugo;
import com.centrifugal.centrifuge.android.credentials.Token;
import com.centrifugal.centrifuge.android.credentials.User;
import com.centrifugal.centrifuge.android.listener.ConnectionListener;
import com.centrifugal.centrifuge.android.listener.DataMessageListener;
import com.centrifugal.centrifuge.android.message.DataMessage;
import com.centrifugal.centrifuge.android.subscription.SubscriptionRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.api.AuthHelper;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Chat;
import cemara.labschool.id.rumahcemara.model.ChatCons;
import cemara.labschool.id.rumahcemara.model.ChatHistory;
import cemara.labschool.id.rumahcemara.model.Datum;
import cemara.labschool.id.rumahcemara.model.Profile;
import cemara.labschool.id.rumahcemara.mylist.fragment.Adapter.ChatAppointmentAdapter;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.adapter.AdapterChat;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAppointmentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;

    @BindView(R.id.message_text)
    EditText messageText;

    @BindView(R.id.btn_send)
    ImageView btnSendChat;

    private Context activity;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ChatAppointmentAdapter mAdapter;
    private List<Chat> mChats;
    private List<Datum> mChat;
    private Profile profile;
    private String appointmentId;
    private String workerId;
    private String workerName;
    private String workerPicture;
    private Realm realm;
    private Centrifugo centrifugo;
    private ArrayList<ChatCons> chats;
    private AdapterChat adapterChat;
    private LinearLayoutManager linearLayoutManager;
    private cemara.labschool.id.rumahcemara.model.Token tokenChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_appointment);
        ButterKnife.bind(this);

        chats = new ArrayList<>();
        appointmentId = getIntent().getStringExtra("appointment_id");
        workerId = getIntent().getStringExtra("worker_id");
        workerName = getIntent().getStringExtra("worker_name");
        workerPicture = getIntent().getStringExtra("worker_picture");
        realm = LocalData.getRealm();
        profile = realm.where(Profile.class).findFirst();
        tokenChat = realm.where(cemara.labschool.id.rumahcemara.model.Token.class).findFirst();
        recyclerView = findViewById(R.id.chat_list);

        activity = this;
//        layoutManager = new LinearLayoutManager(activity,
//                LinearLayout.VERTICAL,
//                false);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setNestedScrollingEnabled(false);

        setToolbar();
        getChatHistory();
//        populateChatHistory(appointmentId);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        String centrifugoAddress = "ws://37.72.172.144:5050/connection/websocket";
        String centrifugoAddress = "ws://68.183.226.23:5050/connection/websocket";
        String userId = profile.getUserId();
        String userToken = ""; //nullable
        String token = tokenChat.getToken();
        String tokenTimestamp = tokenChat.getTimestamp();

        centrifugo = new Centrifugo.Builder(centrifugoAddress)
                .setUser(new User(userId, userToken))
                .setToken(new Token(token, tokenTimestamp))
                .build();
        centrifugo.connect();

        centrifugo.subscribe(new SubscriptionRequest(appointmentId));

        new Thread() {
            @Override
            public void run() {
                super.run();

                centrifugo.setConnectionListener(new ConnectionListener() {
                    @Override
                    public void onWebSocketOpen() {
                        Log.d("centrifugo", "web socket open");
                    }

                    @Override
                    public void onConnected() {
                        Log.d("centrifugo", "on connected");
                    }

                    @Override
                    public void onDisconnected(final int code, final String reason, final boolean remote) {
                        Log.d("centrifugo", "on disconnected");
                    }
                });

                centrifugo.setDataMessageListener(message -> {
                    showMessage(message);
                });
            }
        }.start();
    }

    private void showMessage(final DataMessage message) {
        Log.d("ChatActivity", message.getData());
        runOnUiThread(() -> {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(message.getData());
                ChatCons chat = new ChatCons(jsonObject.get("message").toString(), jsonObject.get("from_id").toString(),
                        jsonObject.get("created_at").toString());
                chats.add(chat);
                adapterChat = new AdapterChat(this, chats);
                recyclerView.setAdapter(adapterChat);
                linearLayoutManager.scrollToPosition(chats.size()-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

//    private void showMessage(final DataMessage message, final String userId) {
//        runOnUiThread(() -> {
//            Gson gson = new Gson();
//            Chat newChat = null;
//            try {
//                newChat = gson.fromJson(message.getBody().getJSONObject("data").toString(), Chat.class);
//                newChat.setId(UUID.randomUUID().toString());
//                newChat.setChannel(message.getBody().getString("channel"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            if (!newChat.getFrom_id().equals(userId)) {
//                mChats.add(newChat);
//                mAdapter.notifyDataSetChanged();
//            }
//
//            LocalData.saveOrUpdate(newChat);
//            Log.e("centrifugo", message.getBody().toString());
//
//        });
//    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(workerName);
        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(workerPicture)
                .into(toolbarImg);
        //chat_appointment
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void populateChatHistory(String channelId){

        mChats = new ArrayList<>();
        mChats.addAll(realm.where(Chat.class).equalTo("channel", channelId).findAll());
        mAdapter = new ChatAppointmentAdapter(mChats, profile , activity);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
    }

    private void getChatHistory() {
        Loading.show(ChatAppointmentActivity.this);
        ListHelper.getChatHistory(appointmentId, new RestCallback<ApiResponse<List<Datum>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<Datum>> body) {
                Loading.hide(ChatAppointmentActivity.this);
                if (body != null && body.isStatus()){
                    List<Datum> res = body.getData();
                    for (int i = 0; i < res.size(); i++) {
                        chats.add(
                                new ChatCons(
                                        res.get(i).getMessage(),
                                        res.get(i).getFromId(),
                                        res.get(i).getCreatedAt()
                                )
                        );
                        linearLayoutManager.scrollToPosition(chats.size()-1);
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(ChatAppointmentActivity.this);
            }

            @Override
            public void onCanceled() {

            }
        });

        adapterChat = new AdapterChat(this, chats);
        recyclerView.setAdapter(adapterChat);
        linearLayoutManager.scrollToPosition(chats.size()-1);
    }

    @OnClick(R.id.btn_send)
    public void sendChat() {
        String commentText = messageText.getText().toString();
        if (!commentText.trim().equals("")){
            btnSendChat.setEnabled(false);

            mChat = new ArrayList<>();

            Datum newChat = new Datum();
            newChat.setChannel(appointmentId);
            newChat.setFromId(profile.getUserId());
            newChat.setFromName(profile.getFullname());
            newChat.setToId(workerId);
            newChat.setMessageType("text");
            newChat.setMessage(commentText);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            newChat.setCreatedAt(dateFormat.format(date));

            try {
                AppointmentHelper.sendMessage(newChat, new RestCallback<ApiResponse>() {
                    @Override
                    public void onSuccess(Headers headers, ApiResponse body) {
                        try {
                            if (body != null && body.isStatus()) {
                                btnSendChat.setEnabled(true);
                                messageText.setText("");
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        btnSendChat.setEnabled(true);
                    }

                    @Override
                    public void onCanceled() {
                        btnSendChat.setEnabled(true);

                    }
                });

                mChat.add(newChat);
                mAdapter.notifyDataSetChanged();


                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        centrifugo.disconnect();
    }
}
