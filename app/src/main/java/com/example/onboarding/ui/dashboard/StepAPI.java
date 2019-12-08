package com.example.onboarding.ui.dashboard;
import android.util.Log;
import com.example.onboarding.Pojo.Profile;
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

    public StepAPI(String stepURL, Profile profile) {
        this.stepURL = stepURL;
        this.profile = profile;
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
                }
            }
        });

    }

}
