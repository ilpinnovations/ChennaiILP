package info.androidhive.customlistviewvolley.adater;

import info.androidhive.customlistviewvolley.R;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;

@SuppressWarnings("unused")

public class CustomListAdapterforcomments extends BaseAdapter {
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie> movieItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapterforcomments(Activity activity, List<Movie> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row_for_comments2, null);

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);

		// getting movie data for the row
		Movie m = movieItems.get(position);

		// Thumb Nail image
		// title
		title.setText(m.getTitle());

		if(m.getRating() == 1.0){
			rating.setBackgroundResource(R.drawable.rater);
		}
		else if(m.getRating() == 2.0){
			rating.setBackgroundResource(R.drawable.rater);
		}
		else if(m.getRating() == 3.0){
			rating.setBackgroundResource(R.drawable.rateo);
		}
		else if(m.getRating() == 4.0){
			rating.setBackgroundResource(R.drawable.ratey);
		}
		else if(m.getRating() == 5.0){
			rating.setBackgroundResource(R.drawable.rateg);
		}
		// rating
		rating.setText(String.valueOf(m.getRating()));
		
		// genre
		String genreStr = "";
		for (String str : m.getGenre()) 
		{
			genreStr += str + ", ";
		}
		
		genreStr = genreStr.length() > 0 ? genreStr.substring(0, genreStr.length() - 2) : genreStr;
		genre.setText(genreStr);
		
		// release year

		return convertView;
	}

}