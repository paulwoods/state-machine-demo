package org.mrpaulwoods.statemachinedemo.actions;

import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.statemachinedemo.Events;
import org.mrpaulwoods.statemachinedemo.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScrappedToProcessedAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {
        log.info("ScrappedToProcessedAction: {} : {} -> {}",
                context.getEvent(),
                context.getSource().getId(),
                context.getTarget().getId());
    }

}
