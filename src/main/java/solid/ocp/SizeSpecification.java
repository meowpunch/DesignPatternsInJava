package solid.ocp;

record SizeSpecification(Size size) implements Specification<Product> {

    @Override
    public boolean isSatisfied(Product item) {
        return item.size() == size;
    }
}
