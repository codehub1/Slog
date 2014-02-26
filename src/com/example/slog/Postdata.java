package com.example.slog;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.os.AsyncTask;

public class Postdata extends AsyncTask<ArrayList<record>, Void, ArrayList<record>> {
    @Override
    protected ArrayList<record> doInBackground(ArrayList<record>... items) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://postcatcher.in/catchers/530dbc5a606c040200001e26");

            // Add your data
            ArrayList<record> logs = items[0];
            JSONArray ja = new JSONArray();
            for (int i=0; logs.size()>i; i++) {
            	record log = logs.get(i);
            	System.out.println(log.toJSON());
            	ja.put(log.toJSON());
            }
            
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("data", ja.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

/*            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String line = "0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            String result11 = sb.toString();*/

            // parsing data
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<record> result) {
        if (result != null) {
            // do something
        } else {
            // error occured
        }
    }
}