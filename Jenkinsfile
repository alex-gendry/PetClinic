pipeline {
    agent any

    environment {
        FOD_API_URL    = credentials('fod-api-url')
        FOD_TENANT    = credentials('fod-tenant')
        FOD_USERNAME    = credentials('fod-username')
        FOD_PAT    = credentials('fod-pat')
        FTFY_SSC_URL     = credentials('ftfy-ssc-url')
        FTFY_CI_TOKEN_DEC = credentials('ftfy-ci-token-dec')
        FTFY_CI_TOKEN_ENC = credentials('ftfy-ci-token-enc')
        FCLI_DEFAULT_SC_SAST_CLIENT_AUTH_TOKEN = credentials('ftfy-default-sc-sast-auth-token')
        APP_VERSION = "${JOB_NAME.replaceAll('/', ':')}"
    }

    stages {
//        stage('dependencies') {
//            steps {
//                container('fortify-ci-tools') {
//                    sh 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
//                }
//            }
//        }
        stage('fod') {
            steps {
                container('sast-client') {
                    // FCLI FOD LOGIN
                    sh 'fcli fod session login --url $FOD_API_URL --tenant $FOD_TENANT -u $FOD_USERNAME -p $FOD_PAT'

                    // Create Application Release
                    sh "fcli fod release create '${CI_PROJECT_TITLE} [AG]':$GIT_BRANCH --status Production --skip-if-exists --store release"

                    // Package sources
                    sh 'scancentral package -bt mvn  -o package.zip'
                    sh 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
                    sh 'zip -r package.zip .debricked-maven-dependencies.tgf'

                    // Upload
                    sh "fcli fod sast setup '${CI_PROJECT_TITLE} [AG]':$GIT_BRANCH --assessment Static --frequency Subscription --audit-preference Automated --technology-stack JAVA/J2EE/Kotlin --language-level 1.8 --oss --store sast_config"
                    sh "fcli fod sast start '${CI_PROJECT_TITLE} [AG]':$GIT_BRANCH --purchase-entitlement  --in-progress Queue -f package.zip --store '?'"
                    sh 'sleep 10'
                    sh "fcli fod sast wait-for '?'  -i 30s"
                }
            }
        }
    }
}
