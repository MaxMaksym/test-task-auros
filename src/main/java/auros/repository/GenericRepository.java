package auros.repository;

import java.util.List;
import java.util.Map;

public interface GenericRepository<A> {
    A add(A entity);

    void delete(Long id);

    List<A> getAllWithFilteringAndSorting(String[] sortingParams,
                                          Map<String, Object[]> filteringParams);
}
