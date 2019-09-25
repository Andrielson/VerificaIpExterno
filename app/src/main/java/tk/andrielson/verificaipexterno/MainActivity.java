package tk.andrielson.verificaipexterno;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import tk.andrielson.verificaipexterno.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Inicializa o ViewModel
        this.viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        this.configuraTela();
        this.binding.contentMain.setIntervaloVerificacao(10);
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
//            Intent intent = new Intent(MainActivity.this, ConfiguracoesActivity.class);
//            startActivity(intent);
            Snackbar.make(this.binding.toolbar, "Configurações", Snackbar.LENGTH_SHORT)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configuraTela() {
        this.viewModel.getUltimaVerificacao().observe(this, verificacao -> {
            this.binding.contentMain.setUltimaVerificacao(verificacao != null ? verificacao.dataHora : "não disponível");
            this.binding.contentMain.setUltimoIpv4(verificacao != null ? verificacao.ipv4 : "não disponível");
        });
    }

    public void onClickIniciar(View view) {
        TesteTask task = new TesteTask(this);
        task.execute();
        final Editable edt = this.binding.contentMain.inputMinutos.getText();
        if (edt != null) {
            JobsUtil.ConsultaIpJob.criaService(Integer.parseInt(edt.toString()));
            this.binding.contentMain.inputMinutos.setEnabled(false);
            this.binding.contentMain.setFlagEmExecucao(true);
        }
    }

    public void onClickParar(View view) {
        JobsUtil.ConsultaIpJob.removeService();
        this.binding.contentMain.inputMinutos.setEnabled(true);
        this.binding.contentMain.setFlagEmExecucao(false);
    }

    private void posExecucaoTask(String ipv4) {
        this.viewModel.salvaVerificacao(ipv4);
    }

    private static final class TesteTask extends AsyncTask<Void, Void, String> {
        final WeakReference<MainActivity> listener;

        private TesteTask(MainActivity activity) {
            listener = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return ConsultaIpService.pesquisaIpv4();
        }

        @Override
        protected void onPostExecute(String s) {
            if (listener.get() == null) return;
            listener.get().posExecucaoTask(s);
        }
    }
}
