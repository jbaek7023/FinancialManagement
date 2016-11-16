package cs.android.ddtc.mvp.Iviews;

public interface ICashflowView {
	
	void advance();
	
	void advanceToGraph(double i, double e);
	
	void advanceToUpdate(double i, double e, double t);
	
	void showStartDate();
	
	void showEndDate();

}
