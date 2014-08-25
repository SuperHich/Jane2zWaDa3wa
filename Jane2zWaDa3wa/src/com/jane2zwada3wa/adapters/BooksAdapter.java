package com.jane2zwada3wa.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jane2zwada3wa.R;
import com.jane2zwada3wa.entity.Book;
import com.jane2zwada3wa.utils.MySuperScaler;

public class BooksAdapter extends ArrayAdapter<Book> {

	Context mContext;
	int layoutResourceId;
	ArrayList<Book> data = null;
	LayoutInflater inflater;
	
	public BooksAdapter(Context mContext, int layoutResourceId, ArrayList<Book> data) {

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
			
			MySuperScaler.scaleViewAndChildren(convertView, MySuperScaler.scale);
			
			// get the elements in the layout
			holder.textview = (TextView) convertView.findViewById(R.id.txv_babTitle); 
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		int size = (int) MySuperScaler.screen_width / 22 ;
		holder.textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

		/*
		 * Set the data for the list item. You can also set tags here if you
		 * want.
		 */
		Book book = data.get(position);

		holder.textview.setText(book.getName());

		return convertView;
	}

	class ViewHolder
	{
		TextView textview; 
	}

}
