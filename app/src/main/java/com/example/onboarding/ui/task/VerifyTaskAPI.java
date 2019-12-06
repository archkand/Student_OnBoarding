package com.example.onboarding.ui.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerifyTaskAPI {
    String verifyTaskURL;
    FragmentActivity context;
    Task verifyTask;
    String jsonData;
    List<Task> verifyTaskList = new ArrayList<>();
    List<Task> verifyTask_List;
    private RecyclerView.Adapter verifyTaskAdapter;
    Profile profile;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public VerifyTaskAPI(String URL, FragmentActivity con, RecyclerView.Adapter verifyAdapter, List<Task> tasks, Profile profile) throws IOException {
        verifyTaskURL=URL;
        context=con;
        verifyTask= new Task();
        verifyTaskAdapter=verifyAdapter;
        verifyTask_List=tasks;
        this.profile = profile;

    }

    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        String email =profile.getId();
        String emailId = "{\"emailId\":\""+email+"\"}";
        JsonParser jsonparer= new JsonParser();
        JsonObject jobject = jsonparer.parse(emailId).getAsJsonObject();
        String param = gson.toJson(jobject);

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(verifyTaskURL)
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
                            JSONObject taskJSONObject = jsonArray.getJSONObject(i);
                            verifyTask.setId(taskJSONObject.getString("taskId"));
                            verifyTask.setTaskName(taskJSONObject.getString("taskName"));
                            verifyTask.setDescription(taskJSONObject.getString("taskDescription"));
                            verifyTask.setDueDate(taskJSONObject.getString("taskDueDate"));
                            verifyTask.setRewards(taskJSONObject.getString("taskReward"));
                            verifyTaskList.add(verifyTask);

                        }


                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                verifyTask_List.addAll(verifyTaskList);
                                //Log.d("chella","Product List on parsing the JSON "+prod_list);
                                verifyTaskAdapter.notifyDataSetChanged();
                            }
                        };
                        handler.sendEmptyMessage(1);

                        Log.d("chella","task list"+verifyTaskList.size());
                    } else {
                        Log.d("chella","Error in retrieving the JSONDATA");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }


}
