pipeline {
    agent any

    environment {
        FTFY_SSC_URL     = credentials('ftfy-ssc-url')
        FTFY_CI_TOKEN_DEC = credentials('ftfy-ci-token-dec')
        FTFY_CI_TOKEN_ENC = credentials('ftfy-ci-token-enc')
        FCLI_DEFAULT_SC_SAST_CLIENT_AUTH_TOKEN = credentials('ftfy-default-sc-sast-auth-token')
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
                    sh 'fcli ssc session login --url $FTFY_SSC_URL -t $FTFY_CI_TOKEN_DEC'
                    sh 'fcli sc-sast session login --ssc-url $FTFY_SSC_URL -t $FTFY_CI_TOKEN_DEC -c $FCLI_DEFAULT_SC_SAST_CLIENT_AUTH_TOKEN'

                    // Create appversion
                    sh 'fcli ssc appversion create \$($JOB_NAME | sed -e "s/\\//\\:/g") --auto-required-attrs --skip-if-exists'

                    // Package sources
                    sh 'scancentral package -bt mvn  -o package.zip'
                    sh 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
                    sh 'zip -r package.zip .debricked-maven-dependencies.tgf'

                    // Run Scan
                    sh "fcli sc-sast scan start -p package.zip --sensor-version 22.2 --appversion \$($JOB_NAME | sed -e \"s/\\//\\:/g\") --store '?'"

                    // Wait for SAST scan to complete
                    sh "fcli sc-sast scan wait-for '?' -i 30s"

                    // Clean up tokens, session variables, ...
                    sh "fcli sc-sast session logout --no-revoke-to"
                }
            }
        }
    }
}

