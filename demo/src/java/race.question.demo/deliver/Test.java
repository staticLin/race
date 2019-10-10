package race.question.demo.deliver;

import race.question.demo.deliver.pojo.Result;

import java.math.BigDecimal;

/**
 * @author linyh
 */
public class Test {

    public static void main(String[] args) {

        Integer projectId = 1;
        String zoneName = "福州市";
        BigDecimal deliverAmount = new BigDecimal("10");
        BigDecimal stander = new BigDecimal("10");
        BigDecimal totalAmount = new BigDecimal("100");

        Result result = NC.deliver(projectId, zoneName, deliverAmount, stander, totalAmount);
        if (result != null) {
            result.print();
        }

        System.out.println("------------------");

        projectId = 1;
        zoneName = "仓山区";
        deliverAmount = new BigDecimal("10");
        stander = new BigDecimal("10");
        totalAmount = new BigDecimal("100");

        result = NC.deliver(projectId, zoneName, deliverAmount, stander, totalAmount);
        if (result != null) {
            result.print();
        }

        System.out.println("------------------");

        projectId = 1;
        zoneName = "晋江市";
        deliverAmount = new BigDecimal("10");
        stander = new BigDecimal("-10");
        totalAmount = new BigDecimal("-100");

        result = NC.deliver(projectId, zoneName, deliverAmount, stander, totalAmount);
        if (result != null) {
            result.print();
        }

        System.out.println("------------------");

        projectId = 2;
        zoneName = "晋江市";
        deliverAmount = new BigDecimal("10");
        stander = new BigDecimal("-20");
        totalAmount = new BigDecimal("-201");

        result = NC.deliver(projectId, zoneName, deliverAmount, stander, totalAmount);
        if (result != null) {
            result.print();
        }

        System.out.println("------------------");

        projectId = 2;
        zoneName = "晋江市";
        deliverAmount = new BigDecimal("503");
        stander = new BigDecimal("-10");
        totalAmount = new BigDecimal("-32423426");

        result = NC.deliver(projectId, zoneName, deliverAmount, stander, totalAmount);
        if (result != null) {
            result.print();
        }

    }
}
