package by.bsuir.diplom.dao.interfaces;


import java.util.List;

interface DaoInterface<E> {

	boolean save(E entity);

	boolean delete(int id);

	E update(E entity);

	E getById(int id);

	List<E> getAll();

}