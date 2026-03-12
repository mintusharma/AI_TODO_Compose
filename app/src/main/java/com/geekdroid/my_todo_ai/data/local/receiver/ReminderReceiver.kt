package com.geekdroid.my_todo_ai.data.local.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.geekdroid.my_todo_ai.MainActivity
import com.geekdroid.my_todo_ai.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntOfExtra("TASK_ID", -1)
        val title = intent.getStringExtra("TASK_TITLE") ?: "Task Reminder"
        val description = intent.getStringExtra("TASK_DESCRIPTION") ?: "You have a task to complete!"

        if (taskId == -1) return

        showNotification(context, taskId, title, description)
    }

    private fun showNotification(context: Context, taskId: Int, title: String, description: String) {
        val channelId = "task_reminders"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this exists or use a generic one
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(taskId, notification)
    }

    private fun Intent.getIntOfExtra(name: String, defaultValue: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getIntExtra(name, defaultValue)
        } else {
            getIntExtra(name, defaultValue)
        }
    }
}
