#######################################
# Subnet publica servicios (us-east-1a)
#######################################

resource "aws_subnet" "subred-servicios-1a-tf" {
  vpc_id            = aws_vpc.vpc-tf.id
  cidr_block        = "10.1.10.0/24"
  availability_zone = "us-east-1a"
  # Instancias dentro de la subred tendran una IP publica
  map_public_ip_on_launch = true
  tags = {
    Name                     = "subred-servicios-1a-tf"
    "kubernetes.io/role/elb" = "1" # EXTERNO
    # Tag de EKS - Identificar subnet para desplegar un ALB interno
    #"kubernetes.io/role/internal-elb" = "1"
  }
}

#########################################################
# Subnet publica con Front-end y NAT Gateway (us-east-1b)
#########################################################

resource "aws_subnet" "subred-front-1b-tf" {
  vpc_id            = aws_vpc.vpc-tf.id
  cidr_block        = "10.1.20.0/24"
  availability_zone = "us-east-1b"
  # Instancias dentro de la subred tendran una IP publica
  map_public_ip_on_launch = true
  tags = {
    Name = "subred-front-1b-tf"
    # Tag de EKS - Identificar subnet para desplegar un ALB externo
    "kubernetes.io/role/elb" = "1"
    # Opcional: tag si se utiliza la subnet por mas de 1 cluster en la cuenta AWS.
    # "kubernetes.io/cluster/<my-cluster>" = "owned/shared"
  }
}