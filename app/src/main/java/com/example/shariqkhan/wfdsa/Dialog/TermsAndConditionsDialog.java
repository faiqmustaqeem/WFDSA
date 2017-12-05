package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.R;


/**
 * Created by Codiansoft on 10/18/2017.
 */

public class TermsAndConditionsDialog extends Dialog implements View.OnClickListener{

    public Activity act;
    public Dialog d;


    public TermsAndConditionsDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
        Dialog dialog = new Dialog(act);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_terms_and_conditions);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        initUI();
    }

    private void initUI() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.tvCancel:
                dismiss();
                break;*/
        }
    }
}
