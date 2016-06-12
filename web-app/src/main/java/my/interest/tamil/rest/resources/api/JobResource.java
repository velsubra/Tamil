package my.interest.tamil.rest.resources.api;

import my.interest.lang.util.TimeUtils;
import my.interest.tamil.rest.resources.exception.ResourceNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobResultChunk;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.persist.object.ObjectSerializer;

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
    public String listJobs(@QueryParam("includeUnits") boolean includeUnits, @DefaultValue("20") @QueryParam("limit") int limit) throws Exception {
        return listJobs(null, includeUnits, limit);
    }


    @GET
    @Path("/list/category/{categoryName:.*}")
    @Produces("application/json; charset=UTF-8")
    public String listJobs(@PathParam("categoryName") String categoryName, @QueryParam("includeUnits") boolean includeUnits, @DefaultValue("20") @QueryParam("limit") int limit) throws Exception {
        List<Long> list = TamilFactory.getJobManager(categoryName).listJobIds(limit);
        JSONObject obj = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            obj.put("jobs", array);
            for (long id : list) {
                JSONObject job = describeJobWithCategory(id, 0, -1, categoryName, includeUnits,false);
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
                                 @DefaultValue("-1") @QueryParam("last-unit-work-count") int last_unit_work_count,
                                 @QueryParam("includeUnits") boolean includeUnits) throws Exception {
        return readJobDefaultWithCateGory(number, continuation_id, last_unit_work_count, null, includeUnits,false);
    }

    @GET
    @Path("/id/{jobid}/category/{categoryName:.*}")
    @Produces("application/json; charset=UTF-8")
    public String readJobDefaultWithCateGory(@PathParam("jobid") long number,
                                             @DefaultValue("0") @QueryParam("last-continuation-id") int continuation_id,
                                             @DefaultValue("-1") @QueryParam("last-unit-work-count") int last_unit_work_count,
                                             @PathParam("categoryName") String categoryName, @QueryParam("includeUnits") boolean includeUnits,@QueryParam("includeProps") boolean includeProps) throws Exception {

        return describeJobWithCategory(number, continuation_id, last_unit_work_count, categoryName, includeUnits,includeProps).toString();
    }


    private JSONObject describeJobWithCategory(long number,
                                               int continuation_id,  int last_unit_work_count,
                                               String categoryName, boolean includeUnits, boolean inccludeProps) throws Exception {

        JSONObject obj = new JSONObject();
        try {

            JobResultSnapShot<String> result = TamilFactory.getJobManager(categoryName).findJobResultSnapShot(number, String.class);
            if (result == null) {
                throw new ResourceNotFoundException();
            }
            List<String> properties = result.getPropertyNames();
            if (inccludeProps) {


                JSONArray props = new JSONArray();
                obj.put("properties", props);
                for (String p : properties) {
                    JSONObject item = new JSONObject();

                    String val = result.getProperty(p);
                    if (val != null) {
                        props.put(item);
                        item.put("name", p);
                        item.put("value", val);
                    }
                }
            }
            tamil.lang.api.job.JobStatus status = result.getStatus();
            obj.put("id", number);
            if (result.getTitleMessage() != null) {
                obj.put("titleMessage", result.getTitleMessage());
            }
            obj.put("titleId", result.getTitleId());
            obj.put("status", status.getStatus().toString());
            if (status.getStatusMessage() != null) {
                obj.put("statusMessage", status.getStatusMessage());
            }
            if (status.getEndTime() != null) {
                obj.put("timeTaken", TimeUtils.millisToLongDHMS(status.getEndTime().getTime() - status.getStartTime().getTime(), false));
            }
            obj.put("percent", status.getCompletionPercent());
            obj.put("unitType", result.getSerializedType().toString());
            obj.put("totalUnitCount", result.getTotalUnitsDone());
            obj.put("startedDesc", TimeUtils.millisToLongDHMS(new Date().getTime() - status.getStartTime().getTime(), false));
            obj.put("updatedDesc", TimeUtils.millisToLongDHMS(new Date().getTime() - status.getLastUpdatedTime().getTime(), false));
            JobResultChunk<String> chunks = null;
            if (last_unit_work_count > 0) {
                chunks = result.getLastResults(last_unit_work_count);
            }  else {
                chunks = result.getNewResults(continuation_id);
            }


            obj.put("latestContinuationId", chunks.getLatestContinuousQueryId());

            if (includeUnits) {
                obj.put("currentContinuationId", chunks.getCurrentContinuousQueryId());
                JSONArray array = new JSONArray();
                obj.put("units", array);
                for (String chunk : chunks.getChunk()) {
                    if (result.getSerializedType() == ObjectSerializer.SERIALIZED_TYPE.JSON) {
                        array.put(new JSONObject(chunk));
                    } else {
                        array.put(chunk);
                    }
                }
            }

            if (status.getExceptionMessages() != null && !status.getExceptionMessages().isEmpty()) {
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
