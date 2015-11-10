package my.interest.lang.tamil.impl;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ApplicationClassloader extends URLClassLoader {

    // private URLClassLoader resourceProvider = null;
    Set<String> poms = new HashSet<String>();

    PrintWriter pr = new PrintWriter(System.out, true);
    PropertyTypeResolver resolver = new PropertyTypeResolver(null, "project.");


    public ApplicationClassloader(ClassLoader cl) {
        super(new URL[]{}, cl);

    }

    public ApplicationClassloader(URL[] urls, ClassLoader cl) {
        this( cl);
        addUrls(urls);

    }

    public void addPom(String pom) throws Exception {
        addPom(pom, true);
    }

    public void addPom(String pom, boolean recursive) throws Exception {
        addPom(null, pom, recursive);
    }

    public void addPom(String root, String pom) throws Exception {
        addPom(null, root, pom, true);
    }

    public void addPom(String root, String pom, boolean recursive) throws Exception {
        addPom(null, root, pom, recursive);
    }

    public void addPom(Map<String, String> map, String root, String pom, boolean recursive) throws Exception {
        if (poms.contains(pom)) {
            return;
        }
        poms.add(pom);
        if (root == null) {
            root = "http://repo1.maven.org/maven2";
        }
        if (!root.endsWith("/")) {
            root += "/";
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        MavenXpp3Reader mavenreader = new MavenXpp3Reader();


        pr.println("opening-> " + pom);
        Model model = mavenreader.read(new URL(pom).openStream(), false);
        Properties props = model.getProperties();
        for (Map.Entry<String, String> e : map.entrySet()) {
            props.setProperty(e.getKey(), e.getValue());
        }
        resolver.addProperties(props);

        Model parentModel = null;
        if (model.getParent() != null) {
            resolver.resolveAs(model.getParent().getGroupId(), "parent.groupId");
            resolver.resolveAs(model.getParent().getArtifactId(), "parent.artifactId");
            resolver.resolveAs(model.getParent().getVersion(), "parent.version");
            try {
                parentModel = mavenreader.read(new URL(root + resolver.findProperty("parent.groupId").replace('.', '/') + "/" + resolver.findProperty("parent.artifactId") + "/" + resolver.findProperty("parent.version") + "/" + resolver.findProperty("parent.artifactId") + "-" + resolver.findProperty("parent.version") + ".pom").openStream(), false);
                resolver.addProperties(parentModel.getProperties());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        String groupid = resolver.resolveAs(model.getGroupId() == null ? resolver.findProperty("parent.groupId") : model.getGroupId(), "project.groupId");
        String artifactid = resolver.resolveAs(model.getArtifactId(), "project.artifactId");
        String version = resolver.resolveAs(model.getVersion() == null ? resolver.findProperty("parent.version") : model.getVersion(), "project.version");

        if (groupid == null || artifactid == null || version == null) {
            throw new RuntimeException("One of project.groupId  project.artifactId project.version is null");
        }


        addURL(new URL(root + groupid.replace('.', '/') + "/" + artifactid + "/" + version + "/" + artifactid + "-" + version + "." + resolver.resolveAs(model.getPackaging(), null)));
        List<Dependency> deps = model.getDependencies();

        for (Dependency dep : deps) {
            if ("test".equals(dep.getScope())) {
                continue;
            }
            String depgroup = resolver.resolveAs(dep.getGroupId(), null);
            String departi = resolver.resolveAs(dep.getArtifactId(), null);
            String depversion = dep.getVersion();
            if (depversion == null) {
                if (parentModel != null && parentModel.getDependencyManagement() != null) {

                    for (Dependency parentDep : parentModel.getDependencyManagement().getDependencies()) {
                        if (parentDep.getGroupId().equals(depgroup) && parentDep.getArtifactId().equals(departi)) {
                            depversion = resolver.resolveAs(parentDep.getVersion(), null);
                            break;
                        }
                    }
                }
            }  else {
                depversion = resolver.resolveAs( dep.getVersion(), null);
            }
            String artifactHome = root + depgroup.replace('.', '/') + "/" + departi + "/" + depversion + "/" + departi + "-" + depversion;

            addURL(new URL(artifactHome + "." + resolver.resolveAs(dep.getType(), null)));
            if (recursive) {
                String subPom = null;
                try {
                    subPom = artifactHome + ".pom";
                    addPom(map, root, subPom, recursive);
                } catch (Exception e) {
                    System.out.println("Unable to add pom:" + subPom);
                    e.printStackTrace();
                }
            }
        }

    }


    public void addUrl(String url) throws Exception {
        this.addURL(new URL(url));
    }

    public void addUrl(URL url) {
        if (url != null) {
            this.addURL(url);
        }

    }

    public void addUrls(URL[] urls) {
        if (urls != null) {
            for (URL u : urls) {
                this.addURL(u);
            }
        }


    }

    public List<String> getAllUrls() {
        List <String>   list = new ArrayList <String>();
        for (URL u : getUrls()) {
            list.add(u.toString());
        }
        return list;
    }


    public URL[] getUrls() {
        return this.getURLs();

    }

//    @Override
//    public InputStream getResourceAsStream(String cls) {
//        pr.println(" Trying to get resource .." + cls);
//        InputStream in = getParent().getResourceAsStream(cls);
//        if (in == null) {
//            return resourceProvider.getResourceAsStream(cls);
//        } else {
//            return in;
//        }
//    }

    @Override
    public Class findClass(String cls) throws ClassNotFoundException {

        InputStream in = getResourceAsStream(cls.replace('.', '/') + ".class");
        if (in == null) {
            throw new ClassNotFoundException(cls);
        }
        byte[] data = readData(in, false);
        return super.defineClass(cls, data, 0, data.length);
    }

    private byte[] readData(InputStream in, boolean chunked) {
        if (in == null)
            throw new RuntimeException("Stream can't be null");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[1024];
            int len;
            len = in.read(buf);
            //System.out.println("READ: " + len + "bytes for the first time");
            while (len > 0) {
                bos.write(buf, 0, len);
                if (chunked) {
                    byte[] check = new String(bos.toByteArray()).trim().getBytes();
                    if (check.length >= 1) {
                        if (check[check.length - 1] == '0') {
                            if (check.length == 1)
                                break;
                            if ((check[check.length - 2]) == '\n') {
                                break;
                            }
                        }
                    }
                }
                len = in.read(buf);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to read:", e);
        }
        return bos.toByteArray();
    }
}



