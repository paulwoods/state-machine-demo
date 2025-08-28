package org.mrpaulwoods.statemachinedemo.guards;

import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.statemachinedemo.Events;
import org.mrpaulwoods.statemachinedemo.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.ReactiveGuard;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProcessedToDoneGuard implements ReactiveGuard<States, Events> {

    @Override
    public Mono<Boolean> apply(StateContext<States, Events> context) {
        return Mono.fromCallable(() -> {
            log.info("ProcessedToDoneGuard event: {} : {} -> {}",
                    context.getEvent(),
                    context.getSource().getId(),
                    context.getTarget().getId());

            return true;
        });
    }

}
