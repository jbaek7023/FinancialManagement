package cs.android.ddtc.mvp.Iviews;

public interface IIncomeView {
	
	void advance();
	
	void advanceToGraph(double sa, double s, double m, double t);
	
	void advanceToUpdate(double sa, double s, double m, double t);
	
	void showStartDate();
	
	void showEndDate();

}

