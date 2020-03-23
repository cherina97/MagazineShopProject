package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private String description;
    private float price;

    public static class Builder {
        private Product product;

        public Builder() {
            product = new Product();
        }
        public Product.Builder withId(int id){
            product.id = id;
            return this;
        }

        public Product.Builder withName(String name){
            product.name = name;
            return this;
        }
        public Product.Builder withDescription(String description){
            product.description = description;
            return this;
        }
        public Product.Builder withPrice(float price){
            product.price = price;
            return this;
        }
        public Product build(){
            return product;
        }
    }
    public static Product of (ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Float price = resultSet.getFloat("price");

            return new Product.Builder()
                    .withId(id)
                    .withName(name)
                    .withDescription(description)
                    .withPrice(price)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Float.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
