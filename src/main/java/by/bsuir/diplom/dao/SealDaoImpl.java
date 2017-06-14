package by.bsuir.diplom.dao;


import by.bsuir.diplom.dao.interfaces.SealDaoInterface;
import by.bsuir.diplom.entity.Seal;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.List;

public class SealDaoImpl implements SealDaoInterface<Seal> {


	public Seal save(Seal entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Integer id = (Integer) session.save(entity);
		entity.setSealId(id);

        session.getTransaction().commit();
        session.close();
		return entity;
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

	@Override
	public List<Seal> getBySerialNumber(String serialNumber) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria cr = session.createCriteria(Seal.class)
				.add(Restrictions.gt("serialNumber", serialNumber));
        List<Seal> result = cr.list();
        session.close();
		return result;
	}

	public List<Seal> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        List<Seal> res = session.createCriteria(Seal.class).list();
        session.close();
		return res;
	}
}