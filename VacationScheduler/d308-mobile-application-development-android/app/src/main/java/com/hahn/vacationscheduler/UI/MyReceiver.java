package com.hahn.vacationscheduler.UI;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.hahn.vacationscheduler.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        String alertType = intent.getStringExtra("alert_type");

        // Use the intent to retrieve the notification title and content
        String VacationStartTitle= intent.getStringExtra("vacationStarttitle");
        String VacationStartContent= intent.getStringExtra("vacationStartcontent");
        String VacationEndTitle= intent.getStringExtra("vacationEndtitle");
        String VacationEndContent= intent.getStringExtra("vacationEndcontent");
        String ExcursionStartTitle= intent.getStringExtra("excursionStarttitle");
        String ExcursionStartContent= intent.getStringExtra("excursionStartcontent");






        createNotificationChannel(context, channel_id);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        System.out.println(VacationStartTitle);
        System.out.println(VacationStartContent);
        System.out.println(VacationEndTitle);
        System.out.println(VacationEndContent);
        System.out.println(ExcursionStartTitle);
        System.out.println(ExcursionStartContent);






        if ("start".equals(alertType)) {
            // This is a start date alert
            builder.setContentTitle(VacationStartTitle);
            builder.setContentText(VacationStartContent);
        } else if ("end".equals(alertType)) {
            // This is an end date alert
            builder.setContentTitle(VacationEndTitle);
            builder.setContentText(VacationEndContent);

        }
        else if ("excursion".equals(alertType)) {
            // This is an excursion start date alert
            builder.setContentTitle(ExcursionStartTitle);
            builder.setContentText(ExcursionStartContent);

        }

        Notification n = builder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);
    }



    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "mychannelname";
        String description = "mychanneldescription";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


}