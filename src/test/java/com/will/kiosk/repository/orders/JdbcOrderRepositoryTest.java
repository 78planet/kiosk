package com.will.kiosk.repository.orders;

import com.will.kiosk.model.Category;
import com.will.kiosk.model.OrderStatus;
import com.will.kiosk.model.Orders;
import com.will.kiosk.repository.category.JdbcCategoryRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcOrderRepositoryTest {

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
    JdbcOrdersRepository orderRepository;

    private Category newCategory = new Category(1, "국", "국물", LocalDateTime.now());

    private Orders newOrders = new Orders(UUID.randomUUID(), OrderStatus.ORDER_ACCEPTED, 12, LocalDateTime.now());

    @Test
    @Order(1)
    void testInsert() {
        orderRepository.insert(newOrders);
        var all = orderRepository.findAll();
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @Order(2)
    @DisplayName("주문 상태를 수정할 수 있다.")
    void testUpdate() {
        newOrders.setOrderStatus(OrderStatus.PAYMENT_COMPLETED);
        orderRepository.update(newOrders);

        var category = orderRepository.findById(newOrders.getOrderId());
        assertThat(category.isEmpty()).isFalse();
        MatcherAssert.assertThat(category.get(), samePropertyValuesAs(newOrders));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 삭제할 수 있다.")
    void testDelete() {
        orderRepository.delete(newOrders);

        var category = orderRepository.findById(newOrders.getOrderId());
        assertThat(category.isEmpty()).isTrue();
    }
}