package vodzinskiy.coursework;

import lombok.Builder;
import lombok.Getter;
import vodzinskiy.coursework.enums.FileType;

import java.util.List;

@Builder
@Getter
public class File {
    private final FileType type;
    private final boolean readOnly;
    private final List<Integer> blocks;
}
