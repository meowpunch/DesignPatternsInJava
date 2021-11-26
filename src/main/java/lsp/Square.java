package lsp;

public class Square extends Rectangle {

    @Override
    public void setHeight(Integer height) {
        super.setHeight(height);
        super.setWidth(height);

    }

    @Override
    public void setWidth(Integer width) {
        super.setWidth(width);
        super.setHeight(width);
    }
}
