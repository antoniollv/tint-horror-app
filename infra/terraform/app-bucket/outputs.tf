output "bucket_name" {
  value       = aws_s3_bucket.app.id
  description = "Name of the S3 bucket."
}

output "website_endpoint" {
  value       = aws_s3_bucket.app.website_endpoint
  description = "S3 static website endpoint."
}

output "website_domain" {
  value       = aws_s3_bucket.app.website_domain
  description = "S3 static website domain."
}
