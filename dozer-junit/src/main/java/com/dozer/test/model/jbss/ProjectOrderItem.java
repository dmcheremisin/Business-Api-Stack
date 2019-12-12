package com.dozer.test.model.jbss;

import java.math.BigDecimal;

public class ProjectOrderItem {
    private int projectOiId;
    private String projectOiName;
    private BigDecimal projectOiPrice;

    public int getProjectOiId() {
        return projectOiId;
    }

    public void setProjectOiId(int projectOiId) {
        this.projectOiId = projectOiId;
    }

    public String getProjectOiName() {
        return projectOiName;
    }

    public void setProjectOiName(String projectOiName) {
        this.projectOiName = projectOiName;
    }

    public BigDecimal getProjectOiPrice() {
        return projectOiPrice;
    }

    public void setProjectOiPrice(BigDecimal projectOiPrice) {
        this.projectOiPrice = projectOiPrice;
    }

    @Override
    public String toString() {
        return "ProjectOrderItem{" +
                "projectOiId=" + projectOiId +
                ", projectOiName='" + projectOiName + '\'' +
                ", projectOiPrice=" + projectOiPrice +
                '}';
    }
}
