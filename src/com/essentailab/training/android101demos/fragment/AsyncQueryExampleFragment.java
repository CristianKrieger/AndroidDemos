package com.essentailab.training.android101demos.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.essentailab.training.android101demos.R;
import com.essentailab.training.android101demos.adapter.CustomListAdapter;
import com.essentailab.training.android101demos.entities.ListElementCookie;

public class AsyncQueryExampleFragment extends SherlockFragment{
	private ListView lv;
	private ProgressBar pb;
	private TextView pb_txt;
	private View pb_container;
	private LayoutInflater inflater;
//	private ThumbAsyncTask task;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater=inflater;
		View v = inflater.inflate(R.layout.fragment_asynctask, container, false);
		lv = (ListView) v.findViewById(R.id.frag_asynctask_lv);
		pb = (ProgressBar) v.findViewById(R.id.frag_asynctask_pb);
		pb_txt = (TextView) v.findViewById(R.id.frag_asynctask_txt);
		pb_container = v.findViewById(R.id.frag_asynctask_container_pb);
		
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		String[] projection = new String[] {
				ContactsContract.RawContacts.ACCOUNT_NAME,
				ContactsContract.RawContacts.ACCOUNT_TYPE,
				ContactsContract.RawContacts.CONTACT_ID,
		};
		
		SimpleAsyncQueryHandler mQHandler = new SimpleAsyncQueryHandler(getSherlockActivity());
		mQHandler.startQuery(SimpleAsyncQueryHandler.TOKEN_RAWCONTACTS,
				null, uri, projection, null, null, null);
		
		uri = ContactsContract.Data.CONTENT_URI;
		projection = new String[] {
				ContactsContract.Data.CONTACT_ID,
				ContactsContract.Data.DISPLAY_NAME//,
//				ContactsContract.Data.
		};
		
		mQHandler.startQuery(SimpleAsyncQueryHandler.TOKEN_CONTACTS,
				null, uri, projection, null, null, null);
		
		return v;
	}
	
	@Override
	public void onStop() {
//		task.cancel(true);
		super.onStop();
	}
	
	private void inflateList(final ArrayList<ListElementCookie> data){
		pb_container.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);
		
		CustomListAdapter adapter = new CustomListAdapter(inflater,
				data,
				R.layout.row_listview, R.id.listview_txt_top,
				R.id.listview_txt_bottom,
				R.id.listview_img);
		lv.setAdapter(adapter);
		
		OnItemClickListener clickHandler = new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        Toast.makeText(getSherlockActivity(), "You pressed: "
		        		+data.get(position).getTitle(), Toast.LENGTH_LONG).show();
		    }
		};
		lv.setOnItemClickListener(clickHandler);
	}
	
	private class SimpleAsyncQueryHandler extends AsyncQueryHandler {

		public static final int TOKEN_RAWCONTACTS = 0;
		public static final int TOKEN_CONTACTS = 1;
		private Context c;
		private boolean calendarAdquisitionDone;
	    private boolean queryError;
		
	    private ArrayList<Integer> contactsIds;
	    ArrayList<ListElementCookie> data;
	    
		public SimpleAsyncQueryHandler(Context c) {
			super(c.getContentResolver());
			this.c=c;
		}
		
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor){
			Activity activity = (Activity)c;
			switch(token){
			case TOKEN_RAWCONTACTS:
				if (activity != null && !activity.isFinishing()) {
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							contactsIds = new ArrayList<Integer>();
					        do {
					            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME));
					            String type = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE));
					            int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
					            Toast.makeText(c, name+" "+type+" "+Integer.toString(id), Toast.LENGTH_LONG).show();
					            contactsIds.add(id);
					        }while (cursor.moveToNext());
					        calendarAdquisitionDone=true;
					    }else
					    	queryError=true;
					}else{
						Toast.makeText(c, "Provider Error", Toast.LENGTH_LONG).show();
						queryError=true;
					}
				}else
					queryError=true;
				break;
			case TOKEN_CONTACTS:
				while((!calendarAdquisitionDone)&&(!queryError));
				
				if (activity != null && !activity.isFinishing() && !queryError) {
					if (cursor != null) {
						if  (cursor.moveToFirst()) {
							data = new ArrayList<ListElementCookie>();
							do {
					            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
					            int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
					            data.add(new ListElementCookie(id,
					            		c.getResources().getDrawable(R.drawable.ic_contact_picture),
					            		name,
					            		"Contact Id: "+Integer.toString(id)));
					            //Toast.makeText(c, name+" "+" "+Integer.toString(id), Toast.LENGTH_LONG).show();
					            
					        }while (cursor.moveToNext());
					        calendarAdquisitionDone=true;
					    }else
					    	queryError=true;
					}else{
						Toast.makeText(c, "Provider Error", Toast.LENGTH_LONG).show();
						queryError=true;
					}
				}else
					queryError=true;
				if(!queryError)
					inflateList(data);
				break;
			}
		}
		
	}
}
