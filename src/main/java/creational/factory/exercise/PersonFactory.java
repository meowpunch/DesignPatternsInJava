package creational.factory.exercise;

import java.util.stream.Stream;

record Person(int id, String name) {}

public class PersonFactory {
    int numOfPerson = 0;

    public Person createPerson(String name) {
        return new Person(numOfPerson++, name);
    }
}