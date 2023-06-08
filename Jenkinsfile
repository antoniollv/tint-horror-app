@Library('global') libglobal
pipeline {
    agent any

    stages {
        stage('Load-Environment-Variables') {
            steps {
                echo env.STAGE_NAME
                showEnvironment()
            }
        }
    }
}
