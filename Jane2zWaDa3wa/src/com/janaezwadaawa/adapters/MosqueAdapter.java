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
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.utils.JDFonts;

public class MosqueAdapter extends ArrayAdapter<Mosque> {

	Context mContext;
	IJana2zListener listener;
	ArrayList<Mosque> data = null;
	LayoutInflater inflater;
	private boolean isEnabled = true;

	public MosqueAdapter(Context mContext, ArrayList<Mosque> data, IJana2zListener listener) {

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
			convertView = inflater.inflate(R.layout.mosque_grid_item, parent, false);

			// get the elements in the layout
			holder.txv_mosque = (TextView) convertView.findViewById(R.id.txv_mosque); 
			holder.txv_mosque_name = (TextView) convertView.findViewById(R.id.txv_mosque_name); 
			holder.txv_nb_jana2z = (TextView) convertView.findViewById(R.id.txv_jana2z); 
			holder.txv_nb_jana2z_nb = (TextView) convertView.findViewById(R.id.txv_jana2z_nb); 
			holder.btn_details = (Button) convertView.findViewById(R.id.btn_details);

			
			holder.txv_mosque.setTypeface(JDFonts.getBDRFont());
			holder.txv_mosque_name.setTypeface(JDFonts.getBDRFont());
			holder.txv_nb_jana2z.setTypeface(JDFonts.getBDRFont());
			holder.txv_nb_jana2z_nb.setTypeface(JDFonts.getBDRFont());
			

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
		Mosque mosque = data.get(position);
		
		holder.txv_mosque_name.setText(mosque.getTitle());
		holder.txv_nb_jana2z_nb.setText(mosque.getJana2z().size() + " " + mContext.getString(R.string.janaez));

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
		TextView txv_mosque;
		TextView txv_mosque_name;
		TextView txv_nb_jana2z;
		TextView txv_nb_jana2z_nb;
		Button btn_details;
	}

}
