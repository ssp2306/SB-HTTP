package com.stephensparker.sb_http;

import android.app.Activity;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;


public class MainActivity extends Activity {

    private Button buttonOpenURL;
    String response;
    TextView tOutputURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // find the ID of the TextView
        tOutputURL = (TextView) findViewById(R.id.editTextURLOutput);
        buttonOpenURL = (Button) findViewById(R.id.buttonOpenURL);

        addListenerOnClick();
    }
    public void addListenerOnClick() {

        //Select a specific button to bundle it with the action you want

        buttonOpenURL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // grab values from the input fields and pass them to the output text
                EditText editURL = (EditText) findViewById(R.id.editURL);
                String eURL = editURL.getText().toString();

                try {
                    URL url = new URL(eURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    readStream(con.getInputStream());

                } catch (Exception e) {
                    e.printStackTrace();
                    response = "Something Wrong " + e.toString();
                    tOutputURL.setText(response);

                }

            }

            private void readStream(InputStream in) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        //System.out.println(line);
                        response += line;


                    }
                    tOutputURL.setText(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    response = "Something Wrong " + e.toString();
                    tOutputURL.setText(response);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            response = "Something Wrong " + e.toString();
                            tOutputURL.setText(response);
                        }
                    }
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
