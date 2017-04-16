package by.bsuir.diplom.dao.interfaces;

import by.bsuir.diplom.entity.Role;

import java.util.List;

public interface RoleDaoInterface<R extends Role> extends DaoInterface<Role> {

    Role save(Role entity);

    void delete(Role entity);

    void update(Role entity);

    Role getById(int id);

    List<Role> getAll();
}
