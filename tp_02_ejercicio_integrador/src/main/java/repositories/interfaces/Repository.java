package repositories.interfaces;

import java.util.List;

public interface Repository<T> {
    void save(T t);

    T selectById(int id);

    List<T> selectAll();

    boolean delete(int id);
}
