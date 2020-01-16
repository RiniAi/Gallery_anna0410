package com.example.gallery_anna0410;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

/**
 * An application shows images from pixabay.com
 **/

public class MainActivity extends Activity {

    EditText edText;
    Button btnSearch;
    Button btnLoadMore;
    Button btnLoadLess;
    String urlString;

    String searchStringNoSpaces = "";

    int page = 1;

    private static final String TAG = "Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edText = (EditText) findViewById(R.id.edText);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnLoadMore = (Button) findViewById(R.id.btnLoadMore);
        btnLoadLess = (Button) findViewById(R.id.btnLoadLess);

        btnLoadMore.setEnabled(false);
        btnLoadLess.setEnabled(false);

        urlString = "https://pixabay.com/api/?key=14936994-8c4443d07406ac0ea977716d4&q=e&page=1";
        startArrayList();

        btnSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                final String searchResult = edText.getText().toString();
                searchStringNoSpaces = searchResult.replace(" ", "+");

                urlString = "https://pixabay.com/api/?key=14936994-8c4443d07406ac0ea977716d4&q=" + searchStringNoSpaces + "&page=" + page;

                Log.d(TAG, "Searching: " + searchResult);
                Log.d(TAG, urlString);
                startArrayList();
            }
        });
    }

    private void startArrayList() {
        final ArrayList<Image> imageArrayList = new ArrayList<Image>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Exception handling
                try {
                    // Parsing json array response
                    JSONArray itemsArray = response.getJSONArray("hits");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemHits = (JSONObject) itemsArray.get(i);
                        String largeImageURL = itemHits.getString("largeImageURL");

                        // Add a new item to the imageArrayList
                        imageArrayList.add(new Image(largeImageURL));
                    }

                    // Create an adapter for imageArrayList and set it for listView
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

        if (page >= 1) {
            btnLoadMore.setEnabled(true);
        }
        if (page == 1) {
            btnLoadLess.setEnabled(false);
        }
    }

    // Hide Keyboard
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void clickBtnLoadMore(View view) {
        page = page + 1;
        urlString = "https://pixabay.com/api/?key=14936994-8c4443d07406ac0ea977716d4&q=" + searchStringNoSpaces + "&page=" + page;
        btnLoadLess.setEnabled(true);
        startArrayList();
    }

    public void clickBtnLoadLess(View view) {
        page = page - 1;
        urlString = "https://pixabay.com/api/?key=14936994-8c4443d07406ac0ea977716d4&q=" + searchStringNoSpaces + "&page=" + page;
        startArrayList();
    }
}
