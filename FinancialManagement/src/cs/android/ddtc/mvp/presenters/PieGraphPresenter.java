package cs.android.ddtc.mvp.presenters;

import cs.android.ddtc.mvp.Iviews.IPieGraphView;
import cs.android.ddtc.mvp.model.Grapher;

public class PieGraphPresenter {
	
	private final IPieGraphView myView;
	private final Grapher myModel;
	
	public PieGraphPresenter(IPieGraphView view, Grapher model){
		myView = view;
		myModel = model;
		myView.setGraph(myModel.makeLineGraphAllSetting());
	}
	
	public void onXClick(){
		myView.advance();
	}
	

}
