package com.janaezwadaawa.externals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.janaezwadaawa.R;
import com.janaezwadaawa.adapters.IFragmentNotifier;
import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.entity.Address;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.entity.Janeza;
import com.janaezwadaawa.entity.JanezaPerson;
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.entity.Mosque2;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.entity.Prayer;
import com.janaezwadaawa.entity.SelectGender;
import com.janaezwadaawa.entity.SelectMosque;
import com.janaezwadaawa.entity.SelectSalat;
import com.janaezwadaawa.entity.SelectTrainer;
import com.janaezwadaawa.gcm.GcmResponse;

public class JDManager {

	static final String TAG = "JDManager";
	
//	private static final String URL_BASE 				= "http://smartlives.ws/projects/exequyApp/api/";
	private static final String URL_BASE 				= "http://gheras.net/exequyApp/api/";
	private static final String URL_JANA2Z 				= URL_BASE + "exequy/";
	private static final String URL_DA3WA 				= URL_BASE + "lectures/";
	private static final String URL_MOSQUES 			= URL_BASE + "mosques";
	private static final String URL_PLACES 				= URL_BASE + "places/";	
	private static final String URL_ADDRESSES 			= URL_BASE + "addresses/";	
	private static final String URL_PLACE 				= URL_BASE + "place/%d";
	private static final String URL_MOSQUE 				= URL_BASE + "mosque/%d/%d";
	private static final String URL_MOSQUE_SALAT_GENDER = URL_BASE + "mosque_salat_gender/%d/%d/%d/%d";
	private static final String URL_LOGIN 				= URL_BASE + "login/%s/%s";
	private static final String URL_ADD_JANEZA 			= URL_BASE + "addExequy";
	private static final String URL_ADD_DA3WA 			= URL_BASE + "addLicuter";
	private static final String URL_SELECT_MOSQUES 		= URL_BASE + "select/mosques";
	private static final String URL_SELECT_TRAINERS 	= URL_BASE + "select/trainers";
	private static final String URL_SELECT_SALATS 		= URL_BASE + "select/salat";
	
	private static final String URL_PUSH_REGISTER	= "http://gheras.net/exequyApp/mobile_data/push_notifications";
	
	private static final String ID 				= "id";
	private static final String TITLE 			= "title";
	private static final String MOSQUE 			= "mosque";
	private static final String PLACE 			= "place";
	private static final String DESCRIPTION 	= "descrption";
	private static final String PRAYER_TIME 	= "prayer_time";
	private static final String START_TIME 		= "start_time";
	private static final String END_TIME 		= "end_time";
	private static final String TRAINER			= "trainer";
	private static final String LAT			 	= "lat";
	private static final String LONG		 	= "long";
	private static final String PLACE_ID 		= "place_id";
	private static final String PLACE_NAME 		= "place_name";
	private static final String MOSQUE_ID 		= "mosque_id";
	private static final String COUNT	 		= "count";
	private static final String ADDRESS	 		= "address";
	private static final String PHONES	 		= "phones";
	private static final String PLACE_EN	 	= "place_en";
	private static final String TOKEN	 		= "token";
	private static final String TYPE	 		= "type";
	private static final String GENDER			= "gender";
	
	private static final String ID_MOSQUE 		= "id_mosque";
	private static final String NAME	 		= "name";
	private static final String ID_SALAT	 	= "id_salat";
	private static final String MEN	 			= "men";
	private static final String WOMEN	 		= "women";
	private static final String CHILD	 		= "child";
	private static final String UID	 			= "uid";
	private static final String BODY	 		= "body";
	private static final String SALAT	 		= "salat";
	private static final String SALAT_TIME	 	= "salat_time";
	private static final String TIME_FROM	 	= "time_from";
	private static final String TIME_TO	 		= "time_to";
	private static final String DATE	 		= "date";
	
	private IFragmentNotifier fragmentNotifier;
	
	private static JDManager mInstance = null;
	private static SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private JSONParser jsonParser;
	private Context mContext;
	
	private Mosque selectedMosque;
	private Mosque2 selectedMosque2;
	private Da3wa selectedDa3wa;
	private Prayer selectedPrayer;
	
	private ArrayList<Place> places = new ArrayList<Place>();
	private ArrayList<SelectMosque> selectMosques = new ArrayList<SelectMosque>();
	private ArrayList<SelectTrainer> selectTrainers = new ArrayList<SelectTrainer>();
	private ArrayList<SelectSalat> selectSalats = new ArrayList<SelectSalat>();
	private ArrayList<SelectGender> selectGenders = new ArrayList<SelectGender>();
	private ISearchListener searchListener;
	
