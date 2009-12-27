package com.ametro;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import java.util.TreeMap;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ametro.resources.FilePackage;
import com.ametro.resources.GenericResource;

/**
 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
 * photo is displayed as an image. This adapter supports clearing the list
 * of photos and adding a new photo.
 * 
 */
public class MapListAdapter extends BaseExpandableListAdapter {

	private class FileGroupsDictionary {
		
		private static final long serialVersionUID = 1272092376968306671L;

		private class StringArray extends ArrayList<String>{
			private static final long serialVersionUID = -1990141892280002055L;

		}

		private TreeMap<String, StringArray> groups = new TreeMap<String, StringArray>();
		private TreeMap<String, String> pathes = new TreeMap<String, String>();

		
		public void putFile(String groupName, String label, String path){
			StringArray c = groups.get(groupName);
			if(c==null){
				groups.put(groupName, c = new StringArray());
			}
			c.add(label);
			String pathId = groupName+";"+label;
			pathes.put(pathId, path);
		}

		public String getFile(String groupName, String label){
			String pathId = groupName+";"+label;
			return pathes.get(pathId);
		}

		public String[] getGroups(){
			Set<String> keys = groups.keySet();
			return (String[]) keys.toArray(new String[keys.size()]);
		}

		public String[] getLabels(String groupName){
			StringArray c = groups.get(groupName);
			return (String[]) (c).toArray(new String[c.size()]);
		}

		public String[] getPathes(String groupName, String[] labels){
			String[] vals = new String[labels.length];
			for (int i = 0; i < vals.length; i++) {
				vals[i] = getFile(groupName, labels[i]);
			}
			return vals;
		}

		//public fillTables() 	

	}

	private String[] mCountries;
	private String[][] mCities; 
	private String[][] mFiles;

	private Context mContext;
	
	
	public MapListAdapter(Context context){
		mContext = context;
	}

	public void Initialize(String path){
		File dir = new File(path);
		String[] files = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File f, String filename) {
				return filename.endsWith(".pmz");
			}
		});

		FileGroupsDictionary map = new FileGroupsDictionary();

		for(int i = 0; i < files.length; i++){
			String fullFileName = path +'/' + files[i];
			FilePackage pkg = null;
			GenericResource res = null;
			try {
				pkg = new FilePackage(fullFileName);
				res = pkg.getCityGenericResource();
				if(res!=null){
					String country = res.getValue("Options", "Country");
					String city = res.getValue("Options", "RusName");
					if(city==null){
						city = res.getValue("Options", "Name");
					}
					map.putFile(country, city, files[i]);
				}

			} catch (Exception e) {
				// skip this file
			} finally{
				res = null;
				if(pkg!=null){
					try { pkg.close(); } catch (IOException e) {}
				}
				pkg = null;
			}
		}

		mCountries = map.getGroups();
		mCities = new String[mCountries.length][];
		mFiles = new String[mCountries.length][];
		for (int i = 0; i < mCountries.length; i++) {
			String country = mCountries[i];
			mCities[i] = map.getLabels(country);
			mFiles[i] = map.getPathes(country, mCities[i]);
		}

	}

	public Object getChild(int groupPosition, int childPosition) {
		return mCities[groupPosition][childPosition];
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return mCities[groupPosition].length;
	}

	public TextView getGenericView() {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);

		TextView textView = new TextView(mContext);
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(36, 0, 0, 0);
		return textView;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition).toString());
		return textView;
	}

	public Object getGroup(int groupPosition) {
		return mCountries[groupPosition];
	}

	public int getGroupCount() {
		return mCountries.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition).toString());
		return textView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

}
