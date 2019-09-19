package tk.andrielson.verificaipexterno.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class VerificacaoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Verificacao... verificacoes);

    @Query("SELECT * FROM tb_verificacao ORDER BY data_hora DESC LIMIT 1")
    public abstract LiveData<Verificacao> getUltima();

}
