//package com.example.myandroidapplication.viewModel
//
//import android.os.Build
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        // Gestisci la ricezione della notifica
////        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            val channel = NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT)
////            notificationManager.createNotificationChannel(channel)
////        }
////
////        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
////            .setContentTitle("Titolo della notifica")
////            .setContentText("Contenuto della notifica")
////            .setSmallIcon(R.drawable.ic_notification_icon)
////            .setPriority(NotificationCompat.PRIORITY_HIGH)
////            .setAutoCancel(true)
////
////        notificationManager.notify(0, notificationBuilder.build())
//
//    }
//}
