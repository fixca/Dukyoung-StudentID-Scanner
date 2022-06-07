package me.fixca.barcord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import java.util.concurrent.Executors;

import me.fixca.barcord.backend.CallBackAdapter;
import me.fixca.barcord.backend.RetrofitFactory;
import me.fixca.barcord.backend.logger.LoggerResult;
import me.fixca.barcord.backend.logger.LoggerService;
import me.fixca.barcord.backend.logger.LoggerBody;
import me.fixca.barcord.env.Env;
import me.fixca.barcord.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
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

        progressBar.setVisibility(View.GONE);

        scanner = new CodeScanner(this, scannerView);
        scanner.setDecodeCallback(result -> {
            runOnUiThread(() -> {
                progressBar.setVisibility(View.VISIBLE);
            });
            LoggerBody loggerBody = new LoggerBody(Env.KEY, result.getText(), Utils.getInstance().getTimeStamp(), "maker");

            Retrofit retrofit = RetrofitFactory.getInstance().getRetrofit();
            LoggerService loggerService = retrofit.create(LoggerService.class);
            CallBackAdapter<LoggerResult> adapter = new CallBackAdapter<>();

            adapter.setIResponse((call, response) -> {
                LoggerResult loggerResult = (LoggerResult) response.body();

                if(loggerResult.success == 1) {
                    Utils.getInstance().printToast(MainActivity.this, "성공적으로 등록되었습니다!");
                }
                else {
                    Utils.getInstance().printToast(MainActivity.this, "등록에 실패하였습니다!");
                }

                try {
                    Thread.sleep(3000);
                    runOnUiThread(() -> {
                        scanner.startPreview();
                        progressBar.setVisibility(View.GONE);
                    });
                } catch (InterruptedException e) {

                }
            });

            adapter.setIFailure((call, t) -> {
                t.printStackTrace();
                Utils.getInstance().printToast(MainActivity.this, "등록에 실패하였습니다!");
                try {
                    Thread.sleep(3000);
                    runOnUiThread(() -> {
                        scanner.startPreview();
                        progressBar.setVisibility(View.GONE);
                    });
                } catch (InterruptedException e) {

                }
            });

            Call<LoggerResult> call = loggerService.log(loggerBody);

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
}