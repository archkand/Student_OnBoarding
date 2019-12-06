package com.example.onboarding.ui.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

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

public class NotifyAPI {
    String notifyURL;
    FragmentActivity context;
    Task notifyTask;
    String jsonData;
    List<Task> notifyList = new ArrayList<>();
    List<Task> notify_List;
    Profile profile;
    RecyclerView.Adapter notifyAdapter;
String Type;
    //task,profile,notifyURL,context,notifyTasks

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NotifyAPI(Task task, Profile profile, String URL, FragmentActivity con, List<Task> notifyTaskList,RecyclerView.Adapter adapter,String type) throws IOException {
        notifyTask = task;
        this.profile = profile;
        notifyURL = URL;
        context = con;
        notify_List = notifyTaskList;
        notifyAdapter = adapter;
        Type=type;
    }
    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        String email =profile.getId();
        String task = notifyTask.getId();
        String notify = "OFF";
        String emailId="";
        //String emailId =  "{\"emailId\":\""+email+"\",\"taskId\":\""+task+"\",\"notify\":\""+notify+"\"}";
        if(Type.equalsIgnoreCase("verify")){
            emailId="{\"taskId\":\""+task+"\"}";
        }else{

        }
        JsonParser jsonparer= new JsonParser();
        JsonObject jobject = jsonparer.parse(emailId).getAsJsonObject();
        String param = gson.toJson(jobject);
        Log.d("chella","json"+email);
        Log.d("chella","json"+param);

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(notifyURL)
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
                            Toast.makeText(context, notifyTask.getTaskName()+ " is sent to verify", Toast.LENGTH_SHORT).show();
                        }
                    };
                    handler.sendEmptyMessage(1);
                } else {
                    Log.d("chella","Error in retrieving the JSONDATA");
                }


            }});
    }
}
