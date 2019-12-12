package com.example.onboarding.ui.dashboard;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.onboarding.MainActivity;
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.R;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StepAPI {
    String stepURL ;
    Profile profile;
    String jsonData;
    FragmentActivity activity;

    public StepAPI(String stepURL, Profile profile, FragmentActivity act) {
        this.stepURL = stepURL;
        this.profile = profile;
        activity=act;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void execute() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(stepURL).newBuilder();
        httpBuider .addQueryParameter("studentId",profile.getStudentID());

        Log.d("chella","Step url"+httpBuider.build());
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("chella", "call fail" + e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string();
                Log.d("stest", "jsondata" + jsonData);
                if(jsonData.equalsIgnoreCase("success")) {
                    Log.d("chella", "jsonData" + jsonData);

                    Handler handler = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            // Any UI task, example
                            Log.d("steps","profile steps"+profile.getStep());
                            View circleOne = activity.findViewById(R.id.circle_one);
                            View circleTwo = activity.findViewById(R.id.circle_two);
                            View circleThree = activity.findViewById(R.id.circle_three);
                            //Drawable mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.step_circle);
                            Drawable greenCircle = ContextCompat.getDrawable(activity,R.drawable.circle_green);

                            switch (profile.getStep()){
                                case "1":
                                    circleOne.setBackground(greenCircle);
                                    break;
                                case "2":
                                    circleOne.setBackground(greenCircle);
                                    circleTwo.setBackground(greenCircle);
                                    break;
                                case "3":
                                    circleOne.setBackground(greenCircle);
                                    circleTwo.setBackground(greenCircle);
                                    circleThree.setBackground(greenCircle);
                                    break;
                            }

                        }
                    };
                    handler.sendEmptyMessage(1);


                }
            }
        });

    }

}
