package com.example.gallery_anna0410;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    EditText edText;
    Button btnSearch;
    String urlString;

    private static final String TAG = "Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edText = (EditText) findViewById(R.id.edText);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                final String searchResult = edText.getText().toString();

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String searchStringNoSpaces = searchResult.replace(" ", "+");

                // API
                String key = "AIzaSyA-k5MdnT8RUh6VYKhBWKBjdxY4Mkfiqp0";
                String cx = "014561948740021196575:u60cafryro8";
                urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";

                Log.d(TAG, "Searching: " + searchResult);
                Log.d(TAG, urlString);

                Toast.makeText(MainActivity.this, "Please wait a few minutes", Toast.LENGTH_SHORT).show();
                startObject();
            }
        });
    }

    private void startObject() {
        final ArrayList<Image> imageArrayList = new ArrayList<Image>();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray itemsArray = response.getJSONArray("items");
                    for (int i = 2; i < itemsArray.length(); i++) {
                        JSONObject item = (JSONObject) itemsArray.get(i);
                        JSONObject pagemap = item.getJSONObject("pagemap");
                        JSONArray cse_image = pagemap.getJSONArray("cse_image");
                        JSONObject cse_imageItem = cse_image.getJSONObject(0);
                        String src = cse_imageItem.getString("src");

                        imageArrayList.add(new Image(src));
                    }

                    ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this, imageArrayList);
                    ListView listView = findViewById(R.id.list);
                    listView.setAdapter(imageAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
