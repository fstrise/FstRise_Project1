package com.fstrise.androidexample;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.fstrise.androidexample.ListAnimation.PlusImageAdapter;
import com.fstrise.androidexample.ListAnimation.SpeedScrollListener;
import com.fstrise.androidexample.model.itemList;
import com.fstrise.androidexample.utils.Utils;

public class MyActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	public static AQuery aq;
	private File cacheDir;
	private CharSequence mTitle;
	private String[] navMenu;
	private SpeedScrollListener listener;
	public static PlusImageAdapter plusAdapter;
	public static ListView listv;
	public static ArrayList<itemList> arList;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(), "8TV");
		else
			cacheDir = this.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		AQUtility.setCacheDir(cacheDir);
		aq = new AQuery(this);
		//
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		DisplayMetrics displayMetrics = Resources.getSystem()
				.getDisplayMetrics();
		heightScre = metrics.heightPixels;
		widthScre = metrics.widthPixels;
		varWidthScre = (int) (displayMetrics.widthPixels
				/ displayMetrics.density + 0.5);
		varHeightScree = (int) (displayMetrics.heightPixels
				/ displayMetrics.density + 0.5);
		//
		setContentView(R.layout.activity_my);
		navMenu = getResources().getStringArray(R.array.arrMenu);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		listv = (ListView) findViewById(R.id.listv);
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		new getItemDataL().execute("");
	}

	@SuppressLint("NewApi")
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		mTitle = navMenu[number - 1];
	}

	@SuppressLint("NewApi")
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.my, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	@SuppressLint("NewApi")
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_my, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MyActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	private void loadData(int pos) {
		try {
			
			listener = new SpeedScrollListener();
			plusAdapter = new PlusImageAdapter(MyActivity.this, listener,
					arList);
			if (listv != null)
				listv.setAdapter(plusAdapter);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public class getItemDataL extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";
//			if (isUpdate) {
//				result = Utils.getUrls(Utils.URL_GET_ITEM_UPDATE
//						+ versionLastUpdate);
//			} else {
				result = Utils.getUrls(Utils.URL_GET_ITEM);
//			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject objOb;

			ArrayList<itemList> arListUpdate = new ArrayList<itemList>();
			arList = new ArrayList<itemList>();
			int version = 0;
			try {
				objOb = new JSONObject(result);
				JSONArray objArr = new JSONArray(objOb.getString("rows"));
				JSONObject e3 = null;
				for (int i = 0; i < objArr.length(); i++) {
					e3 = objArr.getJSONObject(i);
					itemList obj = new itemList();
					obj.setId(e3.getInt("Id"));
					obj.setTitle(e3.getString("Title"));
					obj.setDescription(e3.getString("Description"));
					obj.setAuthor(e3.getString("Author"));
					obj.setLicense(e3.getString("License"));
//					obj.setCodeRun(e3.getInt("Coderun"));
//					obj.setSdkVersion(e3.getString("Sdkversion"));
//					obj.setLinkGithub(e3.getString("Linkgithub"));
//					obj.setPackageapk(e3.getString("Packageapk"));
//					obj.setModerun(e3.getInt("Moderun"));
//					obj.setApkname(e3.getString("Apkname"));
//					obj.setType(e3.getInt("Type"));
//					obj.setVersion(e3.getInt("Version"));
//					if (version < e3.getInt("Version"))
//						version = e3.getInt("Version");
//					MySQLiteHelper mSql = new MySQLiteHelper(QMain.this);
//					mSql.addItem(obj);
//					if (isUpdate) {
//						arListUpdate.add(obj);
//					}
					arList.add(obj);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			if (version != 0) {
//				versionLastUpdate = String.valueOf(version);
//				Editor edit = preferences.edit();
//				edit.putString("versionUpdate", versionLastUpdate);
//				edit.commit();
//			}
//			if (isUpdate) {
//				frameProgressbar.setVisibility(View.GONE);
//				isUpdate = false;
//				if (arListUpdate.size() == 0) {
//					Toast.makeText(QMain.this, "No Data Update", 1).show();
//				} else {
//					listener = new SpeedScrollListener();
//					plusAdapter = new PlusImageAdapter(QMain.this, listener,
//							arListUpdate);
//					if (listv != null)
//						listv.setAdapter(plusAdapter);
//					Toast.makeText(QMain.this,
//							arListUpdate.size() + " Item Updated", 1).show();
//					reloadData();
//				}
//			} else {
//				loadDataByPos(0);
//			}
			loadData(0);
		

		}

	}

}
