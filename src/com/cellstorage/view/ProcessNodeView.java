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

public class ProcessNodeView extends LinearLayout{
	
	ImageView mImageViewFlowUp;
	ImageView mImageViewFlowDown;
	ImageView mImageViewNode;
	LinearLayout mLinearLayoutLeft;
	LinearLayout mLinearLayoutRight;
	
	public static final int NODE_STATUS_DONE = 0;//已经完成的流程
	public static final int NODE_STATUS_DOING = 1;//正在完成的流程
	public static final int NODE_STATUS_WILLDO = 2;//还未完成的流程
	
    public static int LoopNodeCount = 0;//节点计数

    public final static int SHOW_LEFT = 1000;
    public final static int SHOW_RIGHT = 1001;
    
	int mNodeStatus = 0;//节点状态
	int mNodeType = 0;
	int mShowContentStatus = SHOW_RIGHT;

	private final int[] LoopNode = {R.drawable.dot_blue,R.drawable.dot_yellow,R.drawable.dot_red,R.drawable.dot_cyan,R.drawable.dot_purple};
	private Context mContext;
	public ProcessNodeView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public ProcessNodeView(Context context, AttributeSet attrs) {
		super(context,attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public void create(int x_nodeType){
		LayoutParams lpProcessNodeView = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(lpProcessNodeView);
		
		mNodeType = x_nodeType;
		setBackgroundResource(R.color.white);
		LinearLayout rlNode = new LinearLayout(mContext);
		LayoutParams lpNodeParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlNode.setLayoutParams(lpNodeParams);
		rlNode.setOrientation(VERTICAL);
		
		mImageViewNode = new ImageView(mContext);
		LinearLayout.LayoutParams lpIV = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpIV.gravity = Gravity.CENTER_HORIZONTAL;
		mImageViewNode.setLayoutParams(lpIV);

		mImageViewFlowUp = new ImageView(mContext);
		LinearLayout.LayoutParams lpLl = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		mImageViewFlowUp.setLayoutParams(lpLl);
		mImageViewFlowUp.setBackgroundResource(R.drawable.line_gray);
		
		mImageViewFlowDown = new ImageView(mContext);
		mImageViewFlowDown.setLayoutParams(lpLl);
		mImageViewFlowDown.setBackgroundResource(R.drawable.line_gray);
		
		rlNode.addView(mImageViewFlowUp);
		rlNode.addView(mImageViewNode);
		rlNode.addView(mImageViewFlowDown);
		
		mLinearLayoutLeft = new LinearLayout(mContext);
		LinearLayout.LayoutParams lpLiLayoutLeft = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.process_node_textview_width), LayoutParams.WRAP_CONTENT);
		mLinearLayoutLeft.setOrientation(VERTICAL);
		lpLiLayoutLeft.gravity = Gravity.CENTER_VERTICAL;
		mLinearLayoutLeft.setLayoutParams(lpLiLayoutLeft);
		
		mLinearLayoutRight = new LinearLayout(mContext);
		LinearLayout.LayoutParams lpLiLayoutRight = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.process_node_textview_width), LayoutParams.WRAP_CONTENT);
		mLinearLayoutRight.setOrientation(VERTICAL);
		lpLiLayoutRight.gravity = Gravity.CENTER_VERTICAL;
		mLinearLayoutRight.setLayoutParams(lpLiLayoutRight);
		
		if(mNodeStatus == NODE_STATUS_DONE){
			mImageViewNode.setBackgroundResource(LoopNode[LoopNodeCount]);
			LoopNodeCount++;
			if(LoopNodeCount > 4){
				LoopNodeCount = 0;
			}
		}else{
			mImageViewNode.setBackgroundResource(R.drawable.dot_gray);
		}
		addView(mLinearLayoutLeft);
		addView(rlNode);
		addView(mLinearLayoutRight);
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
	public int getmShowContentStatus() {
		return mShowContentStatus;
	}
	/**
	 * @param mShowTextStatus the mShowTextStatus to set
	 */
	public void setmShowContentStatus(int mShowContentStatus) {
		this.mShowContentStatus = mShowContentStatus;
	}

}