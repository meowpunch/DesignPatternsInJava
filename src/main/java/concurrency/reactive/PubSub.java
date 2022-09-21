package concurrency.reactive;

import java.util.function.BiFunction;
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
    Publisher<Integer> pub = getIntegerPublisherOneTo();
    Publisher<Integer> mapPub = mapPub(pub, i -> i * 10);
    Publisher<Integer> sumPub = reducePub(mapPub, Integer::sum);

    Subscriber<Integer> sub = getIntegerPrintSubscriber(3);
    sumPub.subscribe(sub);
  }

  private static Publisher<Integer> reducePub(Publisher<Integer> mapPub, BiFunction<Integer, Integer, Integer> reduceFunction) {
    return subscriber -> mapPub.subscribe(new DelegateSubscriber<Integer>((Subscriber<Integer>) subscriber) {
      int sum = 0;
      @Override
      public void onNext(Integer integer) {
        sum += integer;
      }

      @Override
      public void onComplete() {
        subscriber.onNext(sum);
        subscriber.onComplete();
      }
    });
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

  private static Subscriber<Integer> getIntegerPrintSubscriber(int n) {
    return new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");
        subscription.request(n);
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

  private static Publisher<Integer> getIntegerPublisherOneTo() {
    return new Publisher<>() {
      final Stream<Integer> intStream = Stream.iterate(1, i -> i + 1);

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
