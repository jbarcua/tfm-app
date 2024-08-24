# VPC
resource "aws_vpc" "vpc-tf" {
  cidr_block = "10.1.0.0/16"
  # Habilitar soporte y resolucion de direcciones DNS
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = {
    Name = "vpc-tf"
  }
}

# Internet Gateway
resource "aws_internet_gateway" "igw-tf" {
  vpc_id = aws_vpc.vpc-tf.id
  tags = {
    Name = "igw-tf"
  }
}

# Tabla de rutas con IGW para servicios publicos
resource "aws_route_table" "tabla-rutas-internet-tf" {
  vpc_id = aws_vpc.vpc-tf.id
  # Ruta hacia dentro de la VPC se crea por defecto
  # Ruta hacia internet a traves del igw
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-tf.id
  }
  tags = {
    Name = "tabla-rutas-internet-tf"
  }
}

# Asociacion de la tabla de rutas a Internet a la subnet publica de servicios
resource "aws_route_table_association" "tabla-internet-subred-servicios" {
  subnet_id      = aws_subnet.subred-servicios-1a-tf.id
  route_table_id = aws_route_table.tabla-rutas-internet-tf.id
}
# Asociacion de la tabla de ruta a Internet a la subnet publica con el front
resource "aws_route_table_association" "tabla-internet-subred-front" {
  subnet_id      = aws_subnet.subred-front-1b-tf.id
  route_table_id = aws_route_table.tabla-rutas-internet-tf.id
}

