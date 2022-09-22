package concurrency.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulerReactorEx {

  private static final Logger LOG = LoggerFactory.getLogger(SchedulerReactorEx.class.getName());

  public static void main(String[] args) {
    LOG.info("enter");

    var pub = Flux.range(1, 5);

//    pub
//        .log() // to check which thread are used
//        .subscribeOn(Schedulers.newSingle("sub"))
//        .subscribe(integer -> LOG.info("consume {}", integer));

    pub
        .publishOn(Schedulers.newSingle("pub"))
        .log()
        .subscribe(integer -> LOG.info("consume {}", integer));

    LOG.info("exit");
  }
}
