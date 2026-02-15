#!/bin/bash

# Script to upload the contents of a local directory to a specific path in an S3 bucket
# Usage: ./upload_to_s3.sh <local_directory> <bucket> <s3_path>

## NOTE: You must have the AWS CLI installed and configured with valid credentials before running this script.
# See: https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html

set -euo pipefail


if [ "$#" -ne 3 ]; then
  echo "Usage: $0 <local_directory> <bucket> <s3_path>"
  exit 1
fi

LOCAL_DIR="$1"
BUCKET="$2"
S3_PATH="$3"

if [ ! -d "$LOCAL_DIR" ]; then
  echo "Directory $LOCAL_DIR does not exist."
  exit 2
fi

echo "Uploading $LOCAL_DIR to s3://$BUCKET/$S3_PATH ..."

aws s3 sync "$LOCAL_DIR" "s3://$BUCKET/$S3_PATH" --delete

echo "Upload completed."
