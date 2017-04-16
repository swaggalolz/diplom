import by.bsuir.diplom.dao.HibernateUtil;
import by.bsuir.diplom.entity.Role;
import by.bsuir.diplom.entity.Worker;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDao {


    @org.junit.Test
    public void name() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Role role = new Role("master");
        session.save(role);


        List<Role> roles = new ArrayList<Role>();
        roles.add(role);

        Worker worker = new Worker("Ivan", "Simth", new Date(1990,12,2), 500.5f, roles);

        session.save(worker);
        session.getTransaction().commit();

    }
}