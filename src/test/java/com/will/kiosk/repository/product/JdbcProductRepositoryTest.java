package com.will.kiosk.repository.product;


import com.will.kiosk.model.Category;
import com.will.kiosk.model.Product;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import com.will.kiosk.repository.category.JdbcCategoryRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcProductRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-kiosk", ScriptResolver.classPathScript("schema-kiosk.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    JdbcProductRepository productRepository;

    @Autowired
    JdbcCategoryRepository categoryRepository;

    private Product newProduct = new Product(1, "참이슬", "쥑입니다", 4500, 1, LocalDateTime.now(), LocalDateTime.now());
    private List<Product> newProductList = List.of(
            new Product(4, "참이슬4", "쥑입니다", 4500, 1, LocalDateTime.now(), LocalDateTime.now()),
            new Product(5, "참이슬5", "쥑입니다", 4500, 1, LocalDateTime.now(), LocalDateTime.now()),
            new Product(2, "참이슬2", "쥑입니다", 4500, 2, LocalDateTime.now(), LocalDateTime.now()),
            new Product(3, "참이슬3", "쥑입니다", 4500, 3, LocalDateTime.now(), LocalDateTime.now())

    );
    private List<Category> newCategory = List.of(
            new Category(1, "쥬류", "국물", LocalDateTime.now()),
            new Category(2, "주류2", "국물2", LocalDateTime.now()),
            new Category(3, "주류3", "국물3", LocalDateTime.now())
    );

    @Test
    @Order(1)
    void testCategoryInsert() {
        newCategory.forEach(categoryRepository::insert);
        var all = categoryRepository.findAll();
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @Order(2)
    void testProductInsert() {
        newProductList.forEach(productRepository::insert);
        var all = productRepository.findAll();
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @Order(3)
    void testFindProductsByCategory() {
        var productsByCategory = productRepository.findProductsByCategory(1);
        assertThat(productsByCategory.stream().count()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @Order(3)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        productRepository.update(newProduct);

        var product = productRepository.findById(newProduct.getProductId());
        assertThat(product.isEmpty()).isFalse();
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(4)
    @DisplayName("상품을 삭제할 수 있다.")
    void testDelete() {
        productRepository.delete(newProduct);

        var category = productRepository.findById(newProduct.getProductId());
        assertThat(category.isEmpty()).isTrue();
    }

}