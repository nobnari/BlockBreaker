package plugin.blockbreaker.data;

import com.sun.tools.javac.Main;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Course {

    public static final String EASY = "easy";
    public static final String NORMAL = "normal";
    public static final String HARD = "hard";

    private String value;

    /**
     * コマンドで受け取った引数からコースごとのブロックリストを出すコンストラクタ
     * @param args　コマンド引数
     */
    public Course(String[] args) {

        if (args.length == 1&&(EASY.equals(args[0])||NORMAL.equals(args[0])|| HARD.equals(args[0]))){
            this.value=args[0];
        }else {
            this.value = NORMAL;
        }
}
}
