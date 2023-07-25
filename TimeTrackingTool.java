import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTrackingTool {
    public static void main(String[] args) {
        final String SAMPLE_PATH = "C://Java//Sample.csv"; // csvファイルを指定

        // -s,-f,-vt,vwのいずれかを受け取る
        Scanner scanner = new Scanner(System.in);
        System.out.print("コマンドを指定してください：");
        String cmd = scanner.next();

        // 各コマンドごとの処理
        try (FileWriter fw = new FileWriter(SAMPLE_PATH, true)) {

            // 日時のフォーマットを指定
            Date date = new Date();
            SimpleDateFormat yearMonthDay = new SimpleDateFormat("yy/MM/dd");
            SimpleDateFormat hourMinuteSecond = new SimpleDateFormat("HH:mm:ss");
            String ymd = yearMonthDay.format(date);
            String hms = hourMinuteSecond.format(date);

            switch (cmd) {
                // -sの場合
                case "-s":

                    // タスク名を指定
                    String startTaskName = getTaskName();

                    // タスク名が適切か判断する
                    CSVRead.checkStartTaskNameIsUsable(SAMPLE_PATH, startTaskName);

                    // タスク名と開始時間を記録
                    CSVWrite.writeStartTime(SAMPLE_PATH, startTaskName, ymd, hms);
                    System.out.println("タスク『" + startTaskName + "』の開始時間を記録しました");
                    break;

                // -fの場合
                case "-f":

                    // タスク名を指定
                    String finishTaskName = getTaskName();

                    // タスク名が適切か判断する
                    CSVRead.checkFinishTaskNameIsUsable(SAMPLE_PATH, finishTaskName);

                    // タスクの終了時間を記録
                    CSVWrite.writeFinishTime(SAMPLE_PATH, finishTaskName, hms);
                    System.out.println("タスク『" + finishTaskName + "』の終了時間を記録しました");

                    // タスクの作業時間を算出
                    CSVWrite.writeWorkingTime(SAMPLE_PATH, finishTaskName);
                    break;

                // -vtの場合
                case "-vt":

                    // 本日のタスク名と作業時間を読み取る(すべて)
                    System.out.println("『本日のタスクと作業時間』");
                    CSVRead.getTodayWorkingTime(SAMPLE_PATH, ymd);
                    break;

                // -vwの場合
                case "-vw":
                    CSVRead.getWeekWorkingTime(SAMPLE_PATH, ymd);
                    break;

                // どれでもない場合
                default:

                    break;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTaskName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("タスク名を入力してください：");
        String taskName = scanner.next();
        scanner.close();
        return taskName;
    }
}