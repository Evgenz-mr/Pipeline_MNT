job("MNTLAB-emrykhin-main-build-job") {
    label('master')//in case of slave using
	description()
	keepDependencies(false)
  
  configure {
    project->
        project / 'properties' << 'hudson.model.ParametersDefinitionProperty' {
        parameterDefinitions {
            'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
                name 'BRANCH_NAME'
                quoteValue 'false'
                saveJSONParameterToFile 'false'
                visibleItemCount '1'
                type 'PT_SINGLE_SELECT'
                value "emrykhin, master"
                multiSelectDelimiter ','
                projectName "MNTLAB-emrykhin-main-build-job"
            }
          'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
                name 'BUILDS_TRIGGER'
                quoteValue 'false'
                saveJSONParameterToFile 'false'
                visibleItemCount '5'
                type 'PT_CHECKBOX'
                groovyScript """['MNTLAB-emrykhin-child1-build-job', 'MNTLAB-emrykhin-child2-build-job', 'MNTLAB-emrykhin-child3-build-job', 'MNTLAB-emrykhin-child4-build-job']"""
                multiSelectDelimiter ','
                projectName "MNTLAB-emrykhin-main-build-job"
            }
        }
    }
}
  
  
	disabled(false)
	concurrentBuild(false)
	steps {
        downstreamParameterized {
            trigger('MNTLAB-emrykhin-child1-build-job, MNTLAB-emrykhin-child2-build-job, MNTLAB-emrykhin-child3-build-job, MNTLAB-emrykhin-child4-build-job') {
                block {
                    buildStepFailure('never')
                    failure('never')
                    unstable('never')
                }
                parameters {
                    predefinedProp('GIT_BRANCH', '$BRANCH_NAME')
                             }
            }
           
        }
    }
  
  publishers {
        archiveArtifacts('*tar.gz')
    }
  
	wrappers {
		preBuildCleanup {
			deleteDirectories(false)
			cleanupParameter()
		}
	}
}





for (i in 1..4){
job("MNTLAB-emrykhin-child${i}-build-job") {
	description()
	keepDependencies(false)
     label('master')
  
  
  
  configure {
    project->
        project / 'properties' << 'hudson.model.ParametersDefinitionProperty' {
        parameterDefinitions {
            'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
                name 'GIT_BRANCH'
                quoteValue 'false'
                saveJSONParameterToFile 'false'
                visibleItemCount '1'
                type 'PT_SINGLE_SELECT'
                groovyScript """import jenkins.model.*
def gitURL = "https://github.com/Evgenz-mr/MNT-Lab.git"
def command = "git ls-remote -h \$gitURL"
def proc = command.execute()
proc.waitFor()
def branches = proc.in.text.readLines().collect {
    it.replaceAll(/[a-z0-9]*\\trefs\\/heads\\//, '')
}
return branches"""
                multiSelectDelimiter ','
                defaultValue'emrykhin'
              projectName "MNTLAB-emrykhin-child${i}-build-job"
            }
         
        }
    }
}
  
    
	scm {
		git {
			remote {
				github("Evgenz-mr/MNT-Lab", "https")
			}
			branch("\$GIT_BRANCH")
		}
	}
	disabled(false)
	concurrentBuild(false)
	steps {
		shell("""touch ./output.txt
chmod ugo+x my_echo.sh
./my_echo.sh
tar -cvzf \${GIT_BRANCH}_\${BUILD_TAG}_dsl_script.tar.gz ./output.txt ./jobs.groovy
cp \${GIT_BRANCH}_\${BUILD_TAG}_dsl_script.tar.gz ../MNTLAB-emrykhin-main-build-job""")
	}
	 publishers {
    archiveArtifacts('*.tar.gz')
}
	wrappers {
		preBuildCleanup {
			deleteDirectories(false)
			cleanupParameter()
		}
	}
}
}
