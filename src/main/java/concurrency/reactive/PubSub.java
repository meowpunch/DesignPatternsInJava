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
    Publisher<Integer> pub = streamPublisher(Stream.iterate(1, i -> i + 1) );
    Publisher<Integer> pub1= mapPub(pub, i -> i * 10);
//    Publisher<Integer> pub2 = reducePub(pub1, 0, Integer::sum);
    var pub2 = reducePub(pub1, new StringBuilder(), (sb, i) -> sb.append(i).append(". "));

    var sub = getIntegerPrintSubscriber(10);
    pub2.subscribe(sub);
  }

  private static <T, R> Publisher<R> reducePub(Publisher<T> mapPub, R init , BiFunction<R, T, R> reduceFunction) {
    return subscriber -> mapPub.subscribe(new DelegateSubscriber<T, R>(subscriber) {
      R result = init;

      @Override
      public void onNext(T integer) {
        result = reduceFunction.apply(result, integer);
      }

      @Override
      public void onComplete() {
        subscriber.onNext(result);
        subscriber.onComplete();
      }
    });
  }

  private static <T, R> Publisher<R> mapPub(Publisher<T> publisher, Function<T, R> mapFunction) {
    return new Publisher<>() {
      @Override
      public void subscribe(Subscriber<? super R> subscriber) {
        publisher.subscribe(new DelegateSubscriber<T, R>(subscriber) {
          @Override
          public void onNext(T t) {
            subscriber.onNext(mapFunction.apply(t));
          }
        });
      }
    };
  }

  private static <T> Subscriber<T> getIntegerPrintSubscriber(int n) {
    return new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");
        subscription.request(n);
      }

      @Override
      public void onNext(T integer) {
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

  private static <T> Publisher<T> streamPublisher(Stream<T> stream) {
    return new Publisher<>() {
      @Override
      public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            try {
              stream.limit(n)
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
