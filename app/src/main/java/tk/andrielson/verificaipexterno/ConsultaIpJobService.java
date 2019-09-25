package tk.andrielson.verificaipexterno;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

import tk.andrielson.verificaipexterno.room.AppDatabase;
import tk.andrielson.verificaipexterno.room.Verificacao;
import tk.andrielson.verificaipexterno.room.VerificacaoDao;


public final class ConsultaIpJobService extends JobService {

    private static final String TAG = ConsultaIpJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(final JobParameters job) {
        new Thread(() -> consultaIp(job)).start();
        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters job) {
        return false;
    }

    public void consultaIp(final JobParameters params) {
        if (params != null) {
            String ipv4 = ConsultaIpService.pesquisaIpv4();
            final AppDatabase database = AppDatabase.getInstancia();
            final VerificacaoDao verificacaoDao = database.verificacaoDao();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Verificacao v = new Verificacao();
            v.dataHora = sdf.format(new Date());
            if (ConsultaIpService.testaIpv4(ipv4)) {
                v.ipv4 = ipv4;
            } else {
                v.erro = ipv4;
            }
            Executors.newSingleThreadExecutor().execute(() -> verificacaoDao.insert(v));
        }
        jobFinished(params, true);
    }
}
