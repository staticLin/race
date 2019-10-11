package race.question.demo.algorithm;

/**
 * 消消乐算法
 * 同一行、同一列、同一左斜线、右斜线若存在3个相同的数字则消除
 * 数字会掉落，继续进行新一轮消除，直到不可被消除为止
 * <p>
 * 2 4 3 1 2
 * 3 5 4 1 4
 * 2 3 4 1 3
 * 2 2 2 4 4
 * <p>
 * 2 4 3 0 2
 * 3 5 4 0 4
 * 2 3 4 0 3
 * 0 0 0 4 4
 * <p>
 * 0 0 0 0 2
 * 2 4 3 0 4
 * 3 5 4 0 3
 * 2 3 4 4 4
 * <p>
 * 0 0 0 0 2
 * 2 0 3 0 4
 * 3 5 0 0 3
 * 2 3 0 0 0
 * <p>
 * 0 0 0 0 0
 * 2 0 0 0 2
 * 3 5 0 0 4
 * 2 3 3 0 3
 *
 * @author linyh
 */
public class EliminateSameNum {

    static int x;
    static int y;

    public static void eliminate(int[][] array, String direction) {

        if (array == null || array.length <= 0) {
            return;
        }
        x = array.length;

        if (array[0].length > 0) {
            y = array[0].length;
        } else {
            return;
        }

        int[][] record = new int[x][y];

        int round = 1;

        // 记录相同的数字到record数组中
        while (recordSameNum(array, record, round)) {

            // 调整数组，将record数组记录的需要消除的数据置为零
            adjustArray(array, record, round, direction);
            // 提升轮次，轮次用于记录在record数组中
            round++;
        }
    }

    /**
     * 1 2 3 4 5
     * 0 0 0 0 2
     * 2 0 3 0 4
     * 3 5 0 0 3
     * 2 3 0 0 0
     */
    private static void adjustArray(int[][] array, int[][] record, int round, String direction) {

        for (int i = 0; i < x; i++) {

            for (int j = 0; j < y; j++) {

                if (record[i][j] == round) {
                    array[i][j] = 0;
                }
            }
        }

        // 消除之后
        printArray(array);

        switch (direction) {
            case "D":
                down(array);
                break;
            case "U":
                up(array);
                break;
            case "L":
                left(array);
                break;
            case "R":
                right(array);
                break;
            default:
                // wrong input
                return;
        }

        // 落下之后
        printArray(array);
    }

    private static void right(int[][] array) {

        int lowIndex = -1;

        int j = y - 1;

        for (int i = 0; i < x; i++) {

            while (j >= 0) {

                if (lowIndex == -1 && array[i][j] == 0) {
                    lowIndex = j;
                }

                if (lowIndex != -1) {
                    if (array[j][i] != 0) {
                        array[i][lowIndex] = array[i][j];
                        array[i][j] = 0;
                        lowIndex--;
                    }
                }

                j--;
            }
            j = y - 1;
            lowIndex = -1;
        }
    }


    private static void left(int[][] array) {

        int lowIndex = -1;

        int j = 0;

        for (int i = 0; i < x; i++) {

            while (j <= y - 1) {

                if (lowIndex == -1 && array[i][j] == 0) {
                    lowIndex = j;
                }

                if (lowIndex != -1) {
                    if (array[i][j] != 0) {
                        array[i][lowIndex] = array[i][j];
                        array[i][j] = 0;
                        lowIndex++;
                    }
                }

                j++;
            }
            j = 0;
            lowIndex = -1;
        }
    }

    private static void up(int[][] array) {

        int lowIndex = -1;

        int j = 0;

        for (int i = 0; i < y; i++) {

            while (j <= x - 1) {

                if (lowIndex == -1 && array[j][i] == 0) {
                    lowIndex = j;
                }

                if (lowIndex != -1) {
                    if (array[j][i] != 0) {
                        array[lowIndex][i] = array[j][i];
                        array[j][i] = 0;
                        lowIndex--;
                    }
                }

                j++;
            }
            j = 0;
            lowIndex = -1;
        }
    }

