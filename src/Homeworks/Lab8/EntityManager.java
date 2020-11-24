package Homeworks.Lab8;

import Homeworks.Lab8.domain.BaseEntity;

import java.sql.SQLException;

public interface EntityManager {

    /**
     * Метод сохраняет в БД объект var1
     */
    void persist(Object var1) throws Exception;

    /**
     * Метод обновляет в БД данные, соответствующие объекту
     */
    BaseEntity merge(BaseEntity var1) throws Exception;

    /**
     * Метод удаляет объект из БД
     */
    void remove(BaseEntity var1) throws Exception;

    /**
     * Метод запрашивает из базы данных информацию, соответствующую первичному ключу var2.
     * Полученные данные инициализируют объект типа Class<T>
     * Метод возвращает созданный объект
     * @param var1 - тип создаваемого объекта
     * @param var2 - значение первичного ключа
     */
    <T> T find(Class<T> var1, BaseEntity var2) throws Exception;

    void refresh(Object var1);
}
