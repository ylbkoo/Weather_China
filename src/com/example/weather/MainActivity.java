package com.example.weather;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.example.weather.DisplayMessageActivity;
import com.example.weather.R;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.weather.MESSAGE";
	public static Map<String, String> map = new HashMap<String, String>();
	String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i("see", "entered onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button 
     * @throws Exception */
    public void Lookup(View view) throws Exception {
    
        // Do something in response to button
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	
    	//turn "citycode.txt" into hashmap "map"
    	InputStream inputStream = getResources().openRawResource(R.raw.citycode);  
    	InputStreamReader inputStreamReader = null;  
    	try {  
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        }  
    	BufferedReader in = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = in.readLine()) != null) {
            String parts[] = line.split("=");
            map.put(parts[1], parts[0]);
        }
        in.close();
        System.out.println(map.toString());
        
        //get code 
        if (map.containsKey(message)) {
        	code = map.get(message);
        } else {
        	code = "0";
        }
    	intent.putExtra(EXTRA_MESSAGE, code);
    	startActivity(intent);
    }

}

