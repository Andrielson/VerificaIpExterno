package tk.andrielson.verificaipexterno;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tk.andrielson.verificaipexterno.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.binding.contentMain.setUltimaVerificacao("14/09/2019 19:30:25");
        this.binding.contentMain.setUltimoIpv4("10.25.35.65");
        this.binding.contentMain.setIntervaloVerificacao(10);
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(this.binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar.make(this.toolbar, "Configurações", Snackbar.LENGTH_SHORT)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickIniciar(View view) {
        this.binding.contentMain.setFlagEmExecucao(true);
        TesteTask task = new TesteTask(this);
        task.execute();
    }

    public void onClickParar(View view) {
        this.binding.contentMain.setFlagEmExecucao(false);
    }

    private void posExecucaoTask(String ipv4) {
        this.binding.contentMain.setUltimoIpv4(ipv4);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        this.binding.contentMain.setUltimaVerificacao(sdf.format(new Date()));
        this.binding.contentMain.setFlagEmExecucao(false);
    }

    private static final class TesteTask extends AsyncTask<Void, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .followRedirects(false)
                .build();
        final WeakReference<MainActivity> listener;
        long contentLength = 0;

        private TesteTask(MainActivity activity) {
            listener = new WeakReference<>(activity);
        }

        @Nullable
        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url("https://ipv4.icanhazip.com/")
                    .build();
            Response response;
            try {
                response = okHttpClient.newCall(request).execute();
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    contentLength += responseBody.contentLength();
                    return responseBody.string();
                }
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (listener.get() == null) return;
            listener.get().posExecucaoTask(s);
        }
    }
}
