package com.krieger.training.android101demos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krieger.training.android101demos.R;
import com.krieger.training.android101demos.view.CardView;

public class CustomViewExampleFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_customview, container, false);
		CardView c1 = (CardView) v.findViewById(R.id.frag_custom_card1);
		CardView c2 = (CardView) v.findViewById(R.id.frag_custom_card2);
		CardView c3 = (CardView) v.findViewById(R.id.frag_custom_card3);
		
		String defaultString =
				"Lorem ipsum dolor sit amet," +
				"consectetur adipisicing elit," +
				"sed do eiusmod tempor incididunt" +
				"ut labore et dolore magna aliqua.";
		
		c1.setText("Card 1: Blue");
		c1.setBackText(defaultString);
		c1.setOnClickListener(c1.listener);
		
		c2.setText("Card 2: Red");
		c2.setBackText(defaultString);
		c2.setOnClickListener(c2.listener);
		
		c3.setText("Card 3: Green");
		c3.setBackText(defaultString);
		c3.setOnClickListener(c3.listener);
		return v;
	}
}
