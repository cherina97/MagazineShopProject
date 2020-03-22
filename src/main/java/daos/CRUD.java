package daos;

import java.util.List;
import java.util.Optional;

public interface CRUD<T> {

    T create(T t);

    Optional<T> read(int id);

    void update(T t);

    void delete(int id);

    Optional<List<T>> readAll();
}
