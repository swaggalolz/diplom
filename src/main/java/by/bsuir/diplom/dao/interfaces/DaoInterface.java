package by.bsuir.diplom.dao.interfaces;


import java.util.List;

interface DaoInterface<E> {

	E save(E entity);

	void delete(E entity);

	void update(E entity);

	E getById(int id);

	List<E> getAll();

}