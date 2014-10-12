package com.fstrise.androidexample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fstrise.androidexample.custom.CircularImageView;
import com.fstrise.androidexample.custom.IButton;
import com.fstrise.androidexample.model.itemList;
import com.fstrise.androidexample.sqlite.MySQLiteHelper;
import com.fstrise.androidexample.utils.Cals;
import com.fstrise.androidexample.utils.Utils;

public class DetailActivity extends Activity {
	private CircularImageView imgBGCasi;
	private TextView txtAuthor;
	private TextView txtExName;
	private TextView txtDes;
	private TextView txtLicense;
	private TextView txtLink;
	private IButton btnLink;
	private ImageView imgDemo;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		//
		imgBGCasi = (CircularImageView) findViewById(R.id.imgAuthorDetail);
		LinearLayout.LayoutParams lpImgBGCasi = new LinearLayout.LayoutParams(
				(Cals.w100 * 2), (Cals.w100 * 2)); //
		lpImgBGCasi.topMargin = Cals.h20;
		imgBGCasi.setLayoutParams(lpImgBGCasi);
		imgBGCasi.setBorderColor(getResources().getColor(R.color.GrayLight));
		imgBGCasi.setBorderWidth(10);
		imgBGCasi.addShadow();
		//
		txtAuthor = (TextView) findViewById(R.id.txtAuthor);
		txtAuthor.setTextSize((float) Cals.textSize20);
		//
		txtExName = (TextView) findViewById(R.id.txtExName);
		txtExName.setTextSize((float) Cals.textSize20);
		txtExName.setPadding(Cals.w10, Cals.w10, Cals.w10, Cals.w10);
		//
		txtDes = (TextView) findViewById(R.id.txtDes);
		txtDes.setTextSize((float) Cals.textSize15);
		txtDes.setPadding(Cals.w10, Cals.w10, Cals.w10, Cals.w10);
		//
		txtLicense = (TextView) findViewById(R.id.txtLicense);
		txtLicense.setTextSize((float) Cals.textSize20);
		txtLicense.setPadding(Cals.w10, Cals.w10, Cals.w10, Cals.w10);
		//
		txtLink = (TextView) findViewById(R.id.txtLink);
		txtLink.setTextSize((float) Cals.textSize15);
		txtLink.setPadding(Cals.w10, Cals.w10, Cals.w10, Cals.w10);
		//
		btnLink = (IButton) findViewById(R.id.btnLink);
		btnLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(txtLink.getText().toString()));
				startActivity(browserIntent);
			}
		});
		//
		imgDemo = (ImageView) findViewById(R.id.imgDemo);
		imgDemo.setLayoutParams(new LinearLayout.LayoutParams(Cals.w360,
				Cals.h60 * 10 + Cals.h40));

	}

	private String urlAvatar;

	@SuppressLint("NewApi")
	private void loadDetails() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int position = extras.getInt("pos");
			itemList obj = MyActivity.arList.get(position);
			txtAuthor.setText(obj.getAuthor());
			txtExName.setText(obj.getTitle());
			txtDes.setText(obj.getDescription());
			txtLicense.setText("License: " + obj.getLicense());
			txtLink.setText(obj.getLink());
			if (!obj.getImages().equals("")) {
				MyActivity.DisplayImage(obj.getImages(), imgDemo, 10);
				imgDemo.setScaleType(ScaleType.FIT_XY);
			}
			ActionBar actionBar = getActionBar();
			actionBar.setTitle(obj.getTitle());
			MySQLiteHelper mSql = new MySQLiteHelper(DetailActivity.this);
			mSql.isView(obj);
			urlAvatar = obj.getImg_author();
			if (!urlAvatar.equals("")) {
				new getItemDataL().execute("");
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		loadDetails();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.open_main, R.anim.close_next);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			overridePendingTransition(R.anim.open_main, R.anim.close_next);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class getItemDataL extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			result = Utils.getUrls(urlAvatar);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject objOb;

			try {
				String cals = result.replace("/**/renderContributors(", "");
				cals = cals.substring(0, cals.length() - 1);
				objOb = new JSONObject(cals);
				JSONArray objArr = new JSONArray(objOb.getString("data"));
				JSONObject e3 = null;
				for (int i = 0; i < objArr.length(); i++) {
					e3 = objArr.getJSONObject(i);
					String urAvatar = e3.getString("avatar_url");
					MyActivity.DisplayImage(urAvatar, imgBGCasi, 10);
					imgBGCasi.setScaleType(ScaleType.FIT_XY);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

}
