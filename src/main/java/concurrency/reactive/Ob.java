package concurrency.reactive;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class Ob {


  /*
  *   Observable : source
  *   Observer :
  *
  *
  */
  public static void main(String[] args) {

    Observer ob1 = (o, arg) -> System.out.println(Thread.currentThread().getName() + " ob1 consume " + arg); // override `void update` method
    Observer ob2 = (o, arg) -> System.out.println(Thread.currentThread().getName() + " ob2 consume " + arg);

    IntObservable runnableObservable = new IntObservable();
    runnableObservable.addObserver(ob1);
    runnableObservable.addObserver(ob2);

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(runnableObservable);

    System.out.println(Thread.currentThread().getName() + " EXIT");
    executorService.shutdown();
  }

  static class IntObservable extends Observable implements Runnable {

    // override from Runnable
    public void run() {
      Stream.iterate(0,  i -> i+1).limit(10)
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

}
