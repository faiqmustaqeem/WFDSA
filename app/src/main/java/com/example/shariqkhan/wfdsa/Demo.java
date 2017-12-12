package com.example.shariqkhan.wfdsa;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

/**
 * Created by Codiansoft on 12/8/2017.
 */

public class Demo extends AppCompatActivity {

    FullscreenVideoLayout vid;
    MediaController media;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_try);
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.video_dialog_view);

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setLayout(lp.width, lp.height);

        vid = (FullscreenVideoLayout) dialog.findViewById(R.id.videoview);
        vid.setShouldAutoplay(true);
        media = new MediaController(this);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoviewtestingvideo);
        try {
            vid.setVideoURI(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.show();

    }
}
