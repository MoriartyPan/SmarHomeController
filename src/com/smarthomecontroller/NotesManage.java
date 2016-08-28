package com.smarthomecontroller;

import com.database.NotesDAO;
import com.model.Notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotesManage extends Activity{
	EditText NotesEdit1;
	TextView TimeView;
	Button btnNotesEdit,btnNotesCancel,btnNotesDelete;
	String strid;
	NotesDAO notesdao = new NotesDAO(NotesManage.this);
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notesmanage);
		NotesEdit1 = (EditText) findViewById(R.id.NotesEdit1);
		TimeView = (TextView) findViewById(R.id.TimeView);
		btnNotesEdit = (Button) findViewById(R.id.btnNotesEdit);
		btnNotesCancel = (Button) findViewById(R.id.btnNotesCancel);
		btnNotesDelete = (Button) findViewById(R.id.btnNotesDelete);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strid = bundle.getString(MainActivityTab2.FLAG);
		Notes notes = notesdao.find(Integer.parseInt(strid));
		NotesEdit1.setText(notes.getnotes());
		TimeView.setText(notes.gettime());
		btnNotesEdit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(NotesEdit1.getText().toString().equals(""))
				{
					Toast.makeText(NotesManage.this, "如果留言改为空，请点击删除按钮！",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					Notes notes = new Notes();
					notes.setnotesid(Integer.parseInt(strid));
					notes.setnotes(NotesEdit1.getText().toString());
					notesdao.update(notes);
					Toast.makeText(NotesManage.this, "留言修改成功！",
							Toast.LENGTH_SHORT).show();
					Intent intent=new Intent();
					setResult(1, intent);
					finish();
				}
			}
		});
		btnNotesCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btnNotesDelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notesdao.delete(Integer.parseInt(strid));
				Toast.makeText(NotesManage.this, "留言删除成功！", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
			}
		});
	}
}
