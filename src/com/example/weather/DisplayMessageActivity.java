package com.example.weather;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.weather.MainActivity;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
	
	private TextView tvJson; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 tvJson = new TextView(this);
	     // Get the message from the intent
	        Intent intent = getIntent();
	        String code = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	        
	        if (code.equals("0")) {
	        	tvJson.setText("未找到该城市，请重新输入");
	        	tvJson.setTextSize(20);
	        } else {
		        String url = String.format("http://www.weather.com.cn/data/sk/"+"%s"+".html",
		                   code);
		        System.out.println(url);
		       //获得返回的Json字符串 
		        String strResult;
				try {				
					strResult = connServerForResult(url);		
				//解析Json字符串 
		        parseJson(strResult); 
		        tvJson.setTextSize(40);
				} catch (Exception e) {
					System.out.println("exception here");
					e.printStackTrace();
				} 
	        }
	        
	        tvJson.setGravity(Gravity.CENTER);
	        // Set the text view as the activity layout
	        setContentView(tvJson);       
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	private String connServerForResult(String strUrl) throws Exception { 		
		     
		        URL url;		        
		        url = new URL(strUrl);	        
		        InputStream is =  url.openStream();
		        Reader reader = new InputStreamReader(is);        
		        BufferedReader in = new BufferedReader(reader);
		        String line;		        
		        StringBuffer sb = new StringBuffer();		        
		        while ((line = in.readLine()) != null) {
		        	sb.append(line);
		        }
		        in.close();		        
		        String strResult = sb.toString(); 
		        System.out.println(strResult);		        
		        return strResult; 		        
		    } 

		    // 普通Json数据解析 
	private void parseJson(String strResult) { 		
		        try { 		
		            JSONObject jsonObj = new JSONObject(strResult).getJSONObject("weatherinfo"); 
		            String city = jsonObj.getString("city"); 		
		            String temp = jsonObj.getString("temp"); 		
		            String wd = jsonObj.getString("WD"); 
		            String ws = jsonObj.getString("WS"); 
		            String sd = jsonObj.getString("SD"); 
		            System.out.println("城市:"+ city + ", 温度：" + temp + ",风向：" + wd);
		            tvJson.setText("城市:"+ city + ", \n温度:" + temp + "摄氏度, \n风向:" + wd); 		
		        } catch (JSONException e) { 		
		            System.out.println("Json parse error"); 		
		            e.printStackTrace(); 		
		        } 		
		    } 
   }

