package com.example.slog;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SlogActivity extends Activity {
	ListView lvItems;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slog);
        lvItems = (ListView) findViewById(R.id.call);
        items = getCallDetails();
        System.out.println(items);
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slog, menu);
        return true;
    }


    private ArrayList<String> getCallDetails() {

    	//StringBuffer sb = new StringBuffer();
    	items = new ArrayList<String>();
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
        items.add(obj.toString());
    	//sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
    	//sb.append("\n----------------------------------");
    	}
    	managedCursor.close();
    	return items;
    	}
}
