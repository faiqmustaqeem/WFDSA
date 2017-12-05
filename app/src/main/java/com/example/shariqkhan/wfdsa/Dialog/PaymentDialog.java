package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.shariqkhan.wfdsa.R;


/**
 * Created by Codiansoft on 10/27/2017.
 */

public class PaymentDialog extends Dialog implements View.OnClickListener{

    public Context act;
    public Dialog d;


    public PaymentDialog(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
        Dialog dialog = new Dialog(act);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_payment);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
