package cemara.labschool.id.rumahcemara.options.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.auth.activity.LoginActivity;
import cemara.labschool.id.rumahcemara.options.fragment.activity.EditAccountActivity;
import cemara.labschool.id.rumahcemara.options.fragment.activity.WebActivity;

public class OptionsFragment extends Fragment {

    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.text_toolbar_title)
    TextView title;

    Dialog dialog;

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

    @OnClick(R.id.logout)
    public void dialogLogout() {
        /**VERSI 1**/
        /*new AlertDialog.Builder(getActivity())
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();*/
        /**VERSI 1**/
        showDialog(R.layout.dialog_logout);
        Button btn_ya = dialog.findViewById(R.id.yes_logout);
        Button btn_no = dialog.findViewById(R.id.no_logout);
        btn_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.privacy, R.id.notification, R.id.faq, R.id.term, R.id.rateapp, R.id.shareapp})
    public void onViewClicked(View view) {
        Intent i = new Intent(getContext(), WebActivity.class);
        switch (view.getId()) {
            case R.id.privacy:
                i.putExtra("link", "https://rumahcemara.or.id/tentang-kami/");
                i.putExtra("title", "Privacy Policy");
                startActivity(i);
                break;
            case R.id.notification:
                i.putExtra("link", "https://rumahcemara.or.id/term-and-condition/");
                i.putExtra("title", "Notification");
                startActivity(i);
                break;
            case R.id.faq:
                i.putExtra("link", "https://rumahcemara.or.id/tentang-kami/");
                i.putExtra("title", "FAQ");
                startActivity(i);
                break;
            case R.id.term:
                i.putExtra("link", "https://rumahcemara.or.id/term-and-condition/");
                i.putExtra("title", "Term & Condition");
                startActivity(i);
                break;
            case R.id.rateapp:
                i.putExtra("link", "https://rumahcemara.or.id/term-and-condition/");
                i.putExtra("title", "Rate App");
                startActivity(i);
                break;
            case R.id.shareapp:
                i.putExtra("link", "https://rumahcemara.or.id/term-and-condition/");
                i.putExtra("title", "Share App");
                startActivity(i);
                break;
        }
    }

    private void showDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(getContext()));
        //SET TITLE
        dialog.setTitle("Logout");

        //set content
        dialog.setContentView(layout);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
