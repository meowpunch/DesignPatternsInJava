package concurrency.reactive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntervalEx {

  private static final Logger LOG = LoggerFactory.getLogger(IntervalEx.class.getName());

  public static void main(String[] args) {
    LOG.debug("enter");

    Publisher<Integer> pub = intervalPublisher();
    Publisher<Integer> takePub = takePub(pub, 5);
    takePub.subscribe(getSubscriber());

    LOG.debug("exit");
  }

  private static Publisher<Integer> intervalPublisher() {
    return subscriber ->
        subscriber.onSubscribe(new Subscription() {
          final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
          int i = 0;

          @Override
          public void request(long n) {
            LOG.debug("request");
            exec.scheduleAtFixedRate(
                () -> {
                  subscriber.onNext(i++);
                }, 0, 300, TimeUnit.MILLISECONDS
            );
          }

          @Override
          public void cancel() {
            LOG.debug("cancel");
            exec.shutdown();
          }
        });
  }

  private static Publisher<Integer> takePub(Publisher<Integer> pub, int n) {
    return subscriber ->
        pub.subscribe(new Subscriber<Integer>() {
          int count = 0;
          private Subscription subscription;

          @Override
          public void onSubscribe(Subscription s) {
            subscription = s;
            subscriber.onSubscribe(s);
          }

          @Override
          public void onNext(Integer integer) {
            if (count++ >= n) subscription.cancel();
            else subscriber.onNext(integer);
          }

          @Override
          public void onError(Throwable t) {
            subscriber.onError(t);
          }

          @Override
          public void onComplete() {
            subscriber.onComplete();
          }
        });
  }

  private static Subscriber<Integer> getSubscriber() {
    return new Subscriber<>() {
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
    };
  }
}
