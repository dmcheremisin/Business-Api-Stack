package com.dozer.test;

import com.dozer.test.builder.DozerBuilder;
import com.dozer.test.model.jbss.ProjectSalesOrder;
import com.dozer.test.model.product.OrderItem;
import com.dozer.test.model.product.SalesOrder;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        DozerBuilder dozerBuilder = new DozerBuilder();
        mapper.addMapping(dozerBuilder);

        SalesOrder salesOrder = new SalesOrder();
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

        ProjectSalesOrder projectSalesOrder = mapper.map(salesOrder, ProjectSalesOrder.class);
        System.out.println(projectSalesOrder.getProjectSoId());
        System.out.println(projectSalesOrder.getProjectSoName());
        System.out.println(projectSalesOrder.getProjectOrderItems());

    }
}
