package concurrency.reactive;

import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class PubSub {

  public static void main(String[] args) {

    Publisher<Integer> pub = new Publisher<>() {
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

    Subscriber<Integer> sub = new Subscriber<>() {
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

    pub.subscribe(sub);
  }
}
