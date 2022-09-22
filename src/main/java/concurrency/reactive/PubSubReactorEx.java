package concurrency.reactive;

import reactor.core.publisher.Flux;

public class PubSubReactorEx {

  /*
      Reactor is a reactive library which implements Reactive Streams Specification. Spring choose reactor by default.
   */
  public static void main(String[] args) {
    Flux.range(1, 10)
        .map(i -> i * 10)
        .reduce(Integer::sum)
        .subscribe(System.out::println);
  }
}
