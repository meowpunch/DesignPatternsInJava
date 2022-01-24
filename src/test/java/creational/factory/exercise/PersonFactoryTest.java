package creational.factory.exercise;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PersonFactoryTest {

    @Test
    void createPerson() {
        // given
        final PersonFactory pf = new PersonFactory();
        final String[] names = new String[] {"Marcel", "Tony", "Jinhoon"};

        for (int i = 0; i < names.length; i++) {
            // when
            final Person p = pf.createPerson(names[i]);

            // then
            assertEquals(i, p.id());
        }
    }
}