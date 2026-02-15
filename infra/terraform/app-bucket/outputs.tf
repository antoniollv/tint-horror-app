output "bucket_name" {
  value       = aws_s3_bucket.app.id
  description = "Name of the S3 bucket."
}

output "website_endpoint" {
  value       = format("%s.s3-website-%s.amazonaws.com", aws_s3_bucket.app.bucket, var.aws_region)
  description = "S3 static website endpoint (constructed from bucket name and region)."
}

output "website_domain" {
  value       = format("%s.s3-website-%s.amazonaws.com", aws_s3_bucket.app.bucket, var.aws_region)
  description = "S3 static website domain (constructed)."
}
