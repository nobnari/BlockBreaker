package plugin.blockbreaker.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.bukkit.entity.Player;

public class Connector {

  private SqlSessionFactory sqlSessionFactory;

  public Connector() {
    try {
      InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
      this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * ランキング表示メソッド(ごちゃ混ぜ版)
   *
   * @param player コマンド入力プレイヤー
   */
  public void displayScores(Player player) {
    SqlSession session = sqlSessionFactory.openSession();
    ORM mapper = session.getMapper(ORM.class);
    List<MMScore> playerScores = mapper.getAllRank();
    sendRanking(player, playerScores);
    session.close();
  }

  /**
   * ランキング表示メソッド(コース別)
   *
   * @param player コマンド入力プレイヤー
   */
  public void displayScores(Player player, String difficulty) {
    SqlSession session = sqlSessionFactory.openSession();
    ORM mapper = session.getMapper(ORM.class);
    List<MMScore> playerScores = mapper.getCourseRank(difficulty);
    sendRanking(player, playerScores);
    session.close();
  }

  /**
   * 最近のレコード表示メソッド
   *
   * @param player コマンド入力プレイヤー
   */
  public void displayRecent(Player player) {
    SqlSession session = sqlSessionFactory.openSession();
    ORM mapper = session.getMapper(ORM.class);
    List<MMScore> recentRecords = mapper.getRecentRecords();
    sendRecentRecords(player, recentRecords);
    session.close();
  }

  /**
   * プレイヤーのスコアをデータベースのmmスコアテーブルに登録
   *
   * @param player      プレイヤー
   * @param score       最終な得点
   * @param courseValue 難易度
   */
  public void insertMMScore(Player player, int score, String courseValue) {
    SqlSession session = sqlSessionFactory.openSession(true);
    ORM mapper = session.getMapper(ORM.class);
    MMScore MMScore = new MMScore(player.getName(), score, courseValue);
    mapper.insertMMScore(MMScore);
    session.close();
  }

  /**
   * ランキング順にメッセージを出すメソッド 　タイムは非表示、日付のみ表示
   *
   * @param player      コマンド入力プレイヤー
   * @param MMScoreList MMスコアリスト
   */
  private static void sendRanking(Player player, List<MMScore> MMScoreList) {
    int i = 1;
    for (MMScore score : MMScoreList) {
      player.sendMessage(i + "位   " + score.getScore() + "点   "
          + score.getCourse() + "   "
          + score.getPlayerName() + "   "
          + score.getRegisteredDt()
          .format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
      i++;
    }
  }

  /**
   * 最近の日時順にメッセージを出すメソッド 　日付、時間共に表示
   *
   * @param player      コマンド入力プレイヤー
   * @param MMScoreList MMスコアリスト
   */
  private static void sendRecentRecords(Player player, List<MMScore> MMScoreList) {
    for (MMScore score : MMScoreList) {
      player.sendMessage(
          score.getCourse() + "   "
              + score.getScore() + "点   "
              + score.getRegisteredDt()
              .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "   "
              + score.getPlayerName());
    }
  }

}
