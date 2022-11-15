package com.bill.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.bill.project.model.Enum.HIT;
import static com.bill.project.model.Enum.STAND;

@Getter
@Setter
@Slf4j
@Component
@RequiredArgsConstructor
public class Player {

    private final double ACTIVATE_PERCENTAGE = 0.5;

    private final String name = "PLAYER";
    public boolean stand = false;
    private int wins = 0;
    private Set<Card> hand = new HashSet<>();

    public Enum move(Integer dealerPosition, Integer currentNoOfCardsLeft, HashMap<Integer, Integer> amountOfCards) {
        if (getPosition() < 12 || dealerPosition > getPosition()) {
            return HIT;
        } else {
            double drawValid = 0;

            for (Integer handVal : getValidHandValues()) {
                boolean betterThanDealer = handVal > dealerPosition;

                if (betterThanDealer && handVal > 17) {
                    if (dealerPosition < 12 && dealerPosition > 9) {
                        return HIT;
                    }
                    return STAND;
                } else {
                    for (int i = 1; i < 22 - handVal; i++) {
                        int cardVal = i;
                        if (cardVal < 12) {
                            if (cardVal == 11) {
                                cardVal = 1;
                            }
                            drawValid += amountOfCards.get(cardVal);
                        }
                    }
                }
                if (drawValid / currentNoOfCardsLeft > 0.5d * getValidHandValues().size()) {
                    return HIT;
                }
            }
            return STAND;
        }
    }

    public List<Integer> getHandValues() {
        List<Integer> possibleHandValues = new ArrayList<>();
        int nonAceTotal = 0;

        for (Card card: hand) {
            if ("ACE".equals(card.getValue())) {
                if (possibleHandValues.size() == 0) {
                    possibleHandValues.addAll(Arrays.asList(1, 11));
                }
                else {
                    ArrayList<Integer> elevenHandValues = new ArrayList<>();
                    elevenHandValues.addAll(possibleHandValues);
                    for (int i = 0; i < elevenHandValues.size(); i ++) {
                        elevenHandValues.set(i, elevenHandValues.get(i) + 11);
                    }
                    for (int i = 0; i < possibleHandValues.size(); i ++) {
                        elevenHandValues.set(i, possibleHandValues.get(i) + 1);
                    }
                    possibleHandValues.addAll(elevenHandValues);
                }
            } else {
                nonAceTotal += card.getFaceValue();
            }
        }
        for (int i = 0; i < possibleHandValues.size(); i++) {
            possibleHandValues.set(i, possibleHandValues.get(i) + nonAceTotal);
        }
        if (possibleHandValues.size() == 0) {
            possibleHandValues.add(nonAceTotal);
        }
        return possibleHandValues;
    }

    public void wins() { wins++;}

    public boolean isBust() {
        for (Integer handVal: getHandValues()) {
            if (handVal <= 21) {
                return false;
            }
        }
        System.out.println(this.name + " has busted!");
        return true;
    }

    public boolean hasBlackJack() {
        return getPosition() == 21;
    }


    public int getPosition() {
        int highestValidValue = -1;
        for (Integer handVal: getHandValues()) {
            if (handVal <= 21 && handVal > highestValidValue) {
                highestValidValue = handVal;
            }
        }
        return highestValidValue;
    }

    public void stand() {
        this.stand = true;
    }

    public List<Integer> getValidHandValues() {
        List<Integer> validHandValues = new ArrayList<>();
        for (Integer handVal: getHandValues()) {
            if (handVal < 22 && !validHandValues.contains(handVal)) {
                validHandValues.add(handVal);
            }
        }
        return validHandValues;
    }

}
