package com.example.onboarding.ui.information;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onboarding.Pojo.Info;

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
import okhttp3.Response;

public class InformationAPI {
    String informationURL;
    FragmentActivity context;
    Info information;
    String jsonData;
    List<Info> informationsList = new ArrayList<>();
    List<Info> information_List;
    private RecyclerView.Adapter informationAdapter;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public InformationAPI(String URL, FragmentActivity con, RecyclerView.Adapter infoAdapter, List<Info> informations) throws IOException {
        informationURL=URL;
        context=con;
        information= new Info();
        informationAdapter=infoAdapter;
        information_List=informations;
    }
    public void execute() {

        Log.d("chella", "hit the Information execute");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(informationURL)
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
                            JSONObject info = jsonArray.getJSONObject(i);
                            Info infos = new Info();
                            infos.setInfoId(info.getString("infoId"));
                            infos.setInfoName(info.getString("infoName"));
                            infos.setInfoDetail(info.getString("infoDetail"));
                            infos.setInfoLink(info.getString("infoLink"));
                            informationsList.add(infos);

                        }


                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                information_List.addAll(informationsList);
                                //Log.d("chella","Product List on parsing the JSON "+prod_list);
                                informationAdapter.notifyDataSetChanged();
                            }
                        };
                        handler.sendEmptyMessage(1);

                        Log.d("chella","workshop list"+informationsList.size());
                    } else {
                        Log.d("chella","Error in retrieving the JSONDATA");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }
}
