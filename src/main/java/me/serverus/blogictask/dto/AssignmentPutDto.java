package me.serverus.blogictask.dto;

import com.sun.istack.Nullable;
import me.serverus.blogictask.model.Assignment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AssignmentPutDto {

    @Nullable
    public Long id;
    @NotEmpty
    public String subject;
    @NotNull
    public Long author;
    @NotNull
    public Date deadline;
    @NotEmpty
    public String description;
    @NotNull
    public List<Long> executors;
    @NotNull
    public Assignment.ExecuteStatus executeStatus;
    @NotNull
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
