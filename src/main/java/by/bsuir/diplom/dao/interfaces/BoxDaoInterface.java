package by.bsuir.diplom.dao.interfaces;

import by.bsuir.diplom.entity.Box;

import java.util.List;

public interface BoxDaoInterface<B extends Box> extends DaoInterface<Box> {

    Box save(Box entity);

    void delete(Box entity);

    void update(Box entity);

    Box getById(int id);

    List<Box> getAll();
}
