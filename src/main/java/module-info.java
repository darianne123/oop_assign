module org.example.oop_assign {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.oop_assign to javafx.fxml;
    exports org.example.oop_assign;
}