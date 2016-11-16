package cs.android.ddtc.mvp.support;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.model.Account;

public class AcctListAdapter extends BaseAdapter{
	
	private LayoutInflater inflater = null;
	private ArrayList<Account> acctList = null;
	private ViewHolder viewHolder = null;	
	public AcctListAdapter(Context c, ArrayList<Account> array){
		inflater = LayoutInflater.from(c);
		acctList = array;
	}

	@Override
	public int getCount() {
		return acctList.size();
	}

	@Override
	public Object getItem(int position) {
		return acctList.get(position);
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
			v = inflater.inflate(R.layout.acct_row, null);
			viewHolder.content1 = (TextView) v.findViewById(R.id.listacctname);
			viewHolder.content2 = (TextView) v.findViewById(R.id.listbalance);
			
			v.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		
		viewHolder.content1.setText(acctList.get(position).acct);
		viewHolder.content2.setText(acctList.get(position).strbalance);
		
		return v;
	}
	
    public void setArrayList(ArrayList<Account> arrays) {
    	this.acctList = arrays;
    }
	
    /**
     * 
     * @return infoList
     */
    public ArrayList<Account> getArrayList(){
    	return acctList;
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

    }

}
