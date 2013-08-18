package sdy.itsystem.GeoFileViewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class Fragment3 extends Fragment implements OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
       
        View view = inflater.inflate(R.layout.viewer, container, false);
        
		// after you've done all your manipulation, return your layout to be shown
		return view;        
    }      
  
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}      	
    
}
