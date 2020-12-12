package com.github.menglim.dbutils;

public class Condition {
    public static class Builder<T> {
        private String conditional = "";
        private T newInstance;
        private DbFactoryHelper<T> factoryHelper;

        public Builder(T newInstance) {
            this.newInstance = newInstance;
            factoryHelper = new DbFactoryHelper<>();
        }

        public Builder where(String objectFieldName) {
            String fieldName = factoryHelper.getFieldName(newInstance, objectFieldName);
            this.conditional = " WHERE " + fieldName + "=:" + fieldName;
            return this;
        }

        public Builder and(String objectFieldName) {
            String fieldName = factoryHelper.getFieldName(newInstance, objectFieldName);
            this.conditional = this.conditional + " AND " + fieldName + "=:" + fieldName;
            return this;
        }

        public Builder or(String objectFieldName) {
            String fieldName = factoryHelper.getFieldName(newInstance, objectFieldName);
            this.conditional = this.conditional + " OR " + fieldName + "=:" + fieldName;
            return this;
        }

        public Builder andLike(String objectFieldName) {
            String fieldName = factoryHelper.getFieldName(newInstance, objectFieldName);
            this.conditional = this.conditional + " AND LIKE " + fieldName + "=%:" + fieldName + "%";
            return this;
        }

        public Builder orderBy(String objectFieldName) {
            String fieldName = factoryHelper.getFieldName(newInstance, objectFieldName);
            this.conditional = " ORDER BY " + fieldName;
            return this;
        }

        public String build() {
            return this.conditional;
        }

        public T getInstance() {
            return newInstance;
        }
    }
}
