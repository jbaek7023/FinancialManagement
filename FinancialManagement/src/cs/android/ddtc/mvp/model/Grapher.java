package cs.android.ddtc.mvp.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.graph.animation.GraphAnimation;
import cs.android.ddtc.mvp.graph.vo.GraphNameBox;
import cs.android.ddtc.mvp.graph.vo.circlegraph.CircleGraph;
import cs.android.ddtc.mvp.graph.vo.circlegraph.CircleGraphVO;

public class Grapher {

	private double food, shopping, gas, utility, savings, salary, misc, grocery, income, expense;
	private int btnId;

	public Grapher(double a, double b, double c, double d, double e, int f) {
		food = a;
		shopping = b;
		grocery = c;
		gas = d;
		utility = e;
		btnId = f;
	}
	
	
	public Grapher(double a, double b, double c, int f) {
		savings = a;
		salary = b;
		misc = c;
		btnId = f;
	}
	
	public Grapher(double i, double e, int f) {
		income = i;
		expense = e;
		btnId = f;
	}
	
	

	public CircleGraphVO makeLineGraphAllSetting() {
		// BASIC LAYOUT SETTING
		// padding
		int paddingBottom = CircleGraphVO.DEFAULT_PADDING;
		int paddingTop = CircleGraphVO.DEFAULT_PADDING;
		int paddingLeft = CircleGraphVO.DEFAULT_PADDING;
		int paddingRight = CircleGraphVO.DEFAULT_PADDING;

		// graph margin
		int marginTop = CircleGraphVO.DEFAULT_MARGIN_TOP;
		int marginRight = CircleGraphVO.DEFAULT_MARGIN_RIGHT;

		// radius setting
		int radius = 130;

		CircleGraphVO vo = new CircleGraphVO(paddingBottom, paddingTop,
				paddingLeft, paddingRight, marginTop, marginRight, radius,
				make(btnId));

		// circle Line
		vo.setLineColor(Color.WHITE);

		// set text setting
		vo.setTextColor(Color.WHITE);
		vo.setTextSize(20);

		// set circle center move X ,Y
		vo.setCenterX(0);
		vo.setCenterY(0);

		// set animation
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION,
				2000));
		// set graph name box

		vo.setPieChart(true);

		GraphNameBox graphNameBox = new GraphNameBox();

		// nameBox
		graphNameBox.setNameboxMarginTop(25);
		graphNameBox.setNameboxMarginRight(25);

		vo.setGraphNameBox(graphNameBox);

		return vo;
	}

	public List<CircleGraph> make(int a) {
		List<CircleGraph> arrGraph = new ArrayList<CircleGraph>();
		switch (a) {
		case R.id.spendingchart:
			arrGraph.add(new CircleGraph("Food", Color.parseColor("#3366CC"),
					(float) food));
			arrGraph.add(new CircleGraph("Shopping", Color
					.parseColor("#DC3912"), (float) shopping));
			arrGraph.add(new CircleGraph("Grocery",
					Color.parseColor("#FF9900"), (float) grocery));
			arrGraph.add(new CircleGraph("Gas", Color.parseColor("#109618"),
					(float) gas));
			arrGraph.add(new CircleGraph("Utility Bill", Color
					.parseColor("#990099"), (float) utility));
			break;
		case R.id.cashchart:
			arrGraph.add(new CircleGraph("Income", Color.parseColor("#3366CC"),
					(float) income));
			arrGraph.add(new CircleGraph("Expense", Color
					.parseColor("#DC3912"), (float) expense));
			break;
			
		case R.id.incomechart:
			arrGraph.add(new CircleGraph("Savings", Color.parseColor("#3366CC"),
					(float) savings));
			arrGraph.add(new CircleGraph("Salary", Color
					.parseColor("#DC3912"), (float) salary));
			arrGraph.add(new CircleGraph("Misc",
					Color.parseColor("#FF9900"), (float) misc));
			break;

		default:
			break;

		}

		return arrGraph;

	}
}
