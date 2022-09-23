package eu.hillel.hw18.entity;


public class Nomenclature {
    private int id;
    private String name;
    private String description;
    private double price;

    public int getId() {
        return id;
    }

    public Nomenclature(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price;
    }
}
