package auros.service.impl;

import auros.model.KPack;
import auros.model.KPackSet;
import auros.repository.KPackRepository;
import auros.repository.KPackSetRepository;
import auros.service.KPackSetService;
import auros.service.Validation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class KPackSetServiceImpl implements KPackSetService, Validation {
    private static final Map<String, String> FILTERING_PARAMS_AND_COLUMNS;
    private final KPackSetRepository kpackSetRepository;
    private final KPackRepository kpackRepository;

    static {
        FILTERING_PARAMS_AND_COLUMNS = Map.of(
                "title", "title",
                "id", "id");
    }

    public KPackSetServiceImpl(KPackSetRepository kpackSetRepository,
                               KPackRepository kpackRepository) {
        this.kpackSetRepository = kpackSetRepository;
        this.kpackRepository = kpackRepository;
    }

    @Override
    public KPackSet add(KPackSet entity) {
        return kpackSetRepository.add(entity);
    }

    @Override
    public void delete(Long id) {
        kpackSetRepository.delete(id);
    }

    @Override
    public List<KPack> getAllKPacksById(Long id) {
        return kpackRepository.getAllKPacksBySetId(id);
    }

    @Override
    public List<KPackSet> getAllWithFilteringAndSorting(String sortBy, String id, String title) {
        Map<String, Object[]> filteringParams = new HashMap<>();
        if (id != null) {
            checkIfIdIsValid(id.split(";"));
        }
        addParameterToMap(id, "id", filteringParams);
        addParameterToMap(title, "title", filteringParams);
        String[] sortingParams = getSortingParams(sortBy, FILTERING_PARAMS_AND_COLUMNS);
        return kpackSetRepository.getAllWithFilteringAndSorting(sortingParams, filteringParams);
    }

    private void addParameterToMap(String parameter, String paramName,
                                   Map<String, Object[]> paramsMap) {
        if (parameter != null) {
            String[] values = parameter.split(";");
            paramsMap.put(paramName, values);
        }
    }

    @Override
    public void addKPacksToSet(Long id, List<Long> kpackIds) {
        kpackSetRepository.addKPacksToSet(id, kpackIds);
    }
}
