@Library(['pipeline-maven', 'utils']) _

CalsystemPipeline {
    rancherStackName = 'ccred-calcard'
    profile = 'artifactory'
    patternRule = /develop|master|.*release\/.*/
    EMPRESA = "CALCRED"
    MAVEN_PARAMS = "-Dmaven.test.skip=true"
}