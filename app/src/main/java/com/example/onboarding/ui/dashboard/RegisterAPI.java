package com.example.onboarding.ui.dashboard;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.onboarding.Pojo.Workshop;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterAPI {
    String registerURL = "http://192.168.118.2:3000/workshopStudent/register";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
FragmentActivity context;
Workshop workshop;

    public RegisterAPI(FragmentActivity cn,Workshop w) {
        workshop=w;
        context=cn;
    }

    public void execute() {

        Gson gson = new Gson();
        //String email ="sheetalpatil217@gmail.com";

        String workshopId = "{\"workshopId\":\""+workshop.getId()+"\"}";
        JsonParser jsonparer= new JsonParser();
        JsonObject jobject = jsonparer.parse(workshopId).getAsJsonObject();
        String param = gson.toJson(jobject);

        RequestBody body = RequestBody.create(JSON, param);



        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(registerURL)
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
            String  jsonData = response.body().string();
                Log.d("chella", "jsondata" + jsonData);
                   if(jsonData.equalsIgnoreCase("success")){
                       Handler handler = new Handler(Looper.getMainLooper()) {
                           @Override
                           public void handleMessage(Message msg) {
                               // Any UI task, example
                               Toast.makeText(context, "Registered for "+workshop.getWorkshopName(), Toast.LENGTH_SHORT).show();
                           }
                       };
                       handler.sendEmptyMessage(1);
                   } else {
                        Log.d("chella","Error in retrieving the JSONDATA");
                    }

            }});
    }
}
