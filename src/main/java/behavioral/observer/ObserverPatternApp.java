package behavioral.observer;

public class ObserverPatternApp {

  public static void main(String[] args) {

    Observer<Person> ob1 = p -> System.out.println("ob1 consume " + p.source());
    Observer<Person> ob2 = p -> System.out.println("ob2 consume " + p.source());

    Person p = new Person("Jinhoon", 29);

    p.subscribe(ob1, ob2);

    p.setAge(30);
    p.setAge(31);
  }

}
