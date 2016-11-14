package info.androidhive.customlistviewvolley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.R.layout;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")

public class checkalreadyregistered  extends AsyncTask<String,Void,String> {
	   
	private Context context1;
	private int byGetOrPost = 0;
	ProgressDialog pd;
	//flag 0 means get and 1 means post.(By default it is get.)
	   
	public checkalreadyregistered (Context context) {
		context1 = context;
	   }

	protected void onPreExecute(){
		pd = ProgressDialog.show(context1, "", "");
		pd.setContentView(R.layout.progress2);
		pd.setCancelable(false);
		pd.show();
	   }


	   @Override
	   protected String doInBackground(String... arg0) {

	         try{
	        	String link = "http://theinspirer.in/chennai/checkregister.php?imei=" + arg0[0];
	            Log.d("myTag", link);	            
	            URL url = new URL(link.trim().replace(" ", "xyzzyspoonshift1"));
	            
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
	            return new String(e.getMessage() + "Exception: null");
	         }
	      }
	   
	   @Override
	   protected void onPostExecute(String result){
		   pd.cancel();
		   MainActivity.imeicheck.setText(result);
	   }	  
}
