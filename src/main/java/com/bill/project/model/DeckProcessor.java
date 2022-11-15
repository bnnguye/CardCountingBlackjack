package com.bill.project.model;

import com.bill.project.config.GameConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.bill.project.model.Suit.*;

@Component
@NoArgsConstructor
@Slf4j
@Getter
public class DeckProcessor {

    private static int id = 0;

    private static final ArrayList<Card> cardsInDeck = new ArrayList<>();

    private static final ArrayList<String> suits = new ArrayList<>();

    private final HashMap<Integer, Integer> amountOfCards = new HashMap<>();

    @Autowired
    private GameConfig gameConfig;

    final List<String> values = Arrays.asList("ACE", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "JACK", "QUEEN", "KING");

    @PostConstruct
    public void init() {
        System.out.println("No of decks: " + gameConfig.getNoOfDecks());
        generateAllSuits();
        for (int i = 0; i < gameConfig.getNoOfDecks(); i++) {
            addDeck();
            shuffle();
        }
    }

    private void generateAllSuits() {
        suits.addAll(Arrays.asList(CLUBS, DIAMONDS, HEARTS, SPADES));
    }

    private void addDeck() {
        for (String suit: suits) {
            for (String val: values) {
                Card card = new Card(id, val, suit);
                cardsInDeck.add(card);
                if (!amountOfCards.containsKey(card.getFaceValue())) {
                    amountOfCards.put(card.getFaceValue(), 1);
                }
                else {
                    amountOfCards.put(card.getFaceValue(), amountOfCards.get(card.getFaceValue()) + 1);
                }
                id ++;
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cardsInDeck);
    }

    public Card draw(Player player) {
        shuffle();
        Card card = new Card(cardsInDeck.get(0).getId(),
                cardsInDeck.get(0).getValue(),
                cardsInDeck.get(0).getSuit());
        cardsInDeck.remove(0);
        amountOfCards.put(card.getFaceValue(), amountOfCards.get(card.getFaceValue()) - 1);
        System.out.println(player.getName() + " drew: " + card.getValue());
        return card;
    }

    public int getDeckSize() { return cardsInDeck.size();}

    public void deal(Player player) {
        Card firstCard = draw(player);
        Card secondCard = draw(player);
        player.getHand().add(firstCard);
        player.getHand().add(secondCard);
    }

    public void hit(Player player) {
        player.getHand().add(draw(player));
    }

    public int getAmountOfCardsInDeck() { return cardsInDeck.size();}
}
