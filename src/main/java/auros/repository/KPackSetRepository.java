package auros.repository;

import auros.model.KPackSet;
import java.util.List;

public interface KPackSetRepository extends GenericRepository<KPackSet> {
    void addKPacksToSet(Long id, List<Long> kpackIds);
}
