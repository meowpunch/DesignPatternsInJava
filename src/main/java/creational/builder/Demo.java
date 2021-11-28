package creational.builder;


public class Demo {

    public static void main(String[] args) {
        // we want to build a simple HTML paragraph
        System.out.println("Testing");
        String hello = "hello";
        StringBuilder sb = new StringBuilder();
        sb.append("<p>")
                .append(hello)
                .append("</p>"); // a builder!
        System.out.println(sb);

        // now we want to build a list with 2 words
        String[] words = {"hello", "world"};
        sb.setLength(0); // clear it
        sb.append("<ul>\n");
        for (String word : words) {
            // indentation management, line breaks and other evils
            sb.append(String.format("  <li>%s</li>\n", word));
        }
        sb.append("</ul>");
        System.out.println(sb);

        // ordinary non-fluent builder
        HtmlBuilder builder = new HtmlBuilder("ul");
        builder.addChild("li", "hello");
        builder.addChild("li", "world");
        System.out.println(builder);

        // fluent builder
        builder.clear();
        builder.addChildFluent("li", "hello")
                .addChildFluent("li", "world");
        System.out.println(builder);



        // Class Builder
        CodeBuilder cb = new CodeBuilder("Person").addField("age", "int").addField("name", "String");
        System.out.println(cb);
    }
}
