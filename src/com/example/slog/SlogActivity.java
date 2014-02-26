package com.example.slog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SlogActivity extends Activity {
	ListView lvItems;
	ArrayList<record> items;
	ArrayAdapter<record> itemsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slog);
        lvItems = (ListView) findViewById(R.id.call);
        items = getCallDetails();
        System.out.println(items);
        itemsAdapter = new MyListAdapter();
        lvItems.setAdapter(itemsAdapter);
        final Button button = (Button) findViewById(R.id.postlog);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click  
            	items = getCallDetails();
            	//JSONArray jsArray = new JSONArray(items);
            	new Postdata().execute(items);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slog, menu);
        return true;
    }

    private ArrayList<record> getCallDetails() {

    	//StringBuffer sb = new StringBuffer();
    	items = new ArrayList<record>();
    	JSONObject obj = new JSONObject();
		Cursor managedCursor = getContentResolver().query( CallLog.Calls.CONTENT_URI,null, null,null, null);
    	int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
    	int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
    	int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
    	int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
    	while ( managedCursor.moveToNext() ) {
    	String phNumber = managedCursor.getString( number );
    	String callType = managedCursor.getString( type );
    	String callDate = managedCursor.getString( date );
    	Date callDayTime = new Date(Long.valueOf(callDate));
    	String callDuration = managedCursor.getString( duration );
    	String dir = null;
    	int dircode = Integer.parseInt( callType );
    	switch( dircode ) {
    	case CallLog.Calls.OUTGOING_TYPE:
    	dir = "OUTGOING";
    	break;

    	case CallLog.Calls.INCOMING_TYPE:
    	dir = "INCOMING";
    	break;

    	case CallLog.Calls.MISSED_TYPE:
    	dir = "MISSED";
    	break;
    	}
        try {
			obj.put("number", phNumber);
	        obj.put("call_type",dir );
	        obj.put("time", callDayTime);
	        obj.put("duration", callDuration);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        items.add(new record(dir, phNumber, Integer.parseInt(callDuration), 0,callDayTime));
    	//sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
    	//sb.append("\n----------------------------------");
    	}
    	managedCursor.close();
    	return items;
    	}
    
    private class MyListAdapter extends ArrayAdapter<record> {
		public MyListAdapter() {
			super(SlogActivity.this, R.layout.item_view, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
			}
			
			// Find the car to work with.
			record currentrecord = items.get(position);
			
			// Fill the view
			//ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
			//imageView.setImageResource(currentrecord.getIconID());
			
			// Make:
			TextView makeText = (TextView) itemView.findViewById(R.id.item_txtNum);
			makeText.setText(currentrecord.getType());

			// Year:
			TextView yearText = (TextView) itemView.findViewById(R.id.item_txtCalltype);
			yearText.setText("" + currentrecord.getDuration());
			
			// Condition:
			TextView condionText = (TextView) itemView.findViewById(R.id.item_txtDuration);
			condionText.setText(currentrecord.getNum());

			return itemView;
		}				
	}

}
