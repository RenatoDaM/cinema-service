provider "aws" {
  region = "us-east-2"
}

resource "aws_s3_bucket" "cinema_service_movie_images" {
  bucket = "cinema-service-movie-images"

  tags = {
    Name        = "Movie images"
    Environment = "Prod"
  }
}

resource "aws_s3_object" "provision_source_files" {
  bucket = aws_s3_bucket.cinema_service_movie_images.id
  for_each = fileset("movie_images/", "**/*.*")

  key = each.key
  source = "movie_images/${each.value}"
  content_type = each.value
}