package com.example.shariqkhan.wfdsa.Helper;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShariqKhan on 9/20/2017.
 */

public class getHttpData {
    static String stream = null;

    public getHttpData() {
    }

    public static String getData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();

                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;


    }

    private String getJson() {
        HttpClient httpClient = new DefaultHttpClient();
        String urltoSend = "";

        HttpPost post = new HttpPost(urltoSend);

        StringBuilder buffer = new StringBuilder();

        List<NameValuePair> pairs = new ArrayList<>();

        pairs.add(new BasicNameValuePair("thak", "thak"));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs);
            post.setEntity(encodedFormEntity);
            HttpResponse res = httpClient.execute(post);

            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));
            String Line = "";
            while ((Line = reader.readLine()) != null) {
                buffer.append(Line);

            }

            reader.close();

        } catch (UnsupportedEncodingException e) {
            Log.e("EncodingException", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("ClientException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }


        return buffer.toString();
    }

}

