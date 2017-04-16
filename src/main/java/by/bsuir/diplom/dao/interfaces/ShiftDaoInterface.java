package by.bsuir.diplom.dao.interfaces;


import by.bsuir.diplom.entity.Shift;

import java.util.List;

public interface ShiftDaoInterface extends DaoInterface<Shift> {

	boolean save(Shift entity);

	boolean delete(int id);

	Shift update(Shift entity);

	Shift getById(int id);

	List<Shift> getAll();

}