package com.cellstorage.view;




import com.example.cellstorage.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PreparationProcessNodeView extends ProcessNodeView{
	
	private TextView mTextViewPreparationDate;
	private TextView mTextViewPreparationTime;
	private TextView mTextViewPreparationName;
	private View mViewPreparationTime;
	private View mViewPreparationName;
	private LinearLayout mLinearLayoutPreparationDate;
	
	private String mStringPreparationDate = "";
	private String mStringPreparationTime = "";
	private String mStringPreparationName = "";
	
	private Context mContext;
	public PreparationProcessNodeView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public PreparationProcessNodeView(Context context, AttributeSet attrs) {
		super(context,attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public void create(int x_nodeType){
		super.create(x_nodeType);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mViewPreparationName = inflater.inflate(R.layout.view_process_preparation_name, null);
		mViewPreparationTime = inflater.inflate(R.layout.view_process_preparation_time, null);
		
		mLinearLayoutPreparationDate = (LinearLayout) mViewPreparationTime.findViewById(R.id.LinearLayoutPreparationDate);
		mTextViewPreparationName = (TextView) mViewPreparationName.findViewById(R.id.TextViewPreparationName);
		mTextViewPreparationDate = (TextView) mViewPreparationTime.findViewById(R.id.TextViewDate);
		mTextViewPreparationTime = (TextView) mViewPreparationTime.findViewById(R.id.TextViewTime);
		mImageViewFlowUp.setBackgroundResource(R.drawable.line_preparation_process);
		mImageViewFlowDown.setBackgroundResource(R.drawable.line_preparation_process);
		mTextViewPreparationName.setText(mStringPreparationName);
		mTextViewPreparationDate.setText(mStringPreparationDate);
		mTextViewPreparationTime.setText(mStringPreparationTime);
		mLinearLayoutRight.addView(mTextViewPreparationName);
		mLinearLayoutLeft.addView(mLinearLayoutPreparationDate);
		if(mNodeStatus == NODE_STATUS_DOING){
			mImageViewNode.setBackgroundResource(R.drawable.dot_doing);
		}
		
	}
	public String getmStringPreparationDate() {
		return mStringPreparationDate;
	}
	public void setmStringPreparationDate(String mStringPreparationDate) {
		this.mStringPreparationDate = mStringPreparationDate;
	}
	public String getmStringPreparationTime() {
		return mStringPreparationTime;
	}
	public void setmStringPreparationTime(String mStringPreparationTime) {
		this.mStringPreparationTime = mStringPreparationTime;
	}
	public String getmStringPreparationName() {
		return mStringPreparationName;
	}
	public void setmStringPreparationName(String mStringPreparationName) {
		this.mStringPreparationName = mStringPreparationName;
	}
}