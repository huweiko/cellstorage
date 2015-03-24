package com.cellstorage.view;



import com.example.cellstorage.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CellProcessNodeView extends ProcessNodeView{
	
	private TextView mTextViewNodeName;
	private TextView mTextViewNodeTime;
	
	private String mNodeTextContent = "";
	private String mNodeTimeContent = "";
	
	private static OnClickItemProcessNode mOnClickItemProcessNode;
	private final int[] LoopNodeColor = {R.drawable.sample_process_button_blue,R.drawable.sample_process_button_yellow,R.drawable.sample_process_button_red,R.drawable.sample_process_button_cyan,R.drawable.sample_process_button_purple};
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
		super.create(x_nodeType);
		
		mTextViewNodeName = new TextView(mContext);
		RelativeLayout.LayoutParams lpTXLeft = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.process_node_textview_height));
		mTextViewNodeName.setLayoutParams(lpTXLeft);
		mTextViewNodeName.setTextColor(mContext.getResources().getColor(R.color.white));
		mTextViewNodeName.setText(getmNodeTextContent());
		mTextViewNodeName.setGravity(Gravity.CENTER);
		
		mTextViewNodeTime = new TextView(mContext);
		RelativeLayout.LayoutParams lpTXRight = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mTextViewNodeTime.setLayoutParams(lpTXRight);
		mTextViewNodeTime.setText(getmNodeTimeContent());
		mTextViewNodeTime.setTextColor(mContext.getResources().getColor(R.color.gray));
		mTextViewNodeTime.setTextSize(12);
		mTextViewNodeTime.setGravity(Gravity.CENTER);
		
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
		
		if(getmShowContentStatus() == SHOW_LEFT){
			mLinearLayoutLeft.addView(mTextViewNodeName);
			mLinearLayoutLeft.addView(mTextViewNodeTime);
			mLinearLayoutRight.setVisibility(View.INVISIBLE);
			mLinearLayoutLeft.setPadding(0, 0, 10, 0);
		}else if(getmShowContentStatus() == SHOW_RIGHT){
			mLinearLayoutRight.addView(mTextViewNodeName);
			mLinearLayoutRight.addView(mTextViewNodeTime);
			mLinearLayoutRight.setPadding(10, 0, 0, 0);
			mLinearLayoutLeft.setVisibility(View.INVISIBLE);
		}
		if(mNodeStatus == NODE_STATUS_DONE){
			mTextViewNodeName.setBackgroundResource(LoopNodeColor[LoopNodeCount]);
		}else{
			mLinearLayoutLeft.setClickable(false);
			mLinearLayoutRight.setClickable(false);
			mTextViewNodeName.setTextColor(getResources().getColor(R.color.gray));
		}
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
	public static OnClickItemProcessNode getmOnClickItemProcessNode() {
		return mOnClickItemProcessNode;
	}
	public static void setmOnClickItemProcessNode(
			OnClickItemProcessNode OnClickItemProcessNode) {
		mOnClickItemProcessNode = OnClickItemProcessNode;
	}
}