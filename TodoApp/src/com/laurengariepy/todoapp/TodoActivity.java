package com.laurengariepy.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoActivity extends Activity {

	public static final int EDIT_REQUEST = 0;
	
	private ArrayList<String>    toDoItems;
	private ArrayAdapter<String> aToDoAdapter;
	private ListView     	     lvItems;
	private EditText		     etNewItem;
	private Button			     btnAdd; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		lvItems   = (ListView) findViewById(R.id.lvItems);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		btnAdd    = (Button)   findViewById(R.id.btnAdd);
		
		readItems();
		
		aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
		lvItems.setAdapter(aToDoAdapter);
		
		setupListViewListener();
		setupButtonListener();
	}

	private void setupListViewListener() {
		lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra(EditItemActivity.EXTRA_POSITION, position); 
				
				TextView clickedItem = (TextView) view;
				i.putExtra(EditItemActivity.EXTRA_TEXT, clickedItem.getText().toString());
				
				startActivityForResult(i, EDIT_REQUEST); 
			}
		});
		
		lvItems.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				toDoItems.remove(position);
				aToDoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
	}
	
	private void setupButtonListener() {
		btnAdd.setOnClickListener( new View.OnClickListener() {
			@Override 
			public void onClick(View v) {
				addToDoItem(v);
				writeItems();
			}
		});
	}
	
	private void addToDoItem(View v) {
		String entry = etNewItem.getText().toString();
		toDoItems.add(entry);
		aToDoAdapter.notifyDataSetChanged();
		etNewItem.setText("");
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File toDoFile = new File(filesDir, "todo.txt");
		try {
			toDoItems = new ArrayList<String>(FileUtils.readLines(toDoFile));
		} catch (IOException e) {
			toDoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems() {
		File filesDir = getFilesDir();
		File toDoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(toDoFile, toDoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST) {
			String editedText = data.getStringExtra(EditItemActivity.EXTRA_EDITED_TEXT);
			int position = data.getIntExtra(EditItemActivity.EXTRA_EDITED_POSITION, 0);  
			toDoItems.set(position, editedText); 
			aToDoAdapter.notifyDataSetChanged();
			writeItems();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

}
