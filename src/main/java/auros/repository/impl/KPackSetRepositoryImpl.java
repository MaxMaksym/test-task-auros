package auros.repository.impl;

import auros.exception.DataProcessingException;
import auros.model.KPackSet;
import auros.repository.DynamicQueryBuilder;
import auros.repository.KPackSetRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class KPackSetRepositoryImpl implements KPackSetRepository, DynamicQueryBuilder {
    private final JdbcTemplate jdbcTemplate;

    public KPackSetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public KPackSet add(KPackSet entity) {
        String query = "INSERT INTO k_pack_sets(title) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getTitle());
            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public void delete(Long id) {
        String[] deleteQueries = new String[]{
                "DELETE FROM k_packs_k_pack_sets WHERE k_pack_set_id = ?",
                "DELETE FROM k_pack_sets where id = ?"};
        for (String deleteQuery : deleteQueries) {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement(deleteQuery);
                statement.setLong(1, id);
                return statement;
            });
        }
    }

    @Override
    public void addKPacksToSet(Long setId, List<Long> kpackIds) {
        String query = "INSERT INTO k_packs_k_pack_sets(k_pack_set_id, k_pack_id) values(?, ?)";
        for (Long kpackId : kpackIds) {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, setId);
                statement.setLong(2, kpackId);
                return statement;
            });
        }
    }

    @Override
    public List<KPackSet> getAllWithFilteringAndSorting(String[] sortingParams,
                                                        Map<String, Object[]> filteringParams) {
        String query =
                buildQueryWithSortingAndFiltering(sortingParams, filteringParams, "k_pack_sets");
        return jdbcTemplate.query(connection ->
                        getPreparedStatement(connection, query, filteringParams),
                this::parseKPackSet);
    }

    private KPackSet parseKPackSet(ResultSet resultSet, int i) {
        KPackSet kpackSet = new KPackSet();
        try {
            kpackSet.setId(resultSet.getLong("id"));
            kpackSet.setTitle(resultSet.getString("title"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse k-pack", e);
        }
        return kpackSet;
    }
}
