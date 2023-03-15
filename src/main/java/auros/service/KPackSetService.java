package auros.service;

import auros.model.KPack;
import auros.model.KPackSet;
import java.util.List;

public interface KPackSetService extends GenericService<KPackSet> {
    List<KPack> getAllKPacksById(Long id);

    List<KPackSet> getAllWithFilteringAndSorting(String sortBy, String id, String title);

    void addKPacksToSet(Long id, List<Long> kpackIds);
}
