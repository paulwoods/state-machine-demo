package org.mrpaulwoods.statemachinedemo.guards;

import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.statemachinedemo.Events;
import org.mrpaulwoods.statemachinedemo.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewToScrappedGuard implements Guard<States, Events> {
    @Override
    public boolean evaluate(StateContext<States, Events> context) {
        log.info("NewToScrappedGuard event: {} : {} -> {}",
                context.getEvent(),
                context.getSource().getId(),
                context.getTarget().getId());

        return true;
    }

}
