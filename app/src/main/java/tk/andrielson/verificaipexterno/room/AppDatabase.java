package tk.andrielson.verificaipexterno.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.Contract;

import tk.andrielson.verificaipexterno.App;

@Database(entities = {Verificacao.class}, version = 1)
@TypeConverters(DatetimeToStringConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @Contract(pure = true)
    public static AppDatabase getInstancia() {
        return InnerStaticHolder.INSTANCIA;
    }

    public abstract VerificacaoDao verificacaoDao();

    private static class InnerStaticHolder {
        static final AppDatabase INSTANCIA = Room.databaseBuilder(App.getContext(), AppDatabase.class, "ROOM.db")
                .fallbackToDestructiveMigration().build();
    }
}
