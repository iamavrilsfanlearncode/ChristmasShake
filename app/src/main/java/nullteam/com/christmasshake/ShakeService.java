package nullteam.com.christmasshake;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

public class ShakeService extends Service implements SensorEventListener {
    private SensorManager sensor_manager;
    private Sensor accelero_meter;
    //數學宣告
    private float m_accel; // 除重力外的加速度
    private float m_AccelCurrent;//當前加速度，包括重力
    private float m_AcclLast;//最後的加速度 ,包括重力


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getSystemService型態String , 所以要強制轉型成SensorManager
        sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelero_meter = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_manager.registerListener(this, accelero_meter, SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        m_AcclLast = m_AccelCurrent;
        m_AccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));

        float delta = m_AccelCurrent - m_AcclLast;
        //m_accel存放計算公式後的值
        m_accel = m_accel * 0.9f + delta;
        //數值自己測試看看 範例寫11
        if (m_accel > 11) {
            Random rnd_c = new Random();
            //設定argb(包含透明度alpha) ，接著要讓256色中隨機挑選
            int color = Color.argb(255, rnd_c.nextInt(256), rnd_c.nextInt(256), rnd_c.nextInt(256));
            //MainActivity 要先定義square
            MainActivity.square.setBackgroundColor(color);

            //顏色改變
            TypedArray t_array = getResources().obtainTypedArray(R.array.random_imgs);
            int pic_account = t_array.length();
            int [] p_pics = new int [pic_account];
            for (int i=0 ; i<p_pics.length;i++){
                p_pics[i] =t_array.getResourceId(i,0);
            }
            int pic_id = rnd_c.nextInt(19);
            MainActivity.image.setImageResource(p_pics[pic_id]);
            //MainActivity.image.setBackgroundResource(p_pics[pic_id]);


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
