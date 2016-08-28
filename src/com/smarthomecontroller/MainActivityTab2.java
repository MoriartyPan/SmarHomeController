package com.smarthomecontroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

import com.database.IpDAO;
import com.database.NotesDAO;
import com.model.IPInformation;
import com.model.Notes;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityTab2 extends Fragment{
	public static final int REQUSET = 1; 
	ListView showNotes;
	public  static final String FLAG = "id";
	EditText NotesEdit;
	Button btnNotesSave;
	final Calendar c = Calendar.getInstance();		//获取当前系统日期
	private int year,month,day;
	Handler handler = new Handler();
	String msg = "";
	String ip = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		final RunnableThread runnable = new RunnableThread();
		View view = inflater.inflate(R.layout.activity_main_notes, container, false);
		showNotes = (ListView) view.findViewById(R.id.showNotes);
		NotesEdit = (EditText) view.findViewById(R.id.NotesEdit);
		btnNotesSave = (Button) view.findViewById(R.id.btnNotesSave);
		ShowInfo();
		showNotes.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String strInfo = String.valueOf(((TextView) view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));//从信息项中截取id
				Intent intent = new Intent(getActivity(),NotesManage.class);
				intent.putExtra(FLAG, strid);
				startActivityForResult(intent, REQUSET);
			}
		});
		btnNotesSave.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IpDAO ipdao = new IpDAO(getActivity());
				IPInformation ipinformation = ipdao.find(1);
				ip = ipinformation.getip();
				NotesDAO notesdao = new NotesDAO(getActivity());	//创建DevicesDAO对象
				if(NotesEdit.getText().toString().equals(""))
				{
					Toast.makeText(getActivity(), "留言为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					year = c.get(Calendar.YEAR);
					month = c.get(Calendar.MONTH);
					day = c.get(Calendar.DAY_OF_MONTH);
					String t = Integer.toString(year)+"/"+Integer.toString(month)+"/"+Integer.toString(day);
					String strNotes = NotesEdit.getText().toString();
					Notes notes = new Notes(notesdao.getMaxId()+1,strNotes,t);
					notesdao.add(notes);
					ShowInfo();
					NotesEdit.setText("");
					msg = "时间："+t+"\t"+"发布留言："+strNotes;
					new Thread(runnable).start();
				}
			}
		});
		return view;			//暂时
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == MainActivityTab2.REQUSET && resultCode == 1){
			ShowInfo();
		}
	}
	public void ShowInfo(){
		NotesDAO notesdao = new NotesDAO(getActivity());	//创建DevicesDAO对象
		ArrayAdapter<String> arrayAdapter = null;
		String[] strInfos = null;
		List<Notes> listNotes = notesdao.getScrollData(0, (int)notesdao.getCount());
		strInfos = new String[listNotes.size()];
		int m=0;				//定义一个开始标识
		for(Notes notes:listNotes){		// 遍历List泛型
			//将设备相关信息组合成一个字符串，存储到字符串数组的相应位置
			strInfos[m] = notes.getnotesid() + "|"+notes.gettime()+"|" + notes.getnotes();
			if(strInfos[m].length()>30)
				strInfos[m] = strInfos[m].substring(0,35)+"......";
			m++;
		}
		//使用字符串初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
				strInfos);
		showNotes.setAdapter(arrayAdapter);
	}
	//子线程
	public class RunnableThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Socket socket = null;
			String message = null;
			try{
				socket = new Socket(ip,8888);
				PrintWriter out = new PrintWriter(new BufferedWriter(new 
						OutputStreamWriter(socket.getOutputStream())),true);
				out.println(msg);
				out.flush();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				message = in.readLine();
				out.close();
				in.close();
				}catch(UnknownHostException e){
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					try{
						if(message == null){
							socket.close();
						}
					}catch(IOException e){
						e.printStackTrace();
					}
				}
		}		
	}
}