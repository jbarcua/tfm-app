resource "aws_eks_cluster" "eks-tf" {
  name     = "app-cluster-eks"
  version  = "1.29"
  role_arn = aws_iam_role.eks-role-tf.arn

  vpc_config {
    endpoint_private_access = true
    endpoint_public_access  = true

    subnet_ids = [
      aws_subnet.subred-servicios-1a-tf.id,
      aws_subnet.subred-front-1b-tf.id
    ]
  }

  access_config {
    authentication_mode                         = "API"
    bootstrap_cluster_creator_admin_permissions = true
  }

  depends_on = [aws_iam_role_policy_attachment.asignacion-politica-AmazonEKSClusterPolicy]
}

