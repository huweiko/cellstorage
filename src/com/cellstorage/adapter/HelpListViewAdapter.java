package com.cellstorage.adapter;

import java.util.List;

import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserHelpContent;
import com.cellstorage.struct.UserReminder;
import com.example.cellstorage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HelpListViewAdapter extends BaseAdapter {
	
	private Context 					context;//运行上下文
	private List<UserHelpContent> 					listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 
	private OnItemClickClass onItemClickClass;
	private class ListItemView{				//自定义控件集合  
	    private TextView mTextViewHelpName;
	    private TextView mTextViewHelpContent; 
	}  
	
	public HelpListViewAdapter(Context context, List<UserHelpContent> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
	}

	public void setListItems(List<UserHelpContent> data){
		listItems = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.mTextViewHelpName = (TextView)convertView.findViewById(R.id.TextViewHelpName);
			listItemView.mTextViewHelpContent = (TextView)convertView.findViewById(R.id.TextViewHelpContent);

			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();

		}	
		listItemView.mTextViewHelpName.setText(listItems.get(position).getmHelpName());
		listItemView.mTextViewHelpContent.setText(listItems.get(position).getmHelpContent());
		if(listItems.get(position).ismHelpContentShowStatus()){
			listItemView.mTextViewHelpContent.setVisibility(View.VISIBLE);
		}else{
			listItemView.mTextViewHelpContent.setVisibility(View.GONE);
		}
		listItemView.mTextViewHelpName.setOnClickListener(new OnPhotoClick(position));
		return convertView;
	}
	public interface OnItemClickClass{
		public void OnItemClick(View v,int Position);
	}
	public void setOnItemClickClassListenr(OnItemClickClass x_OnItemClickClass){
		onItemClickClass = x_OnItemClickClass;
	}
	
	class OnPhotoClick implements OnClickListener{
		int position;
		
		public OnPhotoClick(int position) {
			this.position=position;
		}
		@Override
		public void onClick(View v) {
			if (listItems!=null && onItemClickClass!=null ) {
				onItemClickClass.OnItemClick(v, position);
			}
		}
	}
}
