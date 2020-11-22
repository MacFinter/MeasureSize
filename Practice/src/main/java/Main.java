import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.Logger;

import Exceptions.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;


public class Main {

    private static Logger logger;
    private static final Marker INPUT_HISTORY_DATA
            = MarkerManager.getMarker("INPUT_HISTORY_DATA");
    private static final Marker INPUT_ERRORS
            = MarkerManager.getMarker("INPUT_ERRORS");
    private static final Marker THROWS_EXCEPTIONS
            = MarkerManager.getMarker("THROWS_EXCEPTIONS");

    private static final String CORRECT_FORMAT_FOR_VIEW = "C:/Games";
    private static final String INTRODUCE = "Введите путь до папки. Например: "
            + CORRECT_FORMAT_FOR_VIEW;
    private static final String INCORRECT_MESSAGE
            ="Введены неверные данные. Путь должен быть формата \""
            + CORRECT_FORMAT_FOR_VIEW + "\"";

    private static final int GIGABYTE = 1073741824;
    private static final int MEGABYTE = 1048576;
    private static final int KILOBYTE = 1024;

    public static void main(String[] args) {

        logger = LogManager.getRootLogger();
        Scanner scanner = new Scanner(System.in);
        String takePath;

        while (true) {
            try {
                System.out.println(INTRODUCE);
                takePath = scanner.nextLine();

                if ((Files.isDirectory(Paths.get(takePath)))
                        && (!takePath.equals(""))) {
                    logger.info(INPUT_HISTORY_DATA, takePath);
                    System.out.println("Размер папки составляет: "
                            + humanReadableFormat(getSizePackage(takePath)));
                } else {
                    logger.warn(INPUT_ERRORS,takePath);
                    throw new InvalidFormatException(INCORRECT_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(THROWS_EXCEPTIONS,e.getMessage());
            }
        }
    }

    static long getSizePackage(String path) {

        long sizePath = 0;
        try {
             sizePath = Files.walk(Paths.get(path))
                    .filter(f -> !f.toFile().isDirectory())
                    .mapToLong(files -> files.toFile().length())
                    .sum();
        } catch (Exception e) {
            logger.error(THROWS_EXCEPTIONS, e.getMessage());
            e.printStackTrace();
        }

        return sizePath;
    }

    static String humanReadableFormat(long sizePackage) {

        String result;
        DecimalFormat dFormat = new DecimalFormat("#.##");

        if (sizePackage >= GIGABYTE) {
            result = dFormat.format(sizePackage / (double) GIGABYTE) + " ГБ";
        } else if (sizePackage >= MEGABYTE) {
            result = dFormat.format(sizePackage / (double) MEGABYTE) + " МБ";
        } else if (sizePackage >= KILOBYTE) {
            result = dFormat.format(sizePackage / (double) KILOBYTE) + " КБ";
        } else if (sizePackage <= KILOBYTE) {
            result = sizePackage + " байт";
        } else {
            result = 0 + "байт";
        }

        return result;
    }
}

