package com.example.multithreading;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    TextView MutiTextView;
    ProgressBar MutiProgressBar;
    EditText MutiEditText;
    Button MutiButton;
    int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MutiProgressBar = findViewById(R.id.MultiThreadingProgressBar);
        MutiButton =  findViewById(R.id.MultiThreadingButton);
        MutiEditText = findViewById(R.id.MultiThreadingEditText);
        MutiTextView = (TextView)findViewById(R.id.MultiThreadingTextView);

        MutiTextView.setText("0%");

        MutiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MutiEditText.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "You need to type a number!", Toast.LENGTH_LONG).show();
                }
                else {
                    limit = Integer.parseInt(MutiEditText.getText().toString());
                    MutiProgressBar.setProgress(0);
                    new ProgressForLoop().execute();
                }
            }
        });
    }

    class ProgressForLoop extends AsyncTask<Void, Integer, Result>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MutiButton.setEnabled(false);
            MutiEditText.setFocusable(false);
            if(limit == 0) {
                MutiProgressBar.setMax(1);
            } else {
                MutiProgressBar.setMax(Integer.parseInt(MutiEditText.getText().toString()));
            }

        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MutiProgressBar.setProgress(values[0]);
            MutiTextView.setText(Integer.toString(((values[0] * 100)  / limit)) + '%');
        }

        @Override
        protected Result doInBackground(Void... voids) {
            Log.i("a", MutiEditText.getText().toString());
            if(limit == 0) {
                limit = 1;
                publishProgress(1);
            } else {
                for (int i = 1; i <= limit; i++) {
                    publishProgress(i);
                    Log.i("value ", Integer.toString(i));

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            MutiButton.setEnabled(true);
            MutiEditText.setFocusable(true);
        }
    }

}
