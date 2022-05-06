package com.will.kiosk.repository.product;

import com.will.kiosk.model.Product;
import com.will.kiosk.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.will.kiosk.JdbcUtils.toLocalDateTime;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product insert(Product product) {
        var update = jdbcTemplate.update("INSERT INTO product(product_name, description, price, category_id, created_at)" +
                " VALUES(:productName, :description, :price, :categoryId, :createdAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", productRowMapper);
    }

    @Override
    public Product update(Product product) {
        var update = jdbcTemplate.update(
                "UPDATE product SET product_name = :productName, description = :description, price = :price, category_id = :categoryId, updated_at = :updatedAt" +
                        " WHERE product_id = :productId", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return product;
    }

    @Override
    public void delete(int productId) {
        var update = jdbcTemplate.update(
                "DELETE FROM product WHERE product_id = :productId",
                Collections.singletonMap("productId", productId));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    @Override
    public Optional<Product> findById(int productId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM product WHERE product_id = :productId",
                    Collections.singletonMap("productId", productId),
                    productRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findProductsByCategory(int categoryId) {
        return jdbcTemplate.query("select * from product where category_id = :categoryId",
                Collections.singletonMap("categoryId", categoryId),
                productRowMapper);
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        var productId = resultSet.getInt("product_id");
        var productName = resultSet.getString("product_name");
        var description = resultSet.getString("description");
        var price = resultSet.getInt("price");
        var categoryId = resultSet.getInt("category_id");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Product(productId, productName, description, price, categoryId, createdAt, updatedAt);
    };

    private final Map<String, Object> toParamMap(Product product) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("productId", product.getProductId());
        paramMap.put("productName", product.getProductName());
        paramMap.put("description", product.getDescription());
        paramMap.put("price", product.getPrice());
        paramMap.put("categoryId", product.getCategoryId());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());
        return paramMap;
    }
}
