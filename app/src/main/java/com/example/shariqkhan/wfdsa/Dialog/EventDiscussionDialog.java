package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.DiscussionRVAdapter;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.DiscussionModel;
import com.example.shariqkhan.wfdsa.Model.MessageModel;
import com.example.shariqkhan.wfdsa.R;
import com.example.shariqkhan.wfdsa.SelectedEventActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 11/4/2017.
 */

public class EventDiscussionDialog extends Dialog implements View.OnClickListener {
    public Activity act;
    public Dialog d;
    ArrayList<MessageModel> discussionList = new ArrayList<>();
    ImageView imageView;

    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.tvSendComment)
    TextView tvSendComment;

    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    DiscussionRVAdapter discussionRVAdapter;
    DatabaseReference mRootRef;


    public EventDiscussionDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_discussion_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        mRootRef = FirebaseDatabase.getInstance().getReference().child("Messages").child(SelectedEventActivity.id);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        discussionRVAdapter = new DiscussionRVAdapter(act, discussionList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
        rvComments.setLayoutManager(mAnnouncementLayoutManager);
        rvComments.setItemAnimator(new DefaultItemAnimator());

        loadMessages();
        tvSendComment.setOnClickListener(this);

        rvComments.setAdapter(discussionRVAdapter);

        imageView = findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initUI() {

        discussionRVAdapter = new DiscussionRVAdapter(act, discussionList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
        rvComments.setLayoutManager(mAnnouncementLayoutManager);
        rvComments.setItemAnimator(new DefaultItemAnimator());

        loadMessages();
        tvSendComment.setOnClickListener(this);

        rvComments.setAdapter(discussionRVAdapter);

        imageView = findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private boolean validComment() {
        boolean check = false;
        if (etComment.getText().toString().equals("")) {
            check = false;
        } else {
            check = true;
        }
        /*if (etName.getText().toString().equals("")) {
            etName.setError("This field is required");
            check = false;
        } else {
            check = true;
        }*/
        return check;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSendComment:
                if (validComment()) {
                    //  loadMessages();
                    //get time
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                    //get date
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String time = sdfTime.format(date);
                    String Date = dateFormat.format(date);


                    //   Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show();
                    String gettingText = etComment.getText().toString();
                    if (!TextUtils.isEmpty(gettingText)) {
                        //Defining strings takay har bar reference mai likhna na paray

                        String current_user_reference = "Messages/";
                        String chat_user_reference = "Messages/";
                        //for push id of user

                        DatabaseReference user_message_push_id = mRootRef
                                .push();

                        String push_id = user_message_push_id.getKey();

                        Map messageMap = new HashMap();
                        messageMap.put("name", MainActivity.getFirstName + " " + MainActivity.getLastName);
                        messageMap.put("message", gettingText);
                        messageMap.put("post", "DSA MEMBER");
                        messageMap.put("date", Date);
                        messageMap.put("time", time);


                        Map messageUserMap = new HashMap();

                        messageUserMap.put("/" + push_id, messageMap);


                        mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                if (databaseError != null) {
                                    Log.d("DbError", databaseError.getMessage().toString());

                                } else {
                                    discussionList.clear();
                                    loadMessages();
                                }
                            }
                        });

                    }

                    // discussionRVAdapter.notifyDataSetChanged();
                    etComment.setText("");
                }
                break;
        }
    }

    private void loadMessages() {

        // DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Messages").child(SelectedEventActivity.id);
        discussionList.clear();

        mRootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//               Log.e("Inside", String.valueOf(dataSnapshot.getChildrenCount()));
//               for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                   //arrayList.add(String.valueOf(dsp.geValue()));
//                   MessageModel m = new MessageModel();
//
//                   String name = dsp.child("name").getValue().toString();
//                   String message = dsp.child("message").getValue().toString();
//                   String post = dsp.child("post").getValue().toString();
//                   String date = dsp.child("date").getValue().toString();
//                   String time = dsp.child("time").getValue().toString();
//
//
//                   m.setName(name);
//                   m.setMessage(message);
//                   m.setPost(post);
//                   m.setDate(date);
//                   m.setTime(time);
//                   discussionList.add(m);
//                   Log.e("size", String.valueOf(discussionList.size()));
//
//
//               }

                MessageModel m = dataSnapshot.getValue(MessageModel.class);

                    discussionList.add(m);


                discussionRVAdapter.notifyDataSetChanged();

                Log.e("After notify!", String.valueOf(discussionList.size()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}