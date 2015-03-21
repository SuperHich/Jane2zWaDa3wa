package com.janaezwadaawa.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.entity.DrawerItem;
import com.janaezwadaawa.utils.JDFonts;



public class ElementsListAdapter extends ArrayAdapter<DrawerItem>  

{

	Context mContext;
	ArrayList<DrawerItem> data = new ArrayList<DrawerItem>();
	LayoutInflater inflater;

	public ElementsListAdapter(Context mContext, ArrayList<DrawerItem> data) {

		super(mContext, 0, data);
		this.mContext = mContext;
		this.data = data;
		inflater = ((Activity) mContext).getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if(convertView==null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_drawer, parent, false);

			holder.txv_title 		= (TextView) convertView.findViewById(R.id.txv_title);
			holder.icon_txv			= (ImageView) convertView.findViewById(R.id.icon_txv);
			holder.list_item		= (RelativeLayout) convertView.findViewById(R.id.list_item); 


			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}

		holder.icon_txv.setImageResource(data.get(position).getIcon());
		
		holder.txv_title.setTypeface(JDFonts.getBDRFont());

		
		holder.txv_title.setText(data.get(position).getText());
		
		holder.list_item.setTag(position);


		return convertView;
	}

	class ViewHolder
	{
		TextView txv_title;
		RelativeLayout list_item ;
		ImageView icon_txv ;
	}


}
