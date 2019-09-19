package tk.andrielson.verificaipexterno;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


final class ConsultaIpJobService extends JobService {

    private static final String TAG = ConsultaIpJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(final JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(final JobParameters job) {
        return false;
    }

    public void consultaIp(final JobParameters params) {
        if (params != null && params.getExtras() != null) {
            String ipv4 = ConsultaIpService.pesquisaIpv4();
            SharedPreferences sharedPreferences = App.getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            String ultimo_ipv4 = sharedPreferences.getString("ultimo_ipv4", null);
            if (!ipv4.equals(ultimo_ipv4)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ultimo_ipv4", ipv4);
                editor.apply();
            }
        }
    }
}
