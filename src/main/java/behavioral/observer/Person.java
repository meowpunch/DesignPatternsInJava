package behavioral.observer;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
class Person extends Observable<Person>{
  private final String name;
  private int age;

  public void setAge(int age) {
    if (this.age != age) {
      this.age = age;
      propertyChanged(this, "age", age);
    }
  }
}
