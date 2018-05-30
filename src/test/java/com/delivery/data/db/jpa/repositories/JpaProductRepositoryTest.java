package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.ProductData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaProductRepositoryTest {

    @Autowired
    private JpaProductRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    @EntityScan("com.delivery.data.db.jpa.entities")
    static class Config{}

    @Test
    public void findByNameContainingIgnoreCase() {
        // given
        CousineData cousineData = entityManager.persistFlushFind(CousineData.withName("name"));
        StoreData storeData = entityManager.persistFlushFind(StoreData.withNameAndCousine("name", cousineData));

        Arrays.stream(new String[]{"aAbc", "abCc", "abBc"})
                .forEach(name -> {
                    entityManager.persistAndFlush(ProductData.withNameAndStore(name, storeData));
                });

        // when
        List<ProductData> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("aAbc", "abCc");
    }
}