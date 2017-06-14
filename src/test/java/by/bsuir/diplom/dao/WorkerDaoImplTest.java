package by.bsuir.diplom.dao;

import by.bsuir.diplom.entity.Worker;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class WorkerDaoImplTest {


    @Test
    public void testFlow() throws Exception {
        WorkerDaoImpl workerDao = new WorkerDaoImpl();
//        workerDao.save(new Worker("Ivan", "Ivan", "123456", new Date(), 0.0f, null));
        workerDao.save(new Worker("Artem", "Fedorov", "123456", new Date(), 0.0f, null));

    }
}