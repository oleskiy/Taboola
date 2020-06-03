package com.oleskiy.taboola.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.oleskiy.taboola.R;
import com.taboola.android.PublisherInfo;
import com.taboola.android.Taboola;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        PublisherInfo publisherInfo = new PublisherInfo("sdk-tester");
        Taboola.init(publisherInfo);
    }
}
