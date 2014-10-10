package com.fstrise.androidexample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Utils {

	public static final String URL_GET_ITEM = "http://fstrise.com/libapi/getItem.aspx?mode=1";
	public static final String URL_GET_ITEM_UPDATE = "http://123.30.238.206:8001/update.aspx?ver=";

	public static String getUrls(String url) {

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpGet httpGet = new HttpGet(url);
		// Making HTTP Request
		try {
			Log.i("URL","URL: "+url);
			HttpResponse response = httpClient.execute(httpGet);
			// writing response to log
			HttpEntity entity = response.getEntity();
			InputStream is1 = entity.getContent();
			String programlist = convertStreamToString(is1);
			
			return programlist;
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();

		}
		return "";
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
