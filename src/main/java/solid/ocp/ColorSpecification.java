package solid.ocp;

record ColorSpecification(Color color) implements Specification<Product> {

    @Override
    public boolean isSatisfied(Product item) {
        return item.color() == color;
    }
}
