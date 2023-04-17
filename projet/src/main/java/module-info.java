module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.projet to javafx.fxml;
    exports com.example.projet;
}