package by.bsuir.diplom.dao.interfaces;



import by.bsuir.diplom.entity.Worker;

import java.util.List;

public interface WorkerDaoInterface<W extends Worker> extends DaoInterface<Worker> {

	boolean save(Worker entity);

	boolean delete(int id);

	Worker update(Worker entity);

	Worker getById(int id);

	List<Worker> getAll();

}