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
	
	private Context 					context;//����������
	private List<UserReminder> 					listItems;//���ݼ���
	private LayoutInflater 				listContainer;//��ͼ����
	private int 						itemViewResource;//�Զ�������ͼԴ 

	private class ListItemView{				//�Զ���ؼ�����  
	    private TextView mServiceType;
	    private boolean mServiceStatus; 
	}  
	
	public ReminderListViewAdapter(Context context, List<UserReminder> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//������ͼ����������������
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
			//��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//��ȡ�ؼ�����
			listItemView.mServiceType = (TextView)convertView.findViewById(R.id.TextViewServiceType);

			//���ÿؼ�����convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		listItemView.mServiceType.setText(position+1+"."+listItems.get(position).getMsg_Content());
		listItemView.mServiceType.setBackgroundColor(context.getResources().getColor(R.color.transparent));
		return convertView;
	}

}
