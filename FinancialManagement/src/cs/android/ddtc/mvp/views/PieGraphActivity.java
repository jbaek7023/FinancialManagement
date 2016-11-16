package cs.android.ddtc.mvp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.IPieGraphView;
import cs.android.ddtc.mvp.graph.graphview.CircleGraphView;
import cs.android.ddtc.mvp.graph.vo.circlegraph.CircleGraphVO;
import cs.android.ddtc.mvp.model.Grapher;
import cs.android.ddtc.mvp.presenters.PieGraphPresenter;

public class PieGraphActivity extends Activity implements IPieGraphView{
	
	private PieGraphPresenter myPresenter;
	private ViewGroup layoutGraphView;
	private double food, shopping, gas, utility, savings, salary, misc, grocery, income, expense;
	private int btnId;
	private CircleGraphVO graph;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_graph);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		food = extras.getDouble("food");
		shopping = extras.getDouble("shopping");
		grocery = extras.getDouble("grocery");
		gas = extras.getDouble("gas");
		utility = extras.getDouble("utility");
		income = extras.getDouble("income");
		expense = extras.getDouble("expense");
		savings = extras.getDouble("savings");
		salary = extras.getDouble("salary");
		misc = extras.getDouble("misc");
		btnId = extras.getInt("btnId");
		layoutGraphView = (ViewGroup) findViewById(R.id.piegraph);
		
		switch(btnId){
		case R.id.spendingchart:
			myPresenter = new PieGraphPresenter(this, new Grapher(food, shopping, grocery, gas, utility, btnId));
			break;
			
		case R.id.cashchart:
			myPresenter = new PieGraphPresenter(this, new Grapher(income, expense, btnId));
			break;
			
		case R.id.incomechart:
			myPresenter = new PieGraphPresenter(this, new Grapher(savings, salary, misc, btnId));
			break;
		default:
			break;
		}
		
		layoutGraphView.addView(new CircleGraphView(this, graph));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pie_graph, menu);
		return true;
	}
	
	public void OnXClick(View v){
		myPresenter.onXClick();
	}
	
	@Override
	public void advance() {
		finish();
		
	}

	@Override
	public void setGraph(CircleGraphVO myGraph) {
		graph = myGraph;
		
	}



}
