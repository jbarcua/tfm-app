data "aws_eks_cluster" "eks" {
  name = aws_eks_cluster.eks-tf.name
}

data "aws_eks_cluster_auth" "eks" {
  name = aws_eks_cluster.eks-tf.name
}

provider "helm" {
  repository_config_path = "${path.module}/.helm/repositories.yaml" 
  repository_cache       = "${path.module}/.helm"
  kubernetes {
    host                   = data.aws_eks_cluster.eks.endpoint
    cluster_ca_certificate = base64decode(data.aws_eks_cluster.eks.certificate_authority[0].data)
    token                  = data.aws_eks_cluster_auth.eks.token
  }
}

