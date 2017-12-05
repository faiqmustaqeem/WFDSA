package com.example.shariqkhan.wfdsa;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.shariqkhan.wfdsa.SplashActivity.fetchAnnouncements;
import static com.example.shariqkhan.wfdsa.SplashActivity.fetchEvents;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ivWFDSALogo)
    ImageView ivWFDSALogo;
    @BindView(R.id.tvMember)
    TextView tvMember;
    @BindView(R.id.tvNonMember)
    TextView tvNonMember;
    @BindView(R.id.clMember)
    ConstraintLayout clMember;
    @BindView(R.id.clNonMember)
    ConstraintLayout clNonMember;
    @BindView(R.id.llMember)
    LinearLayout llMember;
    @BindView(R.id.llNonMember)
    LinearLayout llNonMember;
    @BindView(R.id.tvNonMemberSignUp)
    TextView tvNonMemberSignUp;
    @BindView(R.id.tvMemberForgetPassword)
    TextView tvMemberForgetPassword;
    @BindView(R.id.tvMemberSignIn)
    TextView tvMemberSignIn;
    @BindView(R.id.tvNonMemberSignIn)
    TextView tvNonMemberSignIn;

    ImageView ivBack;

    android.app.ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2016/02/logo.jpg").into(ivWFDSALogo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Sign In");
        ivBack = (ImageView)findViewById(R.id.ivBack);
        ivBack.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvMember:
                llMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                llNonMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                clMember.setVisibility(View.VISIBLE);
                clNonMember.setVisibility(View.GONE);
                break;
            case R.id.tvNonMember:
                llMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                llNonMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                clNonMember.setVisibility(View.VISIBLE);
                clMember.setVisibility(View.GONE);
                break;
            case R.id.tvNonMemberSignUp:
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.tvNonMemberSignIn:
            case R.id.tvMemberSignIn:
                fetchEvents();
                fetchAnnouncements();
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(loginIntent);
                LoginActivity.this.finish();
                break;
            case R.id.tvMemberForgetPassword:
            case R.id.tvNonMemberForgetPassword:
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(forgotPasswordIntent);
                break;
        }
    }

/*    public void hideOrShowLoginForms() {
        if (clMember.getVisibility() == View.VISIBLE) {
            llMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            llNonMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
//            tvNo
            clNonMember.setVisibility(View.VISIBLE);
            clMember.setVisibility(View.GONE);
        } else {
            llMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
            llNonMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            clMember.setVisibility(View.VISIBLE);
            clNonMember.setVisibility(View.GONE);
        }
    }*/
}
