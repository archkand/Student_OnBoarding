package com.example.onboarding.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Switch;
import com.example.onboarding.MainActivity;
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileAPI {

    String profileURL;
    //FragmentActivity context;
    String jsonData;
    Profile profile;
    String notify;
    Switch notification;
    Activity act;
    List<Task> past_task_list = new ArrayList<>();


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ProfileAPI(String profileURL, Profile profile, Activity activity) {
        this.profileURL = profileURL;
        this.profile = profile;
        act=activity;
    }

    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        String param = gson.toJson(profile);
        Log.d("chella","Param :"+param);

        RequestBody body = RequestBody.create(JSON, param);

        Log.d("chella","Body"+body.toString());

        Request request = new Request.Builder()
                .url(profileURL)
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
                Log.d("chella", "jsondata" + jsonData);
                JSONObject jsonObject=null;
                try{
                    jsonObject = new JSONObject(jsonData);
                    // Log.d("sheetal",e.getMessage());
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("chella","jsonArray"+ jsonArray);
                        for(int i =0; i<jsonArray.length();i++) {
                            JSONObject profileJSONObject = jsonArray.getJSONObject(i);
                            profile.setId(profileJSONObject.getString("_id"));
                            profile.setStudentID(profileJSONObject.getString("studentID"));
                            profile.setRewards(profileJSONObject.getString("rewards"));
                            profile.setName(profileJSONObject.getString("name"));
                            profile.setNotification(profileJSONObject.getString("notification"));
                            profile.setStudentImage(profileJSONObject.getString("studentImg"));
                            JSONArray pastTaskArray = profileJSONObject.getJSONArray("pastTask");
                            for(int j=0;j<pastTaskArray.length();j++){
                                Task task = new Task();
                                JSONObject taskJSONObject = pastTaskArray.getJSONObject(j);
                                task.setId(taskJSONObject.getString("_id"));
                                task.setTaskName(taskJSONObject.getString("taskName"));
                                task.setStatus(taskJSONObject.getString("status"));
                                task.setNotify(taskJSONObject.getString("notify"));
                                past_task_list.add(task);
                                Log.d("chella","Past_Task_List "+past_task_list);
                            }
                            //profile.setPastTask((List<Task>) Arrays.asList(profileJSONObject.getString("pastTask")));
                            profile.setPastWorkshop(profileJSONObject.getString("pastWorkshop"));
                            profile.setStep(profileJSONObject.getString("step"));
                            profile.setPastTask(past_task_list);
                        }

                        Log.d("chella","Profile API past tasks "+past_task_list);

                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                Log.d("chella","before putting it in intent"+profile.toString());
                                Intent intent = new Intent(act, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("profile", profile);
                                intent.putExtras(bundle);
                                act.startActivity(intent);
                            }
                        };
                        handler.sendEmptyMessage(1);

                    } else {
                        Log.d("chella","Error in retrieving the JSONDATA");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }

}
