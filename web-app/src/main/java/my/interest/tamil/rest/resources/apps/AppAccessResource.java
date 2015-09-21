package my.interest.tamil.rest.resources.apps;

import groovy.lang.GroovyShell;
import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppResource;
import my.interest.lang.tamil.generated.types.AppResourceType;
import my.interest.lang.tamil.impl.PropertyFinderForResource;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.tamil.rest.resources.exception.ResourceException;
import org.codehaus.groovy.control.CompilationFailedException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.XML;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */


public class AppAccessResource {

    private AppDescription app = null;


    public AppAccessResource(AppDescription app) {
        this.app = app;
    }

    @Path("/")
    @GET
    public Response findWelcome(@Context HttpServletRequest httpRequest) throws Exception {
        try {
            if (app == null || app.getResources() == null || app.getResources().getWelcome() == null) {
                return Response.status(404).build();
            }
            return access(app.getResources().getWelcome(), httpRequest);
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }

    @Path("/{name:.*}")
    @POST
    public Response post(@PathParam("name") String name, @Context HttpServletRequest httpRequest) throws Exception {
        return access(name, httpRequest);
    }
    @Path("/{name:.*}")
    @PUT
    public Response put(@PathParam("name") String name, @Context HttpServletRequest httpRequest) throws Exception {
        return access(name, httpRequest);
    }

    @Path("/{name:.*}")
    @GET
    public Response access(@PathParam("name") String name, @Context HttpServletRequest httpRequest) throws Exception {

        try {
            if (app == null) {
                return Response.status(404).build();
            }
            final AppResource resource = PersistenceInterface.get().findAppResource(app.getName(), name,false);
            if (resource == null) {
                return Response.status(404).build();
            }
            Map<String, Object> bindingMap = new HashMap<String, Object>();
            SimpleBindings map = new SimpleBindings(bindingMap);

            Enumeration<String> qps = httpRequest.getParameterNames();
            while (qps.hasMoreElements()) {
                String qp = qps.nextElement();
                String val = httpRequest.getParameter(qp);
                val = new String(val.getBytes(StandardCharsets.ISO_8859_1));
                map.put("R_QP_" + qp, val);

            }
             if (resource.getContent() == null) {
                 resource.setContent(new byte[0]);
             }
            byte[] content = URLDecoder.decode(new String(resource.getContent()), "UTF-8").getBytes();
            if (AppResourceType.GROOVY.equals(resource.getType())) {
                CompiledScript script = null;
                try {
                    script = PersistenceInterface.compile(app, resource, app.getName() + "." + name, new String(content), false);

                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    PrintWriter pr = new PrintWriter(bo, true);
                    map.put("R_LOG", pr);
                    map.put("R_APP_NAME", app.getName());
                    map.put("R_PATH", name);
                    map.put("R_BODY", httpRequest.getInputStream());
                    map.put("R_", httpRequest);




                    Object obj = script.eval(map);
                    if (obj == null) {
                        return Response.status(202).build();
                    } else {
                        if (Response.class.isAssignableFrom(obj.getClass())) {
                            return ((Response) obj);
                        } else {
                            String type = null;
                            boolean xml = Boolean.valueOf(httpRequest.getParameter("xml"));
                            if (pr == obj) {
                                type = "text/plain";
                                pr.flush();
                                bo.flush();
                                content = bo.toByteArray();
                            } else if (org.json.JSONObject.class.isAssignableFrom(obj.getClass())) {
                                JSONObject json = (JSONObject) obj;
                                if (xml) {
                                    content = XML.toString(json, "xml").getBytes();
                                    type = "application/xml";
                                } else {

                                    type = "application/json";
                                    content = json.toString().getBytes();
                                }
                            } else if (JSONArray.class.isAssignableFrom(obj.getClass())) {
                                JSONArray json = (JSONArray) obj;
                                if (xml) {
                                    content = XML.toString(json, "xml").getBytes();
                                    type = "application/xml";
                                } else {
                                    type = "application/json";
                                    content = json.toString().getBytes();
                                }
                            } else if (JSONString.class.isAssignableFrom(obj.getClass())) {

                                JSONString json = (JSONString) obj;
                                if (xml) {
                                    content = XML.toString(json, "xml").getBytes();
                                    type = "application/xml";
                                } else {
                                    type = "application/json";
                                    content = json.toString().getBytes();
                                }
                            } else if (byte[].class == obj.getClass()) {
                                type = "application/octect-stream";
                                content = (byte[]) obj;
                            } else if (InputStream.class.isAssignableFrom(obj.getClass())) {
                                type = "application/octect-stream";
                                content = TamilUtils.readAllFrom((InputStream) obj, false);
                            } else {
                                type = type = "text/plain";
                                content = obj.toString().getBytes();
                            }

                            if (type == null) {
                                type = "application/octect-stream";
                            } else {
                                type += "; charset=utf-8";
                            }

                            return Response.status(200).type(type).entity(content).build();
                        }
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                    return handleError(e);
                    //return Response.status(500).type("text/plain; charset=utf-8").entity(e.getMessage()).build();
                }


            } else {
                content = StringUtils.replaceFor$(new String(content), new PropertyFinderForResource(app, resource, bindingMap), false).getBytes();
                content = StringUtils.replaceForT(new String(content)).getBytes();

                String type = null;
                if (AppResourceType.CSS.equals(resource.getType())) {
                    type = "text/css";
                } else if (AppResourceType.HTML.equals(resource.getType())) {
                    type = "text/html";
                } else if (AppResourceType.JAVASCRIPT.equals(resource.getType())) {
                    type = "application/javascript";
                } else if (AppResourceType.JSON.equals(resource.getType())) {
                    type = "application/json";
                } else if (AppResourceType.TAMIL.equals(resource.getType()) || AppResourceType.TEXT.equals(resource.getType())) {
                    type = "text/plain";
                } else if (AppResourceType.XML.equals(resource.getType())) {
                    type = "application/xml";
                } else if (AppResourceType.XSL.equals(resource.getType())) {
                    type = "text/xsl";
                }
                if (type == null) {
                    type = "application/octect-stream";
                } else {
                    type += "; charset=utf-8";
                }
                // System.out.println("Returning Content:" + content);
                return Response.status(200).type(type).entity(content).build();
            }
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }


    private static Response handleError(Throwable t1) {

        try {
            int lineNum = findScriptLineNumber(t1);
            return Response.status(500).type("text/plain; charset=utf-8").entity("Line:" + lineNum + ":=>" + t1.getMessage()).header("GROOVY_SCRIPT_ERROR_LINE_NUMBER", lineNum).build();
        } catch (Exception e) {
            return Response.status(500).type("text/plain; charset=utf-8").entity(e.getMessage()).build();
        }
    }

    public static int findScriptLineNumber(Throwable t1) {


        String message = t1.getMessage();


        //    String[] content = error.split("\n", 2);
        ///  String errorMsg = content[0];
        // String errorTrace = content[1];
        int lineNum = 1;
        int column = 1;
        if (ScriptException.class.isAssignableFrom(t1.getClass())) {
            lineNum = ((ScriptException) t1).getLineNumber();
            column = ((ScriptException) t1).getColumnNumber();
            if (lineNum <= 0) {
                if (t1.getCause() != null) {
                    return findScriptLineNumber(t1.getCause());
                }
            }
        } else if (CompilationFailedException.class.isAssignableFrom(t1.getClass())) {

            //pr.println(message);
            int index = message.indexOf("@ line ");
            if (index >= 0) {
                int numberstart = index + 7;
                int indexComma = message.indexOf(",", numberstart);
                if (indexComma >= 0) {
                    String lineText = message.substring(numberstart, indexComma);
                    try {
                        //pr.println("Line:" + lineText);
                        lineNum = Integer.parseInt(lineText);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }

        } else {
            StackTraceElement[] trace = t1.getStackTrace();

            for (int i = trace.length - 1; i >= 0; i--) {
                System.out.println(trace[i].getClassName());
                if (trace[i].getClassName().startsWith("Script")) {
                    if (CompilationFailedException.class.isAssignableFrom(t1.getClass())) {
                        //To account inner scripts if any ...
                        lineNum += (trace[i].getLineNumber() - 1);
                    } else {
                        lineNum = trace[i].getLineNumber();
                    }
                    break;
                }
            }
        }

        return lineNum;


    }

}
