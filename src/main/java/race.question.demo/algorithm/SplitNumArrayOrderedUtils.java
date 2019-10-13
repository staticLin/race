package race.question.demo.algorithm;

/**
 * dp版本的分割数组
 *
 * @author linyh
 */
public class SplitNumArrayOrderedUtils {

    static int[] inputNumArray;

    static int[] minimumLastArray;

    static int[] maximumFirstArray;

    static int size;

    public static void main(final String[] args) {

        inputNumArray = new int[]{1, 8, 9, 5, 1, 9, 1, 2, 1, 9, 5, 0};

        size = inputNumArray.length;

        findMinimumLastIndex();
        findMaximumFirstIndex();

        int index = 0;

        while (index < size) {

            if (index != 0) {
                System.out.print(',');
            }

            for (int i = index; i <= maximumFirstArray[index]; i++) {
                System.out.print(inputNumArray[i]);
            }

            index = maximumFirstArray[index] + 1;
        }
    }

    public static void findMinimumLastIndex() {

        minimumLastArray = new int[size];
        minimumLastArray[0] = 0;

        for (int i = 1; i < size; i++) {

            for (int j = i; j > 0; j--) {
                if (compareSmallerOrNot(minimumLastArray[j - 1], j - 1, j, i)) {
                    minimumLastArray[i] = j;
                    break;
                }
            }
        }
    }

    public static void findMaximumFirstIndex() {

        int index = size - 1;
        int mini = minimumLastArray[index];

        maximumFirstArray = new int[index + 1];
        maximumFirstArray[mini] = index;

        mini--;
        while (mini >= 0 && inputNumArray[mini] == 0) {
            maximumFirstArray[mini] = index;
            mini--;
        }

        if (mini < 0) {
            return;
        }

        for (int i = mini; i >= 0; i--) {

            for (int j = mini; j >= 0; j--) {

                if (compareSmallerOrNot(i, j, j + 1, maximumFirstArray[j + 1])) {
                    maximumFirstArray[i] = j;
                    break;
                }
            }
        }
    }

    public static boolean compareSmallerOrNot(int leftStart, int leftEnd, int rightStart, int rightEnd) {

        // 遇到0就后移
        while (leftStart <= leftEnd && inputNumArray[leftStart] == 0) {
            leftStart++;
        }
        while (rightStart <= rightEnd && inputNumArray[rightStart] == 0) {
            rightStart++;
        }

        if (leftStart > leftEnd || rightStart > rightEnd) {
            return false;
        }

        int leftLength = leftEnd - leftStart;
        int rightLength = rightEnd - rightStart;

        if (leftLength > rightLength) {
            return false;
        } else if (leftLength < rightLength) {
            return true;
        }

        for (int i = 0; i <= leftLength; i++) {
            if (inputNumArray[leftStart + i] < inputNumArray[rightStart + i]) {
                return true;
            } else if (inputNumArray[leftStart + i] > inputNumArray[rightStart + i]) {
                return false;
            }
        }

        return false;
    }
}
