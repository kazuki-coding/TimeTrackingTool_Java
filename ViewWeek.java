import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewWeek {
    private String filePath;
    private String ymd;

    public ViewWeek(String filePath, String ymd) {
        this.filePath = filePath;
        this.ymd = ymd;
    }

    public void indicateWorkingTimeEachDay() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate today = LocalDate.parse(ymd, f);

        for (int i = 6; i >= 0; i--) {
            String line;
            String aDay = today.minusDays(i).format(f);
            int sumOfMin = 0;
            int sumOfSec = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] cells = line.split(",");
                    if (cells.length > 1 && aDay.equals(cells[1])) {
                        sumOfMin += Integer.parseInt(cells[4]);
                        sumOfSec += Integer.parseInt(cells[5]);
                    }
                }
                System.out.println(aDay + "の作業時間：" + (sumOfMin + (sumOfSec / 60)) + "分" + (sumOfSec % 60) + "秒");
                System.out.println("-------------------------------------------");
            } catch (FileNotFoundException e) {
                System.out.println("指定されたファイルが見つかりませんでした");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("ファイル読み込み時にエラーが発生しました");
                e.printStackTrace();
            }
        }
    }
}
