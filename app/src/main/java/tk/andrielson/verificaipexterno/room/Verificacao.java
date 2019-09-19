package tk.andrielson.verificaipexterno.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_verificacao")
public final class Verificacao {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public Long id;

    @ColumnInfo(name = "ipv4")
    public String ipv4;

    @ColumnInfo(name = "ipv6")
    public String ipv6;

    @ColumnInfo(name = "erro")
    public String erro;

    @ColumnInfo(name = "data_hora")
    public String dataHora;
}
