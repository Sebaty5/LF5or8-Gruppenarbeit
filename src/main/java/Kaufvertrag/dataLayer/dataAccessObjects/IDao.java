package Kaufvertrag.dataLayer.dataAccessObjects;

import java.util.List;

public interface IDao <T,K>{
    T create();
    T create(T objectToInsert);
    T read(K id);
    List<T> readAll();
    void update(T objectToUpdate);
    void delete(K id);
}