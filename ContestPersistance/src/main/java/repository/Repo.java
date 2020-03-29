package repository;

import java.util.Collection;

public interface Repo<E> {

    public E findOne(int id);
    public Collection<E> findAll();
    public int getSize();
}
