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
import com.janaezwadaawa.entity.Janeza;
import com.janaezwadaawa.utils.JDFonts;

public class JanaezAdapter extends ArrayAdapter<Janeza> {

	Context mContext;
	IJana2zListener listener;
	ArrayList<Janeza> data = null;
	LayoutInflater inflater;
	private boolean isEnabled = true;

	public JanaezAdapter(Context mContext, ArrayList<Janeza> data, IJana2zListener listener) {

		super(mContext, 0, data);
		this.mContext = mContext;
		this.data = data;
		this.listener = listener;
		inflater = ((Activity) mContext).getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.janeza_list_item, parent, false);

			// get the elements in the layout
			holder.txv_index 		= (TextView) convertView.findViewById(R.id.txv_index); 
			holder.txv_name 		= (TextView) convertView.findViewById(R.id.txv_name); 
			holder.txv_name_value 	= (TextView) convertView.findViewById(R.id.txv_name_value); 
			holder.txv_gender 		= (TextView) convertView.findViewById(R.id.txv_gender); 
			holder.txv_gender_value = (TextView) convertView.findViewById(R.id.txv_gender_value); 
			
			holder.txv_index.setTypeface(JDFonts.getArabicFont());
			holder.txv_name.setTypeface(JDFonts.getBDRFont());
			holder.txv_name_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_gender.setTypeface(JDFonts.getBDRFont());
			holder.txv_gender_value.setTypeface(JDFonts.getBDRFont());

			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}

		/*
		 * Set the data for the list item. You can also set tags here if you
		 * want.
		 */
		Janeza janeza = data.get(position);
		
		holder.txv_index.setText(""+(position+1));
		holder.txv_name_value.setText(janeza.getTitle());
		holder.txv_gender_value.setText(janeza.getGender());

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
		TextView txv_index;
		TextView txv_name;
		TextView txv_name_value;
		TextView txv_gender;
		TextView txv_gender_value;
	}

}
