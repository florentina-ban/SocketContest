package validator;

import exceptions.RepoException;

public interface Validator<E> {
    public void valideaza(E elem) throws RepoException;
}
