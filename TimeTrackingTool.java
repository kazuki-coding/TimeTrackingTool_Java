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
                // タスク名が適切か判断してタスク名と開始時間を記録する
                case "-s":
                    String startTaskName = getTaskName();
                    Start s = new Start(filePath, startTaskName, ymd, hms);
                    s.writeStartTime();
                    break;

                // タスク名が適切か判断して終了時間と作業時間を記録する
                case "-f":
                    String finishTaskName = getTaskName();
                    Finish f = new Finish(filePath, finishTaskName, hms);
                    f.writeFinishTime();
                    f.writeWorkingTime();
                    break;

                // 本日のタスク名と作業時間を表示する
                case "-vt":
                    ViewToday vt = new ViewToday(filePath, ymd);
                    vt.indicateTodayWorkingTime();
                    break;

                // 直近7日間の作業時間を表示する
                case "-vw":
                    ViewWeek vw = new ViewWeek(filePath, ymd);
                    vw.indicateWorkingTimeEachDay();
                    break;

                // どれでもない場合
                default:
                    System.out.println("-s,-f,-vt,-vwのどれかを指定してください");
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