package com.example.onboarding.ui.dashboard;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Workshop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkshopAPI {
    String workshopURL;
    FragmentActivity context;
    Workshop workshop;
    String jsonData;
    List<Workshop> workshopList = new ArrayList<>();
    List<Workshop> workshop_List;
    private RecyclerView.Adapter workshopAdapter;
    Profile profile;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public WorkshopAPI(String URL, FragmentActivity con, RecyclerView.Adapter infoAdapter, List<Workshop> workshops, Profile p) throws IOException {
        workshopURL=URL;
        context=con;
        workshop= new Workshop();
        workshopAdapter=infoAdapter;
        workshop_List=workshops;
        profile =p;
    }

    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(workshopURL).newBuilder();
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
                Log.d("chella", "jsondata" + jsonData);
                JSONObject jsonObject=null;
                try{
                    jsonObject = new JSONObject(jsonData);
                    // Log.d("sheetal",e.getMessage());
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("sheetal","jsonArray"+ jsonArray);
                        for(int i =0; i<jsonArray.length();i++) {
                            JSONObject workshopJSONObject = jsonArray.getJSONObject(i);
                            Workshop workshop = new Workshop();
                            workshop.setId(workshopJSONObject.getString("workshopId"));
                            workshop.setWorkshopName(workshopJSONObject.getString("workshopName"));
                            workshop.setDescription(workshopJSONObject.getString("workshopDescription"));
                            workshop.setDate(workshopJSONObject.getString("workshopDate"));
                            workshopList.add(workshop);

                        }


                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                workshop_List.addAll(workshopList);
                                //Log.d("chella","Product List on parsing the JSON "+prod_list);
                                workshopAdapter.notifyDataSetChanged();
                            }
                        };
                        handler.sendEmptyMessage(1);

                        Log.d("chella","workshop list"+workshopList.size());
                    } else {
                        Log.d("chella","Error in retrieving the JSONDATA");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }


}
