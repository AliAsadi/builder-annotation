package project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import processor.User_Builder;
import project.test.aliannotation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new User_Builder()
                .setId(1)
                .setName("asd")
                .build();


        Log.d("MainActivity", user.toString());
    }
}
