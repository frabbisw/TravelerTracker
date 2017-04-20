package com.example.frabbi.meem;

/**
 * Created by root on 4/9/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

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
    //public static void checkInAsHome
}