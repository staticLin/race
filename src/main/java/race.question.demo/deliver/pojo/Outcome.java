package race.question.demo.deliver.pojo;

import race.question.demo.deliver.Zone;

import java.math.BigDecimal;

/**
 * 分解资金
 *
 * @author linyh
 */
public class Outcome {

    public Outcome(String zoneName, BigDecimal amount) {
        this.zoneName = zoneName;
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 收入区划
     */
    private String zoneName;

    /**
     * 分入金额
     */
    private String amount;

    @Override
    public String toString() {
        return "资金分解: {" +
                "区划=" + zoneName +
                ", 分得金额=" + amount +
                '}';
    }
}
