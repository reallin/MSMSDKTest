package com.example.smssdkproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends Activity {
	private static String APPKEY = "7c0137f2d377";
	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPSECRET
	private static String APPSECRET = "4a4e7ea365d14d7bf8d707c811dd9455";
	private EditText phonEditText,verEditText;
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ�º���
				
				
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//�ύ��֤��ɹ�
					Toast.makeText(getApplicationContext(), "�ύ��֤��ɹ�", Toast.LENGTH_SHORT).show();

				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "��֤���Ѿ�����", Toast.LENGTH_SHORT).show();
				}
			} else {
				((Throwable) data).printStackTrace();
				Toast.makeText(getApplicationContext(), "��֤�����", Toast.LENGTH_SHORT).show();
			}			
		}		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		phonEditText=(EditText) findViewById(R.id.register_username);
		verEditText=(EditText) findViewById(R.id.register_yanzhengma);
		SMSSDK.initSDK(this,APPKEY,APPSECRET);
		EventHandler eh=new EventHandler(){
			@Override
			public void afterEvent(int event, int result, Object data) {				
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}			
		};
		SMSSDK.registerEventHandler(eh);	
		findViewById(R.id.register_getyanzhengma).setOnClickListener(clickListener);
		
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.register_getyanzhengma:	//��ȡ��֤��
				if(!TextUtils.isEmpty(phonEditText.getText().toString())){
					SMSSDK.getVerificationCode("86",phonEditText.getText().toString());
					
				}else {
					Toast.makeText(MainActivity.this, "�绰����Ϊ��", 1).show();
				}										
				break;
			case R.id.register_ok:
				if(!TextUtils.isEmpty(verEditText.getText().toString())){
					SMSSDK.submitVerificationCode("86", phonEditText.getText().toString(), verEditText.getText().toString());
				}else {
					Toast.makeText(MainActivity.this, "��֤�벻��Ϊ��", 1).show();
				}

			default:
				break;
			}
		}
	};

}
