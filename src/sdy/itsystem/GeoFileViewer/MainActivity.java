package sdy.itsystem.GeoFileViewer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends FragmentActivity 
{
        
	    /* Tab identifiers */
	    static String TAB_A = "Geo";
	    static String TAB_B = "File";
	    static String TAB_C = "Viewer";
	   
	    TabHost mTabHost;
	   
	    Fragment1 fragment1;
	    Fragment2 fragment2;
	    Fragment3 fragment3;
    
	    private ImageView downloadedImg;
	    private ProgressDialog simpleWaitDialog;
	    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	    //private ProgressDialog mProgressDialog;

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            setContentView(R.layout.main);
	           
	            fragment1 = new Fragment1();
	            fragment2 = new Fragment2();
	            fragment3 = new Fragment3();
	           
	            mTabHost = (TabHost)findViewById(android.R.id.tabhost);
	            mTabHost.setOnTabChangedListener(listener);
	            mTabHost.setup();
	           
	            initializeTab();	

        }

		/*
		 * Initialize the tabs and set views and identifiers for the tabs
		 */
		public void initializeTab() {
		   
		    TabHost.TabSpec spec    =   mTabHost.newTabSpec(TAB_A);
		    mTabHost.setCurrentTab(-3);
		    spec.setContent(new TabHost.TabContentFactory() {
		        public View createTabContent(String tag) {
		            return findViewById(android.R.id.tabcontent);
		        }
		    });
		    spec.setIndicator(createTabView(TAB_A));
		    mTabHost.addTab(spec);
		
		
		    spec                    =   mTabHost.newTabSpec(TAB_B);
		    spec.setContent(new TabHost.TabContentFactory() {
		        public View createTabContent(String tag) {
		            return findViewById(android.R.id.tabcontent);
		        }
		    });
		    spec.setIndicator(createTabView(TAB_B));
		    mTabHost.addTab(spec);
		    
		    spec                    =   mTabHost.newTabSpec(TAB_C);
		    spec.setContent(new TabHost.TabContentFactory() {
		        public View createTabContent(String tag) {
		            return findViewById(android.R.id.tabcontent);
		        }
		    });
		    spec.setIndicator(createTabView(TAB_C));
		    mTabHost.addTab(spec);
		}
		
		    /*
		     * TabChangeListener for changing the tab when one of the tabs is pressed
		     */
		    TabHost.OnTabChangeListener listener    =   new TabHost.OnTabChangeListener() {
		          public void onTabChanged(String tabId) {
		            /*Set current tab..*/
		              if(tabId.equals(TAB_A)){
		                pushFragments(tabId, fragment1);
		              }else if(tabId.equals(TAB_B)){
		                pushFragments(tabId, fragment2);
		              }else if(tabId.equals(TAB_C)){
			            pushFragments(tabId, fragment3);
			          }
		          }

		        };
		       
		    /*
		     * adds the fragment to the FrameLayout
		     */
		    public void pushFragments(String tag, Fragment fragment){
		       
		        FragmentManager   manager         =   getSupportFragmentManager();
		        FragmentTransaction ft            =   manager.beginTransaction();
		       
		        ft.replace(android.R.id.tabcontent, fragment);
		        ft.commit();
		    }		    

		    /*
		     * returns the tab view i.e. the tab icon and text
		     */
		    private View createTabView(final String text) {
		        View view = LayoutInflater.from(this).inflate(R.layout.tabs_bg, null);
		        //ImageView imageView =   (ImageView) view.findViewById(R.id.tab_icon);
		        //imageView.setImageDrawable(getResources().getDrawable(id));
		        ((TextView) view.findViewById(R.id.tabsText)).setText(text);
		        return view;
		    }
		    
    	// create settings menu from options_menu.xml
    	@Override
    	public boolean onCreateOptionsMenu(Menu menu)
    	{
    		MenuInflater inflater = getMenuInflater();
    		inflater.inflate(R.menu.options_menu, menu);
    		// Inflate the menu; this adds items to the action bar if it is present.
            //getMenuInflater().inflate(R.menu.main, menu);
    		return true;
    	}	
    	
    	/**
    	 * This method will be called any time a user selects one of the options
    	 * on the menu. For the implementation, whichever button is clicked is
    	 * mapped onto the relevant activity.
    	 * @param item MenuItem
    	 * @return boolean
    	 */
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item)
    	{
    		int id = item.getItemId();
    		if (id == R.id.preferences) {
    			startActivity(new Intent(this, EditPreferences.class));
				return true;
    		} else if (id == R.id.about) {
    			Context context=MainActivity.this;
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.popup);
		    	TextView txt = (TextView)dialog.findViewById(R.id.about);
		    	txt.setText(getString(R.string.about_msg));
		    	dialog.show();
		    	Button ok=(Button)dialog.findViewById(R.id.ok);
		    	ok.setOnClickListener(new OnClickListener() {
		    	     @Override
		    	     public void onClick(View v) {
		    	        dialog.dismiss();
		    	     }
				});
    		}
			return super.onOptionsItemSelected(item);
    	}
    	
	 public void SaveText(View view){	 
		 
		 try {
			  // open myfilename.txt for writing
			  OutputStreamWriter out = new OutputStreamWriter(openFileOutput("myfile.txt",MODE_APPEND));
			  // write the contents on mySettings to the file
			  EditText ET = (EditText)findViewById(R.id.editText1);
			  String text = ET.getText().toString();
			  out.write(text);
			  out.write('\n');			   
			  // close the file
			  out.close();
			  Toast.makeText(this,"Text Saved !",Toast.LENGTH_LONG).show();
			} catch (java.io.IOException e) {
			  //do something if an IOException occurs.
				Toast.makeText(this,"Sorry Text could't be added",Toast.LENGTH_LONG).show();
			}
				 
	 }
	 
	 public void ViewText (View view){			
		 
	 StringBuilder text = new StringBuilder();	 
	 
	 try {
		    // open the file for reading we have to surround it with a try
		 
		    InputStream instream = openFileInput("myfile.txt");//open the text file for reading
		    
		    // if file the available for reading
		    if (instream != null) {		    	
		    	
		      // prepare the file for reading
		      InputStreamReader inputreader = new InputStreamReader(instream);
		      BufferedReader buffreader = new BufferedReader(inputreader);
		       
		      String line=null;
		      //We initialize a string "line" 
		      
		    while (( line = buffreader.readLine()) != null) {
		    	//buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
		    							
					    	text.append(line);	
							text.append('\n');	//to display the text in text line	   
							    
		      }}}    
				
	 		//now we have to surround it with a catch statement for exceptions
			catch (IOException e) {
				e.printStackTrace();
			}
	 
	 		//now we assign the text readed to the textview
	 		TextView tv = (TextView)findViewById(R.id.textView1);	 			 		
	 		tv.setText(text); 		
	 		
		}   

	 public void Image(View view) {
     	String imgUrl = "http://blog.arduino.cc/wp-content/uploads/2013/07/Arduino_logo_pantone.png";
    	downloadedImg = (ImageView) findViewById(R.id.imageView1);
     	new ImageDownloader().execute(imgUrl);
     }

	 private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
		 
		 @Override
		 protected Bitmap doInBackground(String... param) {
		 // TODO Auto-generated method stub
		 return downloadBitmap(param[0]);
		 }
		
		 @Override
		 protected void onPreExecute() {
		 Log.i("Async-Example", "onPreExecute Called");
		 simpleWaitDialog = ProgressDialog.show(MainActivity.this,"Wait", "Downloading Image");
		
		 }
		
		 @Override
		 protected void onPostExecute(Bitmap result) {
		 Log.i("Async-Example", "onPostExecute Called");
		 downloadedImg.setImageBitmap(result);
		 simpleWaitDialog.dismiss();
		
		 }
		
		 public Bitmap downloadBitmap(String url) {
		 // initilize the default HTTP client object
		 final DefaultHttpClient client = new DefaultHttpClient();
		
		 //forming a HttoGet request
		 final HttpGet getRequest = new HttpGet(url);
		 try {
		
		 HttpResponse response = client.execute(getRequest);
		
		 //check 200 OK for success
		 final int statusCode = response.getStatusLine().getStatusCode();
		
		 if (statusCode != HttpStatus.SC_OK) {
		 Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
		 return null;
		
		 }
		
		 final HttpEntity entity = response.getEntity();
		 if (entity != null) {
		 InputStream inputStream = null;
		 try {
		 // getting contents from the stream
		 inputStream = entity.getContent();
		
		 // decoding stream data back into image Bitmap that android understands
		 final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		
		 return bitmap;
		 } finally {
		 if (inputStream != null) {
		 inputStream.close();
		 }
		 entity.consumeContent();
		 }
		 }
		 } catch (Exception e) {
		 // You Could provide a more explicit error message for IOException
		 getRequest.abort();
		 Log.e("ImageDownloader", "Something went wrong while retrieving bitmap from " + url + e.toString());
		 }
		
		 return null;
		 }
	 }

	 public void Audio(View view) {
		 String audUrl = "http://ia600803.us.archive.org/35/items/MozziSoundSynthesisLibraryForArduinoExampleRecordings_814/_12_Samples.mp3";
		 //String file_name = "/test2.mp3";
		 //String path= Environment.getExternalStorageDirectory().getPath() + file_name;
		 String Path = Environment.getExternalStorageDirectory() + "/test2.mp3";
		 
		 new FileDownloader().execute(audUrl, Path);		 
	     }
	 
	 public void Pdf(View view) {
		 String pdfUrl = "http://quarknet.fnal.gov/fnal-uc/quarknet-summer-research/QNET2011/project_files/teacher_files/Getting_Started_with_Arduino.pdf";
		 //String file_name = "/test3.pdf";
		 String Path = Environment.getExternalStorageDirectory() + "/test3.pdf";
		 new FileDownloader().execute(pdfUrl, Path);		 
	     }
	 
	 public void Text(View view) {
		 String txtUrl = "http://www.gnu.org/licenses/gpl.txt";
		 //String file_name = "/test4.txt";
		 String Path = Environment.getExternalStorageDirectory() + "/test4.txt";
		 new FileDownloader().execute(txtUrl, Path);		 
	     }	  
	 
	    @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
			case DIALOG_DOWNLOAD_PROGRESS:
				simpleWaitDialog = new ProgressDialog(this);
				simpleWaitDialog.setMessage("Downloading file..");
				simpleWaitDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				simpleWaitDialog.setCancelable(false);
				simpleWaitDialog.show();
				return simpleWaitDialog;
			default:
				return null;
	        }
	    }

	private class FileDownloader extends AsyncTask<String, String, String> {
	   
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;			

		try {

		URL url = new URL(aurl[0]);
		File fpath = new File(aurl[1]);
		URLConnection conexion = url.openConnection();
		conexion.connect();

		int lenghtOfFile = conexion.getContentLength();
		Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

		InputStream input = new BufferedInputStream(url.openStream());
		;
		OutputStream output = new FileOutputStream(fpath);

		byte data[] = new byte[1024];

		long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				publishProgress(""+(int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
			
			Intent i = new Intent(android.content.Intent.ACTION_VIEW);
			File file = new File(fpath.getAbsolutePath());
			String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
			String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
			i.setDataAndType(Uri.fromFile(file),mimetype);
			startActivity(i);
			
		} catch (Exception e) {}
		return null;	
		
		}
		protected void onProgressUpdate(String... progress) {
			 Log.d("ANDRO_ASYNC",progress[0]);
			 simpleWaitDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
		
	}
			 
}
