package plugin.blockbreaker.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class MetaMap {
    /**
     * プレイヤーごとのBBのon/off変数を管理するマップ
     */
    private Map<String, Boolean> status = new HashMap<>();
    /**
     * プレイヤーごとのBBのプレイヤーデータを管理するマップ
     */
    private Map<String, PlayerData> playerData = new HashMap<>();


}
