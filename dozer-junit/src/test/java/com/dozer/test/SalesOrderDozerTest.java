package com.dozer.test;

import com.dozer.test.builder.DozerBuilder;
import com.dozer.test.model.jbss.ProjectOrderItem;
import com.dozer.test.model.jbss.ProjectSalesOrder;
import com.dozer.test.model.product.OrderItem;
import com.dozer.test.model.product.SalesOrder;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SalesOrderDozerTest {

    private DozerBeanMapper mapper;
    private SalesOrder salesOrder;

    @Before
    public void configure() {
        DozerBuilder dozerBuilder = new DozerBuilder();
        mapper = new DozerBeanMapper();
        mapper.addMapping(dozerBuilder);
    }

    @Before
    public void createSalesOrder() {
        salesOrder = new SalesOrder();
        salesOrder.setId(12345);
        salesOrder.setName("Super Sales Order");

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1);
        orderItem1.setName("First");
        orderItem1.setPrice(20.2);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2);
        orderItem2.setName("Second");
        orderItem2.setPrice(30.3);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        salesOrder.setOrderItems(orderItems);
    }

    @Test
    public void testSalesOrder() {
        ProjectSalesOrder projectSalesOrder = mapper.map(salesOrder, ProjectSalesOrder.class);

        assertTrue(projectSalesOrder != null);
        assertTrue(projectSalesOrder.getProjectSoId().equals(new BigInteger("12345")));
        assertTrue(projectSalesOrder.getProjectSoName().equals("Super Sales Order"));
    }

    @Test
    public void testOrderItems() {
        ProjectSalesOrder projectSalesOrder = mapper.map(salesOrder, ProjectSalesOrder.class);

        assertTrue(projectSalesOrder.getProjectOrderItems() != null);
        assertFalse(projectSalesOrder.getProjectOrderItems().isEmpty());
    }

    @Test
    public void testFirstOrderItem() {
        ProjectSalesOrder projectSalesOrder = mapper.map(salesOrder, ProjectSalesOrder.class);

        ProjectOrderItem firstOrderItem = projectSalesOrder.getProjectOrderItems().get(0);
        assertTrue(firstOrderItem.getProjectOiId() == 1);
        assertTrue(firstOrderItem.getProjectOiName().equals("First"));
        assertTrue(firstOrderItem.getProjectOiPrice().equals(new BigDecimal("20.2")));
    }

    @Test
    public void testSecondOrderItem() {
        ProjectSalesOrder projectSalesOrder = mapper.map(salesOrder, ProjectSalesOrder.class);

        ProjectOrderItem secondOrderItem = projectSalesOrder.getProjectOrderItems().get(1);
        assertTrue(secondOrderItem.getProjectOiId() == 2);
        assertTrue(secondOrderItem.getProjectOiName().equals("Second"));
        assertTrue(secondOrderItem.getProjectOiPrice().equals(new BigDecimal("30.3")));
    }
}
