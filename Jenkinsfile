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
        stage('test') {
            steps {
                println(containerCallTemplates(acrName: 'ACR_NAME',
                                                credentialSecret: 'SECRET_NAME',
                                                nodeSelectorValue: 'NODE_SELECTOR_VALUE',
                                                nodeTaintKey: 'NODE_TAINT_KEY',
                                                listContainers: ['java/jdk8', 'java/jdk11', 'java/jdk17', 'podman']))
            }
        }
    }
}
