package com.example.libraryreservation.common.validation;

import jakarta.validation.GroupSequence;
import static com.example.libraryreservation.common.validation.ValidationGroups.*;

@GroupSequence({NotBlankGroup.class, PatternGroup.class, SizeGroup.class, FutureGroup.class, PositiveGroup.class})
public interface ValidationSequence {
}
