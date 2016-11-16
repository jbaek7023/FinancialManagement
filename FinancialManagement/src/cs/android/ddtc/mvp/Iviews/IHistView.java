package cs.android.ddtc.mvp.Iviews;

import java.util.ArrayList;

import cs.android.ddtc.mvp.model.History;

public interface IHistView {
	
	void advanceToAdd();
	void advance();
	
	void setHistory(ArrayList<History> h);
}
