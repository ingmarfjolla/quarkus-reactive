package org.ing;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/hello")
    public class GreetingResource {
        long blockTime=500;

        @Inject
        Vertx vertx;

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("reactive-wrong")
        public Uni<String> greetReactiveWrong() {
            blockingIOOperation();
            return Uni.createFrom().item("Hello from reactive-wrong, thread"+Thread.currentThread().getName());
        }

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("imperative")
        @Blocking
        public Uni<String> greetImperative() {
            blockingIOOperation();
            return Uni.createFrom().item("Hello from imperative, thread"+Thread.currentThread().getName());
        }

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("reactive-correct")
        public Uni<String> greetReactiveCorrect() {
            return nonBlockingIOOperation().map(unused -> "Hello from reactive-correct, thread"+Thread.currentThread().getName());
        }


        private void blockingIOOperation() {
            try {
                Thread.sleep(blockTime);
            } catch (InterruptedException e) {
                //do nothing
            }
        }

        private Uni<Void> nonBlockingIOOperation() {
            return Uni.createFrom().emitter(e -> vertx.setTimer(blockTime, x -> e.complete(null)));
        }

    }

