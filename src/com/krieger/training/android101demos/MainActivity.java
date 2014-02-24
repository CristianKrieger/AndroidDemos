package com.krieger.training.android101demos;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.krieger.training.android101demos.fragment.AsyncQueryExampleFragment;
import com.krieger.training.android101demos.fragment.AsyncTaskExampleFragment;
import com.krieger.training.android101demos.fragment.CustomViewExampleFragment;
import com.krieger.training.android101demos.fragment.GridViewExampleFragment;

public class MainActivity extends ActionBarActivity implements OnNavigationListener{

	private Fragment currentFragment;
	private int currentFragmentId=FRAG_GRIDVIEW;
	private final static int FRAG_GRIDVIEW=0;
	private final static int FRAG_ASYNCTASK=1;
	private final static int FRAG_ASYNCQUERY=2;
	private final static int FRAG_CUSTOMVIEW=3;
	
	private String[] fragmentTags = {
			"GridViewFragment",
			"AsyncTaskFragment",
			"AsyncQueryFragment",
			"CustomViewFragment"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar ab = getSupportActionBar();
		ab.setTitle("");
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayList<String> data = new ArrayList<String>();
		
		data.add("Custom GridView Adapter");
		data.add("AsyncTask Demo");
		data.add("AsyncQuery Demo");
		data.add("CustomView Demo");
		
		SpinnerAdapter mSpinnerAdapter = new NavigationSpinnerAdapter(this,
				data, R.layout.element_spinner_navigation, R.id.spinner_txt);
		ab.setListNavigationCallbacks(mSpinnerAdapter, this);
		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		currentFragment=new GridViewExampleFragment();
		t.add(R.id.act_main_container_root, currentFragment, fragmentTags[FRAG_GRIDVIEW]);
		t.commit();
	}
	
	private class NavigationSpinnerAdapter implements SpinnerAdapter{
		private Context c;
		private ArrayList<String> data;
		private int layoutId;
		private int textItemId;
		
		public NavigationSpinnerAdapter(Context c, ArrayList<String> data,
				int layoutId, int textItemId) {
			this.c = c;
			this.data = data;
			this.layoutId = layoutId;
			this.textItemId = textItemId;
		}
		
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public String getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
				convertView=((ActionBarActivity)c).getLayoutInflater().inflate(layoutId, null);
			((TextView)convertView.findViewById(textItemId)).setText(getItem(position));
			return convertView;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isEmpty() {
			return getCount() == 0;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if(convertView==null)
				convertView=((ActionBarActivity)c).getLayoutInflater().inflate(layoutId, null);
			((TextView)convertView.findViewById(textItemId)).setText(getItem(position));
			return convertView;
		}
		
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if(itemPosition==currentFragmentId)
			return false;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		t.remove(currentFragment);
		currentFragmentId=itemPosition;
		
		switch (itemPosition) {
		case FRAG_GRIDVIEW:
			currentFragment=new GridViewExampleFragment();
			t.add(R.id.act_main_container_root, currentFragment, fragmentTags[FRAG_GRIDVIEW]);
			break;
		case FRAG_ASYNCTASK:
			currentFragment=new AsyncTaskExampleFragment();
			t.add(R.id.act_main_container_root, currentFragment, fragmentTags[FRAG_ASYNCTASK]);
			break;
		case FRAG_ASYNCQUERY:
			currentFragment=new AsyncQueryExampleFragment();
			t.add(R.id.act_main_container_root, currentFragment, fragmentTags[FRAG_ASYNCQUERY]);
			break;
		case FRAG_CUSTOMVIEW:
			currentFragment=new CustomViewExampleFragment();
			t.add(R.id.act_main_container_root, currentFragment, fragmentTags[FRAG_CUSTOMVIEW]);
			break;
		}
		t.commit();
		return true;
	}
}