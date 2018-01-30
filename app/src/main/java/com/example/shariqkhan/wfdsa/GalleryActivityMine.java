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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shariqkhan.wfdsa.Adapter.EventGalleryGVadapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/29/2017.
 */

public class GalleryActivityMine extends AppCompatActivity {
    Uri.Builder builder;
    ArrayList<EventGalleryModel> imagesList = new ArrayList<EventGalleryModel>();
    GridView gridView;
    FloatingActionButton floatingActionButton;
    EventGalleryGVadapter gridAdapter;
    ImageView imageView;
    ProgressDialog progressDialog;
    public static int MULTI_SELECT = 1;
    String[] imagesPath;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    JSONObject jsonObject;
    ProgressDialog dialog;

    String URL = GlobalClass.base_url+"wfdsa/apis/Event/Upload_Gallery";
    String id;
    private String imageURI;

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

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = GlobalClass.base_url+"wfdsa/apis/Event/Gallery?" + "event_id=" + id;

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

        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

        if (!prefs.getString("type", "").equals("member")) {

            floatingActionButton.setVisibility(View.GONE);

        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose application"), 100);
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
        super.onActivityResult(requestCode, resultCode, data);
        dialog.show();

        try {
            // When an Image is picked
            if (requestCode == 100 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesUriList = new ArrayList<Uri>();
                encodedImageList.clear();
//                if(data.getData()!=null){
//
//                    Uri mImageUri=data.getData();
//
//                    // Get the cursor
//                    Cursor cursor = getContentResolver().query(mImageUri,
//                            filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imageURI  = cursor.getString(columnIndex);
//                    cursor.close();
//
//                }else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageURI = cursor.getString(columnIndex);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        encodedImageList.add(encodedImage);
                        cursor.close();

                    }
                    //  noImage.setText("Selected Images: " + mArrayUri.size());
                    Log.e("Size", String.valueOf(encodedImageList.size()));
                } else if (data.getData() != null) {


                    imagesUriList = new ArrayList<Uri>();
                    encodedImageList.clear();
                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageURI = cursor.getString(columnIndex);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    encodedImageList.add(encodedImage);
                    cursor.close();

                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Northing", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        dialog.dismiss();
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
        //  Toast.makeText(this, String.valueOf(encodedImageList.size()), Toast.LENGTH_SHORT).show();
        try {
            builder = Uri.parse(URL).buildUpon();
            jsonObject.put("event_id", SelectedEventActivity.id);
            jsonObject.put("user_id", MainActivity.getId);
            Log.e("event_id", SelectedEventActivity.id);
            jsonObject.put("imageList", jsonArray);
        } catch (JSONException e) {
            Log.e("JSONObject Here", e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, builder.toString(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("Ok", jsonObject.toString());
                        dialog.dismiss();

                        Toast.makeText(getApplication(), "Images Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error", volleyError.toString());
                Toast.makeText(getApplication(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200 * 30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjectRequest);

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


}
