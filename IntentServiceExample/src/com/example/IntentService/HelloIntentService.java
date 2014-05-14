package com.example.IntentService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


/**
 *IntentService
 *מעגל החיים שלו מתנהל אוטמטי
 *בכל קריאה נעשה מחדש עושה את העבודה ולאחר מכן 
 *נהרס אין צורך לנהל את הטור במחסנית
 */
public class HelloIntentService extends IntentService {

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public HelloIntentService() {
		super("HelloIntentService23134");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("HelloIntentService", "onCreate");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Normally we would do some work here, like download a file.
		
		Log.d("HelloIntentService", "onHandleIntent: start");

		URL url;
		URLConnection urlConnection;
		InputStream urlInputStream;

		try 
		{
			//קבלת כתובת הקובץ להורדה תמונה
			String urlPath = intent.getExtras().getString("url");

			url = new URL(urlPath);
			urlConnection = url.openConnection();
			urlInputStream = urlConnection.getInputStream();
			final Bitmap bmp = BitmapFactory.decodeStream(urlInputStream);
            
			//פתיחת קובץ
			File file = getFileStreamPath(MainActivity.MY_PIC);
			
			FileOutputStream fOut = new FileOutputStream(file);
			
			//שפיכת הביטים לקובץ שפתחנו
			bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
			//ניקוי המשפך מביטים אם נשאר
			fOut.flush();
			//סגירת הקובץ
			fOut.close();
			
			//שליחת מעטפה דריגר שנגמר ההורדה
			sendBroadcast(new Intent(MainActivity.INTENT_ACTION_DOWNLOAD_COMPLETED));
			
		} 
		catch (Exception e) 
		{
			Log.e(getClass().getName(), "Fail sending message", e);
			e.printStackTrace();
		}
		
		Log.d("HelloIntentService", "onHandleIntent: end");

	}

	@Override
	public void onDestroy() {
		Log.d("HelloIntentService", "onDestroy");
		super.onDestroy();
	}

}