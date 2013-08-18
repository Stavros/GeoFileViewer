package sdy.itsystem.GeoFileViewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class Fragment2 extends Fragment implements OnClickListener{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
    	
    	// inflate layout
        View view = inflater.inflate(R.layout.file, container, false);
                               
        return view;
    }    
    
    /*
    private OnClickListener save = new OnClickListener() {
        public void onClick(View view) {        	
        	FileText vt = new FileText(); //This initiates the variable correctly.
       	    vt.SaveText(view);
        }
    };
    
        private OnClickListener load = new OnClickListener() {
            public void onClick(View v) {            	
            	FileText vt = new FileText(); //This initiates the variable correctly.
           	    vt.ViewText(v);
            }
        };
		*/
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}   
		    
}
