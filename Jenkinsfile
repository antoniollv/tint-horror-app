// @Library('tron-library-comun') libcommon
// @Library('tron-config-core') libconfig
// @Library('global-pipeline-library-v9') libglobal
// @Library('security-library') libsec

//def GIT_COMMIT_EMAIL

//def DEVOPS_PLATFORM_ORGANIZATION = 'org-tron'

// Artifacts for deployment proyect
//def libModules = ".deploy/libModules.yml"
// CMN dependencies
//def dependencies = ["NEWTRON_BE_VERSION"] as String[]

pipeline {
    // agent {
    //     kubernetes {
    //         yaml getPodTemplate(DEVOPS_PLATFORM_ORGANIZATION, getContainersTron("api-newtron"))
    //     }
    // }

    // Timeout execution
    // options {
    //     timeout(time: 60, unit: 'MINUTES')
    //     timestamps()
    // }

    // parameters {
    //     choice(name: "ZEUS_ENVIRONMENT", choices: ["IC","INT","PRE","RLS-1","RLS-2"], description: 'This parameter is only for Zeus.')
    // }

    environment {
        // Jenkins credential id to authenticate to artifact repository
        //GIT_AUTH = credentials('app-jenkins-tron-bitbucket')
        GIT_AUTH =
        REPO_NAME = getRepoName(env.JOB_NAME)
        POM_VERSION = getPomVersion()
        ARTIFACT_ID = getArtifactId()
        GROUP_ID = getGroupId()
        ENVIRONMENT = getEnvironmentbyBranch(env.BRANCH_NAME)
        ENVIRONMENT_CICD = getEnvironmentbyBranch(env.BRANCH_NAME, isCauseBuildUser())
        BLOCK_ENVIRONMENT = isEnvironmentUtilitiesBlockByEnvironment(ENVIRONMENT_CICD)
        SOAPUI_FOLDER = '2-INTEGRATION_TEST/soapui'

        INITIALIZE_ENV_VARIABLES_TRON = initializeVarsTron()
    }

    stages {
        stage('Prepare Environment') {
            steps {
                initStageKPI()
                // Print global vars and kpis of jenkinsfile environment
                showEnvironment()
            }
        }

        stage('Set Dependencies') {
            when {
                anyOf {
                    branch 'development'
                }
            }
            steps {
                initStageKPI()
                container('maven') {
                    script {
                        if (isDevelopmentBranchEvent(ENVIRONMENT_CICD, env.BRANCH_NAME)) {
                            setDependenciesCore(dependencies, ENVIRONMENT)
                        } else {
                            echo "No estamos en un Branch Event en la rama 'development' y no se debe ejecutar el stage 'Set Dependencies'"
                        }
                    }
                }
            }
        }

        // Promoción de codigo entre SNAPSHOT, RC, RELEASE, HOTFIX
        stage('Prepare Promotion') {
            when {
                anyOf {
                    branch 'release/*'
                    branch 'master'
                    branch 'support/*'
                }
            }
            environment {
                PROMOTION_TYPE = getPromotionTypebyBranch(BRANCH_NAME)
            }
            steps {
                initStageKPI()
                container('maven') {
                    promotion PROMOTION_TYPE
                    // promote dependencies
                    promoteDependencies(PROMOTION_TYPE, dependencies)
                }
            }
        }

        //Add stage SonarQube
        stage('Quality analysis') {
            when {
                anyOf {
                    branch 'dprov/*'; branch 'development'; branch 'master'
                    allOf {
                        branch 'support-entrega/*'
                        expression {
                            return gitOlderBranches(BRANCH_NAME, SONARQUBE_NUMBER_OLDER_RELEASES).contains(BRANCH_NAME)
                        }
                    }
                }
            }
            environment {
                IS_JAVA = true
            }
            steps {
                initStageKPI()
                runSonarQube(REPO_NAME, BRANCH_NAME, IS_JAVA)
            }
        }

        stage('Security pre-build') {
            steps {
                secPreBuild()
            }
        }

        // Compile with Maven
        stage('Build') {
            when {
                not {
                    anyOf {
                        branch 'PR*'
                        branch 'dprov/*'
                        branch 'integration'
                        environment name: 'BLOCK_ENVIRONMENT', value: 'true'
                    }
                }
            }
            steps {
                initStageKPI()
                container('maven') {
                    sh 'mvn -version'
                    buildWithProfiles(env.LOCAL_PROFILE_MAVEN)
                }
            }
        }

        stage('Security post-build') {
            steps {
                secPostBuild()
            }
        }

        // Publish to artifact repository
        stage('Publish') {
            when {
                anyOf {
                    branch 'release/*'
                    branch 'master'
                    branch 'support/*'
                    branch 'support-entrega/*'
                    allOf {
                        environment name: 'ENVIRONMENT_CICD', value: 'none'
                        branch 'development'
                    }
                }
            }
            steps {
                initStageKPI()
                container('maven') {
                    publish AA_CREDENTIAL_ID, "${env.LOCAL_PROFILE_MAVEN}"
                }
            }
        }

        stage('RC Promotion') {
            when {
                branch 'release/*'
            }
            environment {
                POM_VERSION = getPomVersion()
            }
            steps {
                initStageKPI()
                // push promotion
                gitFetch()
                gitCheckout env.BRANCH_NAME
                // commit RC promotion in release branch
                gitCommit "promotion to RC completed (${env.POM_VERSION})"
                // push all changes
                gitPush()
                // create tag
                gitTag(env.POM_VERSION, "Nuevo tag para la versión rc ${env.POM_VERSION})")
                // push all tags
                gitPushTags()
            }
        }

        stage('Next Snapshot Promotion') {
            when {
                allOf {
                    branch 'release/*'
                }
            }
            environment {
                DEVELOPMENT_BRANCH = 'development'
                PROMOTION_TYPE = 'SNAPSHOT'
            }
            steps {
                initStageKPI()
                // git checkout development
                gitCheckout DEVELOPMENT_BRANCH
                // git merge env.BRANCH_NAME
                gitMerge env.BRANCH_NAME
                // mvn promotion to snapshot
                container('maven') {
                    promotion PROMOTION_TYPE
                    // promote dependencies
                    promoteDependencies(PROMOTION_TYPE, dependencies)
                }
                // commit next SNAPSHOT in development branch
                gitCommit "promotion to next SNAPSHOT completed (${getPomVersion()})"
                // push all changes
                gitPush()
            }
        }

        stage('Release Promotion') {
            when {
                branch 'master'
            }
            environment {
                POM_VERSION = getPomVersion()
            }
            steps {
                // print release KPIs
                initStageKPI()
                releaseKPI()
                // git operations
                gitFetch()
                gitCheckout env.BRANCH_NAME
                // commit RC promotion in release branch
                gitCommit "promotion to RELEASE completed (${env.POM_VERSION})"
                // push all changes
                gitPush()
                // create tag
                gitTag(env.POM_VERSION, "Nuevo tag para la versión ${env.POM_VERSION}")
                // push all tags
                gitPushTags()
            }
        }

        stage('Hotfix Promotion') {
            when {
                branch 'support/*'
            }
            environment {
                POM_VERSION = getPomVersion()
            }
            steps {
                initStageKPI()
                // push promotion
                gitFetch()
                gitCheckout env.BRANCH_NAME
                // commit RC promotion in release branch
                gitCommit "promotion to next HOTFIX completed (${env.POM_VERSION})"
                // push all changes
                gitPush()
                // create tag
                gitTag(env.POM_VERSION, "Nuevo tag para la versión HOTFIX ${env.POM_VERSION}")
                // push all tags
                gitPushTags()
            }
        }

        stage('Next Hotfix Promotion') {
            when {
                branch 'support/*'
            }
            environment {
                SUPPORT_ENTREGA_BRANCH = getSupportEntregaBranch(BRANCH_NAME)
                PROMOTION_TYPE = 'HOTFIX-SNAPSHOT'
            }
            steps {
                initStageKPI()
                // git checkout development
                gitCheckout SUPPORT_ENTREGA_BRANCH
                // git merge env.BRANCH_NAME
                gitMerge env.BRANCH_NAME
                // mvn promotion to snapshot
                container('maven') {
                    // promotion env.BRANCH_NAME
                    promotion PROMOTION_TYPE
                }
                // commit next SNAPSHOT in development branch
                gitCommit "promotion to next HOTFIX SNAPSHOT completed (${getPomVersion()})"
                // push all changes
                gitPush()
                gitCheckoutWithoutTrack(env.BRANCH_NAME)
            }
        }

        stage('Security pre-deploy') {
            when {
                not {
                    environment name: 'ENVIRONMENT_CICD', value: 'none'
                }
            }
            steps {
                secPreDeploy()
            }
        }

        // Deploy JBOSS
        stage('Deploy Jboss') {
            when {
                not {
                    environment name: 'ENVIRONMENT_CICD', value: 'none'
                }
            }
            environment {
                POM_VERSION = getPomVersion()
                BRANCH_NAME = "${(env.ENVIRONMENT == 'RLS-2') ? env.ENVIRONMENT : env.BRANCH_NAME}"
            }
            steps {
                initStageKPI()
                createTempFolder()
                deployJBoss(ENVIRONMENT, libModules)
            }
            post {
                always {
                    // Borrado del directorio temporal en el server-remoto
                    deleteTempDir(ENVIRONMENT, libModules)
                }
            }
        }

        stage('Security post-deploy') {
            when {
                not {
                    environment name: 'ENVIRONMENT_CICD', value: 'none'
                }
            }
            steps {
                secPostDeploy()
            }
        }

        stage('Integration tests') {
            when {
                not {
                    environment name: 'ENVIRONMENT_CICD', value: 'none'
                }
            }
            steps {
                initStageKPI()
                container('maven') {
                    dir("${SOAPUI_FOLDER}") {
                        executeTestSuiteSoapUI(ENVIRONMENT)
                    }
                }
            }
            post {
                always {
                    junit testResults: "${SOAPUI_FOLDER}/target/soapui/*.xml",
                            allowEmptyResults: true, skipPublishingChecks: true
                }
            }
        }
        // Pasos especÃ­ficos del pipeline para WEBLOGIC
        stage('Build WL') {
            when {
                anyOf {
                    allOf {
                        environment name: 'ENVIRONMENT_CICD', value: 'none'
                        branch 'development'
                    }
                    branch 'master'
                    branch 'support/*'
                }
            }
            steps {
                initStageKPI()
                container('maven') {
                    sh 'mvn -version'
                    setVersionWebLogic()
                    buildWithProfiles("${env.LOCAL_PROFILE_MAVEN},WEBLOGIC")
                }
            }
        }

        // Publish to artifact repository
        stage('Publish WL') {
            when {
                anyOf {
                    allOf {
                        environment name: 'ENVIRONMENT_CICD', value: 'none'
                        branch 'development'
                    }
                    branch 'master'
                    branch 'support/*'
                }
            }
            environment {
                POM_VERSION = getPomVersion()
                ARTIFACT_FEED = "${POM_VERSION.contains('SNAPSHOT') ? SNAPSHOT_REGISTRY : RELEASE_REGISTRY}"
            }
            steps {
                initStageKPI()
                container('maven') {
                    publishArtifact("1-DEVELOPMENT/jeeApp/${ARTIFACT_ID}-ear/target", "${ARTIFACT_ID}-ear-${POM_VERSION}.ear", GROUP_ID, "${ARTIFACT_ID}-ear", POM_VERSION, 'ear', ARTIFACT_FEED)
                    publishArtifact('1-DEVELOPMENT/zeroConfig/target', "${ARTIFACT_ID}.zeroConfig-${POM_VERSION}.war", GROUP_ID, "${ARTIFACT_ID}.zeroConfig", POM_VERSION, 'war', ARTIFACT_FEED)
                }
            }
        }
    }

    post {
        always {
            echo '--always--'
            showStageStatus()
            logstashSend failBuild: false, maxLines: 150000
            registerDeploy(ENVIRONMENT)
        }
        success {
            echo '--success--'
            script {
                if (ENVIRONMENT != 'none') {
                    saveVersion(ENVIRONMENT, getPomVersion())
                }
            }
        }
        failure {
            echo '--failure--'
                script {
                    GIT_COMMIT_EMAIL = sh(
                        script: 'git --no-pager show -s --format=\'%ae\'',
                        returnStdout: true
                    ).trim()
                }
            echo "DESTINATARIO ${GIT_COMMIT_EMAIL}"
            sendFailureEmail GIT_COMMIT_EMAIL
        }
    }
}
