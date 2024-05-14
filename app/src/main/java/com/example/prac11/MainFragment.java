package com.example.prac11;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.example.prac11.databinding.FragmentMainBinding;

import java.io.IOException;


public class MainFragment extends Fragment {
    FragmentMainBinding binding;
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "example_channel";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);


        //Web
        binding.buttonWeb.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_webFragment);
        });


        //Music
        Button PlayButton = binding.buttonMusic;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Kangaroo_MusiQue_-_The_Neverwritten_Role_Playing_Game.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.buttonMusic.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
            }
        });

        //Animation
        ObjectAnimator rotateAnim =
                ObjectAnimator.ofFloat(binding.imageView, "rotation", 0f, 360f);
        rotateAnim.setDuration(2000);
        rotateAnim.setRepeatCount(ObjectAnimator.INFINITE);
        rotateAnim.setRepeatMode(ObjectAnimator.RESTART);
        binding.buttonAnimation.setOnClickListener(v -> {
            if (rotateAnim.isRunning()) {
                rotateAnim.end();
            } else {
                rotateAnim.start();
            }
        });


        //Notifications
        createNotificationChannel();
        binding.buttonNotification.setOnClickListener(v -> {
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(getContext(), CHANNEL_ID)

                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Example Notification")
                    .setContentText("This is a test notification")

                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManager notificationManager = getSystemService(getContext(), NotificationManager.class);
            notificationManager.notify(1, builder.build());
        });


        binding.buttonDelayedNotification.setOnClickListener(v -> {
            scheduleNotification(10000);
        });

        return binding.getRoot();
    }

    private void scheduleNotification(int delay) {
        Intent notificationIntent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(getContext(), 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT |
                                PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(getContext(), AlarmManager.class);
        long futureInMillis = System.currentTimeMillis() + delay;
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Example Channel";
            String description = "Channel for example notifications";
            int importance =
                    NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new
                    NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    getSystemService(getContext(), NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }

        @Override
        public void onDestroy () {
            super.onDestroy();
            mediaPlayer.release();
        }
    }