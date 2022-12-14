import models.Book;
import models.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build();

        Metadata metadata =
                new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(Book.class) /*!!!!!!! register class*/
                        .getMetadataBuilder()
                        .build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(new Book("Comedian book", Genre.Comedy, Arrays.asList("Vasya","Petya")));
        session.save(new Book("f5",Genre.Western,Arrays.asList("Kon","Br","Don")));
        session.save(new Book("fdfds",Genre.Horror,Arrays.asList("Mike")));
        session.save(new Book("Harry Poter",Genre.Fantasy,Arrays.asList("Jjj")));
        session.getTransaction().commit();

      List<Book> bookList= session.createQuery("select b from Book b").list();
        System.out.println(bookList);
      List<Book> bookList1=session.createQuery("select b from Book b where b.authors.size>2").list();
      System.out.println(bookList1);

        session.close();
        sessionFactory.close();
    }
}
