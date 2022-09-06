package concurrency.reactive;

import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class PubSub {

  /*
      pub -> [Data1] -> mapPub (operator) -> [Data2] -> sub
   */
  public static void main(String[] args) {
    Publisher<Integer> pub = getIntegerPublisher();
    Publisher<Integer> mapPub = mapPub(pub, i -> i * 10);

    Subscriber<Integer> sub = getIntegerPrintSubscriber();
    mapPub.subscribe(sub);
  }

  private static Publisher<Integer> mapPub(Publisher<Integer> publisher, Function<Integer, Integer> mapFunction) {
    return new Publisher<>() {
      @Override
      public void subscribe(Subscriber<? super Integer> subscriber) {
        publisher.subscribe(new DelegateSubscriber<Integer>((Subscriber<Integer>) subscriber) {
          @Override
          public void onNext(Integer integer) {
            subscriber.onNext(mapFunction.apply(integer));
          }
        });
      }
    };
  }

  private static Subscriber<Integer> getIntegerPrintSubscriber() {
    return new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");
        subscription.request(10);
      }

      @Override
      public void onNext(Integer integer) {
        System.out.println("onNext: " + integer);
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("onError: " + throwable.getMessage());
      }

      @Override
      public void onComplete() {
        System.out.println("onComplete");
      }
    };
  }

  private static Publisher<Integer> getIntegerPublisher() {
    return new Publisher<>() {
      final Stream<Integer> intStream = Stream.iterate(0, i -> i + 1);

      @Override
      public void subscribe(Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            try {
              intStream.limit(n)
                  .forEach(subscriber::onNext);
            } catch (Exception e) {
              subscriber.onError(e);
            }

            subscriber.onComplete();
          }

          @Override
          public void cancel() {
            System.out.print("noOp for cancel");
          }
        });
      }
    };
  }
}
