package by.bsuir.diplom.dao.interfaces;



import by.bsuir.diplom.entity.Worker;

import java.util.List;

public interface WorkerDaoInterface<W extends Worker> extends DaoInterface<Worker> {

	Worker save(Worker entity);

	void delete(Worker entity);

	void update(Worker entity);

	Worker getById(int id);

	List<Worker> getAll();

}