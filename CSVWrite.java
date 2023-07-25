import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CSVWrite {

    // 開始時間を記録するメソッド
    public static void writeStartTime(String path, String taskName, String ymd, String hms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write("Task Name,Date,Started Time,Finished Time,Working Time(min),Working Time(sec)");
            writer.write(System.lineSeparator());
            writer.write(taskName + "," + ymd + "," + hms + "," + "-----" + "," + "-----" + "," + "-----");
            writer.write(System.lineSeparator());
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("開始時間の書き込みに失敗しました");
            e.printStackTrace();
        }
    }

    // 終了時間を記録するメソッド
    public static void writeFinishTime(String path, String taskName, String hms) {
        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) { // ファイルの読み込み
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(","); // 行の各セルを文字列として配列に格納
                if (taskName.equals(cells[0])) {
                    cells[3] = hms; // タスク名が合致したら配列に終了時間を追加
                }
                sb.append(String.join(",", cells)).append("\n"); // 配列をカンマ区切りの文字列に変換
            }
        } catch (IOException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(sb.toString()); // csvファイルに文字列を入力
        } catch (IOException e) {
            System.out.println("終了時間の書き込みに失敗しました");
            e.printStackTrace();
        }
    }

    // 作業時間を記録するメソッド
    public static void writeWorkingTime(String path, String taskName) {
        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) { // ファイルの読み込み
            while ((line = reader.readLine()) != null) {
                String[] cells = line.split(","); // 行の各セルを文字列として配列に格納
                if (taskName.equals(cells[0])) {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime startTime = LocalTime.parse(cells[2], format);
                    LocalTime finishTime = LocalTime.parse(cells[3], format);
                    Duration d = Duration.between(startTime, finishTime);
                    long workingTime = d.getSeconds();
                    cells[4] = String.valueOf(workingTime / 60); // かかった時間（分）
                    cells[5] = String.valueOf(workingTime % 60); // かかった時間（秒）
                }
                sb.append(String.join(",", cells)).append("\n");
            }
        } catch (IOException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(sb.toString()); // csvファイルに文字列を入力
        } catch (IOException e) {
            System.out.println("作業時間の書き込みに失敗しました");
            e.printStackTrace();
        }
    }
}