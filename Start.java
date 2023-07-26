import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Start {
    private String filePath;
    private String startTaskName;
    private String ymd;
    private String hms;
    private String line;

    public Start(String filePath, String startTaskName, String ymd, String hms) {
        this.filePath = filePath;
        this.startTaskName = startTaskName;
        this.ymd = ymd;
        this.hms = hms;
    }

    // csvファイル内に指定したタスク名がなければtrue、あればfalseを返す
    public boolean isTaskNameValid() {
        Set<String> taskNames = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((this.line = br.readLine()) != null) {
                String[] cells = line.split(",");
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
        if (!(taskNames.contains(this.startTaskName))) {
            return true;
        } else {
            return false;
        }
    }

    // 開始時間を記録する
    public void writeStartTime() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath))) {
            bw.write("Task Name,Date,Started Time,Finished Time,Working Time(min),Working Time(sec)");
            bw.newLine();
            bw.write(this.startTaskName + "," + this.ymd + "," + this.hms + "," + "-----" + "," + "-----" + ","
                    + "-----");
            bw.newLine();
            bw.newLine();
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル書き込み時にエラーが発生しました");
            e.printStackTrace();
        }
    }
}