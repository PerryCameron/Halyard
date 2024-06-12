module Halyard {
    requires java.base;
    requires javafx.controls;
    requires org.slf4j;
    requires java.datatransfer;
    requires java.desktop;
    requires spring.tx;
    requires spring.jdbc;
    requires java.sql;
    requires org.mariadb.jdbc;
    requires com.jcraft.jsch;
    exports org.ecsail;
}
