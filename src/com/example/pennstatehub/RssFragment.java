package com.example.pennstatehub;

import java.util.List;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RssFragment extends Fragment implements OnItemClickListener {
	
	private ProgressBar progressBar;
	private ListView listView;
	private View view;
	
	public static final String RSS_LINK = "link";
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		if (view==null){
			view=inflater.inflate(R.layout.fragment_layout, container, false);
			progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			listView = (ListView) view.findViewById(R.id.listView);
			listView.setOnItemClickListener(this);
			startService();
		}
		else {
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		return view;
	}
	
	private void startService() {
		String link=getArguments().getString(RSS_LINK);
		Intent intent = new Intent (getActivity(), RssService.class);
		intent.putExtra(RssService.RECEIVER, resultReceiver);
		intent.putExtra("rsslink", link);
		getActivity().startService(intent);
	}
	
	private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			List<RssItem> items =(List<RssItem>)resultData.getSerializable(RssService.ITEMS);
			if(items != null) {
				RssAdapter adapter = new RssAdapter(getActivity(), items);
				listView.setAdapter(adapter);
			}
			else {
				Toast.makeText(getActivity(), "An error has occured with the rss feed.", Toast.LENGTH_LONG).show();
			}
			progressBar.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		};
	};
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RssAdapter adapter = (RssAdapter)parent.getAdapter();
		RssItem item = (RssItem) adapter.getItem(position);
		Uri uri=Uri.parse(item.getLink());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

}
