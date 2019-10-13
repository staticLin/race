package race.question.demo.deliver;

import java.util.HashMap;
import java.util.Map;

/**
 * 区划
 *
 * @author linyh
 */
public enum Zone {

    /**
     * 中央
     */
    CENTRAL("中央", new String[]{"中央"}),

    /**
     * 福建省
     */
    FUJIAN("福建省", new String[]{"中央", "福建省"}),

    /**
     * 福州市
     */
    FUZHOU("福州市", new String[]{"中央", "福建省", "福州市"}),

    /**
     * 鼓楼
     */
    GULOU("鼓楼区", new String[]{"中央", "福建省", "福州市", "鼓楼区"}),

    /**
     * 仓山
     */
    CANGSHAN("仓山区", new String[]{"中央", "福建省", "福州市", "仓山区"}),

    /**
     * 泉州
     */
    QUANZHOU("泉州市", new String[]{"中央", "福建省", "泉州市"}),

    /**
     * 晋江
     */
    JINJIANG("晋江市", new String[]{"中央", "福建省", "泉州市", "晋江市"}),

    /**
     * 安溪
     */
    ANXI("安溪县", new String[]{"中央", "福建省", "泉州市", "安溪县"});

    private String name;
    private int deliverWayBit;
    public final String[] zones;
    private static final Map<String, Zone> ZONE_MAP = new HashMap<>();

    Zone(String name, String[] zones) {
        this.name = name;
        this.deliverWayBit = 0;
        this.zones = zones;
    }

    public String getZoneName() {
        return this.name;
    }

    public void addDeliverBit(int bit) {
        deliverWayBit |= bit;
    }

    public boolean isDeliver(int bit) {
        return (deliverWayBit & bit) != 0;
    }

    public static Zone getZone(String zoneName) {
        return ZONE_MAP.get(zoneName);
    }

    public static void initZoneMap() {

        Zone[] values = Zone.values();

        for (Zone value : values) {
            ZONE_MAP.put(value.getZoneName(), value);
        }
    }
}
