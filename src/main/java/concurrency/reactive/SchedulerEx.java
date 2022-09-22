package concurrency.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerEx {
  private static final Logger LOG = LoggerFactory.getLogger(SchedulerEx.class.getName());

  public static void main(String[] args) {
    Publisher<Integer> pub = subscriber ->
        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
            subscriber.onNext(4);
            subscriber.onNext(5);
            subscriber.onComplete();
          }

          @Override
          public void cancel() {

          }
        });


    pub.subscribe(new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription s) {
        LOG.debug("onSubscribe");
        s.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(Integer integer) {
        LOG.debug("onNext: {}", integer);
      }

      @Override
      public void onError(Throwable t) {
        LOG.debug("onError: {}", t.getMessage());
      }

      @Override
      public void onComplete() {
        LOG.debug("onComplete");
      }
    });
  }
}
