package com.fstrise.androidexample.ListAnimation;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
			holder.imgSample = (ImageView) convertView
					.findViewById(R.id.imgDemo);
			holder.imgSample.setLayoutParams(new FrameLayout.LayoutParams(
					(Cals.w100 * 2 + Cals.w20), (Cals.h100 * 2 + Cals.h80),
					Gravity.RIGHT));
			// FrameLayout.LayoutParams lpRowItem = new
			// FrameLayout.LayoutParams(
			// LayoutParams.MATCH_PARENT, Cals.h80 * 2);
			// lpRowItem.leftMargin = Cals.w10;
			// lpRowItem.rightMargin = Cals.w10;
			// holder.layoutRowItem.setLayoutParams(lpRowItem);
			// //
			// holder.imgSample = (ImageView) convertView
			// .findViewById(R.id.imgSample);
			// LinearLayout.LayoutParams lpImgSamp = new
			// LinearLayout.LayoutParams(
			// Cals.w60 * 2, LayoutParams.MATCH_PARENT);
			// lpImgSamp.leftMargin = Cals.w10;
			// holder.imgSample.setLayoutParams(lpImgSamp);
			// //
			// holder.frameText = (FrameLayout) convertView
			// .findViewById(R.id.frameText);
			// LinearLayout.LayoutParams lpFrameText = new
			// LinearLayout.LayoutParams(
			// LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			// lpFrameText.leftMargin = Cals.w20;
			// holder.frameText.setLayoutParams(lpFrameText);
			// //
			// holder.txtTitle = (TextView) convertView
			// .findViewById(R.id.txtTitle);
			// //
			// holder.txtDescription = (TextView) convertView
			// .findViewById(R.id.txtDescription);
			// LinearLayout.LayoutParams lpDes = new LinearLayout.LayoutParams(
			// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// lpDes.rightMargin = Cals.w80;
			// holder.txtDescription.setLayoutParams(lpDes);
			// //
			// holder.btnMore = (ImageButton) convertView
			// .findViewById(R.id.btnMore);
			// FrameLayout.LayoutParams lpBtnMore = new
			// FrameLayout.LayoutParams(
			// Cals.w80, Cals.h100 + Cals.h10, Gravity.RIGHT);
			// holder.btnMore.setLayoutParams(lpBtnMore);
			// //
			// holder.imgFav = (ImageView)
			// convertView.findViewById(R.id.imgFav);
			// FrameLayout.LayoutParams lpimgFav = new FrameLayout.LayoutParams(
			// Cals.w40, Cals.w40, Gravity.RIGHT | Gravity.BOTTOM);
			// lpimgFav.rightMargin = Cals.w20;
			// lpimgFav.bottomMargin = Cals.w10;
			// holder.imgFav.setLayoutParams(lpimgFav);
			// //
			// holder.imgNewUpdate = (ImageView) convertView
			// .findViewById(R.id.imgNewUpdate);
			// holder.imgNewUpdate.setLayoutParams(new FrameLayout.LayoutParams(
			// Cals.w50, Cals.w50));

			//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final itemList obj = aList.get(position);
		holder.txtTitle.setText(obj.getTitle());
		// Log.i("listview", "Pos: " + position + " -Item: " + obj.getTitle());
		// holder.txtTitle.setText(obj.getTitle());
		// String des = obj.getDescription();
		// if (des.length() > 2)
		// holder.txtDescription.setText(obj.getDescription());
		//
		// // check fav
		// if (obj.getIsFavorite() == 1) {
		// holder.imgFav.setVisibility(View.VISIBLE);
		// } else {
		// holder.imgFav.setVisibility(View.GONE);
		// }
		// // check isView
		// if (obj.getIsView() == 1) {
		// holder.txtTitle.setTypeface(null, Typeface.NORMAL);
		// } else {
		// holder.txtTitle.setTypeface(null, Typeface.BOLD);
		// }
		//
		// if (obj.getVersion() > 2
		// && obj.getVersion() == Integer
		// .parseInt(QMain.versionLastUpdate)) {
		// holder.imgNewUpdate.setVisibility(View.VISIBLE);
		// } else {
		// holder.imgNewUpdate.setVisibility(View.GONE);
		// }
		//
		// holder.btnMore.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View view) {
		// PopupMenuCompat menu = PopupMenuCompat.newInstance(context,
		// view);
		// menu.inflate(R.menu.qmain);
		// if (obj.getIsFavorite() == 0) {
		// menu.getMenu()
		// .getItem(2)
		// .setTitle(
		// context.getResources().getString(
		// R.string.addToFavorite));
		// } else {
		// menu.getMenu()
		// .getItem(2)
		// .setTitle(
		// context.getResources().getString(
		// R.string.removeFavorite));
		// }
		// menu.setOnMenuItemClickListener(new
		// PopupMenuCompat.OnMenuItemClickListener() {
		// @Override
		// public boolean onMenuItemClick(MenuItem item) {
		// Toast.makeText(context, item.getTitle(),
		// Toast.LENGTH_SHORT).show();
		// if (item.getTitle().equals(
		// context.getResources().getString(
		// R.string.viewDemo))) {
		// Intent i = new Intent(context, DetailActivity.class);
		// i.putExtra("id", obj.getId());
		// context.startActivity(i);
		// qMain.overridePendingTransition(R.anim.open_next,
		// R.anim.close_main);
		// MySQLiteHelper mSql = new MySQLiteHelper(context);
		// obj.setIsView(1);
		// mSql.isView(obj);
		// notifyDataSetChanged();
		// qMain.reloadData();
		// } else if (item.getTitle().equals(
		// context.getResources().getString(
		// R.string.downloadSource))) {
		// //
		// qMain.downloadFile("https://github.com/jfeinstein10/SlidingMenu/archive/master.zip");
		// } else if (item.getTitle().equals(
		// context.getResources().getString(
		// R.string.viewOnGithub))) {
		// Intent browserIntent = new Intent(
		// Intent.ACTION_VIEW, Uri.parse(obj
		// .getLinkGithub()));
		// context.startActivity(browserIntent);
		// } else if (item.getTitle().equals(
		// context.getResources().getString(
		// R.string.addToFavorite))) {
		// MySQLiteHelper mSql = new MySQLiteHelper(context);
		//
		// obj.setIsFavorite(1);
		// mSql.updateFavorite(obj);
		// notifyDataSetChanged();
		//
		// } else if (item.getTitle().equals(
		// context.getResources().getString(
		// R.string.removeFavorite))) {
		// MySQLiteHelper mSql = new MySQLiteHelper(context);
		// obj.setIsFavorite(0);
		// mSql.updateFavorite(obj);
		// if (QMain.posionItemList == 0) {
		// qMain.reloadDataFav();
		// } else {
		// notifyDataSetChanged();
		// }
		//
		// }
		//
		// return true;
		// }
		// });
		//
		// menu.show();
		// }
		// });
		//
		// holder.layoutRowItem.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// Intent i = new Intent(context, DetailActivity.class);
		// i.putExtra("id", obj.getId());
		// context.startActivity(i);
		// qMain.overridePendingTransition(R.anim.open_next,
		// R.anim.close_main);
		// MySQLiteHelper mSql = new MySQLiteHelper(context);
		// obj.setIsView(1);
		// mSql.isView(obj);
		// notifyDataSetChanged();
		// qMain.reloadData();
		//
		// }
		// });
		return convertView;
	}

	static class ViewHolder {
		ImageView imgSample;
		ImageView imgFav;
		TextView txtTitle;
		TextView txtDescription;
		ImageButton btnMore;
		LinearLayout layoutRowItem;
		FrameLayout frameText;
		ImageView imgNewUpdate;
	}
}
