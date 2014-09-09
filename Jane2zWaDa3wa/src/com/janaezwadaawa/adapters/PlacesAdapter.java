package com.janaezwadaawa.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.utils.JDFonts;

public class PlacesAdapter extends ArrayAdapter<Place> {

	Context mContext;
	int layoutResourceId;
	ArrayList<Place> data = null;
	LayoutInflater inflater;
	
	public PlacesAdapter(Context mContext, int layoutResourceId, ArrayList<Place> data) {

		super(mContext, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
		inflater = ((Activity) mContext).getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(layoutResourceId, parent, false);
			
			// get the elements in the layout
			holder.textview = (TextView) convertView.findViewById(R.id.txv_placeName); 
			holder.textview.setTypeface(JDFonts.getBDRFont());
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		/*
		 * Set the data for the list item. You can also set tags here if you
		 * want.
		 */
		Place place = data.get(position);

		holder.textview.setText(place.getTitle());

		return convertView;
	}

	class ViewHolder
	{
		TextView textview; 
	}

}
