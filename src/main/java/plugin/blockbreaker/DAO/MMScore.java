package plugin.blockbreaker.DAO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * プレイヤーのスコアを扱うオブジェクト。 データベースのプレイヤースコアテーブルと連動する。
 */
@Getter
@Setter
@NoArgsConstructor
public class MMScore {

  private int id;
  private String playerName;
  private int score;
  private String course;
  private LocalDateTime registeredDt;

  public MMScore(String playerName, int score, String courseValue) {
    this.playerName = playerName;
    this.score = score;
    this.course = courseValue;
  }
}


