package com.bill.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.bill.project.model.Enum.HIT;
import static com.bill.project.model.Enum.STAND;

@NoArgsConstructor
@Slf4j
@Component
@Getter
@Setter
public class Dealer extends Player {
    public final static int STAND_THRESHOLD = 17;
    private final String name =  "DEALER";

    @Override
    public Enum move(Integer dealerPosition, Integer noOfDecks, HashMap<Integer, Integer> amountOfCards) {
        if (getPosition() >= STAND_THRESHOLD) {
            return STAND;
        }
        return HIT;
    }

    public boolean isBust() {
        for (Integer handVal: getHandValues()) {
            if (handVal <= 21) {
                return false;
            }
        }
        System.out.println(this.name + " has busted!");
        return true;
    }
}
