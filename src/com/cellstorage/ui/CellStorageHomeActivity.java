package com.cellstorage.ui;

import com.cellstorage.AppContext;
import com.cellstorage.view.CellProcessNodeView;
import com.example.cellstorage.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class CellStorageHomeActivity extends Activity{
	private LinearLayout mLinearLayoutText;
	private AppContext appcontext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		appcontext = (AppContext) getApplication();
		setContentView(R.layout.activity_cell_home);
		mLinearLayoutText = (LinearLayout) findViewById(R.id.LinearLayoutTest);
		CellProcessNodeView CellNode_1 = new CellProcessNodeView(appcontext); 
		CellNode_1.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
		CellNode_1.setmShowTextStatus(CellProcessNodeView.SHOW_LEFT);
		CellNode_1.setmTextContent("ºúÍþ1");
		CellNode_1.create();
		CellProcessNodeView CellNode_2 = new CellProcessNodeView(appcontext); 
		CellNode_2.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
		CellNode_2.setmShowTextStatus(CellProcessNodeView.SHOW_RIGHT);
		CellNode_2.setmTextContent("ºúÍþ2");
		CellNode_2.create();
		CellProcessNodeView CellNode_3 = new CellProcessNodeView(appcontext); 
		CellNode_3.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DOING);
		CellNode_3.setmShowTextStatus(CellProcessNodeView.SHOW_LEFT);
		CellNode_3.setmTextContent("ºúÍþ3");
		CellNode_3.create();
		mLinearLayoutText.addView(CellNode_1);
		mLinearLayoutText.addView(CellNode_2);
		mLinearLayoutText.addView(CellNode_3);
	}
}