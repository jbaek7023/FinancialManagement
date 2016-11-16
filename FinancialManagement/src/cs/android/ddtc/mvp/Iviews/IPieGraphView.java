package cs.android.ddtc.mvp.Iviews;

import cs.android.ddtc.mvp.graph.vo.circlegraph.CircleGraphVO;

public interface IPieGraphView {
	
	void advance();
	
	void setGraph(CircleGraphVO myGraph);

}
