package solid.ocp;

import java.util.List;


public class Demo {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("apple", Color.GREEN, Size.S),
                new Product("tree", Color.GREEN, Size.L),
                new Product("house", Color.RED, Size.XL)
        );

        new ProductViolatedFilter().filterByColor(products, Color.GREEN)
                .forEach(System.out::println);

        new ProductOCPFilter().filter(products, new ColorSpecification(Color.GREEN))
                .forEach(System.out::println);

        new ProductOCPFilter().filter(products, new AndSpecification<>(new ColorSpecification(Color.GREEN), new SizeSpecification(Size.L)))
                .forEach(System.out::println);
    }

}
