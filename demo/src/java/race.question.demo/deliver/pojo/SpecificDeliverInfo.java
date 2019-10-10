package race.question.demo.deliver.pojo;

import race.question.demo.deliver.Zone;

import java.math.BigDecimal;

/**
 * 特定分成信息
 *
 * @author linyh
 */
public class SpecificDeliverInfo {

    /**
     * 执收区划
     */
    private Zone doDeliverZone;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 顺序
     */
    private Integer ordered;

    /**
     * 分入区划
     */
    private Zone toDeliverZone;

    /**
     * 定额
     *
     * @return
     */
    private BigDecimal fixNum;

    /**
     * 比例
     *
     * @return
     */
    private BigDecimal ratio;

    public BigDecimal getFixNum() {
        return fixNum;
    }

    public void setFixNum(BigDecimal fixNum) {
        this.fixNum = fixNum;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Zone getDoDeliverZone() {
        return doDeliverZone;
    }

    public void setDoDeliverZone(Zone doDeliverZone) {
        this.doDeliverZone = doDeliverZone;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public Zone getToDeliverZone() {
        return toDeliverZone;
    }

    public void setToDeliverZone(Zone toDeliverZone) {
        this.toDeliverZone = toDeliverZone;
    }
}
