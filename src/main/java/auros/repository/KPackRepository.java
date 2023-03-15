package auros.repository;

import auros.model.KPack;
import java.util.List;

public interface KPackRepository extends GenericRepository<KPack> {
    List<KPack> getAllKPacksBySetId(Long id);
}
