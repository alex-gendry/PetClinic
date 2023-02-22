pipeline {
    agent any

    stages {
        stage('dependencies') {
            steps {
                container('fortify-ci-tools') {
//                     sh 'mvn package'
                    sh 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
                }
            }
        }
        stage('fod') {
            steps {
                fodStaticAssessment applicationName: 'PetClinic [AG]',
                                    releaseName: "$GIT_BRANCH" ,
                                    applicationType: '1',
                                    assessmentType: '274',
                                    auditPreference: '2',
                                    businessCriticality: '1',
                                    entitlementId: '11948',
                                    frequencyId: '2',
                                    inProgressBuildResultType: 'FailBuild',
                                    inProgressScanActionType: 'Queue',
                                    languageLevel: '12',
                                    openSourceScan: 'true',
                                    owner: 59565,
                                    remediationScanPreferenceType: 'RemediationScanIfAvailable',
                                    sdlcStatus: '1',
                                    srcLocation: '.',
                                    technologyStack: '7'
            }
        }
    }
}
