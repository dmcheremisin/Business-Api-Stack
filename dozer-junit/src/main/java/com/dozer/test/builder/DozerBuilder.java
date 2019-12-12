package com.dozer.test.builder;

import com.dozer.test.model.jbss.ProjectOrderItem;
import com.dozer.test.model.jbss.ProjectSalesOrder;
import com.dozer.test.model.product.OrderItem;
import com.dozer.test.model.product.SalesOrder;
import org.dozer.loader.api.BeanMappingBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.dozer.loader.api.FieldsMappingOptions.hintA;
import static org.dozer.loader.api.FieldsMappingOptions.hintB;

public class DozerBuilder extends BeanMappingBuilder {
    protected void configure() {
        mapping(SalesOrder.class, ProjectSalesOrder.class)
                .fields("id", "projectSoId",
                        hintA(int.class),
                        hintB(BigInteger.class))
                .fields("name", "projectSoName")
                .fields("orderItems", "projectOrderItems");

        mapping(OrderItem.class, ProjectOrderItem.class)
                .fields("id", "projectOiId")
                .fields("name", "projectOiName")
                .fields("price", "projectOiPrice",
                        hintA(Double.class),
                        hintB(BigDecimal.class));
    }
}
