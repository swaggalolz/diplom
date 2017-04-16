package by.bsuir.diplom.dao.interfaces;

import by.bsuir.diplom.entity.Box;

import java.util.List;

public interface BoxDaoInterface<B extends Box> extends DaoInterface<Box> {

    boolean save(Box entity);

    boolean delete(int id);

    Box update(Box entity);

    Box getById(int id);

    List<Box> getAll();
}
