package com.will.kiosk.repository.category;


import com.will.kiosk.model.Category;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCategoryRepositoryTest {


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
    JdbcCategoryRepository categoryRepository;

    private Category newCategory = new Category(1, "국", "국물", LocalDateTime.now());


    @Test
    @Order(1)
    void testInsert() {
        categoryRepository.insert(newCategory);
        var all = categoryRepository.findAll();
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @Order(2)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newCategory.setCategoryName("updated-category");
        categoryRepository.update(newCategory);

        var category = categoryRepository.findById(newCategory.getCategoryId());
        assertThat(category.isEmpty()).isFalse();
        assertThat(category.get(), samePropertyValuesAs(newCategory));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 삭제할 수 있다.")
    void testDelete() {
        categoryRepository.delete(newCategory);

        var category = categoryRepository.findById(newCategory.getCategoryId());
        assertThat(category.isEmpty()).isTrue();
    }

}