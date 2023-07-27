import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Finish {
    private String filePath;
    private String finishTaskName;
    private String ymd;
    private String hms;
    private String line;

    public Finish(String filePath, String finishTaskName, String ymd, String hms) {
        this.filePath = filePath;
        this.finishTaskName = finishTaskName;
        this.ymd = ymd;
        this.hms = hms;
    }

    // csvファイル内に指定したタスク名があればtrue、なければfalseを返す
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
        if (taskNames.contains(this.finishTaskName)) {
            return true;
        } else {
            return false;
        }
    }

    // 終了時間を記録する
}
