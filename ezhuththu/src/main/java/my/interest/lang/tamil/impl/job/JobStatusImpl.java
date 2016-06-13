package my.interest.lang.tamil.impl.job;

import tamil.lang.api.job.JobStatus;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  class JobStatusImpl implements JobStatus {
    public void setStatus(JobStatus.STATUS status) {
        this.status = status;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }


    public boolean shouldWait() {
        return  status == STATUS.RUNNING || status == STATUS.SUBMITTED;
    }

    public STATUS getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    private STATUS status;

    private String statusMessage;

    private Date startTime;
    private Date endTime;
    private Date lastUpdatedTime;
    private List<String> exceptionMessages;

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public void setExceptionMessages(List<String> exceptionMessages) {
        this.exceptionMessages = exceptionMessages;
    }

    private String trace;

    public int getCompletionPercent() {
        return completionPercent;
    }


    public String getExceptionTrace() {
        return trace;
    }


    public List<String> getExceptionMessages() {
        if (exceptionMessages== null) {
            exceptionMessages = Collections.emptyList();
        }
        return exceptionMessages;
    }

    public void setCompletionPercent(int completionPercent) {
        this.completionPercent = completionPercent;
    }

    private int completionPercent;

}
