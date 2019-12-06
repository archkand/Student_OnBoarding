package com.example.onboarding.ui.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public TaskAPI(String URL, FragmentActivity con, RecyclerView.Adapter tAdapter, List<Task> tasks) throws IOException {
        taskURL=URL;
        context=con;
        task= new Task();
        taskAdapter=tAdapter;
        task_List=tasks;
    }
    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(taskURL)
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
                            task = new Task();
                            task.setId(taskJSONObject.getString("taskId"));
                            task.setTaskName(taskJSONObject.getString("taskName"));
                            task.setDescription(taskJSONObject.getString("taskDescription"));
                            task.setDueDate(taskJSONObject.getString("taskDueDate"));
                            task.setRewards(taskJSONObject.getString("taskReward"));
                            taskList.add(task);

                        }


                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                task_List.addAll(taskList);
                                //Log.d("chella","Product List on parsing the JSON "+prod_list);
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
