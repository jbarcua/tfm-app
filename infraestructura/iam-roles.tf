###########################################################
# Rol con politica de acceso a S3 para EC2 + Perfil IAM EC2
###########################################################

# Rol para permitir a las EC2 obtener permisos de acceso S3
resource "aws_iam_role" "AmazonS3FullAccess-tf" {
  name = "AmazonS3FullAccess-tf"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        "Effect" : "Allow",
        "Principal" : {
          "Service" : "ec2.amazonaws.com"
        },
        "Action" : "sts:AssumeRole"
      }
    ]
  })
}

# Asignacion de la politica o permiso de acceso a S3 al rol de tipo EC2 creado
resource "aws_iam_role_policy_attachment" "asignacion-politica-AmazonS3FullAccess" {
  role = aws_iam_role.AmazonS3FullAccess-tf.name
  # Politica de AWS existente para acceso completo al servicio S3
  policy_arn = "arn:aws:iam::aws:policy/AmazonS3FullAccess"
}

# Perfil de instancia IAM con el rol creado que se asigna a la EC2 durante su creacion
resource "aws_iam_instance_profile" "perfil-AmazonS3FullAccess-tf" {
  name = "perfil-AmazonS3FullAccess-tf"
  role = aws_iam_role.AmazonS3FullAccess-tf.name
}

#####################################################################
# Rol con politica de permisos para el funcionamiento del cluster EKS
#####################################################################

resource "aws_iam_role" "eks-role-tf" {
  name = "eks-role-tf"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        "Effect" : "Allow",
        "Action" : "sts:AssumeRole",
        "Principal" : {
          "Service" : "eks.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "asignacion-politica-AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.eks-role-tf.name
}

resource "aws_iam_role_policy_attachment" "asignacion-politica-AmazonS3FullAccess-eks" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonS3FullAccess"
  role       = aws_iam_role.eks-role-tf.name
}

####################################################################
# Rol con politica de permisos para los nodos worker del cluster EKS
####################################################################

resource "aws_iam_role" "eks-role-nodes-tf" {
  name = "eks-role-nodes-tf"

  assume_role_policy = jsonencode({
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "ec2.amazonaws.com"
      }
    }]
    Version = "2012-10-17"
  })
}

resource "aws_iam_role_policy_attachment" "asignacion-politica-AmazonEKSWorkerNodePolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
  role       = aws_iam_role.eks-role-nodes-tf.name
}

resource "aws_iam_role_policy_attachment" "asignacion-politica-AmazonEKS_CNI_Policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
  role       = aws_iam_role.eks-role-nodes-tf.name
}

resource "aws_iam_role_policy_attachment" "asignacion-politica-AmazonEC2ContainerRegistryReadOnly" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  role       = aws_iam_role.eks-role-nodes-tf.name
}

################################################################
# Rol con politica de permisos para AWS Load Balancer Controller
################################################################

data "aws_iam_policy_document" "documento-politica-load-balancer-tf" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["pods.eks.amazonaws.com"] # Permisos con EKS Pod Identities
    }

    actions = [
      "sts:AssumeRole",
      "sts:TagSession"
    ]
  }
}

resource "aws_iam_role" "rol-load-balancer-tf" {
  name               = "${aws_eks_cluster.eks-tf.name}-rol-load-balancer-tf"
  assume_role_policy = data.aws_iam_policy_document.documento-politica-load-balancer-tf.json
}

resource "aws_iam_policy" "politica-load-balancer-tf" {
  policy = file("./iam/AWSLoadBalancerController.json")
  name   = "AWSLoadBalancerController"
}

resource "aws_iam_role_policy_attachment" "asignacion-politica-load-balancer-tf" {
  policy_arn = aws_iam_policy.politica-load-balancer-tf.arn
  role       = aws_iam_role.rol-load-balancer-tf.name
}

resource "aws_eks_pod_identity_association" "pod-identity-load-balancer-tf" {
  cluster_name    = aws_eks_cluster.eks-tf.name
  namespace       = "kube-system"
  service_account = "aws-load-balancer-controller" # Cuenta servicio asociada
  role_arn        = aws_iam_role.rol-load-balancer-tf.arn
}


