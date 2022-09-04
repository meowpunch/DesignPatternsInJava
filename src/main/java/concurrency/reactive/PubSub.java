package concurrency.reactive;

import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PubSub {

  private static final Logger LOG = LoggerFactory.getLogger(PubSub.class.getName());

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
            LOG.info("noOp for cancel");
          }
        });
      }
    };

    Subscriber<Integer> sub = new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription subscription) {
        LOG.info("onSubscribe");
        subscription.request(10);
      }

      @Override
      public void onNext(Integer integer) {
        LOG.info("onNext: {}", integer);
      }

      @Override
      public void onError(Throwable throwable) {
        LOG.error("onError: {}", throwable);
      }

      @Override
      public void onComplete() {
        LOG.info("onComplete");
      }
    };

    pub.subscribe(sub);
  }
}
