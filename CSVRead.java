import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CSVRead {

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