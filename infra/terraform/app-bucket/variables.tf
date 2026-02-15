variable "aws_region" {
  type        = string
  description = "AWS region for resources."
}

variable "bucket_name" {
  type        = string
  description = "S3 bucket name for static hosting."
}

variable "environment" {
  type        = string
  description = "Deployment environment name."
}
