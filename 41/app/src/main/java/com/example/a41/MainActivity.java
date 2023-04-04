package com.example.a41;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    Button button;
    CountDownTimer countDownTimer;
    EditText usernameEditText;
    Boolean isPaused = false;


    Boolean loggedIn;
    ProgressBar progressBar;

    public void jumpClick(View view) {

        if (loggedIn) {
            Toast.makeText(this, "You are now logged in", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("username", usernameEditText.getText().toString());
            startActivity(intent);
        }


    }

    public void logOut(View view) {
        if (loggedIn) {
            loggedIn = false;
            Toast.makeText(this, "Account Signed Out", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You didn't logged in yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView loggedTextView = findViewById((R.id.loggedTextView));
        Intent intent = getIntent();
        Boolean isLogged = intent.getBooleanExtra("login", false);
        String name = intent.getStringExtra("username");
        if (name == null) {
            loggedTextView.setText("Welcome, use JUMP to log in");
        } else {
            loggedTextView.setText("Welcome " + name);
        }
        loggedIn = isLogged;
        usernameEditText = findViewById(R.id.usernameEditText);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0); // set the progress to 50%

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownTimer == null) {
                    startCountdownTimer();
                    button.setText("Pause");
                } else if (isPaused) {
                    countDownTimer.start();
                    isPaused = false;
                    button.setText("Pause");
                } else {
                    countDownTimer.cancel();
                    isPaused = true;
                    button.setText("Resume");
                }
            }
        });

    }

    private void startCountdownTimer() {
        TextView ViewTimer = findViewById(R.id.textViewTimer);
        countDownTimer = new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                progressBar.setProgress(progressBar.getMax() - secondsRemaining);
                String time = Integer.toString(secondsRemaining);
                ViewTimer.setText(time);

            }

            public void onFinish() {
                progressBar.setProgress(progressBar.getMax());
                Toast.makeText(MainActivity.this, "Countdown finished", Toast.LENGTH_SHORT).show();
                countDownTimer = null;
                button.setText("Start");
            }

        };

        countDownTimer.start();
    }

}