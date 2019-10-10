package race.question.demo.deliver.way;

import race.question.demo.deliver.Zone;
import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Outcome;
import race.question.demo.deliver.pojo.Result;

import java.math.BigDecimal;

/**
 * @author linyh
 */
public class AmountUtils {

    private AmountUtils() {
    }

    public static void calculateLeftToOutcome(Income income, Zone to, Result result) {
        if (!income.isZero()) {
            calculateToOutcome(income, income.getAmount(), to, result);
        }
    }

    public static void calculateToOutcome(Income income, BigDecimal amount, Zone to, Result result) {

        Outcome outcome;

        // 不够分，直接分余下的所有
        if (!enoughToDeliver(amount, income.getAmount())) {
            outcome = new Outcome(to, income.getAmount());
            income.setAmount(BigDecimal.ZERO);
            result.addResult(outcome);
        } else {
            outcome = new Outcome(to, amount);
            income.setAmount(income.getAmount().subtract(amount));
            result.addResult(outcome);
        }
    }

    public static boolean enoughToDeliver(BigDecimal deliver, BigDecimal left) {

        if (deliver.signum() == -1 || deliver.signum() == 0) {

            // 如果为负数， 需要分配-60，余额-100，则 deliver 大于等于余额表示足够分配
            return deliver.compareTo(left) >= 0;
        }

        // 如果为正数，需要分配金额小于等于余额表示足够分配
        return deliver.compareTo(left) <= 0;
    }

}
