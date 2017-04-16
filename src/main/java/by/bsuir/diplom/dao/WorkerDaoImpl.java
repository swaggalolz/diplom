package by.bsuir.diplom.dao;


import by.bsuir.diplom.dao.interfaces.WorkerDaoInterface;
import by.bsuir.diplom.entity.Worker;
import org.hibernate.Session;

import java.util.List;

public class WorkerDaoImpl implements WorkerDaoInterface<Worker> {


	public Worker save(Worker entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Worker worker = (Worker) session.save(entity);
		session.getTransaction().commit();
		return worker;
	}

	public void delete(Worker entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

	public void update(Worker entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(entity);
		session.getTransaction().commit();
	}

	public Worker getById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.get(Worker.class, id);
	}

	public List<Worker> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createCriteria(Worker.class).list();
	}
}