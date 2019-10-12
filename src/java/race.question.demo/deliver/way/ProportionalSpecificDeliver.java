package race.question.demo.deliver.way;

import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;
import race.question.demo.deliver.pojo.SpecificDeliverInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按照比例的特定分成
 *
 * @author linyh
 */
public class ProportionalSpecificDeliver implements DeliverWay {

    public static final int PROPORTIONAL_SPECIFIC_DELIVER = 1 << 3;

    public static final ProportionalSpecificDeliver INSTANCE = new ProportionalSpecificDeliver();

    public final Map<Integer, List<SpecificDeliverInfo>> specificMap = new HashMap<>();

    /**
     * 将收入分入指定区划 分入金额 =（总金额 * 比例）
     * 余额归属执收区划
     *
     * @param income
     * @param res
     * @return
     */
    @Override
    public void deliver(Income income, Result res) {

        List<SpecificDeliverInfo> infos = specificMap.get(income.getProjectId());

        BigDecimal ratio;
        BigDecimal totalAmount = income.getAmount();
        BigDecimal amount;

        for (SpecificDeliverInfo info : infos) {

            if (income.isZero()) {
                return;
            }

            ratio = info.getRatio();
            amount = ratio.multiply(totalAmount);

            AmountUtils.calculateToOutcome(income, amount, info.getToDeliverZone(), res);
        }

        // 余额归执行区划
        AmountUtils.calculateLeftToOutcome(income, income.getDeliverZone(), res);
    }

    @Override
    public boolean isHandle(Income income) {

        return income.getDeliverZone().isDeliver(PROPORTIONAL_SPECIFIC_DELIVER)
                && specificMap.containsKey(income.getProjectId());
    }

    public void addInfo(List<SpecificDeliverInfo> list) {
        specificMap.put(list.get(0).getProjectId(), list);
    }
}
