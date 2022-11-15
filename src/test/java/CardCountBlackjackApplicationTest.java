import com.bill.project.app.CardCountBlackjackApplication;
import com.bill.project.config.GameConfig;
import com.bill.project.model.DeckProcessor;
import com.bill.project.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {Game.class, CardCountBlackjackApplication.class, GameConfig.class, DeckProcessor.class})
public class CardCountBlackjackApplicationTest {

    @Test
    public void runSimulation() {
        new CardCountBlackjackApplication();
    }
}
