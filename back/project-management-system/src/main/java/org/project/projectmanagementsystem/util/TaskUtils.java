package org.project.projectmanagementsystem.util;

import lombok.experimental.UtilityClass;
import org.project.projectmanagementsystem.domain.Task;

import java.util.EnumSet;

@UtilityClass
public class TaskUtils {

    public EnumSet<Task.TaskStatus> taskStatuses(String type) {
        if("ALL".equals(type)){
            return EnumSet.allOf(Task.TaskStatus.class);
        }
        return EnumSet.of(Task.TaskStatus.valueOf(type.toUpperCase()));
    }
}
