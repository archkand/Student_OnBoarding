package com.example.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.onboarding.Pojo.Profile;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginAPI {
    String loginURL;
    Profile profile;
    String jsonData;
    Activity context;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public LoginAPI(String loginURL, Profile profile, Activity context) {
        this.loginURL = loginURL;
        this.profile = profile;
        this.context = context;
    }

    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        String param = gson.toJson(profile);
        Log.d("chella","json"+param);

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(loginURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("chella", "call fail"+e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string();
                Log.d("chella","jsonData"+jsonData);
                if(jsonData.equalsIgnoreCase("success")){
                    Log.d("chella","jsonData"+jsonData);
                    Log.d("chella","inside the response");
                    Looper.prepare();
                    Toast.makeText(context, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profile", profile);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    Looper.loop();
                }
                else {
                    Looper.prepare();
                    Toast.makeText(context, "Invalid Credentials. Try again!", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


            }});
    }


}
