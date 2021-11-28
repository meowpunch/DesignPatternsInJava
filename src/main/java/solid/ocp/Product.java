package solid.ocp;


record Product(String name, Color color, Size size) {

    @Override
    public String toString() {
        return this.name;
    }
}

