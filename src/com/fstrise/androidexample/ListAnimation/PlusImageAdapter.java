package com.fstrise.androidexample.ListAnimation;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fstrise.androidexample.MyActivity;
import com.fstrise.androidexample.R;
import com.fstrise.androidexample.model.itemList;
import com.fstrise.androidexample.utils.Cals;

public class PlusImageAdapter extends GPlusListAdapter {
	private ArrayList<itemList> aList;

	private Context context;

	// QMain qMain;

	public PlusImageAdapter(Context context,
			SpeedScrollListener scrollListener, ArrayList<itemList> arList) {
		super(context, scrollListener, arList);
		this.context = context;
		// qMain = (QMain) context;
		aList = arList;
	}

	@Override
	protected View getRowView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_item_list, parent, false);

			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.txtTitle);
			holder.txtTitle.setTextSize((float) Cals.textSize20);
			//
			holder.txtDescription = (TextView) convertView
					.findViewById(R.id.txtDes);
			holder.txtDescription.setTextSize((float) Cals.textSize17);
			holder.txtDescription
					.setPadding(Cals.w5, Cals.w5, Cals.w5, Cals.w5);
			holder.imgSample = (ImageView) convertView
					.findViewById(R.id.imgDemo);
			holder.imgSample.setLayoutParams(new FrameLayout.LayoutParams(
					(Cals.w100 * 2 + Cals.w20), (Cals.h100 * 2 + Cals.h80),
					Gravity.RIGHT));
			//
			holder.txtAuthor = (TextView) convertView
					.findViewById(R.id.txtAuthor);
			holder.txtAuthor.setTextSize((float) Cals.textSize15);
			FrameLayout.LayoutParams lptxtA = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.BOTTOM);
			lptxtA.leftMargin = Cals.w50;
			lptxtA.bottomMargin = Cals.w10;
			holder.txtAuthor.setLayoutParams(lptxtA);
			//
			holder.imgAuthor = (ImageView) convertView
					.findViewById(R.id.imgAuthor);
			FrameLayout.LayoutParams lpImgA = new FrameLayout.LayoutParams(
					Cals.w30, Cals.w30, Gravity.BOTTOM);
			lpImgA.leftMargin = Cals.w10;
			lpImgA.bottomMargin = Cals.w10;
			holder.imgAuthor.setLayoutParams(lpImgA);
			//
			holder.layoutInfo = (LinearLayout) convertView
					.findViewById(R.id.layoutInfo);
			holder.layoutInfo.setLayoutParams(new FrameLayout.LayoutParams(
					MyActivity.realWidth - (Cals.w100 * 2 + Cals.w20),
					LayoutParams.WRAP_CONTENT));

			//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		itemList obj = aList.get(position);
		holder.txtTitle.setText(obj.getTitle());
		Log.i("PlusImageAdapter", "position: " + position);
		if (!obj.getImages().equals("")) {
			MyActivity.DisplayImage(obj.getImages(), holder.imgSample, 10);
			holder.imgSample.setScaleType(ScaleType.FIT_XY);
		}

		//
		if (obj.getDescription().length() > 180) {
			holder.txtDescription.setText(obj.getDescription()
					.substring(0, 179) + " ...");
		} else {
			holder.txtDescription.setText(obj.getDescription());
		}
		//
		if(obj.getAuthor().equals("")){
			holder.txtAuthor.setText("Unknown");
		}else{
			holder.txtAuthor.setText(obj.getAuthor());
		}
		
		return convertView;
	}

	static class ViewHolder {
		ImageView imgSample;
		ImageView imgAuthor;
		TextView txtTitle;
		TextView txtDescription;
		TextView txtAuthor;
		ImageButton btnMore;
		LinearLayout layoutInfo;
		FrameLayout frameText;
		ImageView imgNewUpdate;

	}
}
