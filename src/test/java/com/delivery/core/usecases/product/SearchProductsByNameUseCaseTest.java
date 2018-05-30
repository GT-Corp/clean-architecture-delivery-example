package com.delivery.core.usecases.product;

import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SearchProductsByNameUseCaseTest {

    @InjectMocks
    private SearchProductsByNameUseCase useCase;

    @Mock
    private ProductRepository repository;

    @Test
    public void executeReturnsMatchingProducts() {
        // given
        String searchText = "searchText";
        Product product = TestCoreEntityGenerator.randomProduct();

        // and
        doReturn(Collections.singletonList(product))
                .when(repository)
                .searchByName(searchText);

        // when
        List<Product> actual = useCase.execute(searchText);

        // then
        assertThat(actual).containsOnly(product);
    }
}