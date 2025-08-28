package org.mrpaulwoods.statemachinedemo.actions;

import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.statemachinedemo.Events;
import org.mrpaulwoods.statemachinedemo.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.ReactiveAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProcessedToDoneAction implements ReactiveAction<States, Events> {

    @Override
    public Mono<Void> apply(StateContext<States, Events> context) {
        return Mono.fromRunnable(() -> log.info("ProcessedToDoneAction: {} : {} -> {}",
                context.getEvent(),
                context.getSource().getId(),
                context.getTarget().getId()));
    }

}
