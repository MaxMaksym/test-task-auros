package auros.repository;

import auros.exception.DataProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface DynamicQueryBuilder {
    default PreparedStatement getPreparedStatement(Connection connection, String query,
                                                   Map<String, Object[]> filteringParams) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement(query);
            int valueIndex = 1;
            for (Map.Entry<String, Object[]> entry : filteringParams.entrySet()) {
                for (int i = 0; i < entry.getValue().length; i++) {
                    Object value = entry.getValue()[i];
                    if (value.getClass().equals(String.class)) {
                        statement.setString(valueIndex, (String) value);
                    } else if (value.getClass().equals(Long.class)) {
                        statement.setLong(valueIndex, (Long) value);
                    }
                    valueIndex++;
                }
            }
            return statement;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data fom database", e);
        }
    }

    default String buildQueryWithSortingAndFiltering(String[] sortingParams,
                                                     Map<String, Object[]> filteringParams,
                                                     String tableName) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
        queryBuilder.append(tableName);
        List<String> conditions = new ArrayList<>();
        if (!filteringParams.isEmpty()) {
            queryBuilder.append(" WHERE ");
        }
        for (Map.Entry<String, Object[]> entry : filteringParams.entrySet()) {
            String column = entry.getKey();
            Object[] values = entry.getValue();
            String placeholders = String.join(",", Collections.nCopies(values.length, "?"));
            String condition = String.format("%s IN (%s)", column, placeholders);
            conditions.add(condition);
        }
        queryBuilder.append(String.join(" AND ", conditions));
        queryBuilder.append(" ORDER BY ");
        queryBuilder.append(String.join(",", sortingParams));
        return queryBuilder.toString();
    }
}
