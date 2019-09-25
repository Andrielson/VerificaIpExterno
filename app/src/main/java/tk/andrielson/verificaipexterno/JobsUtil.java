package tk.andrielson.verificaipexterno;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public final class JobsUtil {

    private JobsUtil() {
        //construtor privado para evitar que a classe seja instanciada
    }

    public static final class ConsultaIpJob {
        private static final String SERVICETAG = "ConsultaIpJobService";

        public static void criaService(int intervalo) {
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(App.getContext()));
            Job job = dispatcher.newJobBuilder()
                    .setLifetime(Lifetime.FOREVER)
                    .setService(ConsultaIpJobService.class)
                    .setTag(SERVICETAG)
                    .setReplaceCurrent(false)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow(intervalo, intervalo))
                    .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                    .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                    .build();
            dispatcher.mustSchedule(job);
        }

        public static void removeService() {
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(App.getContext()));
            dispatcher.cancel(SERVICETAG);
        }
    }
}
