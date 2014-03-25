package com.laurengariepy.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	public static final String EXTRA_POSITION        = "com.laurengariepy.todoapp.position"; 
	public static final String EXTRA_EDITED_POSITION = "com.laurengariepy.todoapp.editedPosition";
	public static final String EXTRA_TEXT            = "com.laurengariepy.todoapp.text";
	public static final String EXTRA_EDITED_TEXT     = "com.laurengariepy.todoapp.editedText";
	
	private EditText etEditItem;
	private Button   btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
	
		etEditItem = (EditText) findViewById(R.id.etEditItem);
		btnSave    = (Button)   findViewById(R.id.btnSave);
		
		setupEditText();
		
		setupButtonListener();
	}

	private void setupEditText() {
		String text = getIntent().getStringExtra(EXTRA_TEXT); 
		etEditItem.setText(text); 
		int cursorStartPoint = etEditItem.getText().length();
		etEditItem.setSelection(cursorStartPoint);
		etEditItem.requestFocus();
	}
	
	private void setupButtonListener() {
		btnSave.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(); 
				i.putExtra(EXTRA_EDITED_POSITION, getIntent().getIntExtra(EXTRA_POSITION, 0));
				i.putExtra(EXTRA_EDITED_TEXT, etEditItem.getText().toString());
				setResult(RESULT_OK, i); 
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
