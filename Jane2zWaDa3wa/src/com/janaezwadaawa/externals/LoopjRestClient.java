package com.janaezwadaawa.externals;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class LoopjRestClient {

//	private static final String BASE_URL = "http://gheras.net/exequyApp/api/";
	private static final String BASE_URL = "";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void getFromUrl(String url, AsyncHttpResponseHandler responseHandler) {
		client.get(url, responseHandler);
	}
	
	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), responseHandler);
	}

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), responseHandler);
	}
	
	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
