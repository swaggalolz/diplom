package by.bsuir.diplom.dao.interfaces;


import by.bsuir.diplom.entity.Shift;

import java.util.List;

public interface ShiftDaoInterface<S extends Shift> extends DaoInterface<Shift> {

	Shift save(Shift entity);

	void delete(Shift entity);

	void update(Shift entity);

	Shift getById(int id);

	List<Shift> getAll();

}