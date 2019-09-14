package tk.andrielson.verificaipexterno;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import tk.andrielson.verificaipexterno.databinding.ActivityMainBinding;

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
    public boolean onOptionsItemSelected(MenuItem item) {
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
    }

    public void onClickParar(View view) {
        this.binding.contentMain.setFlagEmExecucao(false);
    }
}
