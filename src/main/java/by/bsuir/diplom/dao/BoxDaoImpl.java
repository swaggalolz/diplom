package by.bsuir.diplom.dao;


import by.bsuir.diplom.dao.interfaces.BoxDaoInterface;
import by.bsuir.diplom.entity.Box;
import org.hibernate.Session;

import java.util.List;

public class BoxDaoImpl implements BoxDaoInterface<Box> {

	public Box save(Box entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Box box = (Box) session.save(entity);
		session.getTransaction().commit();
		return box;
	}

	public void delete(Box entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

	public void update(Box entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(entity);
		session.getTransaction().commit();
	}

	public Box getById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.get(Box.class, id);
	}

	public List<Box> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createCriteria(Box.class).list();
	}
}