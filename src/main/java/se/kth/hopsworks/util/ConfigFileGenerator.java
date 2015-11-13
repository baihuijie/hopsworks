package se.kth.hopsworks.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigFileGenerator {

  public static final String TEMPLATE_ROOT = "io" + File.separator + "hops";
  public static final String ZEPPELIN_CONFIG_TEMPLATE
      = TEMPLATE_ROOT + File.separator + "zeppelin" + File.separator + "zeppelin_site_template.xml";
  public static final String ZEPPELIN_ENV_TEMPLATE
      = TEMPLATE_ROOT + File.separator + "zeppelin" + File.separator + "zeppelin_env_template.sh";
  public static final String INTERPRETER_TEMPLATE
      = TEMPLATE_ROOT + File.separator + "zeppelin" + File.separator + "interpreter_template.json";
  /**
   *
   * StringBuilder metadata_rb = ConfigFileGenerator.instantiateFromTemplate( Settings.CB_TEMPLATE_METADATA, "name",
   * repoName, "user", experiment.getUser(), "email", email, "depends", "", "resolve_ips", "", "build_command",
   * experiment.getMavenCommand(), "url_binary", experiment.getUrlBinary(), "url_gitclone", experiment.getUrlGitClone(),
   * "build_command", experiment.getMavenCommand(), "ip_params", "", "more_recipes", recipeDescriptions.toString() );
   *
   *
   *
   * @param filePath
   * @param pairs
   * @return
   * @throws IOException
   */
  public static StringBuilder instantiateFromTemplate(String filePath, String... pairs) throws IOException {
    if (pairs.length % 2 != 0) {
      throw new IOException("Odd number of parameters when instantiating a template. Are you missing a parameter?");
    }
    StringBuilder sb = new StringBuilder();
    String script = IoUtils.readContentFromClasspath(filePath);
    if (pairs.length > 0) {
      for (int i = 0; i < pairs.length; i += 2) {
        String key = pairs[i];
        String val = pairs[i + 1];
        script = script.replaceAll("%%" + key + "%%", val);
      }
    }
    return sb.append(script);
  }
  
  public static boolean mkdirs(String path) {
    File cbDir = new File(path);
    return cbDir.mkdirs();
  }

  public static boolean deleteRecursive(File path) throws FileNotFoundException {
    if (!path.exists()) {
      throw new FileNotFoundException(path.getAbsolutePath());
    }
    boolean ret = true;
    if (path.isDirectory()) {
      for (File f : path.listFiles()) {
        ret = ret && deleteRecursive(f);
      }
    }
    return ret && path.delete();
  }
  
  public static boolean createConfigFile(File path, String contents) throws IOException {
    // write contents to file as text, not binary data
    if (!path.exists()) {
      if (!path.createNewFile()) {
        throw new IOException("Problem creating file: " + path);
      }
    }
    PrintWriter out = new PrintWriter(path);
    out.println(contents);
    out.flush();
    out.close();
    return true;
  }
  
}