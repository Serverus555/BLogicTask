package me.serverus.blogictask.model;

import javax.persistence.*;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "author")
    private Employee author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "assignment_employee",
            joinColumns = @JoinColumn(name = "assignment"),
            inverseJoinColumns = @JoinColumn(name = "employee"))
    private Set<Employee> executors = new LinkedHashSet<>();

    @Column(name = "deadline")
    private ZonedDateTime deadline;

    @Enumerated
    @Column(name = "control_status")
    private ControlStatus controlStatus;

    @Enumerated
    @Column(name = "execute_status")
    private ExecuteStatus executeStatus;

    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExecuteStatus getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(ExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    public ControlStatus getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(ControlStatus controlStatus) {
        this.controlStatus = controlStatus;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<Employee> getExecutors() {
        return executors;
    }

    public void setExecutors(Set<Employee> executors) {
        this.executors = executors;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author_employee_id) {
        this.author = author_employee_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum ControlStatus {
        WAIT,
        HANDLING,
        REJECTED,
        ACCEPTED,
    }

    public enum ExecuteStatus {
        PREPARE,
        EXECUTE,
        CONTROL,
        REWORK,
        ACCEPTED;

        public boolean validateControlStatus(ControlStatus control) {
            return Arrays.stream(validControlStatuses[this.ordinal()]).anyMatch(val -> val == control);
        }

        private static final ControlStatus[][] validControlStatuses = new ControlStatus[][] {
                new ControlStatus[] {ControlStatus.WAIT},
                new ControlStatus[] {ControlStatus.WAIT, ControlStatus.REJECTED},
                new ControlStatus[] {ControlStatus.HANDLING, ControlStatus.ACCEPTED, ControlStatus.REJECTED},
                new ControlStatus[] {ControlStatus.REJECTED},
                new ControlStatus[] {ControlStatus.ACCEPTED},
        };
    }
}