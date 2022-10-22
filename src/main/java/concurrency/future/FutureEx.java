package concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureEx {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    log.debug("Enter");
    final ExecutorService executorService = Executors.newCachedThreadPool();

    final long startTime = System.currentTimeMillis();
    Future<String> future = executorService.submit(
        () -> {
          log.debug("Processing task asynchronously");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

          return "AsyncResult";
        }
    );

    log.debug("Is the async task done? " + future.isDone());
    log.debug("Processing the other task");
    Thread.sleep(1000);

    log.debug("Is the async task done? " + future.isDone());
    // blocking, wait the async result.
    // business logic is messed up with non-business logic
    log.info("The async result is " + future.get());
    log.debug("Exit " + (System.currentTimeMillis() - startTime));
  }
}
