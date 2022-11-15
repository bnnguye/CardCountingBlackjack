package com.bill.project.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static com.bill.project.model.Enum.HIT;
import static com.bill.project.model.Enum.STAND;

@Component
@Slf4j
public class Game {

    @Autowired
    private DeckProcessor deckProcessor;

    private ArrayList<Player> players;

    private Dealer dealer;

    private Player player;

    private int rounds;

    @PostConstruct
    public void init() {
        rounds = 1;
        players = new ArrayList<>();
        dealer = new Dealer();
        player = new Player();
        players.add(player);
        players.add(dealer);
        startGame();
    }

    public void startGame() {
        while (deckProcessor.getDeckSize() >= 5 * players.size()) {
            System.out.println("Round " + rounds);
            playRound();
            rounds++;
        }
        for (Player player: players) {
            System.out.println(player.getName() + " wins: " + player.getWins() );
        }
    }

    public void playRound() {
        boolean roundFinished = false;
        dealCards();
        if (dealer.hasBlackJack() || player.hasBlackJack()) {
            roundFinished = true;
        }

        while (!roundFinished) {
            int playersIn = players.size();
            for (Player player: players) {
                String playerName = player.getName();

                if (!player.isStand() && !player.isBust()) {
                    Enum move = player.move(dealer.getPosition(),
                            deckProcessor.getAmountOfCardsInDeck(),
                            deckProcessor.getAmountOfCards());
                    System.out.println(String.format("%s %s", playerName, move));
                    perform(player, move);

                    if (player.isBust() || player.hasBlackJack()) {
                        roundFinished = true;
                        break;
                    }
                    if (player.isStand()) {
                        playersIn--;
                    }
                }
                else {
                    playersIn--;
                }
            }
            if (playersIn <= 0) {
                roundFinished = true;
            }
        }
        determineRound();
        clear();
    }

    public void dealCards() {
        for (Player player: players) {
            deckProcessor.deal(player);
        }
    }

    public void determineRound() {
        if (player.isBust() || (dealer.getPosition() > player.getPosition())) {
            dealer.wins();
            System.out.println("Dealer wins with: " + dealer.getPosition());
        }
        else if (dealer.isBust() || (dealer.getPosition() < player.getPosition())) {
            player.wins();
            System.out.println("Player wins with: " + player.getPosition());
        }
        else {
            System.out.println("Draw!");
        }
    }

    public void perform(Player player, Enum move) {
        if (HIT.equals(move)) {
            deckProcessor.hit(player);
        }
        else if (STAND.equals(move)) {
            player.stand();
        }
    }

    public void clear() {
        for (Player player: players) {
            System.out.println(player.getName() + " End Hand: " + player.getPosition() + " " + player.getHand());
            player.getHand().clear();
            player.setStand(false);
        }
        System.out.println("______________________________________________________________________");
    }

}
