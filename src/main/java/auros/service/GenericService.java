package auros.service;

public interface GenericService<A> {
    A add(A entity);

    void delete(Long id);
}
