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
    static String ip = "http://192.168.42.78/mock/";
    public static void saveAccount(final Activity activity, final Account account)
    {
        System.out.println("trying to save");
        String url = ip+"registration.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("ok"))
                        {
                            Intent intent = new Intent(activity, MapActivity.class);
                            intent.putExtra("Account", account);
                            activity.startActivity(intent);
                            activity.finish();

                            String json = new Gson().toJson(account);
                            setDefaults("Account", json, activity);
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
                        System.out.println("something went wrong");
                    }
                })
        {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put("id",account.id);
                values.put("name",account.name);
                values.put("password",account.password);

                return values;
            }
        };
        Volley.newRequestQueue(activity).add(request);
    }

    public static void loadAccount(final Activity activity, final String id, final String password)
    {
        System.out.println("trying to load");
        String url = ip+"login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals(""))
                            Toast.makeText(activity, "id and password not matched",Toast.LENGTH_LONG).show();
                        else
                        {
                            String [] str = response.split(",");
                            Account account = new Account(str[0], str[1], str[2]);
                            Intent intent = new Intent(activity, MapActivity.class);
                            intent.putExtra("Account", account);
                            activity.startActivity(intent);
                            activity.finish();

                            String json = new Gson().toJson(account);
                            setDefaults("Account",json,activity);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("something went wrong");
                    }
                })
        {
            protected Map<String,String> getParams()
            {
                Map <String, String> values = new HashMap<String, String>();
                values.put("id",id);
                values.put("password",password);

                return values;
            }
        };

        Volley.newRequestQueue(activity).add(request);
    }

    public static void setDefaults(String key, String value, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getDefaults(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static Account getAccount(Context context)
    {
        String json = getDefaults("Account",context);
        if(json==null|json=="")  return null;

        return new Gson().fromJson(json, Account.class);
    }
    public static void resetAccount(Activity activity)
    {
        setDefaults("Account",null,activity);
    }
}