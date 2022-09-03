package concurrency.reactive;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.ToString;

@SuppressWarnings("deprecation")
public class Ob {

  /*
   *   Observable : Publisher - data source
   *   Observer : Subscriber - consume data
   *
   *   Limitation
   *   1. how to know it's completed - onComplete
   *   2. how to handle error - onError
   */
  public static void main(String[] args) {

    testObserver(new IntObservable());
    testObserver(new Person("Jinhoon", 29));
  }

  private static void testObserver(RunnableObservable runnableObservable) {
    Observer ob1 = (o, arg) -> System.out.println(Thread.currentThread().getName() + " ob1 consume " + arg); // override `void update` method
    Observer ob2 = (o, arg) -> System.out.println(Thread.currentThread().getName() + " ob2 consume " + arg);

    runnableObservable.addObserver(ob1);
    runnableObservable.addObserver(ob2);

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(runnableObservable);

    System.out.println(Thread.currentThread().getName() + " EXIT");
    executorService.shutdown();
  }

  abstract static class RunnableObservable extends Observable implements Runnable {

  }

  static class IntObservable extends RunnableObservable {

    // override from Runnable
    public void run() {
      Stream.iterate(1, i -> i + 1).limit(10)
          .forEach(
              i -> {
                setChanged();

                /*
                    Duality
                    - Iterator - Pull - DATA method()
                    - Observer - Push - method(DATA)
                */
                notifyObservers(i); // push
                // int i = iter.next() // pull

                System.out.println(Thread.currentThread().getName() + " notify " + i);
              }
          );
    }
  }

  @ToString
  @AllArgsConstructor
  static class Person extends RunnableObservable {

    String name;
    int age;

    void setAge(int newAge) {
      if (this.age != newAge) {
        this.age = newAge;
        setChanged();
        notifyObservers(this);
      }
    }

    public void run() {
      setAge(30);
      setAge(31);
    }
  }
}
