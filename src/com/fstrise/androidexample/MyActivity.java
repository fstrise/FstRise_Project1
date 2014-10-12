package com.fstrise.androidexample;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.callback.ImageOptions;
import com.androidquery.util.AQUtility;
import com.fstrise.androidexample.ListAnimation.PlusImageAdapter;
import com.fstrise.androidexample.ListAnimation.SpeedScrollListener;
import com.fstrise.androidexample.model.itemList;
import com.fstrise.androidexample.sqlite.MySQLiteHelper;
import com.fstrise.androidexample.utils.Cals;
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
	public static int realHeight;
	public static int realWidth;

	private CharSequence mTitle;
	private String[] navMenu;
	private SpeedScrollListener listener;
	public static PlusImageAdapter plusAdapter;
	public static ListView listv;
	public static ArrayList<itemList> arList;
	public static String versionLastUpdate = "";
	private SharedPreferences preferences;
	// private ProgressBar progressBar1;
	private LinearLayout layoutprogress;
	private TextView txtLoading;

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
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		versionLastUpdate = preferences.getString("versionUpdate", "");
		//
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		DisplayMetrics displayMetrics = Resources.getSystem()
				.getDisplayMetrics();
		int heightScre = metrics.heightPixels;
		int widthScre = metrics.widthPixels;
		int varWidthScre = (int) (displayMetrics.widthPixels
				/ displayMetrics.density + 0.5);
		int varHeightScree = (int) (displayMetrics.heightPixels
				/ displayMetrics.density + 0.5);
		Display display = getWindowManager().getDefaultDisplay();
		Method mGetRawH = null, mGetRawW = null;
		try {
			// For JellyBeans and onward
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
				display.getRealMetrics(metrics);
				realHeight = metrics.heightPixels;
				realWidth = metrics.widthPixels;
				if (realHeight == 800) {
					realWidth = widthScre;
					realHeight = heightScre;
				}
				// objCS = new CalScreen(realWidth, realHeight, varWidthScre,
				// varHeightScree);

			} else {
				mGetRawH = Display.class.getMethod("getRawHeight");
				mGetRawW = Display.class.getMethod("getRawWidth");

				try {
					realHeight = (Integer) mGetRawH.invoke(display);
					realWidth = (Integer) mGetRawW.invoke(display);
					if (realHeight == 800) {
						realWidth = widthScre;
						realHeight = heightScre;
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception x) {

		}
		new Cals(realWidth, realHeight, varHeightScree, varWidthScre);

		//
		setContentView(R.layout.activity_my);
		navMenu = getResources().getStringArray(R.array.arrMenu);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		listv = (ListView) findViewById(R.id.listv);
		layoutprogress = (LinearLayout) findViewById(R.id.layoutprogress);
		txtLoading = (TextView) findViewById(R.id.txtLoading);
		txtLoading.setTextSize((float) Cals.textSize15);
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		if (versionLastUpdate.equals("")) {
			new getItemDataL().execute("");
		} else {
			// load item favorite
			MySQLiteHelper mSql = new MySQLiteHelper(this);
			arList = mSql.getAllItem();
			loadData(0);
		}

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
		loadData(number);
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
		if (id == R.id.action_example) {
			Toast.makeText(this, "Fav", 1).show();
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
			MySQLiteHelper mSql = new MySQLiteHelper(MyActivity.this);

			if (pos == 0) {
				arList = mSql.getAllItem();
			} else {
				arList = mSql.getAllItem(pos);
			}
			listener = new SpeedScrollListener();
			plusAdapter = new PlusImageAdapter(MyActivity.this, listener,
					arList);
			if (listv != null)
				listv.setAdapter(plusAdapter);
			listv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					Intent i = new Intent(MyActivity.this, DetailActivity.class);
					i.putExtra("pos", pos);
					startActivity(i);
					overridePendingTransition(R.anim.open_next,
							R.anim.close_main);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private int version = 0;

	public class getItemDataL extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			layoutprogress.setVisibility(View.VISIBLE);

		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			// if (isUpdate) {
			// result = Utils.getUrls(Utils.URL_GET_ITEM_UPDATE
			// + versionLastUpdate);
			// } else {
			result = Utils.getUrls(Utils.URL_GET_ITEM);
			// }

			return result;
		}

		@Override
		protected void onPostExecute(final String result) {
			super.onPostExecute(result);

			ArrayList<itemList> arListUpdate = new ArrayList<itemList>();
			arList = new ArrayList<itemList>();
			// new Thread(new Runnable(
			// @Override
			// public void run () {
			// // Perform long-running task here
			// // (like audio buffering).
			// // you may want to update some progress
			// // bar every second, so use handler:
			// mHandler.post(new Runnable() {
			// @Override
			// public void run () {
			// // make operation on UI - on example
			// // on progress bar.
			// }
			// });
			// }
			// )).start();
			new Thread(new Runnable() {

				@Override
				public void run() {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							try {
								JSONObject objOb = new JSONObject(result);
								JSONArray objArr = new JSONArray(objOb
										.getString("rows"));
								JSONObject e3 = null;

								for (int i = 0; i < objArr.length(); i++) {
									e3 = objArr.getJSONObject(i);
									itemList obj = new itemList();
									obj.setId(e3.getInt("Id"));
									obj.setId_menu(e3.getInt("Id_menu"));
									obj.setTitle(e3.getString("Title"));
									obj.setDescription(e3
											.getString("Description"));
									obj.setAuthor(e3.getString("Author"));
									obj.setLicense(e3.getString("License"));
									obj.setImages(e3.getString("Images"));
									obj.setImg_author(e3
											.getString("Img_author"));
									obj.setLink(e3.getString("Link"));
									obj.setVersion(e3.getInt("Version"));

									if (version < e3.getInt("Version"))
										version = e3.getInt("Version");
									MySQLiteHelper mSql = new MySQLiteHelper(
											MyActivity.this);
									mSql.addItem(obj);
									txtLoading.setText("Updating data " + i
											+ "/" + objArr.length());
									if (i == objArr.length() - 1) {
										mHandler.sendMessage(Message.obtain(
												mHandler, LOAD_FINISH));
									}
									// if (isUpdate) {
									// arListUpdate.add(obj);
									// }

									// arList.add(obj);
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}
					});
				}
			}).start();

			// if (isUpdate) {
			// frameProgressbar.setVisibility(View.GONE);
			// isUpdate = false;
			// if (arListUpdate.size() == 0) {
			// Toast.makeText(QMain.this, "No Data Update", 1).show();
			// } else {
			// listener = new SpeedScrollListener();
			// plusAdapter = new PlusImageAdapter(QMain.this, listener,
			// arListUpdate);
			// if (listv != null)
			// listv.setAdapter(plusAdapter);
			// Toast.makeText(QMain.this,
			// arListUpdate.size() + " Item Updated", 1).show();
			// reloadData();
			// }
			// } else {
			// loadDataByPos(0);
			// }

		}
	}

	private final static int LOAD_FINISH = 1;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
			case LOAD_FINISH:
				if (version != -1) {
					versionLastUpdate = String.valueOf(version);
					Editor edit = preferences.edit();
					edit.putString("versionUpdate", versionLastUpdate);
					edit.commit();

				}
				loadData(0);
				layoutprogress.setVisibility(View.GONE);
				break;
			}
		}
	};

	public static void DisplayImage(final String url,
			final ImageView imageView, final int type) {
		File file = aq.getCachedFile(url);
		if (file != null) {

			if (type == 10) {
				ImageOptions options = new ImageOptions();
				options.round = 5;
				// options.animation = AQuery.FADE_IN;
				aq.id(imageView).image(url, options);
			} else if (type == 11) {
				ImageOptions options = new ImageOptions();
				options.round = 15;
				aq.id(imageView).image(url, options);
			} else {
				aq.id(imageView).image(file, false, 0,
						new BitmapAjaxCallback() {
							@Override
							public void callback(String url, ImageView iv,
									Bitmap bm, AjaxStatus status) {
								iv.setImageBitmap(bm);
							}
						});
			}

		} else {
			Bitmap placeholder = null;
			placeholder = aq.getCachedImage(R.drawable.imgdefault);
			if (type == 10) {
				ImageOptions options = new ImageOptions();
				options.round = 5;
				// options.animation = AQuery.FADE_IN;
				aq.id(imageView).image(url, options);
			} else if (type == 11) {
				ImageOptions options = new ImageOptions();
				options.round = 15;
				aq.id(imageView).image(url, options);
			} else {
				aq.id(imageView).image(url, true, true, 0, 0,
						new BitmapAjaxCallback() {
							@Override
							public void callback(String url, ImageView iv,
									Bitmap bm, AjaxStatus status) {
								iv.setImageBitmap(bm);
							}

						});
			}

		}
		//

	}
}
