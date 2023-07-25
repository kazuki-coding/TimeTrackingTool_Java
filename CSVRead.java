import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.InputMismatchException;

public class CSVRead {

    // 開始タスク名が利用できるかチェックするメソッド
    public static void checkStartTaskNameIsUsable(String path, String taskName) {
        String line;
        Set<String> taskNames = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) { // ファイルの読み込み
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(",");
                if (cells.length > 0) {
                    String cellA = cells[0]; // A列のセル情報を格納
                    taskNames.add(cellA);
                }
            }
        } catch (IOException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        }
        if (taskNames.contains(taskName)) {
            throw new InputMismatchException("そのタスクは開始を宣言済みです"); // タスク名が使用済みなら記録不可
        }
    }

    // 終了タスク名が利用できるかチェックするメソッド
    public static void checkFinishTaskNameIsUsable(String path, String taskName) {
        String line;
        Set<String> taskNames = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) { // ファイルの読み込み
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(",");
                if (cells.length > 0) {
                    String cellA = cells[0]; // A列のセル情報を格納
                    taskNames.add(cellA);
                }
            }
        } catch (IOException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        }
        if (!(taskNames.contains(taskName))) {
            throw new InputMismatchException("そのタスクは開始を宣言していません"); // タスク名が使用されてないなら記録不可
        }
    }

    // 当日のタスク名と作業時間の一覧を取得するメソッド
    public static void getTodayWorkingTime(String path, String ymd) {
        String line;
        int MinutesOfWorkingTime = 0;
        int SecondsOfWorkingTime = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((line = reader.readLine()) != null) {
                String cells[] = line.split(",");
                if (cells[0].isEmpty()) {
                    continue;
                } else if (ymd.equals(cells[1])) {
                    System.out.println("・" + cells[0] + "の作業時間は" + cells[4] + "分" + cells[5] + "秒でした");
                    MinutesOfWorkingTime += Integer.parseInt(cells[4]);
                    SecondsOfWorkingTime += Integer.parseInt(cells[5]);
                }
            }
        } catch (IOException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        }
        MinutesOfWorkingTime += (SecondsOfWorkingTime / 60);
        System.out.println("-------------------------------");
        System.out.println("本日の合計作業時間は" + MinutesOfWorkingTime + "分" + (SecondsOfWorkingTime % 60) + "秒でした");
    }

    // 直近7日間の日別作業時間を取得するメソッド
    public static void getWeekWorkingTime(String path, String ymd) {
        String line;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate today = LocalDate.parse(ymd, format);
        int[] minutesOfWorkingTime = new int[7];
        int[] secondsOfWorkingTime = new int[7];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((line = reader.readLine()) != null) {
                String cells[] = line.split(",");
                for (int i = 6; i >= 0; i--) {
                    Period p = Period.ofDays(i);
                    LocalDate ld = today.minus(p);
                    if (cells[0].isEmpty()) {
                        continue;
                    } else if (ld.format(format).equals(cells[1])) {
                        minutesOfWorkingTime[6 - i] += Integer.parseInt(cells[4]);
                        secondsOfWorkingTime[6 - i] += Integer.parseInt(cells[5]);

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        }
        for (int i = 6; i >= 0; i--) {
            Period p = Period.ofDays(i);
            LocalDate ld = today.minus(p);
            minutesOfWorkingTime[6 - i] += (secondsOfWorkingTime[6 - i] / 60);
            System.out.println(ld.format(format) + "の合計作業時間は" + minutesOfWorkingTime[6 - i] + "分"
                    + (secondsOfWorkingTime[6 - i] % 60) + "秒でした");
        }
    }
}