import java.sql.SQLException;
import java.sql.Statement;

public class Mahasiswa {

    private int id;
    private String nama = null;
    private String nim= null;

    public Mahasiswa(int id, String nama, String nim) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public boolean update(String nama, String nim) {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update mahasiswa SET nama='%s', nim='%s' WHERE id=%d";
            sql = String.format(sql, nama, nim, id);
            state.execute(sql);
            db.conn.close();
            return true;
        } catch (SQLException e1) {
            return false;
        }
    }

    public boolean delete() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "delete from mahasiswa WHERE id=%d";
            sql = String.format(sql, id);
            state.execute(sql);
            db.conn.close();
            return true;
        } catch (SQLException e1) {
            return false;
        }
    }
}