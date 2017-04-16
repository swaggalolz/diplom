package by.bsuir.diplom.dao;


import by.bsuir.diplom.dao.interfaces.SealDaoInterface;
import by.bsuir.diplom.entity.Seal;
import org.hibernate.Session;

import java.util.List;

public class SealDaoImpl implements SealDaoInterface<Seal> {


	public Seal save(Seal entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Seal seal = (Seal) session.save(entity);
		session.getTransaction().commit();
		return seal;
	}

	public void delete(Seal entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

	public void update(Seal entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(entity);
		session.getTransaction().commit();
	}

	public Seal getById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.get(Seal.class, id);
	}

	public List<Seal> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createCriteria(Seal.class).list();
	}
}