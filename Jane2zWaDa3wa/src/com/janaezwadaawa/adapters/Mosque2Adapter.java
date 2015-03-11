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
import com.janaezwadaawa.entity.Mosque2;
import com.janaezwadaawa.utils.JDFonts;

public class Mosque2Adapter extends ArrayAdapter<Mosque2> {

	Context mContext;
	ArrayList<Mosque2> data = null;
	LayoutInflater inflater;
	private boolean isEnabled = true;

	public Mosque2Adapter(Context mContext, ArrayList<Mosque2> data) {

		super(mContext, 0, data);
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
			convertView = inflater.inflate(R.layout.mosque_list_item, parent, false);

			// get the elements in the layout
			holder.txv_name = (TextView) convertView.findViewById(R.id.txv_name); 
			holder.txv_name_value = (TextView) convertView.findViewById(R.id.txv_name_value); 
			holder.txv_janaez_nb = (TextView) convertView.findViewById(R.id.txv_janaez_nb); 

			
			holder.txv_name.setTypeface(JDFonts.getBDRFont());
			holder.txv_name_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_janaez_nb.setTypeface(JDFonts.getBDRFont());
			


			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}

		/*
		 * Set the data for the list item. You can also set tags here if you
		 * want.
		 */
		Mosque2 mosque = data.get(position);
		
		holder.txv_name_value.setText(mosque.getTitle());
		holder.txv_janaez_nb.setText(String.valueOf(mosque.getCount()));

		return convertView;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	class ViewHolder
	{
		TextView txv_name;
		TextView txv_name_value;
		TextView txv_janaez_nb;
	}

}
