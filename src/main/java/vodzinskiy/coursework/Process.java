package vodzinskiy.coursework;

import lombok.Getter;
import lombok.Setter;
import vodzinskiy.coursework.enums.BlocksRequestType;
import vodzinskiy.coursework.enums.FileType;
import vodzinskiy.coursework.enums.ProcessState;
import vodzinskiy.coursework.enums.RequestType;

import static vodzinskiy.coursework.Main.random;
import static vodzinskiy.coursework.enums.BlocksRequestType.RANDOM;
import static vodzinskiy.coursework.enums.BlocksRequestType.SEQUENTIAL;
import static vodzinskiy.coursework.enums.ProcessState.*;
import static vodzinskiy.coursework.enums.RequestType.READ;

public class Process {
    public static final int REQUEST_PROCESSING_TIME = 7;

    private final File file;
    private final Processor processor;

    private final HDDController controller;

    @Getter
    private int requestsCounter = 0;

    private int lastRequestedSector;
    private final BlocksRequestType blocksRequestType;

    private ProcessState state = CREATING;
    private int creatingTime = 1;
    private int executionTime = 1;

    private Request createdRequest;

    @Setter
    @Getter
    private boolean creation;

    public Process(File file, Processor processor, HDDController controller) {
        this.file = file;
        this.processor = processor;
        this.controller = controller;

        if (file.getType() == FileType.LARGE && random.nextBoolean()) {
            blocksRequestType = SEQUENTIAL;
        } else {
            blocksRequestType = RANDOM;
        }
        lastRequestedSector = file.getBlocks().getFirst();
    }

    public void execute() {
        if (state == CREATING) {
            if (creatingTime == REQUEST_PROCESSING_TIME) {
                RequestType requestType = file.isReadOnly() ? READ
                        : RequestType.values()[random.nextInt(2)];
                int sector;
                if (blocksRequestType == RANDOM) {
                    sector = file.getBlocks().get(random.nextInt(file.getBlocks().size()));
                } else {
                    sector = file.getBlocks().get((lastRequestedSector + 1) % file.getBlocks().size());
                }
                lastRequestedSector = sector;
                createdRequest = new Request(requestType, sector, this);
                state = CREATED;
            } else {
                creatingTime++;
            }
        } else if (state == CREATED) {
            if (!creation) return;
            try {
                controller.addRequest(createdRequest);
                requestsCounter++;

                if (createdRequest.getType() == READ) {
                    state = BLOCKED;
                } else {
                    state = CREATING;
                    creatingTime = 1;
                }
            } catch (Exception e) {
                processor.processChange();
            }


        } else if (state == EXECUTION) {
            if (executionTime == REQUEST_PROCESSING_TIME) {
                state = CREATING;
                creatingTime = 1;
            } else {
                executionTime++;
            }
        } else {
            throw new IllegalStateException("process is blocked");
        }
    }

    public void processing(Request request) {
        if (request.getType() == READ) {
            state = EXECUTION;
            executionTime = 1;
        }
    }

    public void clearRequestsCounter() {
        requestsCounter = 0;
    }

    public boolean isBlocked() {
        return state == BLOCKED;
    }
}
