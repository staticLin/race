package race.question.demo.deliver;

import race.question.demo.deliver.pojo.Income;
import race.question.demo.deliver.pojo.Result;
import race.question.demo.deliver.pojo.SpecificDeliverInfo;
import race.question.demo.deliver.pojo.ZoneProjectDeliverInfo;
import race.question.demo.deliver.way.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 资金分解器
 *
 * @author linyh
 */
public class NC {

    private final static List<DeliverWay> WAYS = new ArrayList<>(5);

    static {
        WAYS.add(FixedSpecificDeliver.INSTANCE);
        WAYS.add(ProportionalSpecificDeliver.INSTANCE);
        WAYS.add(ZoneProjectDeliver.INSTANCE);
        WAYS.add(ProjectDeliver.INSTANCE);
        WAYS.add(NoDeliver.INSTANCE);

        initDeliverRule();
    }

    public static Result deliver(Integer projectId,
                                 String zoneName,
                                 BigDecimal deliverAmount,
                                 BigDecimal stander,
                                 BigDecimal totalAmount) {

        Income income = new Income();
        income.setProjectId(projectId);

        Zone zone = Zone.getZone(zoneName);
        if (zone == null) {
            throw new RuntimeException("错误的执收区划名称");
        }

        income.setDeliverZone(zone);
        income.setDeliverAmount(deliverAmount);
        income.setStander(stander);
        income.setAmount(totalAmount);

        return deliver0(income);
    }

    private static Result deliver0(Income income) {

        if (income == null) {
            return null;
        }

        Result res = new Result();

        for (DeliverWay deliverWay : WAYS) {

            if (deliverWay.isHandle(income)) {

                deliverWay.deliver(income, res);
                break;
            }
        }

        return res;
    }

    /**
     * 添加分成内容
     */
    private static void initDeliverRule() {

        Zone.ANXI.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN, Zone.QUANZHOU, Zone.ANXI});
        Zone.CANGSHAN.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN, Zone.FUZHOU, Zone.CANGSHAN});
        Zone.JINJIANG.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN, Zone.QUANZHOU, Zone.JINJIANG});
        Zone.GULOU.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN, Zone.FUZHOU, Zone.GULOU});

        Zone.QUANZHOU.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN, Zone.QUANZHOU});
        Zone.FUZHOU.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN, Zone.FUZHOU});

        Zone.FUJIAN.setZones(new Zone[]{Zone.CENTRAL, Zone.FUJIAN});

        Zone.CENTRAL.setZones(new Zone[]{Zone.CENTRAL});

        // 项目分成
        ZoneProjectDeliverInfo info1 = new ZoneProjectDeliverInfo();
        ZoneProjectDeliverInfo info2 = new ZoneProjectDeliverInfo();

        info1.setProjectId(1);
        info1.setFixNum(new BigDecimal[]{BigDecimal.ONE,
                new BigDecimal("2"),
                new BigDecimal("3"),
                new BigDecimal("4")});
        info1.setUpToDown(true);
        info1.setLeftToOwn(true);
        info1.setFixed(true);

        info2.setProjectId(2);
        info2.setRatio(new BigDecimal[]{new BigDecimal("0.1"),
                new BigDecimal("0.2"),
                new BigDecimal("0.3"),
                new BigDecimal("0.4")});
        info2.setUpToDown(true);
        info2.setLeftToOwn(false);
        info2.setFixed(false);

        ProjectDeliver.INSTANCE.addInfo(info1);
        ProjectDeliver.INSTANCE.addInfo(info2);

        // 区划项目分成
        ZoneProjectDeliverInfo info3 = new ZoneProjectDeliverInfo();
        ZoneProjectDeliverInfo info4 = new ZoneProjectDeliverInfo();

        info3.setProjectId(1);
        info3.setZone(Zone.CANGSHAN);
        Zone.CANGSHAN.addDeliverBit(ZoneProjectDeliver.ZONE_PROJECT_DELIVER);
        info3.setRatio(new BigDecimal[]{new BigDecimal("0.1"),
                new BigDecimal("0.4"),
                new BigDecimal("0.3"),
                new BigDecimal("0.4")});
        info3.setUpToDown(true);
        info3.setLeftToOwn(true);

        info4.setProjectId(2);
        info4.setZone(Zone.ANXI);
        Zone.ANXI.addDeliverBit(ZoneProjectDeliver.ZONE_PROJECT_DELIVER);
        info3.setRatio(new BigDecimal[]{new BigDecimal("0.1"),
                new BigDecimal("0.2"),
                new BigDecimal("0.3"),
                new BigDecimal("0.4")});
        info4.setUpToDown(true);
        info4.setLeftToOwn(false);

        ZoneProjectDeliver.INSTANCE.addInfo(info3);
        ZoneProjectDeliver.INSTANCE.addInfo(info4);

        // 定额特定分
        SpecificDeliverInfo info5 = new SpecificDeliverInfo();
        SpecificDeliverInfo info6 = new SpecificDeliverInfo();
        SpecificDeliverInfo info7 = new SpecificDeliverInfo();

        Zone.FUZHOU.addDeliverBit(FixedSpecificDeliver.FIX_SPECIFIC_DELIVER);

        info5.setProjectId(1);
        info5.setOrdered(1);
        info5.setDoDeliverZone(Zone.FUZHOU);
        info5.setToDeliverZone(Zone.CENTRAL);
        info5.setFixNum(new BigDecimal("5"));

        info6.setProjectId(1);
        info6.setOrdered(2);
        info6.setDoDeliverZone(Zone.FUZHOU);
        info6.setToDeliverZone(Zone.QUANZHOU);
        info6.setFixNum(new BigDecimal("5"));

        info7.setProjectId(1);
        info7.setOrdered(3);
        info7.setDoDeliverZone(Zone.FUZHOU);
        info7.setToDeliverZone(Zone.FUJIAN);
        info7.setFixNum(new BigDecimal("5"));

        List<SpecificDeliverInfo> list = new ArrayList<>(3);
        list.add(info5);
        list.add(info6);
        list.add(info7);
        FixedSpecificDeliver.INSTANCE.addInfo(list);

        // 比例特定分
        SpecificDeliverInfo info8 = new SpecificDeliverInfo();
        SpecificDeliverInfo info9 = new SpecificDeliverInfo();
        SpecificDeliverInfo info10 = new SpecificDeliverInfo();

        Zone.QUANZHOU.addDeliverBit(ProportionalSpecificDeliver.PROPORTIONAL_SPECIFIC_DELIVER);

        info8.setProjectId(1);
        info8.setOrdered(1);
        info8.setDoDeliverZone(Zone.QUANZHOU);
        info8.setToDeliverZone(Zone.CENTRAL);
        info8.setRatio(new BigDecimal("0.1"));

        info9.setProjectId(1);
        info9.setOrdered(2);
        info9.setDoDeliverZone(Zone.QUANZHOU);
        info9.setToDeliverZone(Zone.QUANZHOU);
        info9.setRatio(new BigDecimal("0.1"));

        info10.setProjectId(1);
        info10.setOrdered(3);
        info10.setDoDeliverZone(Zone.QUANZHOU);
        info10.setToDeliverZone(Zone.FUJIAN);
        info10.setRatio(new BigDecimal("0.8"));

        List<SpecificDeliverInfo> list2 = new ArrayList<>(3);
        list2.add(info8);
        list2.add(info9);
        list2.add(info10);
        ProportionalSpecificDeliver.INSTANCE.addInfo(list2);

        Zone.initZoneMap();
    }
}
