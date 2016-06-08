package info.androidhive.customlistviewvolley;

import info.androidhive.customlistviewvolley.adater.CustomListAdapterforcomments;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

@SuppressWarnings("unused")

public class comments extends Activity implements OnItemSelectedListener {
	// Log tag
	private static final String TAG = comments.class.getSimpleName();

	// Movies json url
	private static  String url = "http://theinspirer.in/chennai/getcomments2.php?ratingtable=" + list.sno;
	
	private ProgressDialog pDialog;
	private List<Movie> movieList = null;
	private ListView listView;
	private CustomListAdapterforcomments adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.comments);
		
		url = "http://theinspirer.in/chennai/getcomments2.php?ratingtable=" + list.sno;
		
		TextView t1 = (TextView)findViewById(R.id.textView1);
	    TextView t2 = (TextView)findViewById(R.id.textView4);
	    t1.setText(list.placename);
	    t2.setText(list.placeaddress);
		callfiller();
	}
    
	
	ProgressDialog pd;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	void callfiller()
	{		
		movieList = new ArrayList<Movie>();
		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapterforcomments(this, movieList);
		listView.setAdapter(adapter);

		pd = ProgressDialog.show(comments.this, "", "");
		pd.setContentView(R.layout.progress);
		pd.setCancelable(false);
		pd.show();
		// changing action bar color
		
		//image load
		imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.thumbnail);
		thumbNail.setImageUrl(list.placeimage, imageLoader);
		//image load

		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject obj = response.getJSONObject(i);
								Movie movie = new Movie();
								movie.setTitle(obj.getString("title"));
								movie.setThumbnailUrl(obj.getString("image"));
								movie.setRating(((Number) obj.get("rating")).doubleValue());
								movie.setYear(obj.getInt("releaseYear"));

								// Genre is json array
								JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
								}
								
								movie.setGenre(genre);

								// adding movie to movies array
								movieList.add(movie);

							} 
							catch (JSONException e) 
							{
								e.printStackTrace();
							}
						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					pd.cancel();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		pd.cancel();
	}

	private void hidePDialog() {
		pd.cancel();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {	
	}
	
	int from = 0;
	int to = 10;
	
	public void next(View v)
	{
		from = (from+10);
		to = (to+10);

		url = "http://theinspirer.in/chennai/list.php?from="+from+"&to="+to;
		Log.d("asd",url);
		callfiller();
	}
	
	public void back(View v)
	{
		if((from-10)>0)
		{
			from = (from-10);
			to = (to-10);
			url = "http://theinspirer.in/chennai/list.php?from="+from+"&to="+to;
			callfiller();
		}
		else
		{
			final Dialog dialog = new Dialog(comments.this);
			dialog.setContentView(R.layout.customerror);
			dialog.setTitle("");

			 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
				// dialog.getWindow().setTitleColor(R.color.titlecolor);
		       

			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText("No comments Avilaible");


			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}	
	}
}