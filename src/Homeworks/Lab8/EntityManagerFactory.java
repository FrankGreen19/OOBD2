package Homeworks.Lab8;

import Homeworks.Lab6.Lab6Main2;
import Homeworks.Lab7.ScanDB;

import java.util.HashMap;
import java.util.HashSet;

public class EntityManagerFactory {
    private HashMap<String, HashSet<String>> tables = new HashMap<>();

    public EntityManagerFactory() {}

    /**
     * Метод создает, инициализирует экземпляр класса, реализующего EntityManager
     */
    public EntityManager createEntityManager() throws Exception {
        if (this.scanModel())
            return new EntityManagerImpl();
        else
            throw new Exception("Database & Model are different!");
    }

    /**
     * Метод сканирует структуру классов модели, структуру базы данных,
     * сравнивает их и возвращает булевский результат сравнения
     */
    private boolean scanModel() {
        return ScanDB.scanDb(this.tables, Lab6Main2.getGraph());
    }
}
