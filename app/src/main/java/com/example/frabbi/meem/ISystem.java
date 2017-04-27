package com.example.frabbi.meem;

/**
 * Created by root on 4/9/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ISystem
{
    public static void saveAccount(final Activity activity, final Account account)
    {
        System.out.println("trying to save");
        String url = Constants.RegistrationIp;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("ok"))
                        {
                            Context context = activity.getApplicationContext();
                            saveAccountInCache(context, account);
                            Intent intent = new Intent(context, MapActivity.class);

                            activity.startActivity(intent);
                            activity.finish();
                        }
                        else
                        {
                            Toast.makeText(activity,"User ID not available", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,"Network not available", Toast.LENGTH_LONG).show();
                    }
                })
        {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put(Constants.ConstantId,account.id);
                values.put(Constants.ConstantName,account.name);
                values.put(Constants.ConstantPassword,account.password);

                return values;
            }
        };
        Volley.newRequestQueue(activity.getApplicationContext()).add(request);
    }

    public static void loadAccount(final Activity activity, final String id, final String password)
    {
        System.out.println("trying to load");
        String url = Constants.LoginIp;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals(""))
                            Toast.makeText(activity, "id and password not matched",Toast.LENGTH_LONG).show();
                        else
                        {
                            Context context = activity.getApplicationContext();
                            String [] str = response.split(",");
                            Account account = new Account(str[0], str[1], str[2]);

                            saveAccountInCache(context,account);

                            Intent intent = new Intent(context, MapActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,"Network not available", Toast.LENGTH_LONG).show();
                    }
                })
                {
                    protected Map<String,String> getParams()
                    {
                        Map <String, String> values = new HashMap<String, String>();
                        values.put(Constants.ConstantId,id);
                        values.put(Constants.ConstantPassword,password);

                        return values;
                    }
                };

        Volley.newRequestQueue(activity.getApplicationContext()).add(request);
    }
    public static void checkIn(Context context, final CheckedInPosition position)
    {
        Log.e("Checkin","trying");
        String url = Constants.CheckinIp;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CI",response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put(Constants.ConstantId,position.id);
                values.put(Constants.ConstantDate,position.date);
                values.put(Constants.ConstantLatitute,position.latitude);
                values.put(Constants.ConstantLongitude,position.longitude);
                values.put(Constants.CheckInType,position.type);

                return values;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }
    public static void update(Context context, final Account account)
    {
        String url = Constants.UpdateIp;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
            {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put(Constants.ConstantId,account.id);
                values.put(Constants.ConstantLatitute,account.latitude);
                values.put(Constants.ConstantLongitude,account.longitude);

                return values;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
    public static void getImage(final Canvas canvas, String path, final Context context)
    {
        String url = "http://cdn.history.com/sites/2/2013/11/113634012-AB.jpeg";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new ImageRequest
        ImageRequest imageRequest = new ImageRequest(
                url, // Image URL
                new Response.Listener<Bitmap>() { // Bitmap listener
                    @Override
                    public void onResponse(Bitmap response) {
                        // Do something with response
                        Bitmap bitmap = Bitmap.createScaledBitmap(response,90,90,false);
                        canvas.drawBitmap(bitmap,5,5,null);
                    }
                },
                0, // Image width
                0, // Image height
                ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        Log.e("KK","KK");
                        error.printStackTrace();
                    }
                }
        );

        // Add ImageRequest to the RequestQueue
        requestQueue.add(imageRequest);
    }
    public static void loadFriends(final Account account, final ArrayList<Account>friends, Context context)
    {
        String url = Constants.loadFriendsIp;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray!=null)
                            {
                                if(friends.isEmpty())
                                    for(int i=0; i<jsonArray.length(); i++)
                                        friends.add(new Gson().fromJson(jsonArray.getString(i), Account.class));
                                else
                                    for(int i=0; i<jsonArray.length(); i++)
                                        friends.set(i, new Gson().fromJson(jsonArray.getString(i), Account.class));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put(Constants.ConstantId,account.id);

                return values;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
    public static void loadCheckedInPositions(final Account account, final ArrayList<CheckedInPosition>checkedInPositions, Context context)
    {
        String url = Constants.loadCheckedInsIp;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray!=null)
                            {
                                    for(int i=0; i<jsonArray.length(); i++)
                                        checkedInPositions.add(new Gson().fromJson(jsonArray.getString(i), CheckedInPosition.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put(Constants.ConstantId,account.id);

                return values;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
    private static void setDefaults(String key, String value, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    private static String getDefaults(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static void saveAccountInCache(Context context, Account account)
    {
        String json = new Gson().toJson(account);
        setDefaults(Constants.ConstantAccount, json, context);
    }
    public static Account loadAccountFromCache(Context context)
    {
        String json = getDefaults(Constants.ConstantAccount,context);
        if(json==null|json=="")  return null;

        return new Gson().fromJson(json, Account.class);
    }
    public static void resetAccountInCache(Context context)
    {
        setDefaults(Constants.ConstantAccount,null,context);
    }
}