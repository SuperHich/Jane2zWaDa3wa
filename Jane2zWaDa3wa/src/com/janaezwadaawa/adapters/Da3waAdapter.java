package com.janaezwadaawa.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.utils.JDFonts;

public class Da3waAdapter extends ArrayAdapter<Da3wa> {

	Context mContext;
	IJana2zListener listener;
	ArrayList<Da3wa> data = null;
	LayoutInflater inflater;
	private boolean isEnabled = true;

	public Da3waAdapter(Context mContext, ArrayList<Da3wa> data, IJana2zListener listener) {

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
			convertView = inflater.inflate(R.layout.da3wa_list_item, parent, false);

			// get the elements in the layout
			holder.txv_title 		= (TextView) convertView.findViewById(R.id.txv_title); 
			holder.txv_trainer 		= (TextView) convertView.findViewById(R.id.txv_trainer); 
			holder.txv_description 	= (TextView) convertView.findViewById(R.id.txv_description); 
			holder.txv_date 		= (TextView) convertView.findViewById(R.id.txv_date); 
			holder.txv_mosque 		= (TextView) convertView.findViewById(R.id.txv_mosque); 
			holder.btn_details 		= (Button) convertView.findViewById(R.id.btn_details);

			
			holder.txv_title.setTypeface(JDFonts.getBDRFont());
			holder.txv_trainer.setTypeface(JDFonts.getBDRFont());
			holder.txv_description.setTypeface(JDFonts.getBDRFont());
			holder.txv_date.setTypeface(JDFonts.getBDRFont());
			holder.txv_mosque.setTypeface(JDFonts.getBDRFont());

			holder.btn_details.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(!isEnabled())
						return;
					int position = (Integer)(v.getTag());					
					listener.onItemDetailsClicked(position);
				}
			});

			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}

		holder.btn_details.setTag(position);

		/*
		 * Set the data for the list item. You can also set tags here if you
		 * want.
		 */
		Da3wa da3wa = data.get(position);
		
		holder.txv_title.setText(da3wa.getTitle());
		holder.txv_trainer.setText(da3wa.getTrainer());
		holder.txv_description.setText(da3wa.getDescription());
		holder.txv_date.setText(da3wa.getStartTime());
		holder.txv_mosque.setText(da3wa.getMosque());

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
		TextView txv_title;
		TextView txv_trainer;
		TextView txv_description;
		TextView txv_date;
		TextView txv_mosque;
		Button btn_details;
	}

}
