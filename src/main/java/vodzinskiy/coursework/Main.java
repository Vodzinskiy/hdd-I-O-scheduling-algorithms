package vodzinskiy.coursework;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import vodzinskiy.coursework.algorithms.FCFS;
import vodzinskiy.coursework.algorithms.F_LOOK;
import vodzinskiy.coursework.algorithms.SSTF;
import vodzinskiy.coursework.algorithms.SchedulingAlgorithm;
import vodzinskiy.coursework.enums.FileType;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static int PROCESS_NUMBER = 10;
    public static int MAXIMUM_REQUEST_NUMBER = 20;
    public static int REQUESTS_NUMBER = 100000;

    public static double WRITE_PROBABILITY = 0.3;

    public static Random random = new Random(0);
    static int currentBlock = 0;
    static int requestCounter = 0;

    static long time = 0;

    public static void main(String[] args) {

        System.out.println("Choose an algorithm:");
        System.out.println("    1) FCFS");
        System.out.println("    2) SSTF");
        System.out.println("    3) F_LOOK");
        Scanner scanner = new Scanner(System.in);
        int algorithmNumber = scanner.nextInt();

        SchedulingAlgorithm algorithm = switch (algorithmNumber) {
            case 1 -> new FCFS(MAXIMUM_REQUEST_NUMBER);
            case 2 -> new SSTF(MAXIMUM_REQUEST_NUMBER);
            case 3 -> new F_LOOK(MAXIMUM_REQUEST_NUMBER);
            default -> throw new IllegalArgumentException("non-existent algorithm");
        };

        System.out.println("Number of requests per second:");
        int requestsPerSecond = scanner.nextInt();


        List<Process> processes = new ArrayList<>(PROCESS_NUMBER);
        HDD hdd = new HDD();
        HDDController hddController = new HDDController(hdd, algorithm);
        Processor processor = new Processor(processes, requestsPerSecond);

        for (int i = 0; i < PROCESS_NUMBER; i++) {
            processes.add(new Process(generateFile(hdd), processor, hddController));
        }

        while (requestCounter < REQUESTS_NUMBER) {
            processor.execute();
            hddController.execute();
            hdd.execute();
            time++;
        }
        System.out.println("Time: " + time + " ms\n");
        System.out.println(printHdd(hdd.getTracks()));
        chart(hddController.getRequestTimes().subList(0, 300));
    }

    public static void increaseCounter() {
        requestCounter++;
    }

    public static String printHdd(boolean[][] hardDriveTracks) {
        return IntStream.range(0, 500)
                .mapToObj(trackNumber -> trackNumber+1 + "\t" +
                        IntStream.range(0, 100)
                                .mapToObj(sectorNumber -> hardDriveTracks[trackNumber][sectorNumber] ? "1" : "0")
                                .collect(Collectors.joining("")))
                .collect(Collectors.joining("\n"));
    }


    public static File generateFile(HDD hdd) {
        FileType fileType = FileType.values()[random.nextInt(FileType.values().length)];
        int fileSize = switch (fileType) {
            case SMALL -> random.nextInt(1, 11);
            case MEDIUM -> random.nextInt(11, 151);
            case LARGE -> random.nextInt(151, 501);
        };
        List<Integer> blocks = new ArrayList<>(fileSize);
        for (int j = 0; j < fileSize; j++) {
            int block;
            if (WRITE_PROBABILITY > random.nextDouble()) {
                block = currentBlock;
                currentBlock++;
            } else {
                block = currentBlock + 1;
                currentBlock += 2;
            }
            blocks.add(block);
            hdd.markingSector(block);
        }
        return new File(fileType, random.nextBoolean(), blocks);
    }

    public static void chart(List<Integer> list) {
        XYChart chart = new XYChartBuilder().width(1280).height(540).build();

        chart.addSeries("chart", list);
        chart.getStyler().setMarkerSize(0);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setChartBackgroundColor(new Color(0xFFFFFF));
        chart.getStyler().setPlotBorderVisible(false);
        new SwingWrapper<>(chart).displayChart();
    }
}