package com.janaezwadaawa.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.entity.Prayer;
import com.janaezwadaawa.utils.JDFonts;

public class PrayersAdapter extends BaseExpandableListAdapter {

	static final String TAG = PrayersAdapter.class.getSimpleName();
	IMenuListener listener;
	ArrayList<Prayer> items = new ArrayList<Prayer>();
	LayoutInflater inflater;

	public PrayersAdapter(Context context, ArrayList<Prayer> items)
	{
		super();
		this.items.addAll(items);
		inflater= LayoutInflater.from(context);
		listener = (IMenuListener) context;
		
	}

	class ViewGroupHolder
	{
		TextView txv_name_value;
	}
	
	class ViewChildHolder
	{
		TextView txv_men;
		TextView txv_men_value;
		TextView txv_women;
		TextView txv_women_value;
		TextView txv_child;
		TextView txv_child_value;
	}

	@Override
	public int getGroupCount() {
		return items.size();
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}
	@Override
	public Object getGroup(int groupPosition) {
		return items.get(groupPosition);
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return items.get(groupPosition);
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewGroupHolder holder;
		
		if(convertView == null)
		{
			holder = new ViewGroupHolder();
			convertView = inflater.inflate(R.layout.prayer_group_item, null);
			
			holder.txv_name_value = (TextView) convertView.findViewById(R.id.txv_name_value);
			
			holder.txv_name_value.setTypeface(JDFonts.getBDRFont());

			convertView.setTag(holder);
		}
		else {
			
			holder = (ViewGroupHolder)convertView.getTag();
		}
		
		Prayer item = items.get(groupPosition);
		
		holder.txv_name_value.setText(item.getTitle());
	
		return convertView;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewChildHolder holder;
		if(convertView==null)
		{
			holder = new ViewChildHolder();
			convertView = inflater.inflate(R.layout.prayer_child_item, null);
			
			holder.txv_men 			= (TextView) convertView.findViewById(R.id.txv_men);
			holder.txv_men_value 	= (TextView) convertView.findViewById(R.id.txv_men_value);
			holder.txv_women 		= (TextView) convertView.findViewById(R.id.txv_women);
			holder.txv_women_value 	= (TextView) convertView.findViewById(R.id.txv_women_value);
			holder.txv_child 		= (TextView) convertView.findViewById(R.id.txv_child);
			holder.txv_child_value 	= (TextView) convertView.findViewById(R.id.txv_child_value);
			
			holder.txv_men.setTypeface(JDFonts.getBDRFont());
			holder.txv_men_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_women.setTypeface(JDFonts.getBDRFont());
			holder.txv_women_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_child.setTypeface(JDFonts.getBDRFont());
			holder.txv_child_value.setTypeface(JDFonts.getBDRFont());
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewChildHolder)convertView.getTag();
		}
		
		Prayer item = items.get(groupPosition); 
		
		holder.txv_men_value.setText(""+item.getCount_men());
		holder.txv_women_value.setText(""+item.getCount_women());
		holder.txv_child_value.setText(""+item.getCount_child());
		
//		Log.i("TAG", ">>> Men " + item.getCount_men() + " ... Women " + item.getCount_women() + " ... Child " + item.getCount_child());
		 
		return convertView;
	}
	
	
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}