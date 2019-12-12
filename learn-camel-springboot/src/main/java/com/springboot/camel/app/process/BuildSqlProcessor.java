package com.springboot.camel.app.process;

import com.springboot.camel.app.domain.Item;
import com.springboot.camel.app.exceptions.DataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class BuildSqlProcessor implements Processor {

    public static final String TABLE_NAME = "ITEMS";

    @Override
    public void process(Exchange exchange) throws Exception {
        Item item = (Item) exchange.getIn().getBody();
        log.info("Item in Processor is : " + item);

        StringBuilder query = new StringBuilder();

        if(StringUtils.isEmpty(item.getSku())) {
            throw new DataException("Sku is null for " + item.getItemDescription());
        }

        if(item.getTransactionType().equals("ADD")) {
            query.append("INSERT INTO " + TABLE_NAME + " (SKU, ITEM_DESCRIPTION, PRICE) VALUES ('");
            query.append(item.getSku() + "','" + item.getItemDescription() + "'," + item.getPrice() + ")");

        } else if(item.getTransactionType().equals("UPDATE")) {
            query.append("UPDATE " + TABLE_NAME + " SET PRICE=");
            query.append(item.getPrice() + " WHERE SKU = '" + item.getSku() + "'");

        } else if(item.getTransactionType().equals("DELETE")) {
            query.append("DELETE FROM " + TABLE_NAME +  " WHERE SKU = '" + item.getSku() + "'");
        }

        log.info("Final query is : " + query);
        exchange.getIn().setBody(query);
    }
}
