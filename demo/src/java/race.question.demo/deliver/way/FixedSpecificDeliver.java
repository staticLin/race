package race.question.demo.deliver.way;

import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;
import race.question.demo.deliver.pojo.SpecificDeliverInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定额的特定分成
 *
 * @author linyh
 */
public class FixedSpecificDeliver implements DeliverWay {

    public static final int FIX_SPECIFIC_DELIVER = 1 << 1;

    public static final FixedSpecificDeliver INSTANCE = new FixedSpecificDeliver();

    public final Map<Integer, List<SpecificDeliverInfo>> specificMap = new HashMap<>();

    /**
     * 将收入分入指定区划 分入金额 =（执收数量 * 定额）
     * 余额归属执收区划
     *
     * @param income
     * @param res
     * @return
     */
    @Override
    public void deliver(Income income, Result res) {

        List<SpecificDeliverInfo> infos = specificMap.get(income.getProjectId());

        BigDecimal fixNum;
        BigDecimal deliverAmount = income.getDeliverAmount();

        // 如果是负数分配，执收数量也应该是负数
        if (income.getAmount().signum() == -1) {
            if (income.getDeliverAmount().signum() != -1) {
                deliverAmount = deliverAmount.negate();
            }
        }
        BigDecimal amount;

        for (SpecificDeliverInfo info : infos) {

            if (income.isZero()) {
                return;
            }

            fixNum = info.getFixNum();
            amount = fixNum.multiply(deliverAmount);

            AmountUtils.calculateToOutcome(income, amount, info.getToDeliverZone(), res);
        }

        // 余额归执行区划
        AmountUtils.calculateLeftToOutcome(income, income.getDeliverZone(), res);
    }

    @Override
    public boolean isHandle(Income income) {

        return income.getDeliverZone().isDeliver(FIX_SPECIFIC_DELIVER)
                && specificMap.containsKey(income.getProjectId());
    }

    public void addInfo(List<SpecificDeliverInfo> info) {
        specificMap.put(info.get(0).getProjectId(), info);
    }
}
