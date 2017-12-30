package com.example.shariqkhan.wfdsa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.content.CursorLoader;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.shariqkhan.wfdsa.Adapter.EventGalleryGVadapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/29/2017.
 */

public class GalleryActivityMine extends AppCompatActivity {

    ArrayList<EventGalleryModel> imagesList = new ArrayList<EventGalleryModel>();
    GridView gridView;
    FloatingActionButton floatingActionButton;
    EventGalleryGVadapter gridAdapter;
    ImageView imageView;
    ProgressDialog progressDialog;
    public static int MULTI_SELECT = 1;
    String []imagesPath;


    String URL = "http://codiansoft.com/wfdsa/apis/Event/Gallery?";
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_gallery_dialog);
        overridePendingTransition(0,0);
        id = getIntent().getStringExtra("Event_id");

        initUI();
        Task task = new Task();
        task.execute();
        //fetchEventImages();

    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = URL + "event_id=" + id;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Res", s);
            try {
                JSONObject jsonObject = new JSONObject(s);


                JSONObject resultObj = jsonObject.getJSONObject("result");
                String getstatus = resultObj.getString("status");


                if (getstatus.equals("success")) {
                    JSONArray jsonArray = resultObj.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        EventGalleryModel model = new EventGalleryModel(obj.getString("event_id"), obj.getString("gallery_images"));
                        imagesList.add(model);

                    }

                    gridAdapter = new EventGalleryGVadapter(GalleryActivityMine.this, R.layout.event_gallery_item, imagesList);
                    gridView.setAdapter(gridAdapter);
//                    discussionRVAdapter = new InvoiceAdapter(act, discussionList);
//                    RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
//                    rvComments.setLayoutManager(mAnnouncementLayoutManager);
//                    rvComments.setItemAnimator(new DefaultItemAnimator());
//                    rvComments.setAdapter(discussionRVAdapter);

                }
                progressDialog.dismiss();

//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(GalleryActivityMine.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }


    private void fetchEventImages() {
        imagesList.add(new EventGalleryModel("1", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
        imagesList.add(new EventGalleryModel("2", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
        imagesList.add(new EventGalleryModel("3", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
        imagesList.add(new EventGalleryModel("4", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
        imagesList.add(new EventGalleryModel("5", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
        imagesList.add(new EventGalleryModel("6", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
        imagesList.add(new EventGalleryModel("7", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
        imagesList.add(new EventGalleryModel("8", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
        imagesList.add(new EventGalleryModel("9", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));

        gridAdapter = new EventGalleryGVadapter(GalleryActivityMine.this, R.layout.event_gallery_item, imagesList);
        gridView.setAdapter(gridAdapter);
    }

    private void initUI() {
        gridView = (GridView) findViewById(R.id.gridView);
        imageView = (ImageView) findViewById(R.id.close);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddImage);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), MULTI_SELECT);


            }
        });


//        if (MainActivity.DECIDER.equals("member")) {
//            floatingActionButton.setVisibility(View.VISIBLE);
//        } else {
//            floatingActionButton.setVisibility(View.INVISIBLE);
//        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("InsideActivityResult", "Result");
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    imagesPath = new String[count];
                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        try {
                            //Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            imagesPath[currentItem] = getPath(imageUri);
                            Log.e("Path", imagesPath[currentItem]);


                        } catch (Exception e) {
                            Log.e("ExceptionFoundOfImage", e.getMessage());
                            e.printStackTrace();
                        }


                        Log.e("Uri", imageUri.toString());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        currentItem = currentItem + 1;
                    }


                    SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, "",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Response", response);
                                    try {
                                        JSONObject jObj = new JSONObject(response);

                                        String message = jObj.getString("message");

                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        // JSON error
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    for (int i = 0; i <imagesPath.length ; i++) {
                        smr.addFile("image"+String.valueOf(i), imagesPath[i]);
                    }
                    smr.addStringParam("event_id", id);

                    MySingleton.getInstance().addToRequestQueue(smr);









                } else if (data.getData() != null) {
                    imagesPath = new String[1];
                    Bitmap bmp;
                    Uri uri = data.getData();
                    try {
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imagesPath[0]= convertTOString(bmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Uri",imagesPath[0]);
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }

    private String convertTOString(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),    contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


}
