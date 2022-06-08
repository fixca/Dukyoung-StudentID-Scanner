package me.fixca.barcord;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import java.util.concurrent.Executors;

import me.fixca.barcord.backend.CallBackAdapter;
import me.fixca.barcord.backend.RetrofitFactory;
import me.fixca.barcord.backend.logger.LoggerResult;
import me.fixca.barcord.backend.logger.LoggerService;
import me.fixca.barcord.backend.logger.LoggerBody;
import me.fixca.barcord.env.Env;
import me.fixca.barcord.popup.FailurePopup;
import me.fixca.barcord.popup.ResultPopup;
import me.fixca.barcord.utils.Utils;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private CodeScanner scanner;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.INVISIBLE);

        scanner = new CodeScanner(this, scannerView);
        // Use front camera
        scanner.setCamera(1);
        scanner.setDecodeCallback(result -> {
            changeProgressBar(View.VISIBLE);

            // TODO : change room_id to an each specific ID of room
            String id = result.getText();
            int timestamp = Utils.getInstance().getTimeStamp();
            String room = "maker";

            LoggerBody loggerBody = new LoggerBody(Env.KEY, id, timestamp, room);

            Retrofit retrofit = RetrofitFactory.getInstance().getRetrofit();
            LoggerService loggerService = retrofit.create(LoggerService.class);
            CallBackAdapter<LoggerResult> adapter = new CallBackAdapter<>();

            adapter.setIResponse((call, response) -> {
                Log.d("debug", "IResponse executed!");
                Log.d("debug", response.toString());
                Executors.newFixedThreadPool(1).execute(() -> {
                    LoggerResult loggerResult = (LoggerResult) response.body();

                    // TODO : Find a better way to display the result

                        runOnUiThread(() -> {
                            if (response.isSuccessful()) {
                                ResultPopup resultPopup = new ResultPopup(id, "name", timestamp, room);
                                resultPopup.setCallBack(() -> {
                                    try {
                                        changeProgressBar(View.INVISIBLE);
                                        Thread.sleep(5000);
                                        startPreview();
                                    }
                                    catch (InterruptedException e) {

                                    }
                                });
                                resultPopup.printPopup(MainActivity.this, findViewById(android.R.id.content).getRootView());
                            }
                            else {
                                FailurePopup failurePopup = new FailurePopup(response.code());
                                failurePopup.setCallBack(() -> {
                                    try {
                                        changeProgressBar(View.INVISIBLE);
                                        Thread.sleep(5000);
                                        startPreview();
                                    }
                                    catch (InterruptedException e) {

                                    }
                                });
                                failurePopup.printPopup(MainActivity.this, findViewById(android.R.id.content).getRootView());
                            }
                        });
                });
            });

            adapter.setIFailure((call, t) -> {
                Log.d("debug", "IFailure executed!");
                t.printStackTrace();
                Executors.newFixedThreadPool(1).execute(() -> {
                    runOnUiThread(() -> {
                        FailurePopup failurePopup = new FailurePopup(-1);
                        failurePopup.setCallBack(() -> {
                            try {
                                changeProgressBar(View.INVISIBLE);
                                Thread.sleep(5000);
                                startPreview();
                            }
                            catch (InterruptedException e) {

                            }
                        });
                        failurePopup.printPopup(MainActivity.this, findViewById(android.R.id.content).getRootView());
                    });
                });
            });

            Call<LoggerResult> call = loggerService.initCall(loggerBody);

            Executors.newFixedThreadPool(1).execute(() -> call.enqueue(adapter));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.startPreview();
    }

    @Override
    protected void onPause() {
        scanner.releaseResources();
        super.onPause();
    }

    private void changeProgressBar(int status) {
        runOnUiThread(() -> {
            progressBar.setVisibility(status);
        });
    }

    private void startPreview() {
        runOnUiThread(() -> {
            scanner.startPreview();
        });
    }
}