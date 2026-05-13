package com.example.notificationdemo; 
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // A unique ID for our notification channel
    private static final String CHANNEL_ID = "lab_experiment_channel";
    
    // A unique ID for the notification itself
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Create the Notification Channel (Required for API 26+)
        createNotificationChannel();

        Button btnShowNotification = findViewById(R.id.btnShowNotification);

        // 2. Set up the button click listener
        btnShowNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    /**
     * Method to create the Notification Channel.
     * It's safe to call this repeatedly because creating an existing notification
     * channel performs no operation.
     */
    private void createNotificationChannel() {
        // We only need to create the channel on API 26+ (Your minSdk is 27, so this will always run)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lab Notifications";
            String description = "Channel used for Experiment 9";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Method to build and display the actual notification.
     */
    private void sendNotification() {
        // Create an explicit intent for an Activity in your app.
        // This is what happens when the user clicks the notification.
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        // FLAG_IMMUTABLE is strictly required for PendingIntents on modern Android versions
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Using the default app icon
                .setContentTitle("Experiment 9 Success!")
                .setContentText("This is your demonstrated system notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // Attach the click action
                .setAutoCancel(true); // Automatically dismiss the notification when clicked

        // Issue the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
