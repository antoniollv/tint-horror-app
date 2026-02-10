{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "TerraformStateAccess",
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject",
        "s3:DeleteObject",
        "s3:ListBucket"
      ],
      "Resource": [
        "arn:aws:s3:::${TF_STATE_BUCKET}",
        "arn:aws:s3:::${TF_STATE_BUCKET}/*"
      ]
    },
    {
      "Sid": "SSMPrerequisitesRead",
      "Effect": "Allow",
      "Action": [
        "ssm:GetParameter",
        "ssm:GetParameters",
        "ssm:GetParametersByPath"
      ],
      "Resource": "arn:aws:ssm:${AWS_REGION}:*:parameter/${GITHUB_REPO}/${ENVIRONMENT_INPUT}/prerequisites/*"
    },
    {
      "Sid": "SSMAppBucketWrite",
      "Effect": "Allow",
      "Action": [
        "ssm:PutParameter",
        "ssm:GetParameter",
        "ssm:GetParameters"
      ],
      "Resource": "arn:aws:ssm:${AWS_REGION}:*:parameter/${GITHUB_REPO}/${ENVIRONMENT_INPUT}/app/*"
    },
    {
      "Sid": "AppBucketCreate",
      "Effect": "Allow",
      "Action": [
        "s3:CreateBucket"
      ],
      "Resource": "*"
    },
    {
      "Sid": "AppBucketManage",
      "Effect": "Allow",
      "Action": [
        "s3:DeleteBucket",
        "s3:GetBucketLocation",
        "s3:GetBucketPolicy",
        "s3:GetBucketTagging",
        "s3:GetBucketWebsite",
        "s3:ListBucket",
        "s3:PutEncryptionConfiguration",
        "s3:PutBucketOwnershipControls",
        "s3:GetBucketOwnershipControls",
        "s3:PutBucketPolicy",
        "s3:PutBucketPublicAccessBlock",
        "s3:PutBucketVersioning",
        "s3:PutBucketWebsite",
        "s3:PutBucketTagging",
        "s3:PutBucketAcl",
        "s3:GetBucketAcl",        
        "s3:GetBucketPublicAccessBlock"
      ],
      "Resource": "arn:aws:s3:::${APP_BUCKET_NAME}"
    },
    {
      "Sid": "AppBucketObjects",
      "Effect": "Allow",
      "Action": [
        "s3:DeleteObject",
        "s3:GetObject",
        "s3:PutObject"
      ],
      "Resource": "arn:aws:s3:::${APP_BUCKET_NAME}/*"
    }
  ]
}
