package cemara.labschool.id.rumahcemara.util.bottomSheet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.find_service_provider_bottom_sheet_dialog, container, false);
//        ButterKnife.bind(this,rootView);
        return rootView;
    }
    @OnClick(R.id.sheet_btn_close)
    public void closeBottomSheet(){
        getDialog().dismiss();
    }

}
