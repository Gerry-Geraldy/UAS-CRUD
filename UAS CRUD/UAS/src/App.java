import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
// import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
// import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    TableView<Mahasiswa> tableView = new TableView<Mahasiswa>();
    
    public static void main(String[]args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UAS OOP");
        TableColumn<Mahasiswa, Integer> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Mahasiswa, String> columnNama = new TableColumn<>(" NAMA");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Mahasiswa, String> columnNim = new TableColumn<>("NIM");
        columnNim.setCellValueFactory(new PropertyValueFactory<>("nim"));

        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnNim);

        ToolBar toolBar = new ToolBar();

        Button buttonAdd = new Button("ADD");
        toolBar.getItems().add(buttonAdd);
        buttonAdd.setOnAction(e -> add());

        Button buttonDelete = new Button("DELETE");
        toolBar.getItems().add(buttonDelete);
        buttonDelete.setOnAction(e -> delete());

        Button buttonEdit = new Button("EDIT");
        toolBar.getItems().add(buttonEdit);
        buttonEdit.setOnAction(e -> edit());

        Button buttonRefresh = new Button("REFRESH");
        toolBar.getItems().add(buttonRefresh);
        buttonRefresh.setOnAction(e -> re());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from mahasiswa");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Mahasiswa(rs.getInt("id"), rs.getString("nama"), rs.getString("nim")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("SIMPAN");

        addStage.setTitle("MENAMBAHKAN DATA MAHASISWA");

        TextField namaField = new TextField();
        TextField nimField = new TextField();
        Label labelNama = new Label("NAMA");
        Label labelNim = new Label("NIM");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelNim, nimField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into mahasiswa SET nama='%s', nim='%s'";
                sql = String.format(sql, namaField.getText(), nimField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField idField = new TextField();
        Label labelId = new Label("NAMA YANG AKAN DIHAPUS");

        VBox hbox1 = new VBox(5, labelId, idField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from mahasiswa WHERE nama='%s'";
                sql = String.format(sql, idField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("SIMPAN");

        addStage.setTitle("EDIT NAMA");

        TextField namaField = new TextField();
        TextField nimField = new TextField();
        Label labelNama = new Label("NAMA");
        Label labelNim = new Label("NIM");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelNim, nimField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE mahasiswa SET nim ='%s' WHERE nama='%s'";
                sql = String.format(sql, nimField.getText(), namaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from mahasiswa");
            while (rs.next()) {
                tableView.getItems().addAll(new Mahasiswa(rs.getInt("id"), rs.getString("nama"), rs.getString("nim")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE mahasiswa DROP id";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE mahasiswa ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}