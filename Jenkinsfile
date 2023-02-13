pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                container('fortify-ci-tools') {
                    echo 'Building..'
                }
            }
        }
    }
}
