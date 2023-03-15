package auros.repository.impl;

import auros.exception.DataProcessingException;
import auros.model.KPack;
import auros.repository.DynamicQueryBuilder;
import auros.repository.KPackRepository;
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
public class KPackRepositoryImpl implements KPackRepository, DynamicQueryBuilder {
    private final JdbcTemplate jdbcTemplate;

    public KPackRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public KPack add(KPack entity) {
        String query = "INSERT INTO k_packs (title, description, date) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setString(3, toSqlFormal(entity.getCreationDate()));
            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public void delete(Long id) {
        String[] deleteQueries = new String[]{"DELETE FROM k_packs_k_pack_sets WHERE k_pack_id = ?",
                "DELETE FROM k_packs where id = ?"};
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
    public List<KPack> getAllWithFilteringAndSorting(String[] sortingParams,
                                                     Map<String, Object[]> filteringParams) {
        String query = buildQueryWithSortingAndFiltering(
                sortingParams, filteringParams, "k_packs");
        return jdbcTemplate.query(connection ->
                        getPreparedStatement(connection, query, filteringParams),
                this::parseKpack);
    }

    @Override
    public List<KPack> getAllKPacksBySetId(Long id) {
        String query = "SELECT * FROM k_packs kp "
                + "JOIN k_packs_k_pack_sets kpkps on kp.id = kpkps.k_pack_id "
                + "WHERE k_pack_set_id = ?";
        return jdbcTemplate.query(connection -> {
            PreparedStatement statement =
                    connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement;
        }, this::parseKpack);
    }

    private KPack parseKpack(ResultSet resultSet, int rowNum) {
        KPack kpack = new KPack();
        try {
            kpack.setId(resultSet.getLong("id"));
            kpack.setTitle(resultSet.getString("title"));
            kpack.setDescription(resultSet.getString("description"));
            kpack.setCreationDate(resultSet.getString("date"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get kPacks with provided input parameters", e);
        }
        return kpack;
    }

    private String toSqlFormal(String creationDate) {
        String[] date = creationDate.split("-");
        return date[2] + date[1] + date[0];
    }
}

