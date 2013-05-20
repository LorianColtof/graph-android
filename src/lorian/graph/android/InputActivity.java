package lorian.graph.android;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import lorian.graph.android.opengl.GraphRenderer;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class InputActivity extends Activity  {

	private ListView list;
	private InputAdapter inputAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		setupActionBar();
		
		list = (ListView) findViewById(R.id.input_list);
		list.setItemsCanFocus(true);
		inputAdapter = new InputAdapter();
		list.setAdapter(inputAdapter);
	
	}

	@SuppressLint("NewApi")
	private void setupActionBar() {
		if (android.os.Build.VERSION.SDK_INT >= 11)
			getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public void finish()
	{
		/*
		int i=0;
		for(ListItem l: inputAdapter.items)
		{
			GraphActivity.itemTexts[i] = l.caption;
			i++;
		}
		*/
		super.finish();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class InputAdapter extends BaseAdapter 
	{

		private LayoutInflater inflater;
		public List<ListItem> items = new ArrayList<ListItem>();
		private boolean colorPickerOn = false;
		public InputAdapter()
		{
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for(int i=0;i<GraphActivity.MaxFunctions;i++)
			{
				ListItem listItem = new ListItem();
				listItem.caption = GraphActivity.itemTexts[i];
				items.add(listItem);
			}
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return items.size();
		}
		
		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override 
		public long getItemId(int position)
		{ 
			return position;
		}
		
		@Override 
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null)
			{
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.input_item, null);
				holder.caption = (EditText) convertView.findViewById(R.id.input_item_caption);
		 		final TextView label = (TextView) convertView.findViewById(R.id.input_item_label);
				if(position+1 < 10)
					label.setText(String.format(getResources().getString(R.string.y_equals_format), position+1) + "  ");
				else
					label.setText(String.format(getResources().getString(R.string.y_equals_format), position+1));
				final int color = GraphActivity.functionColors[position].toAndroidRGB();
				label.setBackgroundColor(color);
				label.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if(!colorPickerOn)
						{
							showColorPicker(color, label, position);
							return true;
						}
						else
							return false;
						
					}
				});
				
				convertView.setTag(holder);
						
			}
			else 
			{
				 holder = (ViewHolder) convertView.getTag();
            }
					holder.caption.setText(items.get(position).caption);
				holder.caption.setId(position);
				 
	            //we need to update adapter once we finish with editing
	            holder.caption.setOnFocusChangeListener(new OnFocusChangeListener() {
	                public void onFocusChange(View v, boolean hasFocus) {
	                    if (!hasFocus){
	                       final int position = v.getId();
	                        final EditText Caption = (EditText) v;
	                        items.get(position).caption = Caption.getText().toString();
	                    }
	                }
	            });
	            holder.caption.addTextChangedListener(new TextWatcher() {

					@Override
					public void afterTextChanged(Editable e) {
						GraphActivity.itemTexts[position] = e.toString();
						GraphRenderer.notifyFunctionsChanged();
						
					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						
					}
					@Override
					public void onTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						
						
					}
	            });
	            return convertView;
		}
	
 		void showColorPicker(final int initialColor, final TextView label, final int index)
		{
			
			AmbilWarnaDialog dialog = new AmbilWarnaDialog(InputActivity.this, initialColor, new OnAmbilWarnaListener() {
				
				@Override
				public void onOk(AmbilWarnaDialog dialog, int color) {
					label.setBackgroundColor(color);
					GraphActivity.functionColors[index] = lorian.graph.android.opengl.Color.fromAndroidRGB(color);
					GraphRenderer.notifyFunctionColorsChanged();
					colorPickerOn = false;
				}
				
				@Override
				public void onCancel(AmbilWarnaDialog dialog) {
					colorPickerOn = false;
				}
			});
			
			dialog.show();
			colorPickerOn = true;
		}
		
	}
	  class ViewHolder {
	        EditText caption;
	    }
	 
	    class ListItem {
	        String caption;
	    }

	
}

