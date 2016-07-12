package se.kth.hopsworks.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.bbc.jobs.jobhistory.Execution;
import se.kth.bbc.jobs.model.description.JobDescription;
import se.kth.bbc.jobs.spark.SparkJobConfiguration;
import se.kth.bbc.project.fb.Inode;
import se.kth.bbc.project.fb.InodeFacade;
import se.kth.hopsworks.user.model.Users;

/**
 * Takes care of booting the execution of a job.
 * <p/>
 * @author stig
 */
@Stateless
public class ExecutionController {

  //Controllers
  @EJB
  private SparkController sparkController;
  @EJB
  private AdamController adamController;
  @EJB
  private FlinkController flinkController;
  @EJB
  private InodeFacade inodes;


  public Execution start(JobDescription job, Users user) throws IOException {
    Execution exec = null;

    switch (job.getJobType()) {
      case ADAM:
        exec = adamController.startJob(job, user);
        break;
      case FLINK:
        return flinkController.startJob(job, user);
      case SPARK:
        exec = sparkController.startJob(job, user);
        if (exec == null) {
          throw new IllegalArgumentException("Problem getting execution object for: " + job.
              getJobType());
        }
        int execId = exec.getId();
        SparkJobConfiguration config = (SparkJobConfiguration) job.getJobConfig();
        //Check if application arguments are null
        if(config.getArgs() != null){
            String patternString = "hdfs://(.*)\\s";
            Pattern p = Pattern.compile(patternString);
            Matcher m = p.matcher(config.getArgs());
            for (int i = 0; i < m.groupCount(); i++) { // for each filename, resolve Inode from HDFS filename
    //          String filename = m.group(i);
    //           Inode inode = inodes.getInodeAtPath("hdfs://" + filename);
              // insert into inputfiles_executions (inode, execId).
            }
        }
        break;
      default:
        throw new IllegalArgumentException(
            "Unsupported job type: " + job.
            getJobType());
    }

    return exec;
  }

  public void stop(JobDescription job, Users user, String appid) throws
      IOException {
    switch (job.getJobType()) {
      case ADAM:
      //adamController.stopJob(job, user);
      case SPARK:
      //sparkController.stopJob(job, user, appid);
      default:
        throw new IllegalArgumentException("Unsupported job type: " + job.
            getJobType());
    }
  }
}