	private boolean loggedIn ;
	private String Uid ;
	
	public JDManager(Context context) {
		
		mContext = context;
		jsonParser = new JSONParser();
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
		
		selectGenders = new ArrayList<SelectGender>();
		selectGenders.add(new SelectGender(0, mContext.getString(R.string.men)));
		selectGenders.add(new SelectGender(1, mContext.getString(R.string.women)));
		selectGenders.add(new SelectGender(2, mContext.getString(R.string.child_male)));
		selectGenders.add(new SelectGender(3, mContext.getString(R.string.child_female)));
	}

	public synchronized static JDManager getInstance(Context context) {
		if (mInstance == null)
			mInstance = new JDManager(context);

		return mInstance;
	}
	
	public ArrayList<Mosque> getJanaezByPlace(int placeID) {

		HashMap<String, ArrayList<Janeza>> hashJana2z = new HashMap<String, ArrayList<Janeza>>();

		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z + "place/?place=" + placeID);
		if (array != null) 
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject jObj = array.getJSONObject(i);
					Janeza janeza = new Janeza();
					//				janeza.setId(Integer.valueOf(jObj.getString(ID)));
					janeza.setTitle(jObj.getString(TITLE));
					janeza.setMosque(jObj.getString(MOSQUE));
					janeza.setPlace(jObj.getString(PLACE));
					janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
					janeza.setLatitude(jObj.getString(LAT));
					janeza.setLongitude(jObj.getString(LONG));
					janeza.setGender(jObj.getString(GENDER));

					Log.i(TAG, janeza.toString());
					
					ArrayList<Janeza> list = hashJana2z.get(janeza.getMosque());
					if(list == null)
						list = new ArrayList<Janeza>();

					list.add(janeza);
					hashJana2z.put(janeza.getMosque(), list);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		for(ArrayList<Janeza> list : hashJana2z.values()){
			Mosque mosque = new Mosque();
			mosque.setTitle(list.get(0).getMosque());
			mosque.setJana2z(list);
			mosques.add(mosque);
		}

