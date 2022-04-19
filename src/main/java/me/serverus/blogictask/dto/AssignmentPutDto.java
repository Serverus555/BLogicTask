package me.serverus.blogictask.dto;

import com.sun.istack.Nullable;
import me.serverus.blogictask.model.Assignment;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

public class AssignmentPutDto {

    @Nullable
    public Long id;
    public String subject;
    public Long author;
    public ZonedDateTime deadline;
    public String description;
    public List<Long> executors;
    public Assignment.ExecuteStatus executeStatus;
    public Assignment.ControlStatus controlStatus;

    public Assignment createEntity() {
        Assignment a = new Assignment();
        a.setId(id);
        a.setSubject(subject);
        a.setAuthor(null);
        a.setDeadline(deadline);
        a.setDescription(description);
        a.setExecutors(new HashSet<>());
        a.setExecuteStatus(executeStatus);
        a.setControlStatus(controlStatus);
        return a;
    }
}
