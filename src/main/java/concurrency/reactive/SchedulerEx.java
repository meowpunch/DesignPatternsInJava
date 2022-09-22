package concurrency.reactive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerEx {

  private static final Logger LOG = LoggerFactory.getLogger(SchedulerEx.class.getName());

  public static void main(String[] args) {
    LOG.debug("enter");

    Publisher<Integer> pub = subscriber ->
        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            LOG.debug("request");
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

    // subscribeOn
    Publisher<Integer> subOnPub = subscriber ->
        Executors.newSingleThreadExecutor().execute(() -> pub.subscribe(subscriber));

    // publishOn
    Publisher<Integer> pubOnPub = sub ->
        pub.subscribe(new Subscriber<>() {
          final ExecutorService executorService = Executors.newSingleThreadExecutor();

          @Override
          public void onSubscribe(Subscription s) {
            sub.onSubscribe(s);
          }

          @Override
          public void onNext(Integer integer) {
            executorService.execute(() -> sub.onNext(integer));
          }

          @Override
          public void onError(Throwable t) {
            executorService.execute(() -> sub.onError(t));
          }

          @Override
          public void onComplete() {
            executorService.execute(sub::onComplete);
          }
        });

    subOnPub.subscribe(getSubscriber());
    pubOnPub.subscribe(getSubscriber());

    LOG.debug("exit");
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
