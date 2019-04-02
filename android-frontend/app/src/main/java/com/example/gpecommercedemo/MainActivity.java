package com.example.gpecommercedemo;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.realexpayments.hpp.HPPError;
import com.realexpayments.hpp.HPPManager;
import com.realexpayments.hpp.HPPManagerListener;

public class MainActivity extends AppCompatActivity implements HPPManagerListener<ConsumerResponse> {

    private static final int CONTENT_VIEW_ID = 10101010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HPPManager.setIsEncoded(false);
        HPPManager hppManager = new HPPManager();
        hppManager.setHppRequestProducerURL("https://rxp-android-test.azurewebsites.net/index.php/hppRequestProducer");
        hppManager.setHppURL("https://pay.sandbox.realexpayments.com/pay");
        hppManager.setHppResponseConsumerURL("https://rxp-android-test.azurewebsites.net/index.php/hppResponseConsumer");

        Fragment hppManagerFragment = hppManager.newInstance();

        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        getFragmentManager()
                .beginTransaction()
                .add(CONTENT_VIEW_ID,hppManagerFragment)
                .commit();
    }

    @Override public void hppManagerCompletedWithResult(ConsumerResponse consumerResponse) {
        ConsumerResponse response = consumerResponse;
    }
    @Override public void hppManagerFailedWithError(HPPError error) {
        HPPError e = error;
    }
    @Override public void hppManagerCancelled() {
        boolean cancelled = true;
    }
}
