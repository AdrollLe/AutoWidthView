package view.adroll.autowidthview;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import view.adroll.autowidthview.recyclerview.AutoWidthView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flContainer = findViewById(R.id.main_fl_container);
        flContainer.addView(new AutoWidthView(this));
    }
}
