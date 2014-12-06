package com.cellstorage.view;



import com.example.cellstorage.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
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
	
    private final int ID_IMAGENODE = 1;//节点控件的ID
	
    private final static int SHOW_LEFT = 1000;
    private final static int SHOW_RIGHT = 1001;
    
	private int mNodeStatus = 0;//节点状态
	private int mShowTextStatus = SHOW_RIGHT;
	
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
		
		setBackgroundResource(R.color.white);
		setOrientation(LinearLayout.VERTICAL);
		
		mImageViewFlow = new ImageView(context);
		LinearLayout.LayoutParams lpLl = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER;
		mImageViewFlow.setLayoutParams(lpLl);
		mImageViewFlow.setBackgroundResource(R.drawable.flowarrow);
		addView(mImageViewFlow);
		
		RelativeLayout rlNode = new RelativeLayout(context);
		LayoutParams lpNodeParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rlNode.setLayoutParams(lpNodeParams);
		

		
		mImageViewNode = new ImageView(context);
		RelativeLayout.LayoutParams lpIV = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpIV.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
		mImageViewNode.setId(ID_IMAGENODE);
		mImageViewNode.setLayoutParams(lpIV);
		mImageViewNode.setBackgroundResource(NodeStatus[mNodeStatus]);
	
		mTextViewLeft = new TextView(context);
		RelativeLayout.LayoutParams lpTXLeft = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpTXLeft.rightMargin = 10;
		lpTXLeft.addRule(RelativeLayout.LEFT_OF,ID_IMAGENODE);
		mTextViewLeft.setLayoutParams(lpTXLeft);
		
		mTextViewRight = new TextView(context);
		RelativeLayout.LayoutParams lpTXRight = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpTXRight.leftMargin = 10;
		lpTXRight.addRule(RelativeLayout.RIGHT_OF,ID_IMAGENODE);
		mTextViewRight.setLayoutParams(lpTXRight);
		
		if(getmShowTextStatus() == SHOW_LEFT){
			mTextViewRight.setVisibility(View.GONE);
		}else if(getmShowTextStatus() == SHOW_RIGHT){
			mTextViewLeft.setVisibility(View.GONE);
		}
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
	/**
	 * @return the mShowTextStatus
	 */
	public int getmShowTextStatus() {
		return mShowTextStatus;
	}
	/**
	 * @param mShowTextStatus the mShowTextStatus to set
	 */
	public void setmShowTextStatus(int mShowTextStatus) {
		this.mShowTextStatus = mShowTextStatus;
	}
	
}