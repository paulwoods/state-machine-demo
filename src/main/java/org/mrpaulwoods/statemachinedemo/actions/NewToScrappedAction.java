package org.mrpaulwoods.statemachinedemo.actions;

import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.statemachinedemo.Events;
import org.mrpaulwoods.statemachinedemo.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewToScrappedAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {
        log.info("NewToScrappedAction: {} : {} -> {}",
                context.getEvent(),
                context.getSource().getId(),
                context.getTarget().getId());
    }

}
