###################
# Bucket S3 privado
###################

resource "aws_s3_bucket" "db-services-bucket-tf" {
  bucket = "db-services-bucket-tf"
  tags = {
    Name = "db-services-bucket-tf"
  }
}

# Script inicial de la base de datos MongoDB
resource "aws_s3_object" "init-mongodb_fichero" {
  bucket = aws_s3_bucket.db-services-bucket-tf.id
  key    = "init-mongodb.js"
  source = "s3_archivos/init-mongodb.js"
}


# Configmap con las variables que utilizaran los pods del cluster
resource "aws_s3_object" "app-vars-configmap" {
  bucket = aws_s3_bucket.db-services-bucket-tf.id
  key    = "app-vars-configmap.yaml"
  content = templatefile("kubernetes/app-vars-configmap.yaml", {
    postgres_host  = aws_db_instance.rds-sensor-postgres-tf.address
    mongo_host     = aws_instance.ec2-plots-mongo-tf.private_ip
  })
}

