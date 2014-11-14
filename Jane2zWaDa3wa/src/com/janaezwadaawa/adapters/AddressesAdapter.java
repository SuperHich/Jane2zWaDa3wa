package com.janaezwadaawa.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.entity.Address;
import com.janaezwadaawa.utils.JDFonts;

public class AddressesAdapter extends ArrayAdapter<Address> implements OnClickListener{

	Context mContext;
	ArrayList<Address> data = null;
	LayoutInflater inflater;

	Address mAddress ;

	public AddressesAdapter(Context mContext, ArrayList<Address> data) {

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
			convertView = inflater.inflate(R.layout.address_list_item, parent, false);

			// get the elements in the layout
			holder.txv_title_address 		= (TextView) convertView.findViewById(R.id.txv_title_address); 
			holder.txv_address 		= (TextView) convertView.findViewById(R.id.txv_address); 
			holder.txv_title_phones 	= (TextView) convertView.findViewById(R.id.txv_title_phones); 
			holder.txv_phones 		= (TextView) convertView.findViewById(R.id.txv_phones); 


			holder.txv_title_address.setTypeface(JDFonts.getBDRFont());
			holder.txv_address.setTypeface(JDFonts.getBDRFont());
			holder.txv_title_phones.setTypeface(JDFonts.getBDRFont());
			holder.txv_phones.setTypeface(JDFonts.getBDRFont());


			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}


		mAddress = data.get(position);

		holder.txv_address.setText(mAddress.getAddress());
		holder.txv_phones.setText(mAddress.getPhones());

		holder.txv_address.setOnClickListener(this);
		holder.txv_phones.setOnClickListener(this);
		holder.txv_title_address.setOnClickListener(this);
		holder.txv_title_phones.setOnClickListener(this);

		return convertView;
	}


	class ViewHolder
	{
		TextView txv_title_address;
		TextView txv_title_phones;
		TextView txv_address;
		TextView txv_phones;
	}


	@Override
	public void onClick(View v) {

		final String[] phones = splitPhoneNumbers(mAddress.getPhones()) ;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(mContext.getString(R.string.call_title));
		builder.setItems(phones, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Do something with the selection
				
				String num_to_call = phones[item];
				
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num_to_call));
				mContext.startActivity(intent);
				
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}


	private String[] splitPhoneNumbers(String txt){

		String[] phones ;

		phones = txt.split(",");

		
		for (int i = 0; i < phones.length; i++) {
			
			phones[i] = phones[i].replace(" ", "");
		}
		
		
		return phones;

	}

}
