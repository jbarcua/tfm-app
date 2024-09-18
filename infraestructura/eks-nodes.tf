resource "aws_eks_node_group" "eks-nodes-tf" {
  cluster_name    = aws_eks_cluster.eks-tf.name
  version         = "1.29"
  node_group_name = "eks-cluster-nodegroup"
  node_role_arn   = aws_iam_role.eks-role-nodes-tf.arn

  subnet_ids = [
    aws_subnet.subred-servicios-1a-tf.id,
    aws_subnet.subred-front-1b-tf.id
  ]

  capacity_type  = "ON_DEMAND"
  instance_types = ["t3.medium"]

  scaling_config {
    desired_size = 1
    max_size     = 3
    min_size     = 1
  }

  update_config {
    max_unavailable = 1
  }

  labels = {
    role = "general"
  }

  depends_on = [
    aws_iam_role_policy_attachment.asignacion-politica-AmazonEKSWorkerNodePolicy,
    aws_iam_role_policy_attachment.asignacion-politica-AmazonEKS_CNI_Policy,
    aws_iam_role_policy_attachment.asignacion-politica-AmazonEC2ContainerRegistryReadOnly,
  ]

  # Allow external changes without Terraform plan difference
  lifecycle {
    ignore_changes = [scaling_config[0].desired_size]
  }
}

