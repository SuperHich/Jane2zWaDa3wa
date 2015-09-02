package com.janaezwadaawa.gcm;

import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.janaezwadaawa.MainActivity;
import com.janaezwadaawa.R;
import com.janaezwadaawa.externals.JDManager;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class GcmIntentService extends IntentService {
    public static int NOTIFICATION_ID = 1;
	private static final String TAG = "SERVICE";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
      Notification notification = new Notification();
    public GcmIntentService() {
        super("GcmIntentService");
        
//        prefHelper = new BasePreferenceHelper(this);
    }

 
    

    @Override
    protected void onHandleIntent(Intent intent) {
    	
        Bundle extras = intent.getExtras();
        JDManager mManager = JDManager.getInstance(this);
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                
            	//sendNotification("Send error: " + extras.getString( "Alert" ).toString() + "");
            	sendNotification(false,null);
            	
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                
            	//sendNotification("Deleted messages on server: " + extras.toString());
            	sendNotification(false,null);
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
 
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                //TODO Implement proper notifications 
                //sendNotification("Received: " +  extras.toString() + "");
                
                if(extras != null && JDManager.getInstance(this).isNotificationEnabled())
                {
                	Log.i(TAG, "Received: " + extras.toString());
                	sendNotification(true,extras);
                	
                	try {
                		int badgeCounter = mManager.getBadgeCounter();
                		badgeCounter = badgeCounter+1;
            			JDManager.getInstance(this).setBadgeCounter(badgeCounter);
            			Log.e(TAG, "badgeCounter: " + badgeCounter);
            			ShortcutBadger.setBadge(getApplicationContext(), badgeCounter);
            			if(mManager.getGcmDispatcher() != null)
            				mManager.getGcmDispatcher().onNewNotificationReceived();
            		} catch (ShortcutBadgeException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                }
                
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
       // GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    
    
    
    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(boolean isAdded , Bundle extras) {
    	
		if ( !isAdded ) {
			return;
		}
		
		Uri soundUri;
		int sound =  R.raw.notification ;
		
		if (extras != null) {
			if (extras.containsKey("alert")) {
				String key = extras.getString("key");

				if (key.equals("chat")) {
					
					sound = R.raw.chat_incoming ;
					
				} else  {
					sound = R.raw.notification ;
				}
				
				
				
				
			}
		} else {
			
		}
		
		soundUri = Uri.parse("android.resource://" + this.getPackageName() + "/" + sound );
		
		String message = "";
		try{
			JSONObject json = new JSONObject(extras.getString( "aps"));
			message = json.getString("alert");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
				
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, 
//			MainActivity.class), 0); //TODO PEnding IOntent
        Intent intent = new Intent(this, MainActivity.class) ;
        if(message.contains("وفيات"))
        	intent.putExtra(MainActivity.DEFAULT_FRAG_POSITION, 0);
        else
        	intent.putExtra(MainActivity.DEFAULT_FRAG_POSITION, 1);
        
//        Bundle bundle =  new Bundle();
        intent.putExtras( extras );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ); // Flag added to resume running app.
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent , PendingIntent.FLAG_UPDATE_CURRENT); //TODO PEnding IOntent
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.janaez_logo)
        .setPriority(Notification.PRIORITY_MAX)
        .setContentText(message)
        .setTicker(message)
        .setContentTitle(getString(R.string.push_notif) )
//        .setStyle(new NotificationCompat.BigTextStyle()
//        .bigText(extras.getString( "alert" )))
//        .setSummaryText(extras.getString( "msg" )))
        .setOnlyAlertOnce( true )
        .setAutoCancel( true )
         .setSound( soundUri );
		
//        mBuilder.getNotification().defaults|= Notification.DEFAULT_VIBRATE;
        mBuilder.setContentIntent(contentIntent);
        
//        Log.e("NOTIFICATION title", extras.getString( "title" ));
//        Log.e("NOTIFICATION message", extras.getString( "message" ));
        
//         Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.sound_file)
        
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        
//        JDManager.getInstance(getBaseContext()).setIntegerPreference(getBaseContext(), "notif_id", NOTIFICATION_ID);
        NOTIFICATION_ID++;

    }
    
 // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
    	
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, 
//        		MainActivity.class), 0); //TODO PEnding IOntent
        
        Intent intent = new Intent(this, MainActivity.class) ;
        intent.putExtra( "notification", true ) ;
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ); // Flag added to resume running app.
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent , 0); //TODO PEnding IOntent

        

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.janaez_logo)
        .setContentTitle("CardCloud Notification")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setOnlyAlertOnce( true )
        .setAutoCancel( true )
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        
       
        
    }
    
}
