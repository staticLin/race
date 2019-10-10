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
    CENTRAL("中央"),

    /**
     * 福建省
     */
    FUJIAN("福建省"),

    /**
     * 福州市
     */
    FUZHOU("福州市"),

    /**
     * 鼓楼
     */
    GULOU("鼓楼区"),

    /**
     * 仓山
     */
    CANGSHAN("仓山区"),

    /**
     * 泉州
     */
    QUANZHOU("泉州市"),

    /**
     * 晋江
     */
    JINJIANG("晋江市"),

    /**
     * 安溪
     */
    ANXI("安溪县");

    private String name;
    private int deliverWayBit;
    private Zone[] zones;
    private static final Map<String, Zone> zoneMap = new HashMap<>(16);

    Zone(String name) {
        this.name = name;
        this.deliverWayBit = 0;
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

    public void setZones(Zone[] zones) {
        this.zones = zones;
    }

    public Zone[] getZones() {
        return zones;
    }

    public static Zone getZone(String zoneName) {
        return zoneMap.get(zoneName);
    }

    public static void initZoneMap() {

        Zone[] values = Zone.values();

        for (Zone value : values) {
            zoneMap.put(value.getZoneName(), value);
        }
    }
}
