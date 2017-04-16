package by.bsuir.diplom.dao;


import by.bsuir.diplom.dao.interfaces.ShiftDaoInterface;
import by.bsuir.diplom.entity.Shift;
import org.hibernate.Session;

import java.util.List;

public class ShiftDaoImpl implements ShiftDaoInterface<Shift> {


	public Shift save(Shift entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Shift shift = (Shift) session.save(entity);
		session.getTransaction().commit();
		return shift;
	}

	public void delete(Shift entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

	public void update(Shift entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(entity);
		session.getTransaction().commit();
	}

	public Shift getById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.get(Shift.class, id);
	}

	public List<Shift> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createCriteria(Shift.class).list();
	}
}