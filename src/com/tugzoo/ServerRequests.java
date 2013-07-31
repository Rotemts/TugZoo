package com.tugzoo;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Rotem on 26/07/13.
 */
public class ServerRequests {


    private static ServerRequests instance;
    private static String IP = "frozen-sierra-8415.herokuapp.com";

    public static String getIP() {
        return IP;
    }

    private ServerRequests() {
    }

    public static ServerRequests getInstance() {
        if (instance == null)
            instance = new ServerRequests();
        return instance;
    }


    public interface ServerResponse {
        public void gotResponse(JSONObject response);
        public void onError();
    }

    public void makeRequest(String methodName, ArrayList<String> params, ServerResponse rs) {
        new LoadJsonOperation(rs).execute(getUrl(methodName,params));
    }

    private String getUrl(String methodName, ArrayList<String> params) {
        String url = "http://" + IP;
        url += "/" + methodName;
        if (params != null)
            for (int i=0 ; i<params.size() ; i++)
                url += "/" + params.get(i);
        return url;
    }

    private class LoadJsonOperation extends AsyncTask<String, Integer, JSONObject> {
        ServerResponse rs;

        public LoadJsonOperation(ServerResponse rs) {
            this.rs = rs;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String result = "";
            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(params[0]);
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

                //convert response to string
                try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    is.close();
                    result=sb.toString();
                }catch(Exception e){
                    Log.e("log_tag",params[0] + " Error converting result "+e.toString());
                }
            }catch(Exception e){
                Log.e("log_tag",params[0] + " Error in http connection "+e.toString());
            }
            try{
                result=result.replace("ï»¿","");
                Log.i("log_tag",result);
                JSONObject obj = new JSONObject(result);
                return obj;

            }catch(JSONException e){
                Log.e("log_tag " + params[0], "Error parsing data " + e.toString());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(JSONObject obj) {
            if (obj != null)
                rs.gotResponse(obj);
            else
                rs.onError();
        }

    }

}
