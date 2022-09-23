package eu.hillel.hw18.entity;

public class OrderNomenclature {
    private Nomenclature nomenclature;
    private double count;

    public OrderNomenclature(Nomenclature nomenclature, double count) {
        this.nomenclature = nomenclature;
        this.count = count;

    }

    public double getCount() {
        return count;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    @Override
    public String toString() {
        return nomenclature + ", count=" + count;
    }
}
