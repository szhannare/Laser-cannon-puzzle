package laserpuzzle.play.resultmanager;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResult {

    @NonNull private String playerName;
    private boolean solved;
    private int steps;
}