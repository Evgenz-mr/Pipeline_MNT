import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;
import hudson.plugins.git.GitSCM;
import hudson.plugins.ws_cleanup.*
import hudson.tasks.Shell;

def BRANCH_NAME = "*/amatiev"
NEW_JOB = Jenkins.instance.createProject(FreeStyleProject, 'NAME_JOB')


def gitScm = new GitSCM("https://github.com/MNT-Lab/build-principals.git")
gitScm.branches = [new hudson.plugins.git.BranchSpec(BRANCH_NAME)]
NEW_JOB.scm = gitScm

NEW_JOB.buildWrappersList.add(new PreBuildCleanup(null, true, "", ""))
NEW_JOB.buildersList.add(new Shell('echo hello'))
