package com.example.onboarding.ui.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
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
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskAPI {
    String taskURL;
    FragmentActivity context;
    Task task;
    String jsonData;
    List<Task> taskList = new ArrayList<>();
    List<Task> task_List;
    private RecyclerView.Adapter taskAdapter;
    Profile profile;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public TaskAPI(String URL, FragmentActivity con, RecyclerView.Adapter tAdapter, List<Task> tasks, Profile pro) throws IOException {
        taskURL=URL;
        context=con;
        task= new Task();
        taskAdapter=tAdapter;
        task_List=tasks;
        profile=pro;
    }
    public void execute() {

        Log.d("stest", "hit the gettask execute");
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(taskURL).newBuilder();
        httpBuider .addQueryParameter("emailId",profile.getId());

        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
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
                Log.d("stest", "jsondata" + jsonData);
                JSONObject jsonObject=null;
                try{
                    jsonObject = new JSONObject(jsonData);
                    // Log.d("sheetal",e.getMessage());
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("stest","jsonArray"+ jsonArray);
                        for(int i =0; i<jsonArray.length();i++) {
                            JSONObject taskJSONObject = jsonArray.getJSONObject(i);
                            task = new Task();
                            task.setId(taskJSONObject.getString("id"));
                            task.setTaskName(taskJSONObject.getString("taskName"));
                            task.setDescription(taskJSONObject.getString("description"));
                            task.setDueDate(taskJSONObject.getString("dueDate"));
                            task.setRewards(taskJSONObject.getString("rewards"));
                            task.setStatus(taskJSONObject.getString("status"));
                            task.setNotify(taskJSONObject.getString("notify"));
                            taskList.add(task);
                        }


                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                task_List.addAll(taskList);
                                Log.d("stest","Product List on parsing the JSON "+task_List);
                                taskAdapter.notifyDataSetChanged();
                            }
                        };
                        handler.sendEmptyMessage(1);

                        Log.d("chella","task list"+taskList.size());
                    } else {
                        Log.d("chella","Error in retrieving the JSONDATA");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }
}
