package race.question.demo.deliver.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分解资金
 *
 * @author linyh
 */
public class Result {

    private final List<Outcome> result;

    public Result() {
        result = new ArrayList<>();
    }

    public void addResult(Outcome outcome) {
        result.add(outcome);
    }

    public List<Outcome> getResult() {
        return result;
    }

    public void print() {
        for (Outcome outcome : result) {
            System.out.println(outcome);
        }
    }
}
