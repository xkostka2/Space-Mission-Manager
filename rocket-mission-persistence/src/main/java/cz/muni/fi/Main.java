package cz.muni.fi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

public class Main {
    private static EntityManagerFactory emf;

    public static void main(String[] args) throws SQLException {
        new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);

        emf = Persistence.createEntityManagerFactory("default");

        emf.close();
    }
}