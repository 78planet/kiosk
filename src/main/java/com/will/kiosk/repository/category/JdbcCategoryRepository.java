package com.will.kiosk.repository.category;

import com.will.kiosk.model.Category;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.will.kiosk.JdbcUtils.toLocalDateTime;

@Repository
public class JdbcCategoryRepository implements CategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCategoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category insert(Category category) {
        var update = jdbcTemplate.update("INSERT INTO category(category_id, category_name, description, created_at, updated_at)" +
                " VALUES(:categoryId, :categoryName, :description, :createdAt, :updatedAt)", toParamMap(category));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from category", categoryRowMapper);
    }

    @Override
    public Category update(Category category) {
        var update = jdbcTemplate.update(
                "UPDATE category SET category_name = :categoryName, description = :description, created_at = :createdAt, updated_at = :updatedAt" +
                        " WHERE category_id = :categoryId", toParamMap(category));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return category;
    }

    @Override
    public void delete(Category category) {
        var update = jdbcTemplate.update(
                "DELETE FROM category WHERE category_id = :categoryId",
                     Collections.singletonMap("categoryId", category.getCategoryId()));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    @Override
    public Optional<Category> findById(int categoryId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM category WHERE category_id = :categoryId",
                    Collections.singletonMap("categoryId", categoryId),
                    categoryRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final RowMapper<Category> categoryRowMapper = (resultSet, i) -> {
        var categoryId = resultSet.getInt("category_id");
        var categoryName = resultSet.getString("category_name");
        var description = resultSet.getString("description");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Category(categoryId, categoryName, description, createdAt, updatedAt);
    };

    private final Map<String, Object> toParamMap(Category category) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("categoryId", category.getCategoryId());
        paramMap.put("categoryName", category.getCategoryName());
        paramMap.put("description", category.getDescription());
        paramMap.put("createdAt", category.getCreatedAt());
        paramMap.put("updatedAt", category.getUpdatedAt());
        return paramMap;
    }
}
