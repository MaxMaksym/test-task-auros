package auros.service;

import auros.exception.UnsupportedFormatException;
import java.util.Map;

public interface Validation {
    default String[] getSortingParams(String sortBy, Map<String,
            String> filteringParamsAndColumns) {
        String[] params = sortBy.split(";");
        String[] sortColumns = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            checkIfSortingParamIsValid(params[i], filteringParamsAndColumns);
            sortColumns[i] = params[i].replace(":", " ");
        }
        return sortColumns;
    }

    default void checkIfIdIsValid(String[] ids) {
        for (String id : ids) {
            if (!id.matches("\\d+")) {
                throw new UnsupportedFormatException("idParams should contain only digits");
            }
        }
    }

    private void checkIfSortingParamIsValid(String param,
                                            Map<String, String> filteringParamsAndColumns) {
        String[] paramAndOrder = param.split(":");
        if (paramAndOrder.length == 1
                && !filteringParamsAndColumns.containsKey(paramAndOrder[0])) {
            throw new UnsupportedFormatException(String.format(
                    "Filtering parameter %s does not supported", paramAndOrder[0]));
        } else if (paramAndOrder.length == 2
                && !filteringParamsAndColumns.containsKey(paramAndOrder[0])
                && !paramAndOrder[1].equalsIgnoreCase("desc")
                && !paramAndOrder[1].equalsIgnoreCase("asc")) {
            throw new UnsupportedFormatException(String.format(
                    "Order parameter %s or filtering parameter %s does not supported",
                    paramAndOrder[0], paramAndOrder[1]));
        }
    }
}
