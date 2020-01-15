package com.example.gallery_anna0410;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText edText;
    Button btnSearch;

    private static final String TAG = "Google";

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
                String key="AIzaSyA-k5MdnT8RUh6VYKhBWKBjdxY4Mkfiqp0";
                String cx = "014561948740021196575:u60cafryro8";
                String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";

                Log.d(TAG, "Searching: " + searchResult);
                Log.d(TAG, urlString);

            }
        });

    }

}
