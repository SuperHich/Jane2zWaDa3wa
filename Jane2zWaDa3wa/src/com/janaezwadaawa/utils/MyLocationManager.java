package com.janaezwadaawa.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;

public class MyLocationManager implements LocationListener {

	static final String TAG = "JD : " + MyLocationManager.class.getSimpleName();

	private LocationManager mlocManager;
	private Context context;
	
	private String gpsProvider;
	Criteria crta = new Criteria();
	private static MyLocationManager mySelf;
	
	ArrayList <LocationListener> listener = new ArrayList <LocationListener>();
	Location lastLocationGPS;

	public Location getLastLocationGPS() {
		return lastLocationGPS;
	}
	
	public String getLastLocationGPSFormatted() {
		if(lastLocationGPS != null)
			return String.valueOf(lastLocationGPS.getLatitude()) + "," + String.valueOf(lastLocationGPS.getLongitude());
		
		return null;
	}
	
	public GeoPoint getGEOPoint () {
		
		if(lastLocationGPS != null)
			return new GeoPoint((int) (lastLocationGPS.getLatitude() * 1e6), (int) (lastLocationGPS.getLongitude() * 1e6));
		
		return null;
	}

	boolean providerAvailable = false;
	
	private MyLocationManager(Context context) {
		
		this.context = context;
		mlocManager =	(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		crta.setAccuracy(Criteria.ACCURACY_FINE);
		crta.setAltitudeRequired(false);
		crta.setBearingRequired(false);
		crta.setCostAllowed(true);
		crta.setPowerRequirement(Criteria.POWER_LOW);
		
	}

	
	public static MyLocationManager getIntance (Context context) {

		if (mySelf == null)
			mySelf = new MyLocationManager(context);
		
		return mySelf;
	}

	public void register (LocationListener l) {
		listener.add (l);
		l.onLocationChanged(lastLocationGPS);
	}
	
	public void remove (LocationListener l) {
		listener.remove (l);
	}
	
	public void start(){
		setBestProvider();
	}
	
	public void stop(){
		mlocManager.removeUpdates(this);
	}
	
	private void setBestProvider () {
			// remove current if any
		mlocManager.removeUpdates(this);
		
		gpsProvider = mlocManager.getBestProvider(crta, true);
		 
		 	
		if (gpsProvider == null)
			gpsProvider = LocationManager.GPS_PROVIDER;
		 
		 
		 if (gpsProvider != null) {
			
			mlocManager.requestLocationUpdates(gpsProvider, 60000, 1000, MyLocationManager.this);
					   
			// GPS LastKnownLocation
			lastLocationGPS = mlocManager.getLastKnownLocation(gpsProvider);
			if (lastLocationGPS != null)
				onLocationChanged (lastLocationGPS);
		 }
//		 else{
//			 new LoadingData().execute();
//		 }
		 
		 providerAvailable = gpsProvider!=null;

	}

	
	public boolean isProviderAvailable() {
		return providerAvailable;
	}


	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		
		lastLocationGPS = loc;
		notifyNewLocation(loc);
	}
	
	private void notifyNewLocation (Location loc){
		for (LocationListener i : listener) {
			i.onLocationChanged(loc);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {

			// we should have an alternative
		String prov = mlocManager.getBestProvider(crta, true);
		if (prov == null)
			return;

		setBestProvider ();
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
///		mlocManager.requestLocationUpdates(gpsProvider, 0, 100,	this);
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		if (status == LocationProvider.OUT_OF_SERVICE) 
			setBestProvider ();

	}
	
//	private class LoadingData extends AsyncTask<Void, Void, Boolean>
//	{
//		String response=null;
//		double lat, lng;
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//
//			try {
//				response= ConnectionToServer.executeHttpGet("http://api.wipmania.com/jsonp?callback=");
//				response=response.replace("(", "").replace(")", "");
//				Log.e("song response", response);
//
//				if(response!=null)
//				{
//					JSONObject jsonObj=new JSONObject(response);
//
//					lat = jsonObj.getDouble("latitude");
//					lng = jsonObj.getDouble("longitude");
//
//					return true;
//
//				}
//
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return false;
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			super.onPostExecute(result);
//
//			if(result){
//				Log.i(TAG, " ... lat " + lat);
//				Log.i(TAG, " ... lng " + lng);
//				lastLocationGPS.setLatitude(lat);
//				lastLocationGPS.setLongitude(lng);
//				notifyNewLocation(lastLocationGPS);
//			}
//		}
//	}
	
	public boolean isGPSEnabled(Context context){

		boolean isGPSOn = false;

		if (mlocManager == null)
			mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		try {
			isGPSOn = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {}

		return isGPSOn;

	}

	public boolean isNetworkEnabled(Context context){

		boolean isNetworkOn = false;

		if (mlocManager == null)
			mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		try {
			isNetworkOn = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {}

		return isNetworkOn;
	}
	
	
	/**
	 * Get list of address by latitude and longitude
	 * 
	 * @return null or List<Address>
	 */
	public List<Address> getGeocoderAddress() {
		if (getLastLocationGPS() != null) {
			Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
			try {
				List<Address> addresses = geocoder.getFromLocation(getLastLocationGPS().getLatitude(),
						getLastLocationGPS().getLongitude(), 1);
				return addresses;
			} catch (IOException e) {
				// e.printStackTrace();
				Log.e("Error : Geocoder", "Impossible to connect to Geocoder",
						e);
			}
		}

		return null;
	}

	/**
	 * Try to get AddressLine
	 * 
	 * @return null or addressLine
	 */
	public String getAddressLine() {
		List<Address> addresses = getGeocoderAddress();
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			String addressLine = address.getAddressLine(0);

			return addressLine;
		} else {
			return null;
		}
	}

	/**
	 * Try to get Locality
	 * 
	 * @return null or locality
	 */
	public String getLocality() {
		List<Address> addresses = getGeocoderAddress();
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			String locality = address.getLocality();

			return locality;
		} else {
			return null;
		}
	}

	/**
	 * Try to get Postal Code
	 * 
	 * @return null or postalCode
	 */
	public String getPostalCode() {
		List<Address> addresses = getGeocoderAddress();
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			String postalCode = address.getPostalCode();

			return postalCode;
		} else {
			return null;
		}
	}

	/**
	 * Try to get CountryName
	 * 
	 * @return null or postalCode
	 */
	public String getCountryName() {
		List<Address> addresses = getGeocoderAddress();
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			String countryName = address.getCountryName();

			return countryName;
		} else {
			return null;
		}
	}
	
}

