package bdtube.vumobile.com.bdtube.notification;

/**
 * Created by toukirul on 11/10/2017.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.MainActivity;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.VideoPreviewActivity;


public class Utils {
    private static NotificationManager mNotificationManager;
    public static NotificationManager mManager;

    public static int clickCount = 0;
    public static int clickCount1 = 0;
    public static String resultMno = null;
    PHPRequest phpRequest = new PHPRequest();

    public static void setmNotificationManager(NotificationManager mNotificationManager) {
        Utils.mNotificationManager = mNotificationManager;
    }

    @SuppressWarnings("static-access")


    public static void sendNotification(Context context, String messageTitle, String messageBody) {
        int id = (int) System.currentTimeMillis();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500, 500, 500, 500, 500};

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Amar Sticker")
                .setContentText(messageBody)
                .setColor(context.getResources().getColor(android.R.color.primary_text_light))
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());


    }

    public static void setCustomViewNotification(Context context, String replaceAllurl, String title, String sms, String Image, int id, String contentCode, String categoryCode, String sContentType, String sPhysicalFileName, String zedID, String artist) {
        clickCount = clickCount + 1;
        Bitmap remote_picture = null;

        try {
            //Log.d("Response Tariqul sample_url", ImageURL.toString());
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(Image).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        String strDate = sdf.format(c.getTime());

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Creates an explicit intent for an ResultActivity to receive.

        Intent resultIntent = new Intent(context, VideoPreviewActivity.class);
//        resultIntent.putExtra("DoDownload", "4");
//        resultIntent.putExtra("ArtistName", artist);
//        resultIntent.putExtra("videoURL",replaceAllurl );
//        resultIntent.putExtra("getContentTitle",title );
//        resultIntent.putExtra("MenuFree","notification" );
//        resultIntent.putExtra("MobileNumber","" );
//        resultIntent.putExtra("MenuTitle","Home" );

        resultIntent.putExtra("ContentTitle", title);
        resultIntent.putExtra("ContentType", sContentType);
        resultIntent.putExtra("physicalFileName", sPhysicalFileName);
        resultIntent.putExtra("relatedCatCode", "E8E4F496-9CA9-4B35-BADD-9B6470BE2F74");
        resultIntent.putExtra("relatedContentUrl", Config.URL_NEW_VIDEO);
        resultIntent.putExtra("imgUrl", Image);
        resultIntent.putExtra("ContentCode", contentCode);
        resultIntent.putExtra("totalLike", "36");
        resultIntent.putExtra("totalView", "68");
        resultIntent.putExtra("duration", "0:0");
        resultIntent.putExtra("info", "");
        resultIntent.putExtra("genre", "");

//        VideoPreviewActivity.ContentTitle = title;
//        VideoPreviewActivity.ContentType = sContentType;
//        VideoPreviewActivity.physicalFileName = sPhysicalFileName;
//        VideoPreviewActivity.relatedCatCode = "E8E4F496-9CA9-4B35-BADD-9B6470BE2F74";
//        VideoPreviewActivity.relatedContentUrl = Config.URL_NEW_VIDEO;
//        VideoPreviewActivity.imgUrl = Image;
//        VideoPreviewActivity.ContentCode = contentCode;
//        VideoPreviewActivity.totalLike = "36";
//        VideoPreviewActivity.totalView = "68";
//        VideoPreviewActivity.duration = "0:0";
//        VideoPreviewActivity.info = "";
//        VideoPreviewActivity.genre = "";

        Log.d("contentCode", contentCode);
        Log.d("categoryCode", categoryCode);
        Log.d("contentName", title);
        Log.d("sContentType", sContentType);
        Log.d("sPhysicalFileName", sPhysicalFileName);
        Log.d("contentImg", Image);
        Log.d("ZedID", zedID);
     /*   DownloadTask downloadTask = new DownloadTask();

        *//** Starting the task created above *//*
        downloadTask.execute(Image);*/
        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);



        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create remote view and set bigContentView.
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_custom_remote);


        Intent volume = new Intent(context, VideoPreviewActivity.class);
        volume.putExtra("do_action", "play");
        volume.putExtra("ArtistName", artist);
        volume.putExtra("videoURL",replaceAllurl );
        volume.putExtra("getContentTitle",title );
        volume.putExtra("MenuFree","notification" );
        volume.putExtra("MobileNumber",resultMno );
        volume.putExtra("MenuTitle","Home" );

        volume.putExtra("ContentTitle", title);
        volume.putExtra("ContentType", sContentType);
        volume.putExtra("physicalFileName", sPhysicalFileName);
        volume.putExtra("relatedCatCode", "E8E4F496-9CA9-4B35-BADD-9B6470BE2F74");
        volume.putExtra("relatedContentUrl", Config.URL_NEW_VIDEO);
        volume.putExtra("imgUrl", Image);
        volume.putExtra("ContentCode", contentCode);
        volume.putExtra("totalLike", "36");
        volume.putExtra("totalView", "68");
        volume.putExtra("duration", "0:0");
        volume.putExtra("info", "");
        volume.putExtra("genre", "");

////        VideoPreviewActivity.ContentTitle = title;
////        VideoPreviewActivity.ContentType = sContentType;
////        VideoPreviewActivity.physicalFileName = sPhysicalFileName;
////        VideoPreviewActivity.relatedCatCode = "E8E4F496-9CA9-4B35-BADD-9B6470BE2F74";
////        VideoPreviewActivity.relatedContentUrl = Config.URL_NEW_VIDEO;
////        VideoPreviewActivity.imgUrl = Image;
////        VideoPreviewActivity.ContentCode = contentCode;
////        VideoPreviewActivity.totalLike = "36";
////        VideoPreviewActivity.totalView = "68";
////        VideoPreviewActivity.duration = "0:0";
////        VideoPreviewActivity.info = "";
////        VideoPreviewActivity.genre = "";
//
//        VideoPreviewActivity.isNotific = true;
//        VideoPreviewActivity.notification_vUrl = replaceAllurl;

        PendingIntent pVolume = PendingIntent.getActivity(context, 1, volume, 0);
        expandedView.setOnClickPendingIntent(R.id.MainlayoutCustom, pVolume);
        expandedView.setTextViewText(R.id.text_view, sms);

        //expandedView.setTextViewText(R.id.notificationTime, strDate);

        try {
            expandedView.setImageViewBitmap(R.id.imageViewTest, remote_picture );

        }catch (Exception e){

            e.printStackTrace();
        }

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(remote_picture)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(sms)

                //  .setDeleteIntent(pendintIntent)
                .build();

        notification.bigContentView = expandedView;

        notification.defaults |= Notification.DEFAULT_SOUND;
        mNotificationManager.notify(0, notification);
    }



    private static int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.bd : R.drawable.logo;
    }


}
