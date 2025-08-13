package com.farmacia.infrastructure;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

public class ConnectionFactory {
    private static final Properties props = new Properties();
    static {
        try (InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            } else {
                throw new IllegalStateException("Arquivo db.properties não encontrado em resources");
            }
            // Driver é carregado automaticamente no JDBC 4+, mas manter aqui não faz mal:
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("Falha ao carregar configuração do banco: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        try {
            String url  = Objects.requireNonNull(props.getProperty("db.url"), "db.url");
            String user = Objects.requireNonNull(props.getProperty("db.user"), "db.user");
            String pass = Objects.requireNonNull(props.getProperty("db.password"), "db.password");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao abrir conexão: " + e.getMessage(), e);
        }
    }
}

