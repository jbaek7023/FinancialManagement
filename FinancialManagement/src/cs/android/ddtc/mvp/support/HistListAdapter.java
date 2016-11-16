package cs.android.ddtc.mvp.support;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.model.History;

public class HistListAdapter extends BaseAdapter{
	
	private LayoutInflater inflater = null;
	private ArrayList<History> histList = null;
	private ViewHolder viewHolder = null;
	
	public HistListAdapter(Context c, ArrayList<History> array){
		inflater = LayoutInflater.from(c);
		histList = array;
	}

	@Override
	public int getCount() {
		return histList.size();
	}

	@Override
	public Object getItem(int position) {
		return histList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		View v = convertview;
		
		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.hist_row, null);
			viewHolder.content1 = (TextView) v.findViewById(R.id.histrowdate);
			viewHolder.content2 = (TextView) v.findViewById(R.id.histrowcategory);
			viewHolder.content3 = (TextView) v.findViewById(R.id.histrowamount);
			
			v.setTag(viewHolder);			
		}
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		
		viewHolder.content1.setText(histList.get(position).date);
		viewHolder.content2.setText(histList.get(position).category);
		viewHolder.content3.setText(histList.get(position).strcost);
		
		return v;
	}
	
	public void setArrayList(ArrayList<History> arrays){
		this.histList = arrays;
	}
	
	public ArrayList<History> getArrayList(){
		return histList;
	}
	
    class ViewHolder {
    	/**
    	 * 
    	 */
    	public TextView content1 = null;
    	/**
    	 * 
    	 */
    	public TextView content2 = null;
    	
    	public TextView content3 = null;

    }
	

}
