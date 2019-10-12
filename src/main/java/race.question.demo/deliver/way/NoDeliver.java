package race.question.demo.deliver.way;

import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;

/**
 * 不分成（默认）
 *
 * @author linyh
 */
public class NoDeliver implements DeliverWay {

    public static final NoDeliver INSTANCE = new NoDeliver();

    /**
     * 不分成，将收入纳入执收区划
     *
     * @param income
     * @param res
     * @return
     */
    @Override
    public void deliver(Income income, Result res) {
        AmountUtils.calculateLeftToOutcome(income, income.getDeliverZone(), res);
    }

    @Override
    public boolean isHandle(Income income) {
        // always true
        // default handler
        return true;
    }
}
