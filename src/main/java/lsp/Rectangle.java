package lsp;

public class Rectangle {
    private Integer height, width;

    public Rectangle() {
    }

    public Rectangle(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    public Integer getArea() {
        return height * width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
