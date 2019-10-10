package race.question.demo.deliver.way;

import race.question.demo.deliver.Zone;
import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;
import race.question.demo.deliver.pojo.ZoneProjectDeliverInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 区划项目分成
 *
 * @author linyh
 */
public class ZoneProjectDeliver implements DeliverWay {

    public static final int ZONE_PROJECT_DELIVER = 1 << 4;

    public static final ZoneProjectDeliver instance = new ZoneProjectDeliver();

    public final Map<Zone, ZoneProjectDeliverInfo> map = new HashMap<>();

    /**
     * 以区划为标示去分成
     * <p>
     * 根据执收区划级次（中央、省、市、县），逐级分成，根据分成方向可以分为从上往下（中央-> 省->市->县），或从下往上（县->市->省->中央）
     * 金额计算方式分为两类：定额和比例
     * <p>
     * 定额：分入金额 =（执收数量 * 定额）
     * 比例：分入金额 =（总金额 * 比例）
     * <p>
     * 剩余金额分入执收区划或中央
     *
     * @param income
     * @param res
     * @return
     */
    @Override
    public void deliver(Income income, Result res) {

        ZoneProjectDeliverInfo info = map.get(income.getDeliverZone());

        // 比例计算
        BigDecimal[] ratio = info.getRatio();

        Zone[] zones = income.getDeliverZone().getZones();

        BigDecimal totalAmount = income.getAmount();

        // 有可能只有2 3个 例如 执收区划为福州时，只有3个
        for (int i = 0; i < zones.length; i++) {

            if (income.isZero()) break;

            BigDecimal amount = totalAmount.multiply(ratio[i]);

            AmountUtils.calculateToOutcome(income, amount, zones[i], res);
        }

        // 余额划分
        Zone zone = info.isLeftToOwn() ? income.getDeliverZone() : Zone.CENTRAL;
        AmountUtils.calculateLeftToOutcome(income, zone, res);
    }

    @Override
    public boolean isHandle(Income income) {

        return income.getDeliverZone().isDeliver(ZONE_PROJECT_DELIVER)
                && map.get(income.getDeliverZone()).getProjectId().equals(income.getProjectId());
    }

    public void addInfo(ZoneProjectDeliverInfo info) {
        map.put(info.getZone(), info);
    }
}
