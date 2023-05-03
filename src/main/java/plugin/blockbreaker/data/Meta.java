package plugin.blockbreaker.data;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {

  /**
   * プレイヤーごとのBBのon/off変数を管理するマップ
   */
  private Map<String, Boolean> status = new HashMap<>();
  /**
   * プレイヤーごとのBBのリザーブデータを管理するマップ
   */
  private Map<String, ReserveData> reserveData = new HashMap<>();
  /**
   * プレイヤーごとのBBのプレイ中データを管理するマップ
   */
  private Map<String, OnPlayData> onPlayData = new HashMap<>();

}
