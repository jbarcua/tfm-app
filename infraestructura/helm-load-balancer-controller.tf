resource "helm_release" "helm-load-balancer-tf" {
  name = "aws-load-balancer-controller"

  repository = "https://aws.github.io/eks-charts"
  chart      = "aws-load-balancer-controller"
  namespace  = "kube-system"
  version    = "1.7.2"

  set {
    name  = "clusterName"
    value = aws_eks_cluster.eks-tf.name
  }

  set {
    name  = "serviceAccount.name"
    value = "aws-load-balancer-controller" # Cuenta servicio asociada
  }

  depends_on = [aws_eks_node_group.eks-nodes-tf]
}