		return mosques;
	}

	public ArrayList<Mosque> getAllJanaez() {
		
		HashMap<String, ArrayList<Janeza>> hashJana2z = new HashMap<String, ArrayList<Janeza>>();
		
		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Janeza janeza = new Janeza();
				janeza.setId(Integer.valueOf(jObj.getString(ID)));
				janeza.setTitle(jObj.getString(TITLE));
				janeza.setMosque(jObj.getString(MOSQUE));
				janeza.setPlace(jObj.getString(PLACE));
				janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
				janeza.setLatitude(jObj.getString(LAT));
				janeza.setLongitude(jObj.getString(LONG));
				janeza.setGender(jObj.getString(GENDER));
				
				Log.i(TAG, janeza.toString());
				
				ArrayList<Janeza> list = hashJana2z.get(janeza.getMosque());
				if(list == null)
					list = new ArrayList<Janeza>();
				
				list.add(janeza);
				hashJana2z.put(janeza.getMosque(), list);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		for(ArrayList<Janeza> list : hashJana2z.values()){
			Mosque mosque = new Mosque();
			mosque.setTitle(list.get(0).getMosque());
			mosque.setJana2z(list);
			mosques.add(mosque);
		}
		
		return mosques;
	}
	
	public ArrayList<Janeza> getJanaezByMosque(int mosqueID) {

		ArrayList<Janeza> janaez = new ArrayList<Janeza>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z + "mosques?mosque=" + mosqueID);
		Log.i(TAG, "URL : " + URL_MOSQUES + "?mosque=" + mosqueID);
		if (array != null) 
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject jObj = array.getJSONObject(i);
					Janeza janeza = new Janeza();
					janeza.setId(Integer.valueOf(jObj.getString(ID)));
					janeza.setTitle(jObj.getString(TITLE));
					janeza.setMosque(jObj.getString(MOSQUE));
					janeza.setPlace(jObj.getString(PLACE));
					janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
					janeza.setLatitude(jObj.getString(LAT));
					janeza.setLongitude(jObj.getString(LONG));
					janeza.setGender(jObj.getString(GENDER));

					Log.i(TAG, janeza.toString());


					janaez.add(janeza);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		return janaez;
	}
	
	
	public ArrayList<Mosque> getMosquesByPlace(int placeId) {
		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		String url = URL_MOSQUES.concat("/place/?place=".concat(String.valueOf(placeId)));
		JSONArray array = jsonParser.getJSONFromUrl(url);
		Log.i(TAG, ">>> url : " + url);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Mosque mosque = new Mosque();
				mosque.setId(Integer.valueOf(jObj.getString(MOSQUE_ID)));
				mosque.setCount(Integer.valueOf(jObj.getString(COUNT)));
				mosque.setTitle(jObj.getString(MOSQUE));
				mosque.setPlace(jObj.getString(PLACE));
				mosque.setPlaceEn(jObj.getString(PLACE_EN));
				
				Log.i(TAG, mosque.toString());
				
				if(mosque.getPlaceEn() == null 
						|| mosque.getPlaceEn() != null && mosque.getPlaceEn().equals("null"))
					mosques.add(mosque);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return mosques;
	}
	
	public ArrayList<Mosque2> getMosques2ByPlace(int placeId) {
		ArrayList<Mosque2> mosques = new ArrayList<Mosque2>();
		String url = String.format(URL_PLACE, placeId);
		JSONArray array = jsonParser.getJSONFromUrl(url);
		Log.i(TAG, ">>> url : " + url);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Mosque2 mosque = new Mosque2();
				mosque.setId(Integer.valueOf(jObj.getString(ID_MOSQUE)));
				mosque.setCount(Integer.valueOf(jObj.getString(COUNT)));
				mosque.setTitle(jObj.getString(NAME));
				
				Log.i(TAG, mosque.toString());
				
				mosques.add(mosque);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return mosques;
	}
	
	public ArrayList<Prayer> getPrayerByMosque(int placeId, int mosqueId) {
		ArrayList<Prayer> result = new ArrayList<Prayer>();
		String url = String.format(URL_MOSQUE, placeId, mosqueId);
		JSONArray array = jsonParser.getJSONFromUrl(url);
		Log.i(TAG, ">>> url : " + url);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Prayer item = new Prayer();
				item.setId(Integer.valueOf(jObj.getString(ID_SALAT)));
				item.setCount(jObj.getInt(COUNT));
				item.setTitle(jObj.getString(NAME));
				item.setCount_men(jObj.getInt(MEN));
				item.setCount_women(jObj.getInt(WOMEN));
				item.setCount_child(jObj.getInt(CHILD));
				
				Log.i(TAG, item.toString());
				
				result.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public ArrayList<JanezaPerson> getZanaezNames(int placeId, int mosqueId, int prayerId, int genderId) {
		ArrayList<JanezaPerson> result = new ArrayList<JanezaPerson>();
		String url = String.format(URL_MOSQUE_SALAT_GENDER, placeId, mosqueId, prayerId, genderId);
		JSONArray array = jsonParser.getJSONFromUrl(url);
		Log.i(TAG, ">>> url : " + url);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JanezaPerson item = new JanezaPerson(); 
				
				JSONObject pObj = array.getJSONObject(i);
				item.setTitle(pObj.getString(NAME));
				item.setDate(pObj.getString(DATE));
				
				Log.i(TAG, item.toString());
				
				result.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public String login(String username, String password) {
		String uid = null;
		String url = String.format(URL_LOGIN, username, password);
		String response = jsonParser.getStringFromUrl(url);
		Log.i(TAG, ">>> url : " + url);
		if (response != null) 
		{
			if(!response.equalsIgnoreCase("false")){
				response = response.substring(response.indexOf("\""));
				uid = response.replaceAll("\"", "");
			}
		}
		
		return uid;
	}
	
	public boolean addJaneza(String uid, String title, String body, int place, int mosque, int salat, String salat_time, int gender){

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UID, uid));
		params.add(new BasicNameValuePair(TITLE, title));
		params.add(new BasicNameValuePair(BODY, body));
		params.add(new BasicNameValuePair(PLACE, String.valueOf(place)));
		params.add(new BasicNameValuePair(MOSQUE, String.valueOf(mosque)));
		params.add(new BasicNameValuePair(SALAT, String.valueOf(salat)));
		params.add(new BasicNameValuePair(SALAT_TIME, salat_time));
		params.add(new BasicNameValuePair(GENDER, String.valueOf(gender)));
		
		String response = jsonParser.post(URL_ADD_JANEZA, params);
		if(response != null)
			return response.trim().contains("1");
		
		return false;
	}
	
	public boolean addDa3wa(String uid, String title, String body, int place, int mosque, int trainer, String time_from, String time_to){

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UID, uid));
		params.add(new BasicNameValuePair(TITLE, title));
		params.add(new BasicNameValuePair(BODY, body));
		params.add(new BasicNameValuePair(PLACE, String.valueOf(place)));
		params.add(new BasicNameValuePair(MOSQUE, String.valueOf(mosque)));
		params.add(new BasicNameValuePair(TRAINER, String.valueOf(trainer)));
		params.add(new BasicNameValuePair(TIME_FROM, time_from));
		params.add(new BasicNameValuePair(TIME_TO, time_to));
		
		String response = jsonParser.post(URL_ADD_DA3WA, params);
		if(response != null)
			return response.trim().contains("1");
		
		return false;
	}
	
	
	public ArrayList<Da3wa> getAllDa3awi() {
		ArrayList<Da3wa> da3awi = new ArrayList<Da3wa>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_DA3WA);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Da3wa da3wa = new Da3wa();
				da3wa.setId(Integer.valueOf(jObj.getString(ID)));
				da3wa.setTitle(jObj.getString(TITLE));
				da3wa.setDescription(jObj.getString(DESCRIPTION));
				da3wa.setMosque(jObj.getString(MOSQUE));
				da3wa.setPlace(jObj.getString(PLACE));
				da3wa.setTrainer(jObj.getString(TRAINER));
				da3wa.setStartTime(jObj.getString(START_TIME));
				da3wa.setEndTime(jObj.getString(END_TIME));
				da3wa.setLatitude(jObj.getString(LAT));
				da3wa.setLongitude(jObj.getString(LONG));
				
				Log.i(TAG, da3wa.toString());
				
				da3awi.add(da3wa);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return da3awi;
	}
	
	public ArrayList<Place> getAllPlaces() {
		ArrayList<Place> places = new ArrayList<Place>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_PLACES);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Place place = new Place();
				place.setId(Integer.valueOf(jObj.getString(PLACE_ID)));
				place.setTitle(jObj.getString(PLACE_NAME));
				place.setPlaceEn(jObj.getString(PLACE_EN));
				
				Log.i(TAG, place.toString());
				
				places.add(place);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(getPlaces() == null){
			setPlaces(places);
		}
		
		return places;
	}
	
	public ArrayList<Address> getAllAddresses() {
		ArrayList<Address> addresses = new ArrayList<Address>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_ADDRESSES);
		if (array != null) 
		
			for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Address address = new Address();
				address.setAddress(jObj.getString(ADDRESS));
				address.setPhones(jObj.getString(PHONES));
				
				Log.i(TAG, address.toString());
				
				addresses.add(address);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return addresses;
	}
	
	public ArrayList<SelectMosque> getAllSelectMosques() {
		ArrayList<SelectMosque> result = new ArrayList<SelectMosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_SELECT_MOSQUES);
		if (array != null) 
		
			for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				SelectMosque item = new SelectMosque();
				item.setId(jObj.getInt(ID));
				item.setTitle(jObj.getString(NAME));
				
