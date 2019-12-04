package nullteam.com.christmasshake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    //surare設定公開，service才能使用
    public static LinearLayout square;
    public static ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        square = findViewById(R.id.square);
        image = findViewById(R.id.imageView);
        Intent intent = new Intent(this,ShakeService.class);
        //開始service
        startService(intent);
    }
}
