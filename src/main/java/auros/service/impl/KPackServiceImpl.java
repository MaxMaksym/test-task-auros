package auros.service.impl;

import auros.model.KPack;
import auros.repository.KPackRepository;
import auros.service.KPackService;
import auros.service.Validation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class KPackServiceImpl implements KPackService, Validation {
    private static final Map<String, String> FILTERING_PARAMS_AND_COLUMNS;
    private static final List<String> ORDER;
    private final KPackRepository kpackRepository;

    static {
        FILTERING_PARAMS_AND_COLUMNS = Map.of(
                "date", "creation_date",
                "description", "description",
                "title", "title",
                "id", "id");
        ORDER = List.of(
                "desc", "asc");
    }

    public KPackServiceImpl(KPackRepository kpackRepository) {
        this.kpackRepository = kpackRepository;
    }

    @Override
    public KPack add(KPack kpack) {
        return kpackRepository.add(kpack);
    }

    @Override
    public void delete(Long id) {
        kpackRepository.delete(id);
    }

    @Override
    public List<KPack> getAllWithFilteringAndSorting(String sortBy, String id, String title,
                                                     String description, String date) {
        Map<String, Object[]> filteringParams = new HashMap<>();
        if (id != null) {
            checkIfIdIsValid(id.split(";"));
        }
        addParameterToMap(id, "id", filteringParams);
        addParameterToMap(title, "title", filteringParams);
        addParameterToMap(description, "description", filteringParams);
        addParameterToMap(date, "creation_date", filteringParams);
        String[] sortingParams = getSortingParams(sortBy, FILTERING_PARAMS_AND_COLUMNS);
        return kpackRepository.getAllWithFilteringAndSorting(sortingParams, filteringParams);
    }

    private void addParameterToMap(String parameter, String paramName,
                                   Map<String, Object[]> paramsMap) {
        if (parameter != null) {
            String[] values = parameter.split(";");
            paramsMap.put(paramName, values);
        }
    }
}
