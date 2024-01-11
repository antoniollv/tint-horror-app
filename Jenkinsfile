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
                println(containerCallTemplates(imageName: "ACR_NAME",
                                                credentialSecret: "SECRET_NAME",
                                                nodeSelectorValue: "NODE_SELECTOR_VALUE",
                                                nodeTaintKey: "NODE_TAINT_KEY",
                                                listContainers: ["jdk8", "jdk11", "jdk17"]))
            }
        }
    }
}
