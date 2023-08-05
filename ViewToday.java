import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ViewToday {
    private String filePath;
    private String ymd;

    public ViewToday(String filePath, String ymd) {
        this.filePath = filePath;
        this.ymd = ymd;
    }

    public void getTodayWorkingTime() {
        String line;
        int sumOfMinutes = 0;
        int sumOfSeconds = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                if ((cells.length > 1) && (this.ymd.equals(cells[1]))) {
                    System.out.println("タスク:" + cells[0] + "   作業時間：" + cells[4] + "分" + cells[5] + "秒");
                    sumOfMinutes += Integer.parseInt(cells[4]);
                    sumOfSeconds += Integer.parseInt(cells[5]);
                }
            }
            System.out.println("-------------------------------------------");
            System.out.println("本日の合計作業時間：" + (sumOfMinutes + (sumOfSeconds / 60)) + "分" + (sumOfSeconds % 60) + "秒");
        } catch (FileNotFoundException e) {
            System.out.println("指定されたファイルが見つかりませんでした");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ファイル読み込み時にエラーが発生しました");
            e.printStackTrace();
        }
    }
}
