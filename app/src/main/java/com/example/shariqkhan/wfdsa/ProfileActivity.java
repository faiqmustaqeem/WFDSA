package com.example.shariqkhan.wfdsa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Dialog.ProfileEditPermissionDialog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.profile_image)
    ImageView profile_image;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvSignOut)
    TextView tvSignOut;
    @BindView(R.id.tvEditOrUpdate)
    TextView tvEditOrUpdate;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;

    ProfileEditPermissionDialog d;
    public static boolean canEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        initUI();

        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (canEdit) {
                    makeFieldsEditable();
                    tvEditOrUpdate.setText("SAVE");
                }
            }
        });





    }

    private void makeFieldsEditable() {
        etFirstName.setEnabled(true);
        etLastName.setEnabled(true);
        etEmail.setEnabled(true);
        etMobileNumber.setEnabled(true);
    }

    private void initUI() {
        d = new ProfileEditPermissionDialog(this);
        ivBack.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);
        tvEditOrUpdate.setOnClickListener(this);


        Picasso.with(this).load("https://qph.ec.quoracdn.net/main-thumb-64803074-50-dxlthhqswgcemtdghaduorrgglmdgifa.jpeg").into(profile_image);

    }

    @OnClick({R.id.ivBack, R.id.tvSignOut, R.id.tvEditOrUpdate})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSignOut:
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Are you sure to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /*SharedPreferences settings = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("apiSecretKey", "");
                                editor.putString("userID", "");
                                editor.commit();*/

                                Intent logoutIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                                dialog.dismiss();
                                ActivityCompat.finishAffinity(ProfileActivity.this);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ProfileActivity.this.startActivity(logoutIntent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
                break;
            case R.id.tvEditOrUpdate:
                if (canEdit) {
                    updateProfile();
                } else {
                    d.show();
                }
                break;

        }
    }

    private void updateProfile() {
        finish();
    }
}
