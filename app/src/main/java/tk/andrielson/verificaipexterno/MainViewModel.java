package tk.andrielson.verificaipexterno;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

import tk.andrielson.verificaipexterno.room.AppDatabase;
import tk.andrielson.verificaipexterno.room.Verificacao;
import tk.andrielson.verificaipexterno.room.VerificacaoDao;

public class MainViewModel extends ViewModel {
    private final AppDatabase database = AppDatabase.getInstancia();
    private final VerificacaoDao verificacaoDao = database.verificacaoDao();

    LiveData<Verificacao> getUltimaVerificacao() {
        return verificacaoDao.getUltima();
    }

    void salvaVerificacao(String ip) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Verificacao v = new Verificacao();
        v.dataHora = sdf.format(new Date());
        if (ConsultaIpService.testaIpv4(ip)) {
            v.ipv4 = ip;
        } else {
            v.erro = ip;
        }
        Executors.newSingleThreadExecutor().execute(() -> verificacaoDao.insert(v));
    }
}
