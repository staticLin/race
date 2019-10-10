package race.question.demo.deliver.pojo;

import race.question.demo.deliver.Zone;

import java.math.BigDecimal;

/**
 * 收入资金
 *
 * @author linyh
 */
public class Income {

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 执收区划
     */
    private Zone deliverZone;

    /**
     * 执收数量
     */
    private BigDecimal deliverAmount;

    /**
     * 标准
     */
    private BigDecimal stander;

    /**
     * 总金额
     */
    private BigDecimal amount;

    public boolean isZero() {
        if (amount == null)
            return true;

        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Zone getDeliverZone() {
        return deliverZone;
    }

    public void setDeliverZone(Zone deliverZone) {
        this.deliverZone = deliverZone;
    }

    public BigDecimal getDeliverAmount() {
        return deliverAmount;
    }

    public void setDeliverAmount(BigDecimal deliverAmount) {
        this.deliverAmount = deliverAmount;
    }

    public BigDecimal getStander() {
        return stander;
    }

    public void setStander(BigDecimal stander) {
        this.stander = stander;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
