import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Finish {
    private String filePath;
    private String finishTaskName;
    private String hms;
    private String line;

    public Finish(String filePath, String finishTaskName, String hms) {
        this.filePath = filePath;
        this.finishTaskName = finishTaskName;
        this.hms = hms;
    }

    // csvファイル内に指定したタスク名があればtrue、なければfalseを返す
    public boolean isTaskNameValid() {
        Set<String> taskNames = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            while ((this.line = br.readLine()) != null) {
                String[] cells = this.line.split(",");
                if (cells.length > 0) {
                    taskNames.add(cells[0]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル読み込み時にエラーが発生しました");
            e.printStackTrace();
        }
        if (taskNames.contains(this.finishTaskName)) {
            return true;
        } else {
            return false;
        }
    }

    // 終了時間を記録する
    public void writeFinishTime() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            while ((this.line = br.readLine()) != null) {
                String[] cells = this.line.split(",");
                if (this.finishTaskName.equals(cells[0])) {
                    cells[3] = this.hms;
                }
                sb.append(String.join(",", cells)).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル読み込み時にエラーが発生しました");
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath))) {
            bw.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル書き込み時にエラーが発生しました");
            e.printStackTrace();
        }
    }

    // 作業時間を記録する
    public void writeWorkingTime() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            while ((this.line = br.readLine()) != null) {
                String[] cells = this.line.split(",");
                if (this.finishTaskName.equals(cells[0])) {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime startTime = LocalTime.parse(cells[2], format);
                    LocalTime finishTime = LocalTime.parse(cells[3], format);
                    Duration d = Duration.between(startTime, finishTime);
                    long workingTime = d.getSeconds();
                    cells[4] = String.valueOf(workingTime / 60);
                    cells[5] = String.valueOf(workingTime % 60);
                }
                sb.append(String.join(",", cells)).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル読み込み時にエラーが発生しました");
            e.printStackTrace();
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(this.filePath))) {
            br.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル書き込み時にエラーが発生しました");
            e.printStackTrace();
        }
    }
}
