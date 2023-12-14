package vodzinskiy.coursework;

import vodzinskiy.coursework.enums.FileType;

public class File {
    private final FileType type;


    private final int[] blocks;

    public File(FileType type, int[] blocks) {
        this.type = type;
        this.blocks = blocks;
    }

    public FileType getType() {
        return type;
    }

    public int[] getBlocks() {
        return blocks;
    }
}
