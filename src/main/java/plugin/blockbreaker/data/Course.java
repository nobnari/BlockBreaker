package plugin.blockbreaker.data;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import plugin.blockbreaker.BlockBreaker;

@Setter
@Getter
public class Course {

  public static final String EASY = "easy";
  public static final String NORMAL = "normal";
  public static final String HARD = "hard";

  private String value;
  private List<String> blockList;

  /**
   * コマンドで受け取った引数からコースごとのブロックリストを出すコンストラクタ
   *
   * @param args 　コマンド引数
   */
  public Course(BlockBreaker main, String[] args) {
    if (args.length == 1 && (EASY.equals(args[0]) || NORMAL.equals(args[0]) || HARD.equals(
        args[0]))) {
      this.value = args[0];
    } else {
      this.value = NORMAL;
    }
    this.blockList = main.getConfig().getStringList(value);
  }
}
