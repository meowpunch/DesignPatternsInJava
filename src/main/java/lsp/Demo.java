package lsp;

public class Demo {

    private static Integer violateLSP(Rectangle r) {
        r.setHeight(10);
        return r.getArea();
    }

    public static void main(String[] args) {
        Rectangle r = new Rectangle(3, 4);
        System.out.println(violateLSP(r) == 40);

        Rectangle s = new Square();
        s.setWidth(4);
        System.out.println(violateLSP(s) != 40);

        /*
            Theoretically, Square IS A Rectangle

            In above case, Implementation violate Liskov Substitution Principles
            - we don't need the Square class, remove the class or inheritance or use immutable data.
            - create Square, using RectangleFactory.
         */
    }
}
