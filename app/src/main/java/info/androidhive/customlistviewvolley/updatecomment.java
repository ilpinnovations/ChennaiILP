package info.androidhive.customlistviewvolley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.R.layout;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")

public class updatecomment  extends AsyncTask<String,Void,String> {
	
	   private Context context;
	   private int byGetOrPost = 0; 
	   //flag 0 means get and 1 means post.(By default it is get.)
	   
	   public updatecomment(Context context) {
	   }

	   protected void onPreExecute(){
	   }
	   
	   @Override
	   protected String doInBackground(String... arg0) {
		   
	         try{
	            String name = arg0[0];

				 String link =
						 "http://theinspirer.in/chennai/uploadcomment2.php?name=" + MainActivity.name +
								 "&empid=" + MainActivity.employeeid +
								 "&comment=" + arg0[0] +
								 "&rating=" + arg0[1] +
								 "&ano=" + arg0[2];

	            Log.d("myTag", link);	            
	            URL url = new URL(link.trim().replace(" ", "xshift1"));
	            URLConnection conn = url.openConnection(); 
	            conn.setDoOutput(true); 
	            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
	           
	            wr.flush(); 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            
	            // Read Server Response
	            while((line = reader.readLine()) != null)
	            {
	               sb.append(line);
	               break;
	            }
	            return sb.toString();
	         }
	         catch(Exception e){
	            return new String(e.getMessage()+"Exception: null");
	         }
	      }
	   
	   @Override
	   protected void onPostExecute(String result){
		   rating2.result.setText(result);
	   }	  
}
