package com.cellstorage.view;



import com.example.cellstorage.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CellProcessNodeView extends LinearLayout{
	
	private ImageView mImageViewFlow;
	private ImageView mImageViewNode;
	private TextView mTextViewLeft;
	private TextView mTextViewRight;
	
	private final int [] NodeStatus = {R.drawable.done,R.drawable.doing,R.drawable.willdo};
	private final int NODE_STATUS_DONE = 0;//已经完成的流程
	private final int NODE_STATUS_DOING = 1;//正在完成的流程
	private final int NODE_STATUS_WILLDO = 2;//还未完成的流程
	
	private int mNodeStatus = 0;//节点状态
	
	
	public CellProcessNodeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	public CellProcessNodeView(Context context, AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	private void init(final Context context){
		mImageViewFlow = new ImageView(context);
		LayoutParams lpLl = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER;
		mImageViewFlow.setLayoutParams(lpLl);
		mImageViewFlow.setBackgroundResource(NodeStatus[mNodeStatus]);
		addView(mImageViewFlow);
		
		RelativeLayout rlNode = new RelativeLayout(context);
		LayoutParams lpNodeParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rlNode.setLayoutParams(lpNodeParams);
		
		mTextViewLeft = new TextView(context);
		LayoutParams lpTXLeft = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpTXLeft.rightMargin = 10;
		mTextViewLeft.setLayoutParams(lpTXLeft);
		
		mTextViewRight = new TextView(context);
		LayoutParams lpTXRight = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpTXRight.leftMargin = 10;
		mTextViewRight.setLayoutParams(lpTXRight);
		
		mImageViewNode = new ImageView(context);
		LayoutParams lpIV = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpIV.gravity = Gravity.CENTER;
		mImageViewNode.setLayoutParams(lpIV);
		
		rlNode.addView(mTextViewLeft);
		rlNode.addView(mTextViewRight);
		rlNode.addView(mImageViewNode);
		
		addView(rlNode);
		
	}
	public int getmNodeStatus() {
		return mNodeStatus;
	}
	public void setmNodeStatus(int mNodeStatus) {
		this.mNodeStatus = mNodeStatus;
	}
	
}