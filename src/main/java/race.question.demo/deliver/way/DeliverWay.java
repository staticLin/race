package race.question.demo.deliver.way;

import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;

/**
 * 分成方式
 *
 * @author linyh
 */
public interface DeliverWay {

    /**
     * 资金分解
     *
     * @param income
     * @param res
     * @return
     */
    void deliver(Income income, Result res);

    /**
     * 是否可以处理
     *
     * @return
     */
    boolean isHandle(Income income);

}
