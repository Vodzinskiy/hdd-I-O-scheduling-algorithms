package vodzinskiy.coursework;

import vodzinskiy.coursework.algorithms.FCFS;
import vodzinskiy.coursework.algorithms.F_LOOK;
import vodzinskiy.coursework.algorithms.SSTF;
import vodzinskiy.coursework.algorithms.SchedulingAlgorithm;
import vodzinskiy.coursework.enums.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int PROCESS_NUMBER = 10;
    public static int MAXIMUM_REQUEST_NUMBER = 20;
    public static int REQUESTS_NUMBER = 100000;

    public static double WRITE_PROBABILITY = 0.3;

    public static Random random = new Random(0);

    static HDD hdd = new HDD();

    public static void main(String[] args) {

        System.out.println("Choose an algorithm:");
        System.out.println("    1) FCFS");
        System.out.println("    2) SSTF");
        System.out.println("    3) F_LOOK");
        Scanner scanner = new Scanner(System.in);
        int algorithmNumber = scanner.nextInt();

        SchedulingAlgorithm algorithm = switch (algorithmNumber) {
            case 1 -> new FCFS();
            case 2 -> new SSTF();
            case 3 -> new F_LOOK();
            default -> throw new IllegalArgumentException("non-existent algorithm");
        };

        System.out.println("Number of requests per second:");
        int requestsPerSecond = scanner.nextInt();

        List<Process> processes = new ArrayList<>(PROCESS_NUMBER);

        int currentBlock = 0;

        for (int i = 0; i < PROCESS_NUMBER; i++) {
            processes.add(new Process(generateFile(currentBlock)));
        }

    }

    public static File generateFile(int currentBlock) {
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
}