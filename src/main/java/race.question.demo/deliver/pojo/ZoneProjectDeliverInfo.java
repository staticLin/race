package race.question.demo.deliver.pojo;

import race.question.demo.deliver.Zone;

import java.math.BigDecimal;

/**
 * 区划项目分成信息
 *
 * @author linyh
 */
public class ZoneProjectDeliverInfo {

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 区划
     */
    private Zone zone;

    /**
     * 余额归属
     */
    private boolean leftToOwn;

    /**
     * 定额
     *
     * @return
     */
    private BigDecimal[] fixNum;

    /**
     * 比例
     *
     * @return
     */
    private BigDecimal[] ratio;

    /**
     * 是否是从上到下
     *
     * @return
     */
    private boolean upToDown;

    /**
     * 是否定额计算
     *
     * @return
     */
    private boolean isFixed;

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public BigDecimal[] getFixNum() {
        return fixNum;
    }

    public void setFixNum(BigDecimal[] fixNum) {
        this.fixNum = fixNum;
    }

    public BigDecimal[] getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal[] ratio) {
        this.ratio = ratio;
    }

    public boolean isUpToDown() {
        return upToDown;
    }

    public void setUpToDown(boolean upToDown) {
        this.upToDown = upToDown;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public boolean isLeftToOwn() {
        return leftToOwn;
    }

    public void setLeftToOwn(boolean leftToOwn) {
        this.leftToOwn = leftToOwn;
    }
}
