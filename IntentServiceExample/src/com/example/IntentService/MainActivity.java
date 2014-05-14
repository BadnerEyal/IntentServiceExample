package com.example.IntentService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;



import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * ������� ���� ����� �
 * IntentService
 * ����� ����� ������ ����� ���� �����
 * ���� ����� ���� �� ������ �����
 * ����� ���� ���� ���� �� ������ ����� �� ������ ������
 *
 * IntentService -->sendBroadcast-->onReceive
 */
public class MainActivity extends Activity {
    //�� ������ ������ ����� ����� ������
	public static final String INTENT_ACTION_DOWNLOAD_COMPLETED = "com.intent.servicee.DOWNLOAD_COMPLETED";

	public static final String MY_PIC = "my_pic.jpg";
	
	private ImageView imageView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView = (ImageView)findViewById(R.id.imageView);
		Button b1 = (Button) findViewById(R.id.button1);

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//����� ������� ������ �� ����� ������ �����
				Intent intent = new Intent(MainActivity.this, HelloIntentService.class);
				intent.putExtra("url", 
"http://whatstrendingonline.com/wp-content/uploads/2014/04/HD-Black-Car-Wallpaper_31.jpg");
				
				startService(intent);
			}
		});
		
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		//����� ������ �� ����� ���� ���� ����� ����� ����� �����
		//�� ����� ����� ���� �� ���� ����� 
		//mannifest
		registerReceiver(reciver, new IntentFilter(INTENT_ACTION_DOWNLOAD_COMPLETED));
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		//����� ����� 
		unregisterReceiver(reciver);
	}
	
	//���� ���� ����� ������ ����� ������
		//����� ���� ������ ������ ������ ����
		private BroadcastReceiver reciver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent) {
				
				Log.d("MainActivity$BroadcastReceiver","onReceive()");
				Toast.makeText(context, "Download Completed", Toast.LENGTH_LONG).show();
				
				try {
					//����� ������ ����� ����� ����
					Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(getFileStreamPath(MY_PIC)));
					imageView.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		};
	
}

