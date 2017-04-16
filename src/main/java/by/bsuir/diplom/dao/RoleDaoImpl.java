package by.bsuir.diplom.dao;


import by.bsuir.diplom.dao.interfaces.RoleDaoInterface;
import by.bsuir.diplom.entity.Role;
import org.hibernate.Session;

import java.util.List;


public class RoleDaoImpl implements RoleDaoInterface<Role> {


	public Role save(Role entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Role role = (Role) session.save(entity);
		session.getTransaction().commit();
		return role;
	}

	public void delete(Role entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

	public void update(Role entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(entity);
		session.getTransaction().commit();
	}

	public Role getById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.get(Role.class, id);
	}

	public List<Role> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createCriteria(Role.class).list();
	}
}