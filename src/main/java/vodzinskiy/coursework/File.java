package vodzinskiy.coursework;

import vodzinskiy.coursework.enums.FileType;

import java.util.List;

public record File(FileType type, boolean readOnly, List<Integer> blocks) {
}
