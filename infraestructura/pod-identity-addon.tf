# Pod daemon en el cluster para garantizar permisos a varias acciones como usar LoadBalancers
resource "aws_eks_addon" "pod_identity" {
  cluster_name  = aws_eks_cluster.eks-tf.name
  addon_name    = "eks-pod-identity-agent"
  addon_version = "v1.2.0-eksbuild.1"
}