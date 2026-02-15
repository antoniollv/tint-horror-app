{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "TerraformStateAndImages",
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
      "Sid": "IAMSelfManagement",
      "Effect": "Allow",
      "Action": [
        "iam:GetRole",
        "iam:UpdateAssumeRolePolicy",
        "iam:GetPolicy",
        "iam:CreatePolicyVersion",
        "iam:DeletePolicyVersion",
        "iam:ListPolicyVersions",
        "iam:AttachRolePolicy"
      ],
      "Resource": [
        "arn:aws:iam::*:role/${IAM_ROLE_NAME}",
        "arn:aws:iam::*:policy/${IAM_POLICY_NAME}"
      ]
    },
    {
      "Sid": "SSMManagement",
      "Effect": "Allow",
      "Action": [
        "ssm:PutParameter",
        "ssm:GetParameter",
        "ssm:GetParameters",
        "ssm:GetParametersByPath"
      ],
      "Resource": "arn:aws:ssm:*:*:parameter/${GITHUB_REPO}/${ENVIRONMENT_INPUT}/*"
    },
    {
      "Sid": "AppBucketFullManagement",
      "Effect": "Allow",
      "Action": [
        "s3:CreateBucket",
        "s3:DeleteBucket",
        "s3:Get*",
        "s3:ListBucket",
        "s3:PutEncryptionConfiguration",
        "s3:PutBucketOwnershipControls",
        "s3:PutBucketPolicy",
        "s3:PutBucketPublicAccessBlock",
        "s3:PutBucketVersioning",
        "s3:PutBucketWebsite",
        "s3:PutBucketTagging",
        "s3:PutBucketAcl",
        "s3:PutObject",
        "s3:GetObject",
        "s3:DeleteObject"
      ],
      "Resource": [
        "arn:aws:s3:::${APP_BUCKET_NAME}",
        "arn:aws:s3:::${APP_BUCKET_NAME}/*"
      ]
    }
  ]
}
