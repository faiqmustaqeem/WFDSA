package com.example.shariqkhan.wfdsa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.content.CursorLoader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.IpCons;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.shariqkhan.wfdsa.Adapter.EventGalleryGVadapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.Model.Gallery;
import com.example.shariqkhan.wfdsa.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Codiansoft on 12/29/2017.
 */

public class GalleryActivityMine extends AppCompatActivity {
    public static int MULTI_SELECT = 1;
    Uri.Builder builder;
    ArrayList<EventGalleryModel> imagesList = new ArrayList<EventGalleryModel>();
    GridView gridView;
    FloatingActionButton floatingActionButton;
    EventGalleryGVadapter gridAdapter;
    ImageView imageView;
    ProgressDialog progressDialog;
    String[] imagesPath;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    JSONObject jsonObject;
    ProgressDialog dialog;

    String URL = GlobalClass.base_url+"wfdsa/apis/Event/Upload_Gallery";
    String id;
    private String imageURI;
    int new_images_uploaded=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_gallery_dialog);
        overridePendingTransition(0, 0);
        id = getIntent().getStringExtra("Event_id");


        initUI();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        jsonObject = new JSONObject();
        encodedImageList = new ArrayList<>();

        Task task = new Task();
        task.execute();
        //fetchEventImages();

    }

//    private void fetchEventImages() {
//        imagesList.add(new EventGalleryModel("1", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//        imagesList.add(new EventGalleryModel("2", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
//        imagesList.add(new EventGalleryModel("3", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
//        imagesList.add(new EventGalleryModel("4", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
//        imagesList.add(new EventGalleryModel("5", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//        imagesList.add(new EventGalleryModel("6", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
//        imagesList.add(new EventGalleryModel("7", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
//        imagesList.add(new EventGalleryModel("8", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//        imagesList.add(new EventGalleryModel("9", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
//
//        gridAdapter = new EventGalleryGVadapter(GalleryActivityMine.this, R.layout.event_gallery_item, imagesList);
//        gridView.setAdapter(gridAdapter);
//    }

    private void initUI() {
        gridView = (GridView) findViewById(R.id.gridView);
        imageView = (ImageView) findViewById(R.id.close);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddImage);

        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

        if (!prefs.getString("type", "").equals("member")) {

            floatingActionButton.setVisibility(View.GONE);

        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("image", "1");
                if(SelectedEventActivity.photos_upload_left == 0)
                {
                    Toast.makeText(GalleryActivityMine.this,"You can not upload more Images , You have already uploaded 5 Images in this event",Toast.LENGTH_LONG).show();
                }
                else {
                    startActivityForResult(
                            ImagePicker.create(GalleryActivityMine.this)
                                    .folderMode(true) // folder mode (false by default)
                                    .toolbarFolderTitle("Folder") // folder selection title
                                    .toolbarImageTitle("Tap to select") // image selection title
                                    .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                                    .multi() // multi mode (default mode)
                                    .showCamera(false)
                                    .limit(SelectedEventActivity.photos_upload_left) // max images can be selected (99 by default)
                                    .imageDirectory("Camera")
                                    .getIntent(GalleryActivityMine.this), IpCons.RC_IMAGE_PICKER);

                    Log.e("image", "2");

                }

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Choose application"), 100);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        dialog.show();
        Log.e("image", "3");


        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            Log.e("image", "4");

            List<com.esafirm.imagepicker.model.Image> images = ImagePicker.getImages(data);
            encodedImageList.clear();



            for (int k = 0; k < images.size(); k++) {
                com.esafirm.imagepicker.model.Image image = images.get(k);
                String path = image.getPath();
                if (!path.equals("")) {
                    Log.e("path", path);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    encodedImageList.add(encodedImage);
                }

            }


            JSONArray jsonArray = new JSONArray();
            if (encodedImageList.isEmpty()) {
                Toast.makeText(this, "Please select some images first.", Toast.LENGTH_SHORT).show();
                return;
            }

            for (String encoded : encodedImageList) {
                jsonArray.put(encoded);
                Log.e("forLoop", "o");
            }
            new_images_uploaded=encodedImageList.size();
            //  Toast.makeText(this, String.valueOf(encodedImageList.size()), Toast.LENGTH_SHORT).show();
            try {

                builder = Uri.parse(URL).buildUpon();
                jsonObject.put("event_id", SelectedEventActivity.id);
                jsonObject.put("user_id", MainActivity.getId);
                Log.e("event_id", SelectedEventActivity.id);
                jsonObject.put("imageList", jsonArray);

                Log.e("image_upload_params", jsonObject.toString());
            } catch (JSONException e) {
                Log.e("JSONObject Here", e.toString());

            }

            Log.e("builder", builder.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, builder.toString(), jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.e("Ok", jsonObject.toString());

                            dialog.dismiss();

                            Log.e("image_upload_response", jsonObject.toString());

                            try {

                                Toast.makeText(getApplication(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                SelectedEventActivity.photos_upload_left=SelectedEventActivity.photos_upload_left-new_images_uploaded;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Log.e("Volley_error", error.getMessage());
                            } else {
                                Toast.makeText(GalleryActivityMine.this, "You application is not connected to internet", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                            finish();
                        }
                    });
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200 * 30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }
        super.onActivityResult(requestCode, resultCode, data);


    }


    private String convertTOString(Bitmap bmp) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = GlobalClass.base_url + "wfdsa/apis/Event/Gallery?" + "event_id=" + id;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                Log.e("Res", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);


                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    String getstatus = resultObj.getString("status");


                    if (getstatus.equals("success")) {
                        JSONArray jsonArray = resultObj.getJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            boolean isRegistered = GlobalClass.isAlreadyRegistered;
                            boolean isCheckedIn = SelectedEventActivity.isCheckedIn;
                            String permission = obj.getString("permission");

                            if (permission.equals("1")) {
                                if (isRegistered && isCheckedIn) {
                                    EventGalleryModel model = new EventGalleryModel(obj.getString("event_id"), obj.getString("gallery_images"));
                                    imagesList.add(model);
                                }
                            } else if (permission.equals("0")) {
                                if (isRegistered) {
                                    EventGalleryModel model = new EventGalleryModel(obj.getString("event_id"), obj.getString("gallery_images"));
                                    imagesList.add(model);
                                }
                            }
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
            else {
                Toast.makeText(GalleryActivityMine.this , "You are not connected to internet !" , Toast.LENGTH_LONG).show();
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


}
