package dev.mobile.ass_3_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

import dev.mobile.ass_3_alarm.alarm.AlarmReceiver;

/**
 * @author CuogNV
 */
public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static MainActivity inst;
    private TextView alarmTextView;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        // get view from layout
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        final ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);

        // use alarm service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // toggle button click listner
        alarmToggle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if (alarmToggle.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Alarm On",
                                Toast.LENGTH_SHORT).show();
                        Calendar calendar = Calendar.getInstance();
                        // set selected time from timepicker to calendar
                        calendar.set(Calendar.HOUR_OF_DAY,
                                alarmTimePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE,
                                alarmTimePicker.getCurrentMinute());

                        Intent myIntent = new Intent(MainActivity.this,
                                AlarmReceiver.class);
                        myIntent.putExtra("keys", "Di hoc");

                        // A PendingIntent specifies an action to take in the
                        // future
                        pendingIntent = PendingIntent.getBroadcast(
                                MainActivity.this, 0, myIntent, 0);

                        // set alarm time
                        alarmManager.set(AlarmManager.RTC,
                                calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        // Cancel alarm
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(getApplicationContext(), "Alarm Off",
                                Toast.LENGTH_SHORT).show();
                        setAlarmText("");
                    }
                } catch (Exception ex) {

                }

            }
        });
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}