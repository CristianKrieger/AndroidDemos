package com.krieger.training.android101demos.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krieger.training.android101demos.entities.ListElementCookie;

public class CustomListAdapter extends BaseAdapter{
	private ArrayList<ListElementCookie> data;
	private int layoutId;
	private int textItemId;
	private int imageId;
	private LayoutInflater inflater;
	private int textDesId;
	
	public CustomListAdapter(LayoutInflater inflater,
			ArrayList<ListElementCookie> data, int layoutId,
			int textItemId, int textDesId, int imageId) {
		this.inflater=inflater;
		this.data = data;
		this.layoutId = layoutId;
		this.textDesId=textDesId;
		this.textItemId = textItemId;
		this.imageId=imageId;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public ListElementCookie getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
			convertView = inflater.inflate(layoutId, null);
		ListElementCookie current = getItem(position);
		
		TextView tvT = (TextView) convertView.findViewById(textItemId);
		tvT.setText(current.getTitle());
		
		TextView tvD = (TextView) convertView.findViewById(textDesId);
		tvD.setText(current.getDescription());
		
		ImageView iv = (ImageView) convertView.findViewById(imageId);
		iv.setImageDrawable(current.getThumb());
		
		return convertView;
	}
}