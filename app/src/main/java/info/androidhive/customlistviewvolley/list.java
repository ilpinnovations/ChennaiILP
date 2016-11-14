package info.androidhive.customlistviewvolley;

import info.androidhive.customlistviewvolley.adater.CustomListAdapter;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.app.ProgressDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import android.view.View.OnClickListener;

public class list extends Activity implements OnItemSelectedListener {
	// Log tag
	private static final String TAG = list.class.getSimpleName();

	// Movies json urlwq
	private static  String url = "";
	private List<Movie> movieList = null;
	private AbsListView listView;
	private CustomListAdapter adapter;
	private ProgressDialog pd;
	int f = 0;
	int list_view_selection = 0;
	String sort_flag ="lthname";
	ImageButton btnRefresh;
	EditText E1;
	ImageButton searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		E1 = (EditText)findViewById(R.id.editText12);

		url = "http://theinspirer.in/chennai/list2.php";
   	 	callfiller();

		btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
        searchButton = (ImageButton) findViewById(R.id.button3);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                E1.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(E1, InputMethodManager.SHOW_IMPLICIT);
            }
        });
		btnRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sort_flag.equals("htlrating")) {
					url = "http://theinspirer.in/chennai/list3.php";
				} else if (sort_flag.equals("lthrating")) {
					url = "http://theinspirer.in/chennai/list4.php";
				} else if (sort_flag.equals("htlname")) {
					url = "http://theinspirer.in/chennai/list5.php";
				} else if (sort_flag.equals("lthname")) {
					url = "http://theinspirer.in/chennai/list2.php";
				}
				callfiller();
			}
		});

	}   
	
	void callfiller()
	{
		movieList = new ArrayList<Movie>();
		listView = (AbsListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
		
		listView.setAdapter(adapter);

		pd = ProgressDialog.show(list.this, "", "");
		pd.setContentView(R.layout.progress);
		pd.show();
		pd.setCancelable(true);
		
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
								movie.setSno(obj.getString("sno"));

								// Genre is json array
								JSONArray genreArry = obj.getJSONArray("genre");
								
								ArrayList<String> genre = new ArrayList<String>();
								
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
								}
								
								movie.setGenre(genre);

								// adding movie to movies array
								movieList.add(movie);

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						//notifying list adapter about data changes
						//so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
						
						f = 0;
						listView.setSelection(list_view_selection);
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
	

	private void hidePDialog() {
		pd.cancel();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	//int from = 0;
	//int to = 20;
	
	/*public void next()
	{
		to = (to + 20);
		url = "http://theinspirer.in/chennai/list2.php?from=" + from + "&to=" + to;
		Log.d("asd", url);
		callfiller();
	}*/
	
	public void rating(View v)
	{
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp = movieList.get(position);
	    
		Intent i = new Intent(getApplicationContext(), rating2.class);
        ArrayList<String> temp1 = pp.getGenre();
 		ratingtable = temp1.get(0);
		sno = pp.getSno();

 		startActivity(i);
	}

	public void rate_high_to_low(View v){
		url = "http://theinspirer.in/chennai/list3.php";
		sort_flag ="htlrating";
		callfiller();
	}

	public void rate_low_to_high(View v){
		url = "http://theinspirer.in/chennai/list4.php";
		sort_flag ="lthrating";
		callfiller();
	}

	public void name_high_to_low(View v){
		url = "http://theinspirer.in/chennai/list5.php";
		sort_flag ="htlname";
		callfiller();
	}

	public void name_low_to_high(View v){
		url = "http://theinspirer.in/chennai/list2.php";
		sort_flag ="lthname";
		callfiller();
	}


	public static String ratingtable = "";
	public static String sno = "";
	
	public void showcomments(View v)
	{
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp = movieList.get(position);

	    Intent i = new Intent(getApplicationContext(), comments.class);
        ArrayList<String> temp1 = pp.getGenre();

		sno = pp.getSno();
        ratingtable = temp1.get(0);
 		lon = temp1.get(2);
		lat = temp1.get(1);
		placename = pp.getTitle();
		placeaddress = temp1.get(3);
		placeimage = pp.getThumbnailUrl();
 		startActivity(i);
	}
	
	public void maps(View v)
	{
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp = movieList.get(position);
	    
	    ArrayList<String> temp1 = pp.getGenre();
	    lat = temp1.get(2);
	    lon = temp1.get(1);
		placename = pp.getTitle();
		placeaddress = temp1.get(3);
		placeimage = temp1.get(4);
	    
		Intent i = new Intent(getApplicationContext(), MapActivity.class);
		i.putExtra("lat", lat);
		i.putExtra("lon", lon);
		i.putExtra("name", placename);
		i.putExtra("address", placeaddress);
        startActivity(i);
	}
	
	public static String placename = "";
	public static String placeaddress = "";
	public static String placeimage = "";
	public static String lon = "";
	public static String lat = "";
   
   public void search(View v)
   {
      //from = 0;
      //to = 10;


      if(String.valueOf(E1.getText()).equals("")) {
    	  Toast.makeText(getApplicationContext(), "Type a hotel name", Toast.LENGTH_SHORT).show();
      }
      else {
    	 url = "http://theinspirer.in/chennai/list2.php?pattern=" + E1.getText();
    	 callfiller(); 
      }    
   }
   
   @Override
   public void onDestroy() {
       android.os.Process.killProcess(android.os.Process.myPid());
       super.onDestroy();
   }
   
   @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}