package by.bsuir.diplom.dao.interfaces;

import by.bsuir.diplom.entity.Seal;

import java.util.List;

public interface SealDaoInterface<S extends Seal> extends DaoInterface<Seal> {

	boolean save(Seal entity);

	boolean delete(int id);

	Seal update(Seal entity);

	Seal getById(int id);

	List<Seal> getAll();

}