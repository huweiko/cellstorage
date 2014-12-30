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
	public static final int NODE_STATUS_DONE = 0;//已经完成的流程
	public static final int NODE_STATUS_DOING = 1;//正在完成的流程
	public static final int NODE_STATUS_WILLDO = 2;//还未完成的流程
	
    private final int ID_IMAGENODE = 1;//节点控件的ID
	
    public final static int SHOW_LEFT = 1000;
    public final static int SHOW_RIGHT = 1001;
    
	private int mNodeStatus = 0;//节点状态
	private int mNodeType = 0;//节点类型（1咨询和预约、2签订合同、3采集准备、4样本采集、5样本运输、6样本交接、7样本制备、8检测报告、9样本入库）
	private int mShowTextStatus = SHOW_RIGHT;
	
	private static OnClickItemProcessNode mOnClickItemProcessNode;
	
	private String mTextContent = "";
	
	private Context mContext;
	public CellProcessNodeView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public CellProcessNodeView(Context context, AttributeSet attrs) {
		super(context,attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public void create(int x_nodeType){
		mNodeType = x_nodeType;
		setBackgroundResource(R.color.white);
		setOrientation(LinearLayout.VERTICAL);
		
		mImageViewFlow = new ImageView(mContext);
		LinearLayout.LayoutParams lpLl = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		mImageViewFlow.setLayoutParams(lpLl);
		mImageViewFlow.setBackgroundResource(R.drawable.flowarrow);
		addView(mImageViewFlow);
		
		RelativeLayout rlNode = new RelativeLayout(mContext);
		LayoutParams lpNodeParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rlNode.setLayoutParams(lpNodeParams);
		

		
		mImageViewNode = new ImageView(mContext);
		RelativeLayout.LayoutParams lpIV = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpIV.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
		mImageViewNode.setId(ID_IMAGENODE);
		mImageViewNode.setLayoutParams(lpIV);
		mImageViewNode.setBackgroundResource(NodeStatus[mNodeStatus]);
	
		mTextViewLeft = new TextView(mContext);
		RelativeLayout.LayoutParams lpTXLeft = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpTXLeft.rightMargin = 10;
		lpTXLeft.addRule(RelativeLayout.LEFT_OF,ID_IMAGENODE);
		lpTXLeft.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		mTextViewLeft.setLayoutParams(lpTXLeft);
		mTextViewLeft.setText(getmTextContent());
		mTextViewLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnClickItemProcessNode.ShowNodeContent(v, mNodeType);
			}
		});
		mTextViewRight = new TextView(mContext);
		RelativeLayout.LayoutParams lpTXRight = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpTXRight.leftMargin = 10;
		lpTXRight.addRule(RelativeLayout.RIGHT_OF,ID_IMAGENODE);
		lpTXRight.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		mTextViewRight.setLayoutParams(lpTXRight);
		mTextViewRight.setText(getmTextContent());
		mTextViewRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnClickItemProcessNode.ShowNodeContent(v, mNodeType);
			}
		});
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
	/**
	 * @return the mTextContent
	 */
	public String getmTextContent() {
		return mTextContent;
	}
	/**
	 * @param mTextContent the mTextContent to set
	 */
	public void setmTextContent(String mTextContent) {
		this.mTextContent = mTextContent;
	}
	public static OnClickItemProcessNode getmOnClickItemProcessNode() {
		return mOnClickItemProcessNode;
	}
	public static void setmOnClickItemProcessNode(
			OnClickItemProcessNode mOnClickItemProcessNode) {
		CellProcessNodeView.mOnClickItemProcessNode = mOnClickItemProcessNode;
	}
	public interface OnClickItemProcessNode{
		public void ShowNodeContent(View v,int NodeType);
	} 
}