//				Log.i(TAG, item.toString());
				
				result.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		setSelectMosques(result);
		
		return result;
	}
	
	public ArrayList<SelectTrainer> getAllSelectTrainers() {
		ArrayList<SelectTrainer> result = new ArrayList<SelectTrainer>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_SELECT_TRAINERS);
		if (array != null) 
		
			for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				SelectTrainer item = new SelectTrainer();
				item.setId(jObj.getInt(ID));
				item.setTitle(jObj.getString(NAME));
				
//				Log.i(TAG, item.toString());
				
				result.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		setSelectTrainers(result);
		
		return result;
	}
	
	public ArrayList<SelectSalat> getAllSelectSalats() {
		ArrayList<SelectSalat> result = new ArrayList<SelectSalat>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_SELECT_SALATS);
		if (array != null) 
		
			for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				SelectSalat item = new SelectSalat();
				item.setId(jObj.getInt(ID));
				item.setTitle(jObj.getString(NAME));
				
//				Log.i(TAG, item.toString());
				
				result.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		setSelectSalats(result);
		
		return result;
	}
	
	public boolean registerPush(String regID) {
		boolean isOK = false;
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TOKEN, regID));
		params.add(new BasicNameValuePair(TYPE, "android"));
		
		GcmResponse result = jsonParser.getGcmResponse(URL_PUSH_REGISTER, params);
		if (result != null) 
		{
			Log.i(TAG, "result " + result.isSuccess() + "\n" + result.getMessage());
			isOK = result.isSuccess();

		}
		
		return isOK;
	}

	private String deviceToken;
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public void updateDeviceToken(String token){
		setDeviceToken(token);
		new AsyncTask<String, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(String... params) {
				return registerPush(params[0]);
			}
		}.execute(token);
	}
	
	
	public boolean refreshLocalityIfPossible(String locality){

		Log.v(TAG, ">>> locality " + locality);
		if(getPlaces() != null)
			for(Place place : getPlaces()){
				Log.v(TAG, ">>> Place EN " + place.getPlaceEn());
				if(place.getPlaceEn().equalsIgnoreCase(locality)
						|| place.getTitle().equalsIgnoreCase(locality)){
					setSelectedPlace(place);
					return true;
				}
			}
		
		return false;
	}
	
	public void setNotificationSettings(boolean isRegistered){
		editor.putBoolean("notifs", isRegistered);
		editor.commit();
	}
	
	public boolean isNotificationEnabled(){
		return settings.getBoolean("notifs", false);
	}
	
	public void setBadgeCounter(int counter){
		editor.putInt("badge_counter", counter);
		editor.commit();
	}
	
	public int getBadgeCounter(){
		return settings.getInt("badge_counter", 0);
	}
	
	public IFragmentNotifier getFragmentNotifier() {
		return fragmentNotifier;
	}

	public void setFragmentNotifier(IFragmentNotifier fragmentNotifier) {
		this.fragmentNotifier = fragmentNotifier;
	}

	public Mosque getSelectedMosque() {
		return selectedMosque;
	}

	public void setSelectedMosque(Mosque selectedMosque) {
		this.selectedMosque = selectedMosque;
	}

	public Mosque2 getSelectedMosque2() {
		return selectedMosque2;
	}

	public void setSelectedMosque2(Mosque2 selectedMosque2) {
		this.selectedMosque2 = selectedMosque2;
	}
	
	public Prayer getSelectedPrayer() {
		return selectedPrayer;
	}

	public void setSelectedPrayer(Prayer selectedPrayer) {
		this.selectedPrayer = selectedPrayer;
	}
	
	public Place getSelectedPlace() {
		
		Place selectedPlace = null;
		int placeID = settings.getInt("place_id", -1);
		if(placeID != -1){
			selectedPlace = new Place();
			selectedPlace.setId(placeID);
			selectedPlace.setTitle(settings.getString("place_title", null));
			selectedPlace.setPlaceEn(settings.getString("place_en", null));
		}
		
		return selectedPlace;
	}

	public void setSelectedPlace(Place selectedPlace) {
//		this.selectedPlace = selectedPlace;
		
		editor.putInt("place_id", selectedPlace.getId());
		editor.putString("place_title", selectedPlace.getTitle());
		editor.putString("place_en", selectedPlace.getPlaceEn());
		
		editor.commit();
		
	}

	public Da3wa getSelectedDa3wa() {
		return selectedDa3wa;
	}

	public void setSelectedDa3wa(Da3wa selectedDa3wa) {
		this.selectedDa3wa = selectedDa3wa;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}

	public ArrayList<SelectMosque> getSelectMosques() {
		return selectMosques;
	}

	public void setSelectMosques(ArrayList<SelectMosque> selectMosques) {
		this.selectMosques = selectMosques;
	}

	public ArrayList<SelectTrainer> getSelectTrainers() {
		return selectTrainers;
	}

	public void setSelectTrainers(ArrayList<SelectTrainer> selectTrainers) {
		this.selectTrainers = selectTrainers;
	}

	public ArrayList<SelectSalat> getSelectSalats() {
		return selectSalats;
	}

	public void setSelectSalats(ArrayList<SelectSalat> selectSalats) {
		this.selectSalats = selectSalats;
	}

	public ArrayList<SelectGender> getSelectGenders() {
		return selectGenders;
	}

	public void setSelectGenders(ArrayList<SelectGender> selectGenders) {
		this.selectGenders = selectGenders;
	}

	public ISearchListener getSearchListener() {
		return searchListener;
	}

	public void setSearchListener(ISearchListener searchListener) {
		this.searchListener = searchListener;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}
}