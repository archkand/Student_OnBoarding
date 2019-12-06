package com.example.onboarding.ui.profile;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    FragmentActivity context;
    String jsonData;
    List<Profile> profileList = new ArrayList<>();
    List<Profile> profile_List;
    Profile profile;
    String notify;
    Switch notification;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ProfileAPI(String profileURL, FragmentActivity context, Profile profile) {
        this.profileURL = profileURL;
        this.context = context;
        this.profile = profile;
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
                            profile.setPastTask(profileJSONObject.getString("pastTask"));
                            profile.setPastWorkshop(profileJSONObject.getString("pastWorkshop"));
                            profile.setStep(profileJSONObject.getString("step"));
                            //profileList.add(profile);
                        }



                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                               // profile_List.addAll(profileList);
                                Log.d("chella","setting the user profile"+profile);

                                ((TextView)context.findViewById(R.id.profileName)).setText(profile.getName());
                                ((TextView)context.findViewById(R.id.profileId)).setText(profile.getStudentID());
                                ((TextView)context.findViewById(R.id.rewardText)).setText(profile.getRewards());
                                notification = ((Switch)context.findViewById(R.id.notificationSwitch));
                                notify = profile.getNotification();
                                Log.d("chella","notify "+notify);
                                if(notify.equalsIgnoreCase("ON")){
                                    Log.d("chella","notify"+profile.getNotification());
                                    notification.setChecked(true);
                                }
                                else {
                                    notification.setChecked(false);
                                }


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
