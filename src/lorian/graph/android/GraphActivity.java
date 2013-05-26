package lorian.graph.android;

import lorian.graph.WindowSettings;
import lorian.graph.android.opengl.*;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class GraphActivity extends Activity {

	
	private GraphSurfaceView gSurfaceView;
	public static final String TAG = "Graph";
	private static ProgressDialog dialog;
	public static final int MaxFunctions = 10;
	public static String[] itemTexts = new String[MaxFunctions];
	public static final Color[] functionColors = { new Color(37, 119, 255), new Color(224,0,0), new Color(211,0,224), new Color(0,158,224), new Color(0,255,90), new Color(221,224,0), new Color(224,84,0),  new Color(37, 119, 255), new Color(224,0,0), new Color(211,0,224) };  
	// NOTE: functionColors.length >= MaxFunctions!
	private static Handler handler;
	private boolean isReady = false;

	
	//@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_graph);
		Log.d(TAG, "GraphActivity created"); 
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		WindowSettingsActivity.windowsettings = new WindowSettings(prefs.getInt("window_settings_xmin", -8), prefs.getInt("window_settings_xmax", 8), prefs.getInt("window_settings_ymin", -8), prefs.getInt("window_settings_ymax", 8), prefs.getBoolean("window_settings_grid", false));
		GraphRenderer.auto_calc_ymin_ymax = prefs.getBoolean("window_settings_auto_calc_ymin_ymax", true);
		
		dialog = new ProgressDialog(GraphActivity.this, R.style.LoadingDialogTheme);
		dialog.setMessage(getResources().getString(R.string.loading));
		dialog.setCancelable(false);
		
		gSurfaceView = new GraphSurfaceView(this);
		
		setContentView(gSurfaceView); 
		gSurfaceView.setRenderAxes(true);
		gSurfaceView.setRenderFunctions(true);
		
		//TODO This is a temporary fix, since an onTouchListener will be used for other things.
		gSurfaceView.setOnTouchListener(new OnTouchListener() {
			
			// For devices without a menu button
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				openOptionsMenu();
				return true;
			}
		});
		handler = new Handler();
		
	
		isReady = true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.graph_menu, menu);
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
	    if (! isReady)
	        menu.getItem(1).setEnabled(false);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_input:
	          startActivity(new Intent(this, InputActivity.class));
	            return true;
	        case R.id.menu_windowsettings:
	        	startActivity(new Intent(this, WindowSettingsActivity.class));
		         
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public static void showLoadingDialog()
	{
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() { // This thread runs in the UI
                    @Override
                    public void run() {
                        dialog.show(); 
                    }
                });
            }
        };
    new Thread(runnable).start();
	}
	public static void hideLoadingDialog()
	{
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() { // This thread runs in the UI
                    @Override
                    public void run() {
                        dialog.hide();
                    }
                });
            }
        };
    new Thread(runnable).start();
	}
	
	public static void changeLoadingDialogText(final String text) 
	{
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() { // This thread runs in the UI
                    @Override
                    public void run() {
                        dialog.setMessage(text);
                    }
                });
            }
        };
    new Thread(runnable).start();
	}
	
	
	/*
	findViewById(R.id.show_input_button).setOnClickListener(new View.OnClickListener()
	{
		@Override
		public void onClick(View v) {
			showSlideMenu();
		}
		
	});
	
	findViewById(R.id.inner_content).setOnTouchListener(new View.OnTouchListener() {
		private float start_x;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	
			 if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
			      start_x=event.getX();
			     // start_y=event.getY();
			    } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
			      if(event.getX()>start_x)
			    	 showSlideMenu();
			   }
			return true;
		}
	});
	*/
	/*
	setOnDragListener(new View.OnDragListener() {
		
		@SuppressLint("NewApi")
		@Override
		public boolean onDrag(View v, DragEvent event) {
			System.out.println("onDrag");
		    switch (event.getAction()) {
			    case DragEvent.ACTION_DRAG_STARTED:
			      System.out.println("ACTION_DRAG_STARTED");
			      break;
			    case DragEvent.ACTION_DRAG_ENTERED:
			      
			      break;
			    case DragEvent.ACTION_DRAG_EXITED:        
			      
			      break;
			    case DragEvent.ACTION_DROP:
			      
			      break;
			    case DragEvent.ACTION_DRAG_ENDED:
			    	System.out.println("ACTION_DRAG_ENDED");
			    	break;
			    default:
			      break;
		    }
		    return true;
		}
	});
	private void showSlideMenu()
	{
		
		int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
		SlideoutActivity.prepare(GraphActivity.this, R.id.inner_content, width);
		startActivity(new Intent(GraphActivity.this,
				InputMenuActivity.class));
		overridePendingTransition(0, 0);
		
	}
	*/

}