    private static void down(int[][] array) {

        int lowIndex = -1;

        int j = x - 1;

        for (int i = 0; i < y; i++) {

            while (j >= 0) {

                if (lowIndex == -1 && array[j][i] == 0) {
                    lowIndex = j;
                }

                if (lowIndex != -1) {
                    if (array[j][i] != 0) {
                        array[lowIndex][i] = array[j][i];
                        array[j][i] = 0;
                        lowIndex--;
                    }
                }

                j--;
            }
            j = x - 1;
            lowIndex = -1;
        }
    }

    private static void printArray(int[][] array) {

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }

    /**
     * 0 0 0 0 2
     * 2 4 3 0 4
     * 3 5 4 0 3
     * 2 3 4 4 4
     */
    private static boolean recordSameNum(int[][] array, int[][] record, int round) {

        boolean hasSame = false;

        for (int i = 0; i < x; i++) {

            for (int j = 0; j < y; j++) {

                // 处理过了
                if (record[i][j] == round || array[i][j] == 0) continue;

                if (detect(array, i, j, record, round)) hasSame = true;
            }
        }

        return hasSame;
    }

    private static boolean detect(int[][] array, int i, int j, int[][] record, int round) {

        boolean hasSame = false;

        if (i < 0 || i >= x || j < 0 || j >= y) {
            return hasSame;
        }

        int detectNum = array[i][j];
        int expandLeft = 0;
        int expandRight = 0;
        int expandUp = 0;
        int expandDown = 0;
        int expandLUp = 0;
        int expandRUp = 0;
        int expandLDown = 0;
        int expandRDown = 0;

        // 行
        while (isSame(array, i, j - 1 - expandLeft, detectNum)) {
            expandLeft++;
        }
        while (isSame(array, i, j + 1 + expandRight, detectNum)) {
            expandRight++;
        }

        // 列
        while (isSame(array, i - 1 - expandUp, j, detectNum)) {
            expandUp++;
        }
        while (isSame(array, i + 1 + expandDown, j, detectNum)) {
            expandDown++;
        }

        // 左斜线
        while (isSame(array, i - 1 - expandLUp, j - 1 - expandLUp, detectNum)) {
            expandLUp++;
        }
        while (isSame(array, i + 1 + expandRDown, j + 1 + expandRDown, detectNum)) {
            expandRDown++;
        }

        // 右斜线
        while (isSame(array, i - 1 - expandRUp, j + 1 + expandRUp, detectNum)) {
            expandRUp++;
        }
        while (isSame(array, i + 1 + expandLDown, j - 1 - expandLDown, detectNum)) {
            expandLDown++;
        }

        if (expandLeft + expandRight >= 2) {
            hasSame = true;
            for (int k = 0; k <= expandRight; k++) {
                record[i][j + k] = round;
            }
            for (int k = 1; k <= expandLeft; k++) {
                record[i][j - k] = round;
            }
        }

        if (expandUp + expandDown >= 2) {
            hasSame = true;
            for (int k = 0; k <= expandUp; k++) {
                record[i - k][j] = round;
            }
            for (int k = 1; k <= expandDown; k++) {
                record[i + k][j] = round;
            }
        }

        if (expandLUp + expandRDown >= 2) {
            hasSame = true;
            for (int k = 0; k <= expandLUp; k++) {
                record[i - k][j - k] = round;
            }

            for (int k = 1; k <= expandRDown; k++) {
                record[i + k][j + k] = round;
            }
        }

        if (expandRUp + expandLDown >= 2) {
            hasSame = true;
            for (int k = 0; k <= expandRUp; k++) {
                record[i - k][j + k] = round;
            }

            for (int k = 1; k <= expandLDown; k++) {
                record[i + k][j - k] = round;
            }
        }

        return hasSame;
    }

    private static boolean isSame(int[][] array, int i, int j, int pre) {

        if (i < 0 || i >= x || j < 0 || j >= y) {
            return false;
        }

        return array[i][j] == pre;
    }
}
