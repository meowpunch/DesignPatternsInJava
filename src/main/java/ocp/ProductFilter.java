package ocp;

import java.util.List;
import java.util.stream.Stream;


class ProductFilter {
    /*
        imagine the situation where you have to add other criteria such as price
     */
    Stream<Product> filterByColor(List<Product> product, Color color) {
        return product.stream().filter(p -> p.color() == color);
    }

    Stream<Product> filterBySize(List<Product> product, Size size) {
        return product.stream().filter(p -> p.size() == size);
    }

    Stream<Product> filterByCColorAndSize(List<Product> product, Color color, Size size) {
        return product.stream().filter(p -> p.color() == color && p.size() == size);
    }
}

