package com.dozer.test.model.jbss;

import java.math.BigInteger;
import java.util.List;

public class ProjectSalesOrder {
    private BigInteger projectSoId;
    private String projectSoName;

    private List<ProjectOrderItem> projectOrderItems;

    public BigInteger getProjectSoId() {
        return projectSoId;
    }

    public void setProjectSoId(BigInteger projectSoId) {
        this.projectSoId = projectSoId;
    }

    public String getProjectSoName() {
        return projectSoName;
    }

    public void setProjectSoName(String projectSoName) {
        this.projectSoName = projectSoName;
    }

    public List<ProjectOrderItem> getProjectOrderItems() {
        return projectOrderItems;
    }

    public void setProjectOrderItems(List<ProjectOrderItem> projectOrderItems) {
        this.projectOrderItems = projectOrderItems;
    }
}
