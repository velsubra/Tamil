package my.interest.tamil.rest.resources.api;

import my.interest.lang.util.TimeUtils;
import my.interest.tamil.rest.resources.exception.ResourceNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobResultChunk;
import tamil.lang.api.job.JobResultSnapShot;

import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/jobs/")
public class JobResource extends BaseResource {


    @GET
    @Path("/list/")
    @Produces("application/json; charset=UTF-8")
    public String listJobs(@QueryParam("includeUnits") boolean includeUnits) throws Exception {
        return listJobs(null, includeUnits);
    }


    @GET
    @Path("/list/category/{categoryName:.*}")
    @Produces("application/json; charset=UTF-8")
    public String listJobs(@PathParam("categoryName") String categoryName, @QueryParam("includeUnits") boolean includeUnits) throws Exception {
        List<Long> list = TamilFactory.getJobManager(categoryName).listJobIds();
        JSONObject obj = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            obj.put("jobs", array);
            for (long id : list) {
                JSONObject job = describeJobWithCategory(id, 0, categoryName, includeUnits);
                array.put(job);
            }

        } catch (Exception e) {
            handle(obj, e);
        }
        return obj.toString();
    }


    @GET
    @Path("/id/{jobid}/")
    @Produces("application/json; charset=UTF-8")
    public String readJobDefault(@PathParam("jobid") long number,
                                 @DefaultValue("0") @QueryParam("last-continuation-id") int continuation_id,
                                 @QueryParam("includeUnits") boolean includeUnits) throws Exception {
        return readJobDefaultWithCateGory(number, continuation_id, null, includeUnits);
    }

    @GET
    @Path("/id/{jobid}/category/{categoryName:.*}")
    @Produces("application/json; charset=UTF-8")
    public String readJobDefaultWithCateGory(@PathParam("jobid") long number,
                                             @DefaultValue("0") @QueryParam("last-continuation-id") int continuation_id,
                                             @PathParam("categoryName") String categoryName, @QueryParam("includeUnits") boolean includeUnits) throws Exception {
        return describeJobWithCategory(number, continuation_id, categoryName, includeUnits).toString();
    }


    private JSONObject describeJobWithCategory(long number,
                                               int continuation_id,
                                               String categoryName, boolean includeUnits) throws Exception {
        JSONObject obj = new JSONObject();
        try {

            JobResultSnapShot<String> result = TamilFactory.getJobManager(categoryName).findJobResultSnapShot(number, String.class);
            if (result == null) {
                throw new ResourceNotFoundException();
            }
            tamil.lang.api.job.JobStatus status = result.getStatus();
            obj.put("id", number);
            if (result.getTitleMessage() != null) {
                obj.put("titleMessage", result.getTitleMessage());
            }
            obj.put("status", status.getStatus().toString());
            if (status.getEndTime() != null) {
                obj.put("timeTaken", TimeUtils.millisToLongDHMS(status.getEndTime().getTime() - status.getStartTime().getTime(), false));
            }
            obj.put("percent", status.getCompletionPercent());
            obj.put("unitType", result.getSerializedType().toString());
            obj.put("startedDesc", TimeUtils.millisToLongDHMS(new Date().getTime() - status.getStartTime().getTime(), false));
            obj.put("updatedDesc", TimeUtils.millisToLongDHMS(new Date().getTime() - status.getLastUpdatedTime().getTime(), false));
            JobResultChunk<String> chunks = result.getNewResults(continuation_id);


            obj.put("latestContinuationId", chunks.getLatestContinuousQueryId());
            obj.put("currentContinuationId", continuation_id);
            if (includeUnits) {
                JSONArray array = new JSONArray();
                obj.put("units", array);
                for (String chunk : chunks.getChunk()) {
                    array.put(chunk);
                }
            }

            if (status.getExceptionMessages() != null  && !status.getExceptionMessages().isEmpty()) {
                JSONArray error = new JSONArray();
                obj.put("errorMessages", error);
                for (String e : status.getExceptionMessages()) {
                    error.put(e);
                }
                obj.put("trace", status.getExceptionTrace());

            }


        } catch (Exception e) {
            handle(obj, e);
        }
        return obj;
    }


}
