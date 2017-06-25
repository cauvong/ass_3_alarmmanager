package dev.mobile.ass_3_alarm.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import dev.mobile.ass_3_alarm.MainActivity;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * @author CuogNV
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        // this will update the UI ith message
        try {
            MainActivity inst = MainActivity.instance();
            String ss = intent.getStringExtra("keys");
            inst.setAlarmText("Alarm! " + ss);

            // this will sound the alarm tone
            // this will sound the alarm once, if you wish to
            // raise alarm in loop continuously then use MediaPlayer and

            Uri alarmUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

            // this will send a notification message
            ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmService.class.getName());
            intent.setComponent(comp);

            // If extended by BroadcastReceiver class then comment this code
            startWakefulService(context, (intent.setComponent(comp)));


            setResultCode(Activity.RESULT_OK);
        } catch (Exception ex) {

        }
    }
}
