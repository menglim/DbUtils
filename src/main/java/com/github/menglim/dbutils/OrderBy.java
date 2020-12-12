package com.github.menglim.dbutils;

public class OrderBy {

    public static class Builder<T> {
        private String orderBy = "";
        private T newInstance;
        private DbFactoryHelper<T> factoryHelper;

        public Builder(T newInstance) {
            this.newInstance = newInstance;
            factoryHelper = new DbFactoryHelper<>();
        }

        public Builder orderBy(OrderBy.Direction direction, String... objectFieldNames) {
            String orderFiledNames = "";
            for (String aFieldName : objectFieldNames
            ) {
                String fieldName = factoryHelper.getFieldName(newInstance, aFieldName);
                orderFiledNames = orderFiledNames + fieldName + ",";
            }
            if (orderFiledNames.endsWith(",")) {
                orderFiledNames = orderFiledNames.substring(orderFiledNames.length() - 1);
            }
            this.orderBy = " ORDER BY " + orderFiledNames + " " + direction.toString();
            return this;
        }

        public String build() {
            return this.orderBy;
        }
    }

    public enum Direction {
        ASC,
        DESC
    }
}


