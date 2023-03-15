package auros.service;

import auros.model.KPack;
import java.util.List;

public interface KPackService extends GenericService<KPack> {
    List<KPack> getAllWithFilteringAndSorting(String sortBy,
                                              String id,
                                              String title,
                                              String description,
                                              String creationDate);
}
