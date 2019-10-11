package race.question.demo.deliver.way;

import race.question.demo.deliver.Zone;
import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;
import race.question.demo.deliver.pojo.ZoneProjectDeliverInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目分成
 *
 * @author linyh
 */
public class ProjectDeliver implements DeliverWay {

    public final Map<Integer, ZoneProjectDeliverInfo> map = new HashMap<>();

    public static final ProjectDeliver INSTANCE = new ProjectDeliver();

    /**
     * 以项目为标示去分成
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

        ZoneProjectDeliverInfo info = map.get(income.getProjectId());

        // 定额或者比例
        BigDecimal[] ratio;
        // 执收数量或者总数
        BigDecimal deliverAmount;

        // 定额计算
        if (info.isFixed()) {
            ratio = info.getFixNum();
            // 执收数量
            deliverAmount = income.getDeliverAmount();

            // 如果是负数分配，执收数量也应该是负数
            if (income.getAmount().signum() == -1) {
                if (income.getDeliverAmount().signum() != -1) {
                    deliverAmount = deliverAmount.negate();
                }
            }
        } else {

            // 比例计算

            // 比例
            ratio = info.getRatio();
            // 总数
            deliverAmount = income.getAmount();
        }

        Zone[] zones = income.getDeliverZone().getZones();

        // 有可能只有3个分配 -> 中央 福建 福州
        for (int i = 0; i < zones.length; i++) {

            if (income.isZero()) break;

            AmountUtils.calculateToOutcome(income, deliverAmount.multiply(ratio[i]), zones[i], res);
        }

        // 余额划分
        Zone zone = info.isLeftToOwn() ? income.getDeliverZone() : Zone.CENTRAL;
        AmountUtils.calculateLeftToOutcome(income, zone, res);
    }

    @Override
    public boolean isHandle(Income income) {
        return map.containsKey(income.getProjectId());
    }

    public void addInfo(ZoneProjectDeliverInfo info) {
        map.put(info.getProjectId(), info);
    }
}
