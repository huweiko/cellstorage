package com.cellstorage.adapter;

import java.util.List;

import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserReminder;
import com.example.cellstorage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReminderListViewAdapter extends BaseAdapter {
	
	private Context 					context;//运行上下文
	private List<UserReminder> 					listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 

	private class ListItemView{				//自定义控件集合  
	    private TextView mServiceType;
	    private boolean mServiceStatus; 
	}  
	
	public ReminderListViewAdapter(Context context, List<UserReminder> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
	}

	public void setListItems(List<UserReminder> data){
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
			listItemView.mServiceType = (TextView)convertView.findViewById(R.id.TextViewServiceType);

			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		listItemView.mServiceType.setText(listItems.get(position).getMsg_Content());
		if(!listItemView.mServiceStatus){
			listItemView.mServiceType.setBackgroundColor(context.getResources().getColor(R.color.gray));
		}
		return convertView;
	}

}
