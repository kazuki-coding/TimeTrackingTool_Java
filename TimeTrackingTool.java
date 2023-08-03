import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTrackingTool {
    public static void main(String[] args) {
        String filePath = "C://Java//Sample.csv"; // csvファイルを指定

        // -s,-f,-vt,vwのいずれかを受け取る
        Scanner scanner = new Scanner(System.in);
        System.out.print("コマンドを指定してください：");
        String cmd = scanner.next();

        // 各コマンドごとの処理
        try (FileWriter fw = new FileWriter(filePath, true)) {

            // 日時のフォーマットを指定
            Date date = new Date();
            SimpleDateFormat yearMonthDay = new SimpleDateFormat("yy/MM/dd");
            SimpleDateFormat hourMinuteSecond = new SimpleDateFormat("HH:mm:ss");
            String ymd = yearMonthDay.format(date);
            String hms = hourMinuteSecond.format(date);

            switch (cmd) {
                // -sの場合
                case "-s":

                    // タスク名を指定してインタンス化
                    String startTaskName = getTaskName();
                    Start s = new Start(filePath, startTaskName, ymd, hms);

                    // タスク名が適切か判断してタスク名と開始時間を記録
                    if (s.isTaskNameValid()) {
                        s.writeStartTime();
                        System.out.println("タスク『" + startTaskName + "』の開始時間を記録しました");
                    } else {
                        System.out.println("そのタスクは開始を宣言済みです");
                    }
                    break;

                // -fの場合
                case "-f":

                    // タスク名を指定してインスタンス化
                    String finishTaskName = getTaskName();
                    Finish f = new Finish(filePath, finishTaskName, hms);

                    // タスク名が適切か判断して終了時間と作業時間を記録
                    if (f.isTaskNameValid()) {
                        f.writeFinishTime();
                        f.writeWorkingTime();
                        System.out.println("タスク『" + finishTaskName + "』の終了時間と作業時間を記録しました");
                    } else {
                        System.out.println("そのタスクは開始を宣言してません");
                    }
                    break;

                // -vtの場合
                case "-vt":

                    // 本日のタスク名と作業時間を読み取る(すべて)
                    System.out.println("『本日のタスクと作業時間』");
                    CSVRead.getTodayWorkingTime(filePath, ymd);
                    break;

                // -vwの場合
                case "-vw":
                    CSVRead.getWeekWorkingTime(filePath, ymd);
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