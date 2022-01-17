package creational.builder.exercise;

import java.util.ArrayList;
import java.util.List;


public class CodeBuilder {
    private record Field(String name, String type) {
    }

    private final String className;
    private final List<Field> fields = new ArrayList<>();
    private final int indentSize = 4;


    public CodeBuilder(String className) {
        this.className = className;
    }

    public CodeBuilder addField(String name, String type) {
        fields.add(new Field(name, type));
        return this;
    }

    public String buildCode() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("public class %s\n", className));
        sb.append("{\n");
        fields.forEach(field -> sb.append(String.format("%" + indentSize + "s" + "public %s %s;\n", "", field.type, field.name)));
        sb.append("}\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return buildCode();
    }
}