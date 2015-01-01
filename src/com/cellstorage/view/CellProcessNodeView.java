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
	private TextView mTextViewNodeName;
	private TextView mTextViewNodeTime;
	private LinearLayout mLinearLayoutLeft;
	private LinearLayout mLinearLayoutRight;
	
	public static final int NODE_STATUS_DONE = 0;//已经完成的流程
	public static final int NODE_STATUS_DOING = 1;//正在完成的流程
	public static final int NODE_STATUS_WILLDO = 2;//还未完成的流程
	
    private final int ID_IMAGENODE = 1;//节点控件的ID
    public static int LoopNodeCount = 0;//节点计数
	
    public final static int SHOW_LEFT = 1000;
    public final static int SHOW_RIGHT = 1001;
    
	private int mNodeStatus = 0;//节点状态
	private int mNodeType = 0;//节点类型（1咨询和预约、2签订合同、3采集准备、4样本采集、5样本运输、6样本交接、7样本制备、8检测报告、9样本入库）
	private int mShowTextStatus = SHOW_RIGHT;
	
	private static OnClickItemProcessNode mOnClickItemProcessNode;
	
	private String mNodeTextContent = "";
	private String mNodeTimeContent = "";
	
	private final int[] LoopNode = {R.drawable.dot_blue,R.drawable.dot_yellow,R.drawable.dot_red,R.drawable.dot_cyan,R.drawable.dot_purple};
	private final int[] LoopNodeColor = {R.color.node_blue,R.color.node_yellow,R.color.node_red,R.color.node_cyan,R.color.node_purple};
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
/*		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER);
		LayoutParams lpppp = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(lpppp);*/
		LinearLayout rlNode = new LinearLayout(mContext);
		LayoutParams lpNodeParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		rlNode.setId(ID_IMAGENODE);
		rlNode.setLayoutParams(lpNodeParams);
		rlNode.setOrientation(VERTICAL);
		
		mImageViewNode = new ImageView(mContext);
		LinearLayout.LayoutParams lpIV = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpIV.gravity = Gravity.CENTER_HORIZONTAL;
		mImageViewNode.setLayoutParams(lpIV);

		mImageViewFlow = new ImageView(mContext);
		LinearLayout.LayoutParams lpLl = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		mImageViewFlow.setLayoutParams(lpLl);
		mImageViewFlow.setBackgroundResource(R.drawable.line_gray);
		
		rlNode.addView(mImageViewNode);
		rlNode.addView(mImageViewFlow);
		
		mLinearLayoutLeft = new LinearLayout(mContext);
		RelativeLayout.LayoutParams lpLiLayoutLeft = new RelativeLayout.LayoutParams(200, 100);
//		lpLiLayoutLeft.addRule(RelativeLayout.LEFT_OF,ID_IMAGENODE);
		mLinearLayoutLeft.setOrientation(VERTICAL);
		mLinearLayoutLeft.setLayoutParams(lpLiLayoutLeft);
		
		mLinearLayoutRight = new LinearLayout(mContext);
		RelativeLayout.LayoutParams lpLiLayoutRight = new RelativeLayout.LayoutParams(200, 100);
//		lpLiLayoutRight.addRule(RelativeLayout.RIGHT_OF,ID_IMAGENODE);
		mLinearLayoutRight.setOrientation(VERTICAL);
		mLinearLayoutRight.setLayoutParams(lpLiLayoutRight);
		
		
		
		mTextViewNodeName = new TextView(mContext);
		RelativeLayout.LayoutParams lpTXLeft = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 50);
		lpTXLeft.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		mTextViewNodeName.setLayoutParams(lpTXLeft);
		mTextViewNodeName.setText(getmNodeTextContent());
		
		
		mTextViewNodeTime = new TextView(mContext);
		RelativeLayout.LayoutParams lpTXRight = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lpTXRight.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		mTextViewNodeTime.setLayoutParams(lpTXRight);
		mTextViewNodeTime.setText(getmNodeTimeContent());
		mTextViewNodeTime.setTextColor(mContext.getResources().getColor(R.color.gray));
		
		mLinearLayoutLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnClickItemProcessNode.ShowNodeContent(v, mNodeType);
			}
		});
		

		
		mLinearLayoutRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnClickItemProcessNode.ShowNodeContent(v, mNodeType);
			}
		});
		
		
		if(getmShowTextStatus() == SHOW_LEFT){
			mLinearLayoutLeft.addView(mTextViewNodeName);
			mLinearLayoutLeft.addView(mTextViewNodeTime);
			mLinearLayoutRight.setVisibility(View.INVISIBLE);
			mTextViewNodeName.setGravity(Gravity.CENTER);
			mTextViewNodeTime.setGravity(Gravity.CENTER);
			mLinearLayoutLeft.setPadding(0, 0, 10, 0);
		}else if(getmShowTextStatus() == SHOW_RIGHT){
			mLinearLayoutRight.addView(mTextViewNodeName);
			mLinearLayoutRight.addView(mTextViewNodeTime);
			mLinearLayoutRight.setPadding(10, 0, 0, 0);
			mLinearLayoutLeft.setVisibility(View.INVISIBLE);
			mTextViewNodeName.setGravity(Gravity.CENTER);
			mTextViewNodeTime.setGravity(Gravity.CENTER);
		}
		if(mNodeStatus == NODE_STATUS_DONE){
			mTextViewNodeName.setTextColor(mContext.getResources().getColor(R.color.white));
			mImageViewNode.setBackgroundResource(LoopNode[LoopNodeCount]);
			mTextViewNodeName.setBackgroundColor(mContext.getResources().getColor(LoopNodeColor[LoopNodeCount]));
			LoopNodeCount++;
		}else{
			mTextViewNodeName.setTextColor(mContext.getResources().getColor(R.color.gray));
			mImageViewNode.setBackgroundResource(R.drawable.dot_gray);
			mTextViewNodeName.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
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
	public int getmShowTextStatus() {
		return mShowTextStatus;
	}
	/**
	 * @param mShowTextStatus the mShowTextStatus to set
	 */
	public void setmShowTextStatus(int mShowTextStatus) {
		this.mShowTextStatus = mShowTextStatus;
	}
	public static OnClickItemProcessNode getmOnClickItemProcessNode() {
		return mOnClickItemProcessNode;
	}
	public static void setmOnClickItemProcessNode(
			OnClickItemProcessNode mOnClickItemProcessNode) {
		CellProcessNodeView.mOnClickItemProcessNode = mOnClickItemProcessNode;
	}
	public String getmNodeTextContent() {
		return mNodeTextContent;
	}
	public void setmNodeTextContent(String mNodeTextContent) {
		this.mNodeTextContent = mNodeTextContent;
	}
	public String getmNodeTimeContent() {
		return mNodeTimeContent;
	}
	public void setmNodeTimeContent(String mNodeTimeContent) {
		this.mNodeTimeContent = mNodeTimeContent;
	}
	public interface OnClickItemProcessNode{
		public void ShowNodeContent(View v,int NodeType);
	} 
}