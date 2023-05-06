package plugin.blockbreaker.DAO;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ORM {

  /**
   * MMスコアテーブルからスコアの高い順に最大12人分のデータを取得
   *
   * @return MMスコアテーブルの全てのデータ
   */
  @Select("select * from mm_score order by score desc limit 12")
  List<MMScore> getAllRank();

  /**
   * MMスコアテーブルから同じ難易度でスコアの高い順に最12人分のデータを取得
   *
   * @return MMスコアテーブルの指定難易度のデータ
   */
  @Select("select * from mm_score where course = #{courseValue} order by score desc limit 12")
  List<MMScore> getCourseRank(String courseValue);

  /**
   * MMスコアテーブルから最近の日時順に最6人分のデータを取得
   *
   * @return MMスコアテーブルの最近のデータ
   */
  @Select("select * from mm_score order by registered_dt desc limit 6")
  List<MMScore> getRecentRecords();

  /**
   * MMスコアテーブルにプレイヤーのスコアを登録
   *
   * @param mmScore プレイヤースコアインスタンス
   */
  @Insert("insert mm_score (player_name, score, course, registered_dt) " +
      "values (#{playerName}, #{score}, #{course}, now())")
  void insertMMScore(MMScore mmScore);
}